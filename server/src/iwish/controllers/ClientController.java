package iwish.controllers;

import iwish.models.responses.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import static iwish.models.objects.*;
import static iwish.models.requests.*;

public class ClientController {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ClientController(Socket s) throws Exception {
        this.socket = s;
        inputStream = new ObjectInputStream(this.socket.getInputStream());
        outputStream = new ObjectOutputStream(this.socket.getOutputStream());
    }

    public void run() {
        try {
            while (SocketController.isRunning()) {
                try {
                    Object request = inputStream.readObject();
                    handleRequest(request);
                } catch (Exception exception) {
                    disconnect();
                    break;
                }
            }
        } catch (Exception exception) {
            try {
                disconnect();
            } catch (Exception exception1) {
                SceneController.showErrorAlert(exception1);
            }
        }
    }

    public void disconnect() throws Exception {
        outputStream.close();
        inputStream.close();
        socket.close();
    }

    private void handleRequest(Object request) throws Exception {
        String requestType = request.getClass().getSimpleName(); // "FindItemsRequest"

        if (requestType.equals("GetItemsRequest")) {
            
            
            List<Item> items = Items.getItems();
            
            GetItemsResponse response;
            response = new GetItemsResponse(true, "Found items", items);
            
            outputStream.writeObject(response);
            outputStream.flush();
            
            
            
        } else if (requestType.equals("FindItemsRequest")) {
            FindItemsRequest req = (FindItemsRequest) request;

            List<Item> items;
            if (req.getUserID() == -1) {
                items = Items.findItems(req.getSearchQuery());
            } else {
                items = Items.findItems(req.getUserID(), req.getSearchQuery());
            }
            
            FindItemsResponse response = new FindItemsResponse(true, "Found items", items);
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("CreateUserRequest")) {
            CreateUserRequest req = (CreateUserRequest) request;
            String name = req.getName();
            String email = req.getEmail();
            String password = req.getPassword();
            CreateUserResponse response = null;
            try {
                User user = Users.createUser(name, email, password);
                response = new CreateUserResponse(true, "User was successfully created", user);
            } catch (RuntimeException e) {
                response = new CreateUserResponse(false, e.getMessage(), null);
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("LoginUserRequest")) {
            LoginUserRequest req = (LoginUserRequest) request;
            String email = req.getEmail();
            String password = req.getPassword();
            LoginUserResponse response = null;
            try {
                User user = Users.loginUser(email, password);
                response = new LoginUserResponse(true, "User was successfully logged in", user);
            } catch (RuntimeException e) {
                response = new LoginUserResponse(false, e.getMessage(), null);
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("AddItemToWishlistRequest")) {
            AddItemToWishlistRequest req = (AddItemToWishlistRequest) request;
            int user_id = req.getUser_id();
            int item_id = req.getItem_id();
            AddItemToWishlistResponse response = null;
            try {
                Wishlists.addItem(user_id, item_id);
                response = new AddItemToWishlistResponse(true, "Added item successfully to the user's wishlist");
            } catch (RuntimeException e) {
                response = new AddItemToWishlistResponse(false, e.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("GetWishlistItemsRequest")) {
            GetWishlistItemsRequest req = (GetWishlistItemsRequest) request;
            List<WishListItem> items = Wishlists.getItems(req.getUser_id());
            GetWishlistItemsResponse response = new GetWishlistItemsResponse(true, "Found items", items);
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("RemoveWishlistItemRequest")) {
            RemoveWishlistItemRequest req = (RemoveWishlistItemRequest) request;
            RemoveWishlistItemResponse response;
            try {
                Wishlists.removeItem(req.getUser_id(), req.getItem_id());
                response = new RemoveWishlistItemResponse(true, "Removed item from wishlist successfully ");
            } catch (Exception exception) {
                response = new RemoveWishlistItemResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("GetFriendsRequest")) {
            GetFriendsRequest req = (GetFriendsRequest) request;
            List<User> friends = Users.getFriends(req.getUser_id());
            GetFriendsResponse response = new GetFriendsResponse(true, "Found friends", friends);
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("RemoveFriendRequest")) {
            RemoveFriendRequest req = (RemoveFriendRequest) request;
            RemoveFriendResponse response;
            try {
                Users.removeFriend(req.getUser_id(), req.getFriend_id());
                response = new RemoveFriendResponse(true, "Removed friend successfully ");
            } catch (Exception exception) {
                response = new RemoveFriendResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("FindPeopleRequest")) {
            FindPeopleRequest req = (FindPeopleRequest) request;
            List<User> people;
            if (req.getUserID() == -1) {
                people = Users.findPeople(req.getSearchQuery());
            } else {
                people = Users.findPeople(req.getUserID(), req.getSearchQuery());
            }
            FindPeopleResponse response = new FindPeopleResponse(true, "Found people", people);
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("SendFriendRequestRequest")) {
            SendFriendRequestRequest req = (SendFriendRequestRequest) request;
            SendFriendRequestResponse response;
            try {
                Users.sendFriendRequest(req.getUser_id(), req.getFriend_id());
                response = new SendFriendRequestResponse(true, "Sent friend request successfully ");
            } catch (Exception exception) {
                response = new SendFriendRequestResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("GetFriendRequestsRequest")) {
            GetFriendRequestsRequest req = (GetFriendRequestsRequest) request;
            List<User> requesters = Users.getFriendRequests(req.getUser_id());
            GetFriendRequestsResponse response = new GetFriendRequestsResponse(true, "Found friend requests", requesters);
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("AcceptFriendRequestRequest")) {
            AcceptFriendRequestRequest req = (AcceptFriendRequestRequest) request;
            AcceptFriendRequestResponse response;
            try {
                Users.acceptFriendRequest(req.getUser_id(), req.getFriend_id());
                response = new AcceptFriendRequestResponse(true, "Friend has been added successfully");
            } catch (Exception exception) {
                response = new AcceptFriendRequestResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("DeclineFriendRequestRequest")) {
            DeclineFriendRequestRequest req = (DeclineFriendRequestRequest) request;
            DeclineFriendRequestResponse response;
            try {
                Users.declineFriendRequest(req.getUser_id(), req.getFriend_id());
                response = new DeclineFriendRequestResponse(true, "Friend request has been declined successfully");
            } catch (Exception exception) {
                response = new DeclineFriendRequestResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("ContributeRequest")) {
            ContributeRequest req = (ContributeRequest) request;
            ContributeResponse response;
            try {
                Wishlists.contribute(req.getContributor_id(), req.getReceiver_id(), req.getItem_id(), req.getAmount());
                response = new ContributeResponse(true, "Contribution was successful");
            } catch (Exception exception) {
                response = new ContributeResponse(false, exception.getMessage());
            }
            outputStream.writeObject(response);
            outputStream.flush();
        } else if (requestType.equals("GetNotificationsRequest")) {
            GetNotificationsRequest req = (GetNotificationsRequest) request;

            List<Notification> notifications = Users.getNotifications(req.getUser_id());
            
            GetNotificationsResponse response = new GetNotificationsResponse(true, "Found notifications", notifications);
            outputStream.writeObject(response);

            outputStream.flush();
        } else {
            outputStream.flush();
            throw new ClassNotFoundException();
        }
    }
}
