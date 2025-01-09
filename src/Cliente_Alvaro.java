import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente_Alvaro {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream());
                Scanner sc = new Scanner(System.in)) {

            System.out.println("Conectado al servidor.");

            String respuesta;
            // bucle para que el cliente este todo el rato atento a si hay alguna "pregunta"
            while ((respuesta = entrada.readLine()) != null) {
                System.out.println("Servidor: " + respuesta);

                // si la respuesta del servdor acaba en : o ? entonces se entiende que es el
                // turno del cliente de responder
                if (respuesta.endsWith(":") || respuesta.endsWith("?")) {
                    System.out.print("Tu respuesta: ");
                    String input = sc.nextLine();
                    salida.println(input);
                    salida.flush();
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
