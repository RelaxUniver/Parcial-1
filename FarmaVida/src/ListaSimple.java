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

    // Geonel Sánchez
     public BigDecimal PromedioPrecioPorCategoria(String categoria) {

        if (categoria == null){
            return 0.0;
        }

        Nodo actual = cabeza;
        BigDecimal sumaPrecios = BigDecimal.ZERO;
        int cantidadMedicamentos = 0;

        while (actual != null) {
            if (actual.getDato().getCategoria().equals(categoria)) {
                sumaPrecios = sumaPrecios.add(actual.getDato().getPrecio());
                cantidadMedicamentos++;
            }
            actual = actual.getSiguiente();
        }

        if (cantidadMedicamentos == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal promedio = sumaPrecios.divide(
                BigDecimal.valueOf(cantidadMedicamentos),
                2,
                RoundingMode.HALF_UP
        );

        return promedio;
    }

    /* Merlyn Sosa Borges
    c) ActualizarStock(String codigo, int cantidadAgregada): Busca un medicamento 
    por su código y le suma la cantidad entregada. Devuelve true si existe y false si no.
    */

    // Yaisel Pinos Durán
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

    // Ray Sanjudo Mesa
    public int AplicarAumentoGeneral(double porcentaje) {
        if (!(porcentaje>0) || inicio== null) {
            return 0;
        }

        BigDecimal factor= BigDecimal.ONE.add(BigDecimal.valueOf(porcentaje));

        int modificados= 0;
        Nodo actual= inicio;

        while (actual != null) {
            Medicamento med= actual.getDato();
            med.setPrecio(med.getPrecio().multiply(factor));
            modificados++;
            actual = actual.getSiguiente();
        }

        return modificados;
    }

}
