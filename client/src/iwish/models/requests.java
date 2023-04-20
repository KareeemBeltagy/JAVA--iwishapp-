package iwish.models;

import java.io.Serializable;

public class requests {
    public static class GetItemsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
    }

    public static class FindItemsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int userID = -1;
        private String searchQuery;

        //constructor wl user 3amel login 
        public FindItemsRequest(int userID, String searchQuery) {
            this.userID = userID;
            this.searchQuery = searchQuery;
        }
        
        //constructor law 7aga general fl home msh user 3amel login
        public FindItemsRequest(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        public String getSearchQuery() {
            return searchQuery;
        }

        public int getUserID() {
            return userID;
        }
    }


    public static class SendFriendRequestRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int friend_id;

        public SendFriendRequestRequest(int user_id, int friend_id) {
            this.user_id = user_id;
            this.friend_id = friend_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getFriend_id() {
            return friend_id;
        }
    }

    public static class FindPeopleRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int userID = -1;
        private String searchQuery;


        public FindPeopleRequest(int userID, String searchQuery) {
            this.userID = userID;
            this.searchQuery = searchQuery;
        }

        public FindPeopleRequest(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        public String getSearchQuery() {
            return searchQuery;
        }

        public int getUserID() {
            return userID;
        }
    }


    public static class CreateUserRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private String email;
        private String password;

        
    public boolean isValidEmail(String email) {
    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return email.matches(regex);
}
    
    public CreateUserRequest(String name, String email, String password) {
    this.name = name;
    if (isValidEmail(email)) {
        this.email = email;
    } else {
        throw new IllegalArgumentException("Invalid email format.");
    }
    this.password = password;
}


        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class LoginUserRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private String email;
        private String password;

        public LoginUserRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class AddItemToWishlistRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int item_id;

        public AddItemToWishlistRequest(int user_id, int item_id) {
            this.user_id = user_id;
            this.item_id = item_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getItem_id() {
            return item_id;
        }
    }

    public static class RemoveWishlistItemRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int item_id;

        public RemoveWishlistItemRequest(int user_id, int item_id) {
            this.user_id = user_id;
            this.item_id = item_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getItem_id() {
            return item_id;
        }
    }

    public static class GetWishlistItemsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;

        public GetWishlistItemsRequest(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return user_id;
        }
    }

    public static class RemoveFriendRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int friend_id;

        public RemoveFriendRequest(int user_id, int friend_id) {
            this.user_id = user_id;
            this.friend_id = friend_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getFriend_id() {
            return friend_id;
        }
    }


    public static class GetFriendsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;

        public GetFriendsRequest(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return user_id;
        }
    }


    public static class AcceptFriendRequestRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int friend_id;

        public AcceptFriendRequestRequest(int user_id, int friend_id) {
            this.user_id = user_id;
            this.friend_id = friend_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getFriend_id() {
            return friend_id;
        }
    }

    public static class DeclineFriendRequestRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;
        private int friend_id;

        public DeclineFriendRequestRequest(int user_id, int friend_id) {
            this.user_id = user_id;
            this.friend_id = friend_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public int getFriend_id() {
            return friend_id;
        }
    }

    public static class GetFriendRequestsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private int user_id;

        public GetFriendRequestsRequest(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return user_id;
        }
    }

    public static class GetNotificationsRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private final int user_id;

        public GetNotificationsRequest(int user_id) {
            this.user_id = user_id;
        }

        public int getUser_id() {
            return user_id;
        }
    }

    public static class ContributeRequest implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int contributor_id;
        private final int receiver_id;
        private final int item_id;
        private final double amount;


        public ContributeRequest(int contributor_id, int receiver_id, int item_id, double amount) {
            this.contributor_id = contributor_id;
            this.receiver_id = receiver_id;
            this.item_id = item_id;
            this.amount = amount;
        }

        public int getContributor_id() {
            return contributor_id;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public int getItem_id() {
            return item_id;
        }

        public double getAmount() {
            return amount;
        }
    }
}