package dao;

import java.util.List;

import project.backend.Friend;
import project.backend.UserDetails;

public interface FriendDAO {
	List<UserDetails> getListOfSuggestedUsers(String username);

	void addFriendRequest(String username, String toId);

	List<Friend> getPendingRequests(String username);

	void updatePendingRequest(Friend pendingRequest);

	List<Friend> listOfFriends(String username);

}
