package iwish.models;

import java.io.Serializable;

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

    public static class Item implements Serializable {
        // lma gena nb3at ben el server wl client object kan by2ol en el serial 3'alat w en fe issue fl security f el 7al mawdo3 el serialVersion 
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
