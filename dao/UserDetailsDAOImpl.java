package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import project.backend.UserDetails;

@Repository
@Transactional
public class UserDetailsDAOImpl implements UserDetailsDAO {
@Autowired
private SessionFactory sessionFactory;

public void registerUser(UserDetails user)
{
	Session session=sessionFactory.getCurrentSession();
	session.save(user);
	/*session.flush();
	session.close();*/
}

public UserDetails validateUsername(String username) 
{
	Session session=sessionFactory.getCurrentSession();
	UserDetails user=(UserDetails)session.get(UserDetails.class, username);
	//session.flush();
	//session.close();
	return user;
}


public UserDetails validateEmail(String email)
{
	Session session=sessionFactory.getCurrentSession();
	Query query=session.createQuery("from User where email=?");
	query.setString(0,email);
	UserDetails user=(UserDetails)query.uniqueResult();
	//session.flush();
	//session.close();
	return user;
}


public UserDetails login(UserDetails user)
{
	Session session=sessionFactory.getCurrentSession();
	Query query=session.createQuery("from User where username = ? and password = ?");
	query.setString(0, user.getUsername());
	query.setString(1, user.getPassword());
	//session.flush();
	//session.close();
	return (UserDetails)query.uniqueResult();
}


public void update(UserDetails user) 
{
	Session session=sessionFactory.getCurrentSession();
	session.update(user);//update the values [online status]
	//session.flush();
	//session.close();
	
}

public UserDetails getUserByUsername(String username)
{
	Session session=sessionFactory.getCurrentSession();
	UserDetails user=(UserDetails)session.get(UserDetails.class, username);
	//session.flush();
	//session.close();
	return user;
}

}
