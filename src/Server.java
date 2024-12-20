import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class Server implements Runnable{
    private ServerSocket server = null;
    private Socket cliente = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader bf = null;
    private PrintWriter pw = null;
    private OutputStream os = null;
    private Semaphore sm = new Semaphore(1);
    private List<Ticket> tickets = new ArrayList<>();
    int port = 0;

    private procesoCliente proCliente = new procesoCliente(port,server, cliente, is, isr, pw, os, tickets);


    public Server(int port){
        this.port = port;
    }

    @Override
    public void run(){
        System.out.println("INFO: Server launching...");
        boolean seguirPrograma = true;
        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("ERROR: Unable to open socket on TCP " + port );
            return;
        }
        while (seguirPrograma) {
            try {
                sm.acquire();
                proCliente.proceso();
            } catch (Exception e) {
                e.printStackTrace();
                cerrarConexion();
            } finally{
                sm.release();
            }
        }
    }

    public void cerrarConexion() {
        try {
            pw.write("Adios! :)");
            pw.flush();
            if (pw != null) pw.close();
            if (os != null) os.close();
            if (bf != null) bf.close();
            if (isr != null) isr.close();
            if (is != null) is.close();
            if (cliente != null) cliente.close();
            if (server != null) server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
