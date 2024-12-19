public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1337;

        Server srv = new Server(port);
        Thread tServer = new Thread(srv);
        tServer.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Cliente c = new Cliente(host, port);

    }
}
