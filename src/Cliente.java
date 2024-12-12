import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente{
    String host = "";
    int port = 0;

    Socket socket = null;
    InputStreamReader isr = null;
    BufferedReader br = null;

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
            br = new BufferedReader(isr);
            //En caso de que sea solo una linea de mensaje
            String ans = br.readLine();
            System.out.println("CLIENTE: Message received");
            //Recordar cerrar al final
            br.close();
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

