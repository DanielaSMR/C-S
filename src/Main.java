public class Main {
    public static void main(String[] args) {
        int port = 12345; 
        String host = "localhost";

        Server server = new Server(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        new Cliente(host, port);
    }
}
