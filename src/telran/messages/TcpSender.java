package telran.messages;

import telran.view.InputOutput;

public class TcpSender implements Sender {

	@Override
	public void send(InputOutput io, String text) {
		String ipAddress = io.readPredicate("Enter IP address", text, TcpSender::ipV4Predicate);
		int port = io.readInt("Enter port number", "Wrong port number", 1024, 49151);
		System.out.printf("text '%s' has been sent to %s:%d\n",text, ipAddress, port);

	}
	static boolean ipV4Predicate(String ip) {
		return ip.matches(ipV4RegEx());
	}
	private static String ipV4RegEx() {
		
		return String.format("((%1$s)\\.){3}(%1$s)", ipV4Part());
	}
	private static String ipV4Part() {
		
		return "\\d\\d?|[01]\\d{2}|2[0-4]\\d|25[0-5]";
	}

}
