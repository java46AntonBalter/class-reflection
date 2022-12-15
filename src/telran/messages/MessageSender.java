package telran.messages;

import java.lang.reflect.*;


import telran.view.*;

public class MessageSender {
	private static final String BASE_PACKAGE = "telran.messages.";
	private static final String SENDER_CLASS_SUFFIX = "Sender";
	
	 void send(InputOutput io) {
		 String messageType = io.readString("Enter message type");
		 Sender sender = getSenderObject(messageType);
		 String text = io.readString("Enter text");
		 sender.send(io, text);
	 }
	protected Sender getSenderObject(String messageType) {
		String className = BASE_PACKAGE + messageType + SENDER_CLASS_SUFFIX;
		
		try {
			Class<Sender> clazz = (Class<Sender>) Class.forName(className);
			Constructor<Sender> constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Wrong message type ");
		} 
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
			
		} 
 
	}
}
