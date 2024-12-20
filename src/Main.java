public class Main {
    public static void main(String[] args) {
        int port = 12345; // Puerto que usará el servidor y el cliente
        String host = "localhost"; // Dirección del host

        // Iniciar el servidor en un hilo separado
        Server2 server = new Server2(port);
        Thread serverThread = new Thread(server);
        serverThread.start();

        // Iniciar el cliente en el hilo principal
        new Cliente2(host, port);
    }
}
