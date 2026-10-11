import java.math.BigDecimal;

public class ListaSimple {

    private Nodo inicio;
    private int cantidad;

    public ListaSimple() {
        this.inicio = null;
        this.cantidad = 0;
    }

    public Nodo getInicio() {
        return inicio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /* Jorge Luis Reyes Pérez
    a) MedicamentosAgotados(): Devuelve los nombres de los medicamentos 
    cuyo stock sea menor o igual a 5 unidades.
    */

    /* Geonel Sánchez
    b) PromedioPrecioPorCategoria(String categoria): Devuelve el precio 
    promedio de los medicamentos de la categoría especificada (0.0 si no existen).
    */

    /* Merlyn Sosa Borges
    c) ActualizarStock(String codigo, int cantidadAgregada): Busca un medicamento 
    por su código y le suma la cantidad entregada. Devuelve true si existe y false si no.
    */

    /* Yaisel Pinos Durán
    d) ListarPreciosInversos(): Devuelve la lista de precios en orden inverso al 
    que fueron ingresados.
    */

    private void invertir() {

        Nodo actual = this.inicio;
        Nodo anterior = null;

        while(actual!=null) {
            Nodo siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }
        this.inicio = anterior;
        
    }

    public BigDecimal[] ListarPreciosInversos() {

        invertir();
        BigDecimal[] preciosInvertidos = new BigDecimal[cantidad];
        int i = 0;
        Nodo actual = inicio;

        while(actual != null) {
            preciosInvertidos[i++] = actual.getDato().getPrecio();
            actual = actual.getSiguiente();
        }

        invertir();

        return preciosInvertidos;

    }

    /* Ray Sanjudo Mesa
    e) AplicarAumentoGeneral(double porcentaje): Incrementa el precio de todos los 
    medicamentos en el porcentaje dado y devuelve la cantidad de medicamentos modificados.
    */

}