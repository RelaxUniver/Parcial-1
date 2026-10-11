public class Nodo {
    
    private Medicamento dato;
    private Nodo siguiente;

    public Nodo(Medicamento dato, Nodo siguiente) {
        this.dato = dato;
        this.siguiente = siguiente;
    }

    public Medicamento getDato() {
        return dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setDato(Medicamento dato) {
        this.dato = dato;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
}
