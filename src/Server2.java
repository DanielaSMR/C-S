import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server2 implements Runnable {
    private ServerSocket serverSocket = null;
    private List<Ticket> tickets = new ArrayList<>();
    private int port;

    public Server2(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("INFO: Server launching...");
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("OK: Connection");
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to open socket on TCP " + port);
        }
    }

    private void handleClient(Socket clientSocket) throws IOException {
        InputStream is = clientSocket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bf = new BufferedReader(isr);
        OutputStream os = clientSocket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);

        boolean continuar = true;

        while(continuar){
            try {
                String nombre = solicitarDato(pw, bf, "Por favor, introduce tu nombre:");

                if (nombre.length() > 20) {
                    continuar = false;
                    throw new Exception("ERROR: El nombre es demasiado largo. La conexión se cerrará.");
                }

                String problema = solicitarDato(pw, bf, "Por favor, describe tu problema:");
                boolean problemaL = true;
                while(problemaL){
                    if (nombre.length() > 50) {
                        pw.println("ERROR: El problema es demasiado largo. Escribelo de nuevo");
                        problemaL = true;
                        problema = solicitarDato(pw, bf, "Por favor, describe tu problema:");
                    }else{
                        problemaL = false;
                    }
                }

                int prioridad = Integer.parseInt(solicitarDato(pw, bf, "Por favor, indica la prioridad del problema (1-5):"));

                if (prioridad > 5) {
                    prioridad = 5;
                } else if (prioridad < 1) {
                    prioridad = 1;
                }
                
                int estado = Integer.parseInt(solicitarDato(pw, bf, "Por favor, introduce el estado del problema: 1-No solucionado 2-En proceso 3-Solucionado"));

                if (estado > 3 || estado < 1) {
                    continuar = false;
                    throw new Exception("ERROR: Nombre demasiado largo,se cerrara la conexion");
                }

                Ticket ticket = new Ticket(nombre, problema, prioridad, estado);
                tickets.add(ticket);

                pw.write("Ticket creado exitosamente: " + ticket.toString());
                pw.flush();

                String opcion = solicitarDato(pw, bf, "¿Qué le gustaría hacer? 1-Crear otro ticket 2-Mostrar tickets creados 3-Irse");
                if (opcion.equals("2")) {
                    mostrarTickets(pw);
                    continuar = false;
                }else if(opcion.equals("1")){
                    continue;
                } else if (opcion.equals("3")) {
                    continuar = false;
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                continuar = false;
            }
        }
        cerrarConexion(clientSocket, pw, os, bf, isr, is);
    }

    private String solicitarDato(PrintWriter pw, BufferedReader bf, String mensaje) throws IOException {
        pw.write(mensaje + "\n");
        pw.flush();
        return bf.readLine();
    }

    private void mostrarTickets(PrintWriter pw) {
        pw.write("----- Lista de Tickets ----  ");
        pw.flush();
        int contador = 1;
        for (Ticket ticket : tickets) {
            pw.write(contador + "-" + ticket);
            pw.flush();
            contador++;
        }
        pw.write("  ----------------------------");
        pw.flush();
    }

    private void cerrarConexion(Socket clientSocket, PrintWriter pw, OutputStream os, BufferedReader bf, InputStreamReader isr, InputStream is) throws IOException {
        pw.write("Adios! :)");
        pw.flush();
        if (pw != null) pw.close();
        if (os != null) os.close();
        if (bf != null) bf.close();
        if (isr != null) isr.close();
        if (is != null) is.close();
        if (clientSocket != null) clientSocket.close();
    }
}
