package dao;

import project.backend.ProfilePicture;

public interface ProfilePictureDAO {
	void saveProfilePicture(ProfilePicture profilePicture);

	ProfilePicture getProfilePicture(String username);
}
