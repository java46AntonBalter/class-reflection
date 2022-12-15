package telran.messages;

import telran.view.InputOutput;

public interface Sender {
void send(InputOutput io, String text);
}
