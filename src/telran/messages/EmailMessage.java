package telran.messages;

public class EmailMessage implements Message{
	String emailAddress;

	@Override
	public void send(String text) {
		System.out.printf("email message '%s' has been sent to email address %s\n", text, emailAddress );

	}

	public EmailMessage(String emailAddress) {
		
		this.emailAddress = emailAddress;
	}

}
