import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
    private Socket socket = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader bf = null;
    private PrintWriter pw = null;
    private OutputStream os = null;

    public Cliente2(String host, int port) {
        try {
            socket = new Socket(host, port);
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            bf = new BufferedReader(isr);
            os = socket.getOutputStream();
            pw = new PrintWriter(os);

            interactuar();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion();
        }
    }

    private void interactuar() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                // Leer el mensaje del servidor
                String serverMessage = bf.readLine();
                System.out.println(serverMessage);

                // Enviar la respuesta al servidor
                String userInput = scanner.nextLine();
                pw.write(userInput + "\n");
                pw.flush();

                if (serverMessage.contains("Adios! :)")) {
                    System.out.println("CLIENTE: Desconectado");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private void cerrarConexion() {
        try {
            if (pw != null) pw.close();
            if (os != null) os.close();
            if (bf != null) bf.close();
            if (isr != null) isr.close();
            if (is != null) is.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
