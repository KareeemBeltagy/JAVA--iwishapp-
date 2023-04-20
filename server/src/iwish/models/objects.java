package iwish.models;

import iwish.controllers.DatabaseController;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class objects {
    public static class Notification implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final int receiver_id;
        private final String text;

        public Notification(int id, int receiver_id, String text) {
            this.id = id;
            this.receiver_id = receiver_id;
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public String getText() {
            return text;
        }
    }

    public static class User implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String name;
        private final String email;

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

    public static class Users implements Serializable {
        public static List<User> findPeople(int user_id, String searchQuery) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM users WHERE id NOT IN (SELECT friend_id FROM friends WHERE user_id = ? ) AND id NOT IN (SELECT requester_id FROM friend_requests WHERE receiver_id = ?) AND id NOT IN (SELECT receiver_id FROM friend_requests WHERE requester_id = ?) AND id != ? AND name LIKE ?;";

            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setInt(1, user_id);
            statement.setInt(2, user_id);
            statement.setInt(3, user_id);
            statement.setInt(4, user_id);
            statement.setString(5, '%' + searchQuery + '%');

            ResultSet resultSet = statement.executeQuery();

            List<User> people = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                User person = new User(id, name, email);
                if (person != null)
                    people.add(person);
            }

            return people;
        }

        public static List<User> findPeople(String searchQuery) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM users WHERE name LIKE ?;";

            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setString(1, '%' + searchQuery + '%');

            ResultSet resultSet = statement.executeQuery();

            List<User> people = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");

                User person = new User(id, name, email);
                if (person != null)
                    people.add(person);
            }

            return people;
        }


        public static List<User> getFriends(int user_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT friend_id FROM friends WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setInt(1, user_id);

            ResultSet resultSet = statement.executeQuery();

            List<User> friends = new ArrayList<>();
            while (resultSet.next()) {
                int friend_id = resultSet.getInt("friend_id");
                User friend = Users.getUserByID(friend_id);
                if (friend != null)
                    friends.add(friend);
            }

            return friends;
        }

        public static void removeFriend(int user_id, int friend_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "DELETE FROM friends WHERE (user_id = ? AND friend_id = ?) or (user_id = ? AND friend_id = ?) ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.setInt(3, friend_id);
            statement.setInt(4, user_id);
            statement.executeUpdate();
        }

        public static void sendFriendRequest(int user_id, int friend_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO friend_requests (requester_id, receiver_id) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();
        }

        public static List<User> getFriendRequests(int user_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT requester_id FROM friend_requests WHERE receiver_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);

            ResultSet resultSet = statement.executeQuery();

            List<User> requesters = new ArrayList<>();
            while (resultSet.next()) {
                requesters.add(getUserByID(resultSet.getInt("requester_id")));
            }
            return requesters;
        }

        public static User getUserByID(int user_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");

            return new User(id, name, email);
        }

        public static User getUserByEmail(String email) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            return new User(id, name, email);
        }

        public static String getUserPasswordByEmail(String email) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT password FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            return resultSet.getString("password");
        }

        public static User createUser(String name, String email, String password) throws Exception {
            if (getUserByEmail(email) != null)
                throw new RuntimeException("A User with this email address '" + email + "' already exists.");

            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.executeUpdate();

            return getUserByEmail(email);
        }

        public static User loginUser(String email, String password) throws Exception {
            User dbUser = getUserByEmail(email);
            if (dbUser == null) throw new RuntimeException("No user with this email address '" + email + "' exists.");

            String dbPassword = getUserPasswordByEmail(email);
            if (!dbPassword.equals(password)) throw new RuntimeException("Wrong password");
            return dbUser;
        }

        public static void deleteFriendRequests(int user_id, int friend_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "DELETE FROM friend_requests WHERE receiver_id = ? AND requester_id = ? ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();

            sql = "DELETE FROM friend_requests WHERE requester_id = ? AND receiver_id = ? ;";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();

        }

        public static void acceptFriendRequest(int user_id, int friend_id) throws Exception {
            deleteFriendRequests(user_id, friend_id);

            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO friends (user_id, friend_id) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();

            sql = "INSERT INTO friends (friend_id, user_id) VALUES (?, ?);";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, friend_id);
            statement.executeUpdate();
        }

        public static void sendNotification(int user_id, String text) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO notifications (receiver_id, text) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setString(2, text);
            statement.executeUpdate();
        }

        public static void declineFriendRequest(int user_id, int friend_id) throws Exception {
            deleteFriendRequests(user_id, friend_id);
        }

        public static List<Notification> getNotifications(int receiver_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM notifications WHERE receiver_id = ?;";
            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setInt(1, receiver_id);

            ResultSet resultSet = statement.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String text = resultSet.getString("text");

                notifications.add(new Notification(id, receiver_id, text));
            }

            return notifications;
        }
    }

    public static class Item implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String title;
        private final double price;
        private final byte[] image;

        public Item(int id, String title, double price, byte[] image) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public byte[] getImage() {
            return image;
        }
    }

    public static class Wishlists implements Serializable {
        public static boolean checkWishlistStatus(int user_id, int item_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM wishlists WHERE user_id = ? AND item_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, item_id);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }

        public static void addItem(int user_id, int item_id) throws Exception {
            if (checkWishlistStatus(user_id, item_id))
                throw new RuntimeException("Item already exists in your wishlist");

            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO wishlists (user_id, item_id) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, item_id);
            statement.executeUpdate();
        }

        public static void removeItem(int user_id, int item_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "DELETE FROM wishlists WHERE user_id = ? AND item_id = ? ;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setInt(2, item_id);
            statement.executeUpdate();
        }
        public static List<WishListItem> getItems(int user_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT item_id, is_complete FROM wishlists WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setInt(1, user_id);

            ResultSet resultSet = statement.executeQuery();

            List<WishListItem> items = new ArrayList<>();
            while (resultSet.next()) {
                int item_id = resultSet.getInt("item_id");
                Item itemInfo = Items.getItemByID(item_id);
                boolean is_complete = resultSet.getBoolean("is_complete");
                
                WishListItem item = new WishListItem(itemInfo.id,itemInfo.title,itemInfo.price,itemInfo.image, is_complete);
                
                items.add(item);
            }

            return items;
        }

        public static List<User> getContributors(int receiver_id, int item_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT contributor_id, amount FROM contributions WHERE receiver_id = ? AND item_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receiver_id);
            statement.setInt(2, item_id);

            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                int contributor_id = resultSet.getInt("contributor_id");
                users.add(Users.getUserByID(contributor_id));
            }

            return users;
        }

        public static double getLeftContribution(int receiver_id, int item_id) throws Exception {
            Item item = Items.getItemByID(item_id);
            double contributions = 0;

            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT amount FROM contributions WHERE receiver_id = ? AND item_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, receiver_id);
            statement.setInt(2, item_id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                contributions += resultSet.getDouble("amount");
            }

            return item.getPrice() - contributions;
        }

        public static void contribute(int contributor_id, int receiver_id, int item_id, double amount) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO contributions (contributor_id, receiver_id, item_id, amount) VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, contributor_id);
            statement.setInt(2, receiver_id);
            statement.setInt(3, item_id);
            statement.setDouble(4, amount);
            statement.executeUpdate();

            User contributor = Users.getUserByID(contributor_id);
            User receiver = Users.getUserByID(receiver_id);
            Item item = Items.getItemByID(item_id);

            // Notification for getting a contribution
            String message = contributor.getName()+" has contributed "+amount+" toward buying an item '"+item.getTitle()+"' in your wishlist";
            Users.sendNotification(receiver_id, message);

            if (getLeftContribution(receiver_id, item_id) > 0) return;

            sql = "UPDATE wishlists SET is_complete = TRUE WHERE user_id = ? AND item_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, receiver_id);
            statement.setInt(2, item_id);
            statement.executeUpdate();

            List<User> contributors = getContributors(receiver_id, item_id);
            String namesOfContributors = "";
            for (User c : contributors) {
                String name = c.getName();
                namesOfContributors = namesOfContributors + ", " + name;
            }
            // Notification for completion for the user
            message = "Item '"+item.getTitle()+"' price has been fulfilled in "+receiver.getName()+"'s wishlist by "+ namesOfContributors;
            Users.sendNotification(receiver.getId(), message);
            
            // Notification for the completion for the buyers
            for (User con: getContributors(receiver_id, item_id)) {
                message = "Item '"+item.getTitle()+"' price has been fulfilled in "+receiver.getName()+"'s wishlist";
                Users.sendNotification(con.getId(), message);
            }
        }
    }
// Manager 3ashan feha s
    public static class Items implements Serializable {
        public static List<Item> getItems() throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM items;";
            PreparedStatement statement = connection.prepareStatement((sql));

            ResultSet resultSet = statement.executeQuery();
            
            // hna b3mel list of objects ya3ni b3mel kaza object a7othom fl list "list of items"
            
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                //bageb el data ml records bta3t el database w a7otha f variable 
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                byte[] image = resultSet.getBytes("image");
                
                //object mn class item eli howa fo2 5ales mn 3'er s
                Item item = new Item(id, title, price, image);
               items.add(item);
            }
           

            return items;
        }

        public static Item getItemByID(int item_id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM items WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, item_id);

            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) return null;

            int id = resultSet.getInt("id");
            String title = resultSet.getString("title");
            double price = resultSet.getDouble("price");
            byte[] image = resultSet.getBytes("image");

            return new Item(id, title, price, image);
        }

        public static List<Item> findItems(int user_id, String searchQuery) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM items WHERE title LIKE ? AND id NOT IN (SELECT item_id FROM wishlists WHERE user_id = ?);";

            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setString(1, '%' + searchQuery + '%');
            statement.setInt(2, user_id);

            ResultSet resultSet = statement.executeQuery();

            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                byte[] image = resultSet.getBytes("image");

                Item item = new Item(id, title, price, image);
                items.add(item);
            }

            return items;
        }

        public static List<Item> findItems(String searchQuery) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "SELECT * FROM items WHERE title LIKE ?";

            PreparedStatement statement = connection.prepareStatement((sql));
            statement.setString(1, '%' + searchQuery + '%');

            ResultSet resultSet = statement.executeQuery();

            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                byte[] image = resultSet.getBytes("image");

                Item item = new Item(id, title, price, image);
                if (item != null)
                    items.add(item);
            }

            return items;
        }

        public static void addItem(String title, double price, byte[] image) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "INSERT INTO items (title, price, image) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setDouble(2, price);
            statement.setBytes(3, image);
            statement.executeUpdate();
        }

        public static void editItem(int id, String title, double price, byte[] image) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "UPDATE items SET title = ?, price = ?, image = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, title);
            statement.setDouble(2, price);
            statement.setBytes(3, image);
            statement.setInt(4, id);
            statement.executeUpdate();
        }

        public static void deleteItem(int id) throws Exception {
            Connection connection = DatabaseController.getConnection();

            String sql = "DELETE FROM items WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    
     public static class WishListItem implements Serializable {
        // lma gena nb3at ben el server wl client object kan by2ol en el serial 3'alat w en fe issue fl security f el 7al mawdo3 el serialVersion 
        private static final long serialVersionUID = 1L;

        private final int id;
        private final String title;
        private final double price;
        private final byte[] image;
        public final boolean is_complete;

        public WishListItem(int id, String title, double price, byte[] image, boolean is_complete) {
            this.id = id;
            this.title = title;
            this.price = price;
            this.image = image;
            this.is_complete=is_complete;
        }

        
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }

        public byte[] getImage() {
            return image;
        }
        
    }
}
