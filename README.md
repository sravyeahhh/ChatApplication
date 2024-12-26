# Multi-Threaded Chat Server

## About The Project

This project is a multi-threaded chat application that allows users to communicate in real-time. Multiple clients can connect to the server, send messages, and chat with each other. The project demonstrates the use of multi-threading to handle multiple client connections at once.

## Built With

This project was built with:

- Java
- Swing (for GUI)
- Multi-threading

## Getting Started

To run the chat application, follow these steps:

### Installation

1. Clone the repository to your local machine:

    ```bash
    git clone https://github.com/yourusername/GUIChatServer.git
    ```

2. Open the project in your favorite IDE (e.g., NetBeans).
3. Run the `GUIChatServer.java` file to start the server.
4. Run the `GUIChatClient.java` file to start the client. You can run multiple instances of the client to test communication.
5. Clients will be able to send messages to each other via the chat window, and the server will broadcast messages to all connected clients.

### Usage

- The server accepts multiple client connections, and each client has its own GUI window.
- Clients can send and receive messages to/from the server.
- The number of connected clients is displayed on the server GUI.
- The server can send messages to all connected clients.

### Roadmap

The following features and improvements are planned for future releases:

- Implement private messaging between clients.
- Enhance the user interface.
- Add more control features to the server (like disconnecting clients, etc.).

### Contributing

Contributions are welcome! If you have any ideas or find any issues, please open an issue or submit a pull request.

### License

Distributed under the MIT License. See LICENSE for more information.


