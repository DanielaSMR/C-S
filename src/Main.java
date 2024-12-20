public class Main {
    public static void main(String[] args) {
        int port = 12345; 
        String host = "localhost";

        Server2 server = new Server2(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        new Cliente2(host, port);
    }
}
