package telran.messages;

public class SmsMessage implements Message {
String phoneNumber;

	@Override
	public void send(String text) {
		System.out.printf("sms message '%s' has been sent to phone %s\n", text, phoneNumber );

	}

	public SmsMessage(String phoneNumber) {
		
		this.phoneNumber = phoneNumber;
	}

}
