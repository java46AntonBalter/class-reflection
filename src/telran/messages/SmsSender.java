package telran.messages;

import telran.view.InputOutput;

public class SmsSender implements Sender {

	@Override
	public void send(InputOutput io, String text) {
		String phoneNumber = io.readPredicate("Enter phone number", "Wrong phone number",
				SmsSender::phoneNumberPredicate);
		System.out.printf("text '%s' has been sent to %s\n", text, phoneNumber);

	}
	static boolean phoneNumberPredicate(String phone) {
		return phone.matches(phoneNumberRegEx());
	}
	private static String phoneNumberRegEx() {
		return "(\\+972\\s*-?|0)(5\\d|7[2-7])(-?\\d){7}";
	}

}
