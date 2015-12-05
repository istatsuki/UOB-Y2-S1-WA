import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 * The mail model for validating the account and sending message
 * 
 * @author Anh
 */
public class MailModel {
	/**
	 * the account of the EMail address
	 */
	private String account;
	
	/**
	 * the password of the account
	 */
	private String password;
	
	/** 
	 * constructor
	 *
	 * @param account   the account of the user
	 * @param password  the password of the user
	 */
	public MailModel(String account, String password)
	{
		this.account = account;
		this.password = password;
	}
	
	/** 
	 * Check if the account is valid
	 *
	 * @return if the account is valid
	 */
	public boolean validateAccount()
	{
		// set up smtphost
		String smtphost = "smtp.gmail.com";
		
		//  Set all Properties
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");
		
		// Set Property with account and password for authentication  
		props.setProperty("mail.user", account);
		props.setProperty("mail.password", password);

		// Establish a mail session (java.mail.Session) to check if email/pass combination is correct
		Session session = Session.getDefaultInstance(props);

		try {
			//  Send the message by javax.mail.Transport .			
			Transport tr = session.getTransport("smtp");	// Get Transport object from session		
			tr.connect(smtphost, account, password); // We need to connect

			// change page
	    	return true;

		} catch (MessagingException e) {
			// error window pop up if there is something wrong
			return false;
		}
	}

	/** 
	 * Try to send the message
	 *
	 * @param  receiver the receivers
	 * @param  subject  the subject
	 * @param  content  the content
	 * @return if the message is composed and sent
	 */
	public boolean compose(String receiver, String subject, String content)
	{
		// boolean for checking valid mail
		boolean isValidMails = false;
		
		// shell for getting the EMail address
		String email = "";
		
		// set up list for storing the receivers
		ArrayList<String> receiverList = new ArrayList<String>();

		// the mail is valid
		isValidMails = true;
		
		// set up list for storing end locations of the addresses.
		ArrayList<Integer> receivers = new ArrayList<Integer>();
		for(int i = 0; i < receiver.length(); i++)
		{
			if(String.valueOf(receiver.charAt(i)).equals(",") || String.valueOf(receiver.charAt(i)).equals(";"))
			{
				receivers.add(i);
			}
		}
		receivers.add(receiver.length());
		
		// get and validate the first email
		email = receiver.substring(0, receivers.get(0));
	   try {
	      InternetAddress emailAddr = new InternetAddress(email);
	      emailAddr.validate();							      
	      receiverList.add(email);							      
	   } catch (AddressException ex) {
		// TODO Auto-generated catch block
		   // make the mail invalid
			isValidMails = false;
			ex.printStackTrace();
	   }	
		
	   // get and validate the other mails
		for(int i = 0; i < receivers.size() - 1 && isValidMails; i++)
		{
			email = receiver.substring(receivers.get(i) + 1, receivers.get(i+1));

			   try {
			      InternetAddress emailAddr = new InternetAddress(email);
			      emailAddr.validate();							      
			      receiverList.add(email);							      
			   } catch (AddressException ex) {
				// TODO Auto-generated catch block
				   // make the mail invalid
					isValidMails = false;
					ex.printStackTrace();
			   }														  						      
		}
		
		// if the mail is valid
		if(isValidMails)
		{				
			// set up the smtphost
			String smtphost = "smtp.gmail.com";

			// Get system properties
			Properties props = System.getProperties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtphost);
			props.put("mail.smtp.port", "587");

			// Set Property with account and password for authentication  
			props.setProperty("mail.user", account);
			props.setProperty("mail.password", password);

			//Establish a mail session (java.mail.Session)
			Session session = Session.getDefaultInstance(props);

			try {

				// Create a message
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(account));
				
				// add all the receiver EMail address
				for(int i = 0; i < receiverList.size(); i++)
				{
					message.addRecipients(Message.RecipientType.TO,
							InternetAddress.parse(receiverList.get(i)));
				}
				
				// add the subject
				message.setSubject(subject);
				
				// Create the message part
		         BodyPart messageBodyPart = new MimeBodyPart();

		        // Now set the actual message
		        messageBodyPart.setText(content);

		        // Create a multipart message
		        Multipart multipart = new MimeMultipart();

		        // Set text message part
		        multipart.addBodyPart(messageBodyPart);

		        // Send the complete message parts
		        message.setContent(multipart);

				message.saveChanges();

				// Send the message by javax.mail.Transport .			
				Transport tr = session.getTransport("smtp");	// Get Transport object from session		
				tr.connect(smtphost, account, password); // We need to connect
				tr.sendMessage(message, message.getAllRecipients()); // Send message
				
				// the mail sent
				return true;
			} catch (MessagingException e1) {
				// can't send the mail
				return false;
			}
		}
		// the mail is invalid
		else
		{
			return false;
		}
		   
	}
}
