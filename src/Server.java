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

class Server implements Runnable{
    private ServerSocket server = null;
    private Socket cliente = null;
    private InputStream is = null;
    private InputStreamReader isr = null;
    private BufferedReader bf = null;
    private PrintWriter pw = null;
    private OutputStream os = null;
    private List<Ticket> tickets = new ArrayList<>();

    int port = 0;

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
                cliente = server.accept();
                System.out.println("OK: Connection");

                is = cliente.getInputStream();
                isr = new InputStreamReader(is);
                bf = new BufferedReader(isr);

                os = cliente.getOutputStream();
                pw = new PrintWriter(os);

                //Leer mensajes
                    try {
                        //Recibir nombre
                        System.out.println("SERVER: Waiting");
                        pw.write("Por favor, introduce tu nombre:\n");
                        pw.flush();
                        System.out.println("SERVER: Nombre pedido...Esperando respuesta");

                        String nombre = bf.readLine();
                        recibirNombre(nombre);
                        System.out.println("SERVER: Message received");

                        //Recibir Problema
                        System.out.println("SERVER: Waiting");
                        pw.write("Por favor, introduce el problema:\n");
                        pw.flush();
                        System.out.println("SERVER: Problema pedido...Esperando respuesta");

                        String problema = bf.readLine();

                        while (problema.length() > 50) {
                            System.out.println("El problema es demasiado largo...\nEscribelo de nuevo");
                            problema = bf.readLine();
                        }
                        System.out.println("SERVER: Message received");
                        recibirProblema(problema);

                        //Recibir Prioridad
                        System.out.println("SERVER: Waiting");
                        pw.write("Por favor, introduce la prioridad del problema(1-5):\n");
                        pw.flush();
                        System.out.println("SERVER: Nombre pedido...Esperando respuesta");

                        String prioridadS = bf.readLine();
                        int numPrioridad = Integer.parseInt(prioridadS);
                        System.out.println("SERVER: Message received");
                        recibirPrioridad(numPrioridad);

                        //Recibir Estado
                        System.out.println("SERVER: Waiting");
                        pw.write("Por favor, introduce el estado del problema:\n 1-No solucionado \n 2-En proceso \n 3-Solucionado \n");
                        pw.flush();
                        System.out.println("SERVER: Nombre pedido...Esperando respuesta");

                        String estadoS = bf.readLine();
                        int numEstado = Integer.parseInt(estadoS);
                        System.out.println("SERVER: Message received");
                        recibirEstado(numEstado);

                        //Crear y guardar en una lista
                        Ticket ticket = new Ticket(nombre, problema, numPrioridad,numEstado);
                        tickets.add(ticket);

                        // Confirmar al usuario
                        pw.write("Ticket creado exitosamente: " + ticket + "\n");
                        pw.flush();

                        //Mostrar Ticket
                        System.out.println(ticket.toString());

                        //Terminando..
                        System.out.println("Que le gustaria hacer? \n 1-Crear otro ticket \n 2-Mostrar tickets creados \n 3-Irse");
                        String finalS = bf.readLine();
                        int numFinal = Integer.parseInt(finalS);
                        if(numFinal == 2){
                            mostrarTickets();
                        }else if(numFinal == 3){
                            cerrarConexion();
                            seguirPrograma = false;
                        }

                        //Cerrar
                        cerrarConexion();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                cerrarConexion();
                return;
            }
            


        }
    }

    public void recibirNombre(String nombre) throws Exception {

        if (nombre.length() > 20) {
            throw new Exception("ERROR: Nombre demasiado largo");
        }else{
            System.out.println("SERVER: Nombre recibido");
        }

        try {
            String answer = "Hola " + nombre + " ! :)\n Vamos a crear tu ticket";
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Mensaje enviado");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir el nombre");
        }
    }

    public void recibirProblema(String problema) throws Exception{
        try {
            String answer = "Problema recibido! Pasemos al siguiente dato";
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Message problem sent");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir el problema");
        }
        
    }

    public void recibirPrioridad(int prioridad) throws Exception{
        if(prioridad > 5){
            prioridad = 5;
        }else if(prioridad < 1){
            prioridad = 1;
        }
        try {
            String answer = "Prioridad recibida! Pasemos al siguiente dato";
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Message priority sent");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir la prioridad");
        }
        
    }

    public void recibirEstado(int estado) throws Exception{
        if(estado > 3 || estado < 1){
            throw new Exception("ERROR: No se selecciono una opción");
        }else{
            System.out.println("SERVER: Estado recibido");
        }
        try {
            String answer = "Estado recibido! Pasemos al final";
            pw.write(answer);
            pw.flush();
            System.out.println("SERVER: Message sent");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: problema al recibir el estado");
        }
        
    }

    public void mostrarTickets() {
        System.out.println( "----- Lista de Tickets -----");
        for (Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        System.out.println("----------------------------");
    }

    private void cerrarConexion() {
        try {
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
