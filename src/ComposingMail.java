import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ComposingMail for compose-mail page
 */
@WebServlet("/ComposingMail")
public class ComposingMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComposingMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		// get the account and password from the session
		String account = request.getSession().getAttribute("account").toString();
		String password = request.getSession().getAttribute("password").toString();
		
		// create a mail model with the account and password
		MailModel m = new MailModel(account, password);
		
		// get the fields input by the users
		String recipient = request.getParameter("recipient");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		
		// check if the mail can compose
		if(m.compose(recipient, subject, content))
		{
			// if it is composed open a notification page
			RequestDispatcher rd=request.getRequestDispatcher("/sent.jsp");
			rd.forward(request, response);
		}
		else
		{
			// if it fail to compose, add an error text field
			response.setContentType("text/html");  
		    PrintWriter out = response.getWriter();
	    	out.println("Please input valid receiver(s)");   
	        RequestDispatcher rd=request.getRequestDispatcher("/composingMail.jsp");
	        rd.include(request, response); 
	        out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		// button action for going back to the compose page
		if (request.getParameter("backButton") != null) 
		{
			// go back to the compose page
			RequestDispatcher rd = request.getRequestDispatcher("/composingMail.jsp");  
	        rd.forward(request, response); 
		}
		// button action for logging out
		else if (request.getParameter("logOutButton") != null)
		{  
			// go back to the index page
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");  
	        rd.include(request, response); 
	        
	        // log out of the session
	        request.getSession().invalidate();
		}
	}

}
