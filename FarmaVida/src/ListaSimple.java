import java.math.BigDecimal;
import java.math.RoundingMode;

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

    public String[] MedicamentosAgotados() {
        Nodo cursor = inicio;
        int contador = 0;
        if (inicio == null) {
            return new String[0];
        }

        while (cursor != null) {
            if (cursor.getDato().getStock() <= 5) {
                contador++;
            }
            cursor = cursor.getSiguiente();
        }
        String[] aux = new String[contador];
        cursor = inicio;
        int posicionAux = 0;
        while (cursor != null) {
            if (cursor.getDato().getStock() <= 5) {
                aux[posicionAux] = cursor.getDato().getNombre();
                posicionAux++;
            }
            cursor = cursor.getSiguiente();
        }

        return aux;
    }

    public BigDecimal PromedioPrecioPorCategoria(String categoria) {

        if (categoria == null) {
            return new BigDecimal("0.0");
        }

        Nodo actual = this.inicio;
        BigDecimal sumaPrecios = new BigDecimal("0.0");
        int cantidadMedicamentos = 0;

        while (actual != null) {
            if (actual.getDato().getCategoria().name().equals(categoria)) {
                sumaPrecios = sumaPrecios.add(actual.getDato().getPrecio());
                cantidadMedicamentos++;
            }
            actual = actual.getSiguiente();
        }

        if (cantidadMedicamentos == 0) {
            return new BigDecimal("0.0");
        }

        BigDecimal promedio = sumaPrecios.divide(BigDecimal.valueOf(cantidadMedicamentos), 2, RoundingMode.HALF_UP);

        return promedio;
    }

    public boolean ActualizarStock(String codigo, int cantidadAgregada) {

        if (codigo == null) {
            System.out.println("El código es null, se rechaza la solicitud");
            return false;
        }

        if (cantidadAgregada < 0) {
            System.out.println("La cantidad es negativa, se rechaza la solicitud");
            return false;
        }

        Nodo actual = inicio;

        while (actual != null) {

            if (actual.getDato().getCodigo().equals(codigo)) {
                long aumento = (long) actual.getDato().getStock() + cantidadAgregada;
                if (aumento > Integer.MAX_VALUE) {
                    System.out.println("El stock excede el máximo permitido, se rechaza la solicitud");
                    return false;
                }
                actual.getDato().setStock((int) aumento);
                return true;
            }

            actual = actual.getSiguiente();
        }

        System.out.println("Medicamento no encontrado");
        return false;
    }

    private void invertir() {

        Nodo actual = this.inicio;
        Nodo anterior = null;

        while (actual != null) {
            Nodo siguiente = actual.getSiguiente();
            actual.setSiguiente(anterior);
            anterior = actual;
            actual = siguiente;
        }
        this.inicio = anterior;

    }

    public BigDecimal[] ListarPreciosInversos() {

        int totalNodos = 0;
        Nodo actual = inicio;

        while (actual != null) {
            totalNodos++;
            actual = actual.getSiguiente();
        }

        invertir();

        BigDecimal[] preciosInvertidos = new BigDecimal[totalNodos];
        int i = 0;
        actual = inicio;

        while (actual != null) {
            preciosInvertidos[i++] = actual.getDato().getPrecio();
            actual = actual.getSiguiente();
        }

        invertir();

        return preciosInvertidos;

    }

    public int AplicarAumentoGeneral(double porcentaje) {

        if (!Double.isFinite(porcentaje)) {
            System.out.println("El porcentaje debe ser un número finito");
            return 0;
        }

        if (porcentaje < 0) {
            System.out.println("El porcentaje no puede ser negativo");
            return 0;
        }

        if (porcentaje == 0.0) {
            System.out.println("No hubo cambios: el porcentaje es 0");
            return 0;
        }

        BigDecimal factor = BigDecimal.ONE.add(BigDecimal.valueOf(porcentaje));

        int modificados = 0;
        Nodo actual = inicio;

        while (actual != null) {

            Medicamento med = actual.getDato();
            med.setPrecio(med.getPrecio().multiply(factor));
            modificados++;
            actual = actual.getSiguiente();

        }

        return modificados;
    }

}
