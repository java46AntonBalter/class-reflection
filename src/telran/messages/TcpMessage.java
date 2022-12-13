package telran.messages;

public class TcpMessage implements Message {
String ipPort; //ipv4:port
	@Override
	public void send(String text) {
		System.out.printf("tcp message '%s' has been sent to ip:port %s\n", text, ipPort );

	}
	public TcpMessage(String ipPort) {
		this.ipPort = ipPort;
	}

}
