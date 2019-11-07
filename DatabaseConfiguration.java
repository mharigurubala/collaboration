package project.backend;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import dao.BlogPostDAO;
import dao.BlogPostDAOImpl;

import dao.FriendDAO;
import dao.FriendDAOImpl;
import dao.JobDAO;
import dao.JobDAOImpl;
import dao.ProfilePictureDAO;
import dao.ProfilePictureDAOImpl;
import dao.UserDetailsDAO;
import dao.UserDetailsDAOImpl;

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration {

    public DataSource getOracleDatasource()
    {
    	
    	DriverManagerDataSource dt=new DriverManagerDataSource();
    	dt.setDriverClassName("oracle.jdbc.driver.OracleDriver");
    	dt.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
    	dt.setUsername("hr");
    	dt.setPassword("oracle");
		return dt;
    }
    
        
    @Autowired
    @Bean
    public SessionFactory getSessionFactory() {	
    	Properties pro=new Properties();
    	pro.put("hibernate.hbm2ddl.auto","update");
    	pro.put("hibernate.dialect","org.hibernate.dialect.Oracle10gDialect");
    	LocalSessionFactoryBuilder builder=new LocalSessionFactoryBuilder(getOracleDatasource());
    	builder.addProperties(pro);
    	builder.addAnnotatedClass(UserDetails.class);
    	builder.addAnnotatedClass(BlogPost.class);
    	builder.addAnnotatedClass(Job.class);
    	builder.addAnnotatedClass(Friend.class);
    	builder.addAnnotatedClass(ProfilePicture.class);
    	SessionFactory session=builder.buildSessionFactory();
    	return session;
    }
    
    @Autowired
    @Bean("txManager")
    public HibernateTransactionManager getHibernateTransctionManager() {
    	HibernateTransactionManager tx=new HibernateTransactionManager(getSessionFactory());
    	return tx;
    }
	 @Autowired
	 @Bean("userdetailsDAO")
		public UserDetailsDAO getUserDetailsDAO()
		{
			UserDetailsDAOImpl a=new UserDetailsDAOImpl();
			return a;
		}
	 
	 @Autowired
	 @Bean("blogpostDAO")
		public BlogPostDAO getBlogDAO()
		{
			BlogPostDAOImpl ba=new BlogPostDAOImpl();
			return ba;
		}
	 
	 @Autowired
	 @Bean("friendDAO")
		public FriendDAO getFriendDAO()
		{
			FriendDAOImpl ba=new FriendDAOImpl();
			return ba;
		}
	 @Autowired
	 @Bean("jobDAO")
		public JobDAO getJobDAO()
		{
			JobDAOImpl ba=new JobDAOImpl();
			return ba;
		}
	 @Autowired
	 @Bean("profilepictureDAO")
		public ProfilePictureDAO getProfilePictureDAO()
		{
		 ProfilePictureDAOImpl ba=new ProfilePictureDAOImpl();
			return ba;
		}

}
