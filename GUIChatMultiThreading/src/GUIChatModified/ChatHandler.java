package GUIChatModified;

import java.io.*;

import java.net.*;

public class ChatHandler implements Runnable {
    private Socket connection;
    private DataInputStream input;
    private DataOutputStream output;
    private GUIChatServer server;
    private String clientName;

    public ChatHandler(Socket connection, GUIChatServer server) {
        this.connection = connection;
        this.server = server;
        try {
            this.input = new DataInputStream(connection.getInputStream());
            this.output = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // Receive client name (optional)
            clientName = input.readUTF();
            server.updateChatOutput(clientName + " connected.");
            
            // Listen for incoming messages from client
            String message;
            while ((message = input.readUTF()) != null) {
                String messageToBroadcast = clientName + ": " + message;
                server.updateChatOutput(messageToBroadcast); // Display on server
                broadcastMessage(messageToBroadcast); // Send to all clients
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send message to the client
    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast message to all other clients
    private void broadcastMessage(String message) {
        for (ChatHandler client : server.clients) {
            if (client != this) {  // Don't send message back to the sender
                client.sendMessage(message);
            }
        }
    }
}
