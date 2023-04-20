package iwish.models;

import iwish.models.objects.Item;
import iwish.models.objects.Notification;

import java.io.Serializable;
import java.util.List;

import static iwish.models.objects.User;
import iwish.models.objects.WishListItem;

public class responses {
    public static class GetItemsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<Item> items;

        public GetItemsResponse(boolean isSuccess, String message, List<Item> items) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.items = items;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class FindItemsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        // haro7 3la objects hashof item 3ashan el response ma3mol f shakl object wl object da shayl el information 
        private final List<Item> items;

        
        public FindItemsResponse(boolean isSuccess, String message, List<Item> items) {
            this.isSuccess = isSuccess;
            this.message = message; //dialogue
            this.items = items; //data
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<Item> getItems() {
            return items;
        }
    }

    public static class CreateUserResponse implements Serializable {
        private static final long serialVersionUID = 1L;


        private final boolean isSuccess;
        private final String message;
        private final User user;

        public CreateUserResponse(boolean isSuccess, String message, User user) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public User getUser() {
            return user;
        }
    }

    public static class LoginUserResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final User user;

        public LoginUserResponse(boolean isSuccess, String message, User user) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.user = user;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public User getUser() {
            return user;
        }
    }


    public static class AddItemToWishlistResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public AddItemToWishlistResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class RemoveWishlistItemResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public RemoveWishlistItemResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class GetWishlistItemsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<WishListItem> items;

        public GetWishlistItemsResponse(boolean isSuccess, String message, List<WishListItem> items) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.items = items;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<WishListItem> getItems() {
            return items;
        }
    }

    public static class GetFriendsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<User> friends;

        public GetFriendsResponse(boolean isSuccess, String message, List<User> friends) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.friends = friends;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<User> getFriends() {
            return friends;
        }
    }

    public static class RemoveFriendResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public RemoveFriendResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class  SendFriendRequestResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public SendFriendRequestResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }


    public static class FindPeopleResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<User> people;

        public FindPeopleResponse(boolean isSuccess, String message, List<User> people) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.people = people;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<User> getPeople() {
            return people;
        }
    }

    public static class GetFriendRequestsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<User> requesters;

        public GetFriendRequestsResponse(boolean isSuccess, String message, List<User> requesters) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.requesters = requesters;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<User> getRequesters() {
            return requesters;
        }
    }

    public static class  AcceptFriendRequestResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public AcceptFriendRequestResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class DeclineFriendRequestResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public DeclineFriendRequestResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class GetNotificationsResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;
        private final List<Notification> notifications;

        public GetNotificationsResponse(boolean isSuccess, String message, List<Notification> notifications) {
            this.isSuccess = isSuccess;
            this.message = message;
            this.notifications = notifications;
        }


        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }

        public List<Notification> getNotifications() {
            return notifications;
        }
    }

    public static class ContributeResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private final boolean isSuccess;
        private final String message;

        public ContributeResponse(boolean isSuccess, String message) {
            this.isSuccess = isSuccess;
            this.message = message;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

        public String getMessage() {
            return message;
        }
    }
}
