package middleware;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dao.UserDetailsDAO;
import project.backend.UserDetails;

@RestController
public class UserDetailsController {

	@Autowired
	private UserDetailsDAO userdetailsDAO;
	//? - Any Type
    @RequestMapping(value="/registeruser",method=RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody UserDetails user){
    	try{
    	UserDetails duplicateUser	=userdetailsDAO.validateUsername(user.getUsername());
    	if(duplicateUser!=null){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(2,"Username already exists.. please enter different username");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
    	UserDetails duplicateEmail=userdetailsDAO.validateEmail(user.getEmail());
    	if(duplicateEmail!=null){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(3,"Email address already exists.. please enter different email address");
    		return new ResponseEntity<Error>(error,HttpStatus.NOT_ACCEPTABLE);
    	}
		userdetailsDAO.registerUser(user);
		//response.data is user
		//response.status is 200 OK
		return new ResponseEntity<UserDetails>(user,HttpStatus.OK);
    	}catch(Exception e){
    		//response.data is error
    		//response.status is 406 NOT_ACCEPTABLE
    		Error error=new Error(1,"Unable to register user details " + e.getMessage());
    		return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    	}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	//HttpRequest Body : {username:"adam" , password:"123"}
	public ResponseEntity<?> login(@RequestBody UserDetails user, HttpSession session)
	{
		UserDetails validuser = userdetailsDAO.login(user);
		if (validuser==null)//invalid credentials
		{
			Error error = new Error (4, "Invalid username/password..please enter valid username/password");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		validuser.setOnline(true);
		userdetailsDAO.update(validuser);//update only the online status from 0 to 1
		session.setAttribute("username", validuser.getUsername());
		//HttpResponse Body;
		// {username :"adam" , password:"123", firstname="Adam", lastname="Eve", email :"adam@gmail.com", online_status :"true"
		return new ResponseEntity<UserDetails>(validuser, HttpStatus.OK);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		String username = (String)session.getAttribute("username");
		UserDetails user = userdetailsDAO.getUserByUsername(username);
		user.setOnline(false);
		userdetailsDAO.update(user);
		session.removeAttribute("username");
        session.invalidate();
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getuser",method=RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		String username=(String)session.getAttribute("username");
		UserDetails user=userdetailsDAO.getUserByUsername(username);
		return new ResponseEntity<UserDetails>(user,HttpStatus.OK);
		
	}
	/*
	 * Error, 401 - UNAUTHORISED ACCESS
	 * Error, 500 - EXCEPTION
	 * void, 200 - SUCCESSFUL UPDATE
	 */
	
	@RequestMapping(value="/updateuser",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody UserDetails user, HttpSession session)
	{
		if (session.getAttribute("username")==null)
		{
			Error error = new Error(5,"UnAuthorised User");
			return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
		}
		
		try{
			userdetailsDAO.update(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}catch(Exception e){
			Error error = new Error(6,"Unable to edit user profile"+ e.getMessage());
			return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
