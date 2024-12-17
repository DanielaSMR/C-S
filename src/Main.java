public class Main {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1337;

        Cliente c = new Cliente(host, port);

        if(!c.connect()){
            System.out.println("ERROR: Can't connect to the server");
            return;
        }
        c.send("Hola mundo");
        String ans = c.receive();
        System.out.println(ans);

        if(!c.connect()){
            System.out.println("ERROR: Can't connect to the server");
            return;
        }
        c.send("Adios mundo");
        ans = c.receive();
        System.out.println(ans);

        Server srv = new Server(port);
        Thread tServer = new Thread(srv);
        tServer.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
