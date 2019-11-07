package dao;

import project.backend.UserDetails;

public interface UserDetailsDAO {

	void registerUser(UserDetails user);
	UserDetails validateUsername(String username);
	UserDetails validateEmail(String email);
	UserDetails login(UserDetails user);
	void update(UserDetails user);
	UserDetails getUserByUsername(String username);
}
