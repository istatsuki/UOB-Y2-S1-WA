import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoggingIn for log-in page
 */
@WebServlet("/LoggingIn")
public class LoggingIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoggingIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get the account and password input by the user
		String account = request.getParameter("GMail Address");  
	    String password = request.getParameter("password");
	    
	    request.setAttribute("account", account);
	    
	    // create a mail model with the account and password
	    MailModel m = new MailModel(account, password);
	    
	    // check if the fields are filled
	    if(account != null && password != null)
	    {
	    	// check if the combination is valid
		    if(m.validateAccount())
		    {
		    	// create a new session for the user
		    	HttpSession session = request.getSession(true);		    
		    	
		    	// Get last access time of this web page.
			    Date lastAccessTime = new Date(session.getLastAccessedTime());
			    
			    // initiate visit count
			    Integer visitCount = new Integer(0);
			    
			    // initiate userIDKey for session
			    String userIDKey = new String("userID");
			    String userID = new String(account);
			    
			    // check if session is new
			    if (session.getAttribute("loginTime") == null)
			    {	
			    	 // set the userIDKey userID
			         session.setAttribute(userIDKey, userID);
			         
			         // set the session time out of 5 minutes
				     session.setMaxInactiveInterval(300);
			    }
			    else 
			    {
			    	 // increase the visit count
			         visitCount = (Integer)session.getAttribute("loginTime");
			         visitCount = visitCount + 1;
			    }
			    session.setAttribute("loginTime",  visitCount);
		    	
		    	// set attributes for the session ( the account and password)
		    	session.setAttribute("account", account);
		    	session.setAttribute("password", password);
		    	session.setAttribute("lastLoginDate", lastAccessTime);
		    	
		    	// open the compose page
		    	RequestDispatcher rd = request.getRequestDispatcher("/composingMail.jsp");  
		        rd.forward(request, response);  
		    }
		    else
		    {
		    	// if invalid combination, ask the user to put other combinations
			    response.setContentType("text/html");  
			    PrintWriter out = response.getWriter();
		    	out.println("EMail Address or Password Error! Please enter again!");   
		        RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
		        rd.include(request, response);
		        out.close();
		    }
	    }
	    else
	    {
	    	// if the field(s) is(are) empty, ask user to fill in
		    response.setContentType("text/html");  
		    PrintWriter out = response.getWriter();
	    	out.println("EMail Address or Password Error! Please enter again!");   
	        RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
	        rd.include(request, response);  
	        out.close();
	    }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
