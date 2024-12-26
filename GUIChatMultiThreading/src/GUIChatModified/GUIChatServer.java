package GUIChatModified;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class GUIChatServer extends JFrame {
    private JTextArea chatInput;
    private JTextArea chatOutput;
    private JButton chatSend;
    private JPanel chatInputContainer;

    private static ServerSocket server;
    private static ExecutorService clientExecutor = Executors.newFixedThreadPool(10);
    static List<ChatHandler> clients = new ArrayList<>();

    public GUIChatServer() {
        super("Chat Server");

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

        chatSend.addActionListener(e -> sendData(chatInput.getText()));

        setVisible(true);
        startServer();
    }

    private void startServer() {
        try {
            server = new ServerSocket(12345);
            System.out.println("Server is listening on port 12345");

            while (true) {
                Socket connection = server.accept();
                System.out.println("Client connected!");

                // Create a new ChatHandler for each client and assign it to a thread
                ChatHandler chatHandler = new ChatHandler(connection, this);
                clients.add(chatHandler);
                clientExecutor.execute(chatHandler);

                // Update the server display with the number of clients
                updateChatOutput("Clients connected: " + clients.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all clients
    private void sendData(String message) {
        for (ChatHandler client : clients) {
            client.sendMessage("Server: " + message);
        }
        updateChatOutput("Server: " + message);
    }

    // Update the chat output JTextArea safely on the Event Dispatch Thread
    public void updateChatOutput(String message) {
        SwingUtilities.invokeLater(() -> {
            chatOutput.append(message + "\n");
            chatOutput.setCaretPosition(chatOutput.getDocument().getLength());  // Scroll to the bottom
        });
    }

    public static void main(String[] args) {
        new GUIChatServer();
    }
}
