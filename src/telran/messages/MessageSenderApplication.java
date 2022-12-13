package telran.messages;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import telran.view.*;

public class MessageSenderApplication {
	private static final String BASE_PACKAGE = "telran.messages.";
	private static final String[] messageTypesArray = { "EmailMessage", "SmsMessage", "TcpMessage" };

	public static void main(String[] args) throws Exception {
		InputOutput io = new ConsoleInputOutput();
		Item[] items = getMenuItems();
		ArrayList<Item> menuItems = new ArrayList<>(Arrays.asList(items));
		Menu menu = new Menu("Message Sender", menuItems);
		menu.perform(io);
		System.out.println("See you next time! Bye!");
	}

	public static Item[] getMenuItems() {
		Item[] res = { Item.of("Send Message", t -> {
			try {
				sendMessage(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}), Item.of("Exit", io -> {
		}, true) };
		return res;

	}

	static void sendMessage(InputOutput io) throws Exception{
		String messageDetails[] = setArguments(io);
		@SuppressWarnings("unchecked")
		Class<Message> clazz = (Class<Message>) Class.forName(BASE_PACKAGE + messageDetails[0]);
		Constructor<Message> constructor = clazz.getConstructor(String.class);
		Message message = constructor.newInstance(messageDetails[2]);
		message.send(messageDetails[1]);
	}

	private static String[] setArguments(InputOutput io) {
		String res[] = new String[3];
		setMessageType(io, res);
		setMessageText(io, res);
		setRecepient(io, res);
		return res;
	}

	private static void setRecepient(InputOutput io, String[] res) {
		boolean correctRecepient = false;
		do {
			res[2] = io.readString("enter recepient: ");
			correctRecepient = checkRecepient(res);
			if (!correctRecepient) {
				System.out.print("\n" + res[2] + " is not a valid recepient. please enter correct recepient.");
			}
		} while (!correctRecepient);
	}

	private static boolean checkRecepient(String[] res) {
		String regexPattern;
		if (res[0].equals("EmailMessage")) {
			regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
			return matchInputWithPattern(res[2], regexPattern);
		} else if (res[0].equals("SmsMessage")) {
			regexPattern = "^\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}$";
			return matchInputWithPattern(res[2], regexPattern);
		}
		try {
			URI uri = new URI("my://" + res[2]);
			regexPattern = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";
			if (uri.getHost() == null || !matchInputWithPattern(uri.getHost(), regexPattern) || uri.getPort() < 1024 || uri.getPort() > 49151) {
				throw new URISyntaxException(uri.toString(), "recepient must have IPv4 and port parts");
			}

			return true;

		} catch (URISyntaxException ex) {
			System.out.println(ex.getMessage());
			return false;
		}

	}

	private static boolean matchInputWithPattern(String input, String regexPattern) {
		return Pattern.compile(regexPattern).matcher(input).matches();
	}

	private static void setMessageText(InputOutput io, String[] res) {
		res[1] = io.readString("enter message text");
	}

	private static void setMessageType(InputOutput io, String[] res) {
		Set<String> messageTypes = new HashSet<String>(Arrays.stream(messageTypesArray).collect(Collectors.toSet()));
		boolean correctType = false;
		do {
			System.out.println("available message types are:");
			Arrays.stream(messageTypesArray).forEach(m -> System.out.print(m + ", "));
			res[0] = io.readString("enter message type: ");
			correctType = checkMessageType(res[0], messageTypes);
			if (!correctType) {
				System.out.println("wrong message type. please enter correct option.");
			}
		} while (!correctType);
	}

	private static boolean checkMessageType(String string, Set<String> messageTypes) {
		return messageTypes.contains(string);
	}
}
