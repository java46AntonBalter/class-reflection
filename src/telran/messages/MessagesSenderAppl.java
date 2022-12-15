package telran.messages;

import telran.view.*;

public class MessagesSenderAppl {

	public static void main(String[] args) {
	InputOutput io = new ConsoleInputOutput();
		Item [] items = getItems();
		Menu menu = new Menu("Sending Messages", items);
		menu.perform(io);

	}

	private static Item[] getItems() {
		MessageSender messageSender = new MessageSender();
		Item [] items = {
				Item.of("send message", messageSender::send),
				Item.exit()
		};
		return items;
	}

}
