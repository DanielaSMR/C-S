public class Ticket {
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";    // RED
    public static final String GREEN = "\033[0;32m";  // GREEN
    public static final String YELLOW = "\033[0;33m"; // YELLOW

    private String nombre;
    private String problema;
    private int prioridad;
    private int estado;

    public Ticket(String nombre, String problema, int prioridad,int estado) {
        this.nombre = nombre;
        this.problema = problema;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    @Override
    public String toString() {
        String color = "";
        String estadoS = "";
        if(estado == 1){
            color = RED;
            estadoS = "No solucionado";
        }else if(estado == 2){
            color = YELLOW;
            estadoS = "En proceso";
        }else if(estado == 3){
            color = GREEN;
            estadoS = "Solucionado";
        }
        return "Ticket{" + color + "-" + RESET + problema + " Usuario: " + nombre + " Prioridad: " + prioridad + " Estado: " + color + estadoS + RESET +'}';
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    
}
