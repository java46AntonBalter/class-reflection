package telran.messages;

import java.lang.reflect.Constructor;

public class MessageSenderApplication {
private static final String BASE_PACKAGE = "telran.messages.";

@SuppressWarnings("unchecked")
public static void main(String[] args) throws Exception{
	if (args.length < 2) {
		System.out.println("too few arguments; usage - first argument"
				+ " is message type second - address");
		
	} else {
		Class<Message> clazz = (Class<Message>) Class.forName(BASE_PACKAGE + args[0]);
		Constructor<Message> constructor = clazz.getConstructor(String.class);
		Message message = constructor.newInstance(args[1]);
		message.send("Hello World!");
	}
}
}
