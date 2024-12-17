import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente{
    String host = "localhost";
    int port = 0;
    PrintWriter pw = null;
    OutputStream os = null;
    Socket socket = null;
    InputStreamReader isr = null;
    BufferedReader bf = null;

    final String errorMSG = "CLIENT ERROR";

    public Cliente(String host,int port){
        this.host = host;
        this.port = port;
    }

    //Para conectar con el servidor
    //Devuelve un boolean que nos indicara si esta conectado o no
    public boolean connect(){
        try {
            socket = new Socket(host,port);
            System.out.println("CLIENT: Connected");
            return true;
        } catch (Exception e) {
            System.out.println("CLIENT: Connection rejected");
            return false;
        }
    }

    public String receive(){

        try {
            isr = new InputStreamReader(socket.getInputStream());
            bf = new BufferedReader(isr);
            //En caso de que sea solo una linea de mensaje
            String ans = bf.readLine();
            System.out.println("CLIENTE: Message received");
            //Recordar cerrar al final
            bf.close();
            isr.close();
            return ans;
        } catch (Exception e) {
            e.printStackTrace();
            return errorMSG;
        }
        
    }

    public boolean send(String message){
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(message);
            pw.flush();
            System.out.println("CLIENT: Message sent");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

