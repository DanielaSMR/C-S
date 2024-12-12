import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server implements Runnable{
    private ServerSocket server = null;
    private Socket cliente = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader bf = null;
    private PrintWriter pw = null;
    private OutputStream os = null;
    int port = 0;

    public Server(int port){
        this.port = port;
    }

    @Override
    public void run(){
        

        System.out.println("INFO: Server launching...");

        try {
            server = new ServerSocket(port);
        } catch (Exception e) {
            System.out.println("ERROR: Unable to open socket on TCP " + port );
            return;
        }
        while (true) {
            try {
                cliente = server.accept();
                System.out.println("OK: Connection");

                is = cliente.getInputStream();
                isr = new InputStreamReader(is);
                bf = new BufferedReader(isr);

                //Leer mensajes
                System.out.println("SERVER: Waiting");
                String nombre = bf.readLine();
                System.out.println("SERVER: Message received");
                recibirNombre(nombre);

                String problema = bf.readLine();

               

                //Cerrar
                pw.close();
                os.close();
                bf.close();
                isr.close();
                is.close();
                cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            


        }
    }

    public void recibirNombre(String nombre){
        try {
            String answer = "Hola " + nombre + " ! :)\n Vamos a crear tu ticket";
            os = cliente.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Message name sent");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir el nombre");
        }
        
    }

    public void recibirProblema(String problema){
        if(problema.length() > 50){
            
        }
        try {
            String answer = "Hola " + nombre + " ! :)\n Vamos a crear tu ticket";
            os = cliente.getOutputStream();
            pw = new PrintWriter(os);
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Message name sent");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir el nombre");
        }
        
    }

}
