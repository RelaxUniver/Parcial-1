import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        // a) MedicamentosAgotados()

        {
            Nodo n5 = new Nodo(new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO, 5, new BigDecimal("15.00")), null);
            Nodo n4 = new Nodo(new Medicamento("M04", "Vitamina C", Categoria.VITAMINA, 20, new BigDecimal("5.00")), n5);
            Nodo n3 = new Nodo(new Medicamento("M03", "Ibuprofeno", Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50")), n4);
            Nodo n2 = new Nodo(new Medicamento("M02", "Amoxicilina", Categoria.ANTIBIÓTICO, 3, new BigDecimal("12.00")), n3);
            Nodo n1 = new Nodo(new Medicamento("M01", "Paracetamol", Categoria.ANALGÉSICO, 15, new BigDecimal("2.50")), n2);
            ListaSimple lista = new ListaSimple();
            lista.setInicio(n1);
            lista.setCantidad(5);
            ListaSimple listaVacia = new ListaSimple();

            System.out.println("=== a) MedicamentosAgotados() ===\n");

            System.out.println("--- Caso positivo: algunos medicamentos agotados ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: MedicamentosAgotados()");
            System.out.println("Esperado: [Amoxicilina, Ibuprofeno, Azitromicina]");
            String[] a1 = lista.MedicamentosAgotados();
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + Arrays.toString(a1) + "\n");

            System.out.println("--- Caso negativo: lista vacía ---");
            System.out.println("ANTES  : (lista vacía)");
            System.out.println("Llamada: MedicamentosAgotados()");
            System.out.println("Esperado: []");
            String[] a2 = listaVacia.MedicamentosAgotados();
            System.out.println("DESPUÉS: (lista vacía, sin cambios)");
            System.out.println("Resultado: " + Arrays.toString(a2) + "\n");

            Nodo sn3 = new Nodo(new Medicamento("X03", "MedC", Categoria.VITAMINA, 15, new BigDecimal("3.00")), null);
            Nodo sn2 = new Nodo(new Medicamento("X02", "MedB", Categoria.ANTIBIÓTICO, 20, new BigDecimal("2.00")), sn3);
            Nodo sn1 = new Nodo(new Medicamento("X01", "MedA", Categoria.ANALGÉSICO, 10, new BigDecimal("1.00")), sn2);
            ListaSimple listaSinAgotados = new ListaSimple();
            listaSinAgotados.setInicio(sn1);
            listaSinAgotados.setCantidad(3);

            System.out.println("--- Caso negativo: ningún medicamento con stock <= 5 ---");
            System.out.println("ANTES  : X01(stock=10) → X02(stock=20) → X03(stock=15)");
            System.out.println("Llamada: MedicamentosAgotados()");
            System.out.println("Esperado: []");
            String[] a3 = listaSinAgotados.MedicamentosAgotados();
            System.out.println("DESPUÉS: X01(stock=10) → X02(stock=20) → X03(stock=15) (sin cambios)");
            System.out.println("Resultado: " + Arrays.toString(a3) + "\n");

            Nodo tn3 = new Nodo(new Medicamento("Y03", "MedF", Categoria.VITAMINA, 1, new BigDecimal("3.00")), null);
            Nodo tn2 = new Nodo(new Medicamento("Y02", "MedE", Categoria.VITAMINA, 5, new BigDecimal("2.00")), tn3);
            Nodo tn1 = new Nodo(new Medicamento("Y01", "MedD", Categoria.ANALGÉSICO, 3, new BigDecimal("1.00")), tn2);
            ListaSimple listaTodosAgotados = new ListaSimple();
            listaTodosAgotados.setInicio(tn1);
            listaTodosAgotados.setCantidad(3);

            System.out.println("--- Caso borde: todos los medicamentos agotados ---");
            System.out.println("ANTES  : Y01(stock=3) → Y02(stock=5) → Y03(stock=1)");
            System.out.println("Llamada: MedicamentosAgotados()");
            System.out.println("Esperado: [MedD, MedE, MedF]");
            String[] a4 = listaTodosAgotados.MedicamentosAgotados();
            System.out.println("DESPUÉS: Y01(stock=3) → Y02(stock=5) → Y03(stock=1) (sin cambios)");
            System.out.println("Resultado: " + Arrays.toString(a4) + "\n");
        }

        // b) PromedioPrecioPorCategoria(String categoria)

        {
            Nodo n5 = new Nodo(new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO, 5, new BigDecimal("15.00")), null);
            Nodo n4 = new Nodo(new Medicamento("M04", "Vitamina C", Categoria.VITAMINA, 20, new BigDecimal("5.00")), n5);
            Nodo n3 = new Nodo(new Medicamento("M03", "Ibuprofeno", Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50")), n4);
            Nodo n2 = new Nodo(new Medicamento("M02", "Amoxicilina", Categoria.ANTIBIÓTICO, 3, new BigDecimal("12.00")), n3);
            Nodo n1 = new Nodo(new Medicamento("M01", "Paracetamol", Categoria.ANALGÉSICO, 15, new BigDecimal("2.50")), n2);
            ListaSimple lista = new ListaSimple();
            lista.setInicio(n1);
            lista.setCantidad(5);
            ListaSimple listaVacia = new ListaSimple();

            System.out.println("=== b) PromedioPrecioPorCategoria() ===\n");

            System.out.println("--- Caso positivo: categoría con 2 medicamentos ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANTIBIÓTICO\")");
            System.out.println("Esperado: 13.50");
            BigDecimal b1 = lista.PromedioPrecioPorCategoria("ANTIBIÓTICO");
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + b1 + "\n");

            System.out.println("--- Caso positivo: categoría con 1 medicamento ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANALGÉSICO\")");
            System.out.println("Esperado: 2.50");
            BigDecimal b2 = lista.PromedioPrecioPorCategoria("ANALGÉSICO");
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + b2 + "\n");

            System.out.println("--- Caso negativo: categoría sin medicamentos ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: PromedioPrecioPorCategoria(\"CATEGORIA_INEXISTENTE\")");
            System.out.println("Esperado: 0.0");
            BigDecimal b3 = lista.PromedioPrecioPorCategoria("CATEGORIA_INEXISTENTE");
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + b3 + "\n");

            System.out.println("--- Caso negativo: lista vacía ---");
            System.out.println("ANTES  : (lista vacía)");
            System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANTIBIÓTICO\")");
            System.out.println("Esperado: 0.0");
            BigDecimal b4 = listaVacia.PromedioPrecioPorCategoria("ANTIBIÓTICO");
            System.out.println("DESPUÉS: (lista vacía, sin cambios)");
            System.out.println("Resultado: " + b4 + "\n");

            System.out.println("--- Caso negativo: categoría null ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: PromedioPrecioPorCategoria(null)");
            System.out.println("Esperado: 0.0");
            BigDecimal b5 = lista.PromedioPrecioPorCategoria(null);
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + b5 + "\n");
        }

        // c) ActualizarStock(String codigo, int cantidadAgregada)
    
        {
            Nodo n5 = new Nodo(new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO, 5, new BigDecimal("15.00")), null);
            Nodo n4 = new Nodo(new Medicamento("M04", "Vitamina C", Categoria.VITAMINA, 20, new BigDecimal("5.00")), n5);
            Nodo n3 = new Nodo(new Medicamento("M03", "Ibuprofeno", Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50")), n4);
            Nodo n2 = new Nodo(new Medicamento("M02", "Amoxicilina", Categoria.ANTIBIÓTICO, 3, new BigDecimal("12.00")), n3);
            Nodo n1 = new Nodo(new Medicamento("M01", "Paracetamol", Categoria.ANALGÉSICO, 15, new BigDecimal("2.50")), n2);
            ListaSimple lista = new ListaSimple();
            lista.setInicio(n1);
            lista.setCantidad(5);
            ListaSimple listaVacia = new ListaSimple();

            System.out.println("=== c) ActualizarStock() ===\n");

            System.out.println("--- Caso positivo: código existente (M02, 3 + 10) ---");
            System.out.println("ANTES  : M02 (stock=3)");
            System.out.println("Llamada: ActualizarStock(\"M02\", 10)");
            System.out.println("Esperado: true, stock después = 13");
            boolean c1 = lista.ActualizarStock("M02", 10);
            System.out.println("DESPUÉS: M02 (stock=" + n2.getDato().getStock() + ")");
            System.out.println("Resultado: " + c1 + "\n");

            System.out.println("--- Caso positivo: cantidad cero ---");
            System.out.println("ANTES  : M03 (stock=2)");
            System.out.println("Llamada: ActualizarStock(\"M03\", 0)");
            System.out.println("Esperado: true, stock después = 2");
            boolean c2 = lista.ActualizarStock("M03", 0);
            System.out.println("DESPUÉS: M03 (stock=" + n3.getDato().getStock() + ")");
            System.out.println("Resultado: " + c2 + "\n");

            System.out.println("--- Caso negativo: código inexistente ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: ActualizarStock(\"M99\", 10)");
            System.out.println("Esperado: false");
            boolean c3 = lista.ActualizarStock("M99", 10);
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + c3 + "\n");

            System.out.println("--- Caso negativo: cantidad negativa ---");
            System.out.println("ANTES  : M02 (stock=13)");
            System.out.println("Llamada: ActualizarStock(\"M02\", -5)");
            System.out.println("Esperado: false, stock sin cambios");
            boolean c4 = lista.ActualizarStock("M02", -5);
            System.out.println("DESPUÉS: M02 (stock=" + n2.getDato().getStock() + ")");
            System.out.println("Resultado: " + c4 + "\n");

            System.out.println("--- Caso negativo: código null ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: ActualizarStock(null, 10)");
            System.out.println("Esperado: false");
            boolean c5 = lista.ActualizarStock(null, 10);
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + c5 + "\n");

            System.out.println("--- Caso negativo: lista vacía ---");
            System.out.println("ANTES  : (lista vacía)");
            System.out.println("Llamada: ActualizarStock(\"M01\", 10)");
            System.out.println("Esperado: false");
            boolean c6 = listaVacia.ActualizarStock("M01", 10);
            System.out.println("DESPUÉS: (lista vacía, sin cambios)");
            System.out.println("Resultado: " + c6 + "\n");
        }

        // d) ListarPreciosInversos()

        {
            Nodo n5 = new Nodo(new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO, 5, new BigDecimal("15.00")), null);
            Nodo n4 = new Nodo(new Medicamento("M04", "Vitamina C", Categoria.VITAMINA, 20, new BigDecimal("5.00")), n5);
            Nodo n3 = new Nodo(new Medicamento("M03", "Ibuprofeno", Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50")), n4);
            Nodo n2 = new Nodo(new Medicamento("M02", "Amoxicilina", Categoria.ANTIBIÓTICO, 3, new BigDecimal("12.00")), n3);
            Nodo n1 = new Nodo(new Medicamento("M01", "Paracetamol", Categoria.ANALGÉSICO, 15, new BigDecimal("2.50")), n2);
            ListaSimple lista = new ListaSimple();
            lista.setInicio(n1);
            lista.setCantidad(5);
            ListaSimple listaVacia = new ListaSimple();

            Nodo zn1 = new Nodo(new Medicamento("Z01", "MedG", Categoria.VITAMINA, 5, new BigDecimal("7.25")), null);
            ListaSimple listaUnNodo = new ListaSimple();
            listaUnNodo.setInicio(zn1);
            listaUnNodo.setCantidad(1);

            System.out.println("=== d) ListarPreciosInversos() ===\n");

            System.out.println("--- Caso positivo: lista con 5 medicamentos ---");
            System.out.println("ANTES  : M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00)");
            System.out.println("Llamada: ListarPreciosInversos()");
            System.out.println("Esperado: [15.00, 5.00, 4.50, 12.00, 2.50]");
            BigDecimal[] d1 = lista.ListarPreciosInversos();
            System.out.println("DESPUÉS: M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00) (restaurada)");
            System.out.println("Resultado: " + Arrays.toString(d1) + "\n");

            System.out.println("--- Caso negativo: lista vacía ---");
            System.out.println("ANTES  : (lista vacía)");
            System.out.println("Llamada: ListarPreciosInversos()");
            System.out.println("Esperado: []");
            BigDecimal[] d2 = listaVacia.ListarPreciosInversos();
            System.out.println("DESPUÉS: (lista vacía, sin cambios)");
            System.out.println("Resultado: " + Arrays.toString(d2) + "\n");

            System.out.println("--- Caso borde: lista con un solo nodo ---");
            System.out.println("ANTES  : Z01(7.25)");
            System.out.println("Llamada: ListarPreciosInversos()");
            System.out.println("Esperado: [7.25]");
            BigDecimal[] d3 = listaUnNodo.ListarPreciosInversos();
            System.out.println("DESPUÉS: Z01(7.25) (restaurada)");
            System.out.println("Resultado: " + Arrays.toString(d3) + "\n");
        }

        // e) AplicarAumentoGeneral(double porcentaje)

        {
            Nodo n5 = new Nodo(new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO, 5, new BigDecimal("15.00")), null);
            Nodo n4 = new Nodo(new Medicamento("M04", "Vitamina C", Categoria.VITAMINA, 20, new BigDecimal("5.00")), n5);
            Nodo n3 = new Nodo(new Medicamento("M03", "Ibuprofeno", Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50")), n4);
            Nodo n2 = new Nodo(new Medicamento("M02", "Amoxicilina", Categoria.ANTIBIÓTICO, 3, new BigDecimal("12.00")), n3);
            Nodo n1 = new Nodo(new Medicamento("M01", "Paracetamol", Categoria.ANALGÉSICO, 15, new BigDecimal("2.50")), n2);
            ListaSimple lista = new ListaSimple();
            lista.setInicio(n1);
            lista.setCantidad(5);
            ListaSimple listaVacia = new ListaSimple();

            Nodo zn1 = new Nodo(new Medicamento("Z01", "MedG", Categoria.VITAMINA, 5, new BigDecimal("7.25")), null);
            ListaSimple listaUnNodo = new ListaSimple();
            listaUnNodo.setInicio(zn1);
            listaUnNodo.setCantidad(1);

            System.out.println("=== e) AplicarAumentoGeneral() ===\n");

            System.out.println("--- Caso positivo: aumento del 10% ---");
            System.out.println("ANTES  : M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00)");
            System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
            System.out.println("Esperado: 5");
            int e1 = lista.AplicarAumentoGeneral(0.10);
            System.out.println("DESPUÉS: M01(2.75) → M02(13.20) → M03(4.95) → M04(5.50) → M05(16.50)");
            System.out.println("Resultado: " + e1 + "\n");

            System.out.println("--- Caso negativo: porcentaje negativo ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: AplicarAumentoGeneral(-0.10)");
            System.out.println("Esperado: 0");
            int e2 = lista.AplicarAumentoGeneral(-0.10);
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + e2 + "\n");

            System.out.println("--- Caso negativo: porcentaje cero ---");
            System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
            System.out.println("Llamada: AplicarAumentoGeneral(0.0)");
            System.out.println("Esperado: 0");
            int e3 = lista.AplicarAumentoGeneral(0.0);
            System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
            System.out.println("Resultado: " + e3 + "\n");

            System.out.println("--- Caso negativo: lista vacía ---");
            System.out.println("ANTES  : (lista vacía)");
            System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
            System.out.println("Esperado: 0");
            int e4 = listaVacia.AplicarAumentoGeneral(0.10);
            System.out.println("DESPUÉS: (lista vacía, sin cambios)");
            System.out.println("Resultado: " + e4 + "\n");

            System.out.println("--- Caso borde: lista con un solo nodo ---");
            System.out.println("ANTES  : Z01(7.25)");
            System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
            System.out.println("Esperado: 1");
            int e5 = listaUnNodo.AplicarAumentoGeneral(0.10);
            System.out.println("DESPUÉS: Z01(7.975)");
            System.out.println("Resultado: " + e5 + "\n");
        }
        
    }
}