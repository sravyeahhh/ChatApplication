import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GUIChatClient extends JFrame {
    private JTextArea chatInput;
    private JTextArea chatOutput;
    private JButton chatSend;
    private JPanel chatInputContainer;
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;

    public GUIChatClient() {
        super("Chat Client");

        setSize(500, 500);
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        chatOutput = new JTextArea();
        chatInput = new JTextArea(3, 20);
        chatSend = new JButton("Send");
        chatInputContainer = new JPanel();

        add(new JScrollPane(chatOutput), BorderLayout.CENTER);
        chatInputContainer.add(chatInput);
        chatInputContainer.add(chatSend);
        add(chatInputContainer, BorderLayout.SOUTH);

        chatSend.addActionListener(e -> sendMessage(chatInput.getText()));

        setVisible(true);
        connectToServer();
    }

    private void connectToServer() {
        try {
            // Connect to the server at localhost (or replace with server IP address)
            connection = new Socket("127.0.0.1", 12345); 
            input = new DataInputStream(connection.getInputStream());
            output = new DataOutputStream(connection.getOutputStream());

            // Send client name to the server
            output.writeUTF("Client1");

            // Start the listener thread to handle incoming messages
            new Thread(new ChatListener(input)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            output.writeUTF(message);
            chatInput.setText(""); // Clear the input field after sending
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ChatListener implements Runnable {
        private DataInputStream input;

        public ChatListener(DataInputStream input) {
            this.input = input;
        }

        @Override
        public void run() {
            try {
                String message;
                while (true) {
                    message = input.readUTF();  // Listen for messages from the server
                    chatOutput.append(message + "\n");  // Display it in the chat output area
                    chatOutput.setCaretPosition(chatOutput.getDocument().getLength());  // Scroll to the bottom
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new GUIChatClient();
    }
}
