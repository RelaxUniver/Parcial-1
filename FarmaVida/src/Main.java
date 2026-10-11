import java.math.BigDecimal;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        
        Medicamento m01 = new Medicamento("M01", "Paracetamol",  Categoria.ANALGÉSICO,      15, new BigDecimal("2.50"));
        Medicamento m02 = new Medicamento("M02", "Amoxicilina",  Categoria.ANTIBIÓTICO,      3, new BigDecimal("12.00"));
        Medicamento m03 = new Medicamento("M03", "Ibuprofeno",   Categoria.ANTIINFLAMATORIO, 2, new BigDecimal("4.50"));
        Medicamento m04 = new Medicamento("M04", "Vitamina C",   Categoria.VITAMINA,        20, new BigDecimal("5.00"));
        Medicamento m05 = new Medicamento("M05", "Azitromicina", Categoria.ANTIBIÓTICO,      5, new BigDecimal("15.00"));

        ListaSimple lista = new ListaSimple();
        Nodo n5 = new Nodo(m05, null);
        Nodo n4 = new Nodo(m04, n5);
        Nodo n3 = new Nodo(m03, n4);
        Nodo n2 = new Nodo(m02, n3);
        Nodo n1 = new Nodo(m01, n2);
        lista.setInicio(n1);
        lista.setCantidad(5);

        ListaSimple listaVacia = new ListaSimple();

        // a) MedicamentosAgotados()
        /*
        System.out.println("=== a) MedicamentosAgotados() ===");
        System.out.println();

        // --- Caso positivo 1: lista con 5 medicamentos, algunos agotados ---
        System.out.println("--- Caso positivo: algunos medicamentos agotados ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: MedicamentosAgotados()");
        System.out.println("Esperado: [Amoxicilina, Ibuprofeno, Azitromicina]");
        String[] agotados1 = lista.MedicamentosAgotados();
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + Arrays.toString(agotados1));
        System.out.println();

        // --- Caso negativo 1: lista vacía ---
        System.out.println("--- Caso negativo: lista vacía ---");
        System.out.println("ANTES  : (lista vacía)");
        System.out.println("Llamada: MedicamentosAgotados()");
        System.out.println("Esperado: []");
        String[] agotados2 = listaVacia.MedicamentosAgotados();
        System.out.println("DESPUÉS: (lista vacía, sin cambios)");
        System.out.println("Resultado: " + Arrays.toString(agotados2));
        System.out.println();

        // --- Caso negativo 2: ningún medicamento cumple la condición ---
        Medicamento x01 = new Medicamento("X01", "MedA", Categoria.ANALGÉSICO,      10, new BigDecimal("1.00"));
        Medicamento x02 = new Medicamento("X02", "MedB", Categoria.ANTIBIÓTICO,      20, new BigDecimal("2.00"));
        Medicamento x03 = new Medicamento("X03", "MedC", Categoria.VITAMINA,         15, new BigDecimal("3.00"));
        Nodo xn3 = new Nodo(x03, null);
        Nodo xn2 = new Nodo(x02, xn3);
        Nodo xn1 = new Nodo(x01, xn2);
        ListaSimple listaSinAgotados = new ListaSimple();
        listaSinAgotados.setInicio(xn1);
        listaSinAgotados.setCantidad(3);

        System.out.println("--- Caso negativo: ningún medicamento tiene stock <= 5 ---");
        System.out.println("ANTES  : X01(stock=10) → X02(stock=20) → X03(stock=15)");
        System.out.println("Llamada: MedicamentosAgotados()");
        System.out.println("Esperado: []");
        String[] agotados3 = listaSinAgotados.MedicamentosAgotados();
        System.out.println("DESPUÉS: X01(stock=10) → X02(stock=20) → X03(stock=15) (sin cambios)");
        System.out.println("Resultado: " + Arrays.toString(agotados3));
        System.out.println();

        // --- Caso positivo 2: todos cumplen la condición ---
        Medicamento y01 = new Medicamento("Y01", "MedD", Categoria.ANALGÉSICO, 3, new BigDecimal("1.00"));
        Medicamento y02 = new Medicamento("Y02", "MedE", Categoria.VITAMINA,    5, new BigDecimal("2.00"));
        Medicamento y03 = new Medicamento("Y03", "MedF", Categoria.VITAMINA,    1, new BigDecimal("3.00"));
        Nodo yn3 = new Nodo(y03, null);
        Nodo yn2 = new Nodo(y02, yn3);
        Nodo yn1 = new Nodo(y01, yn2);
        ListaSimple listaTodosAgotados = new ListaSimple();
        listaTodosAgotados.setInicio(yn1);
        listaTodosAgotados.setCantidad(3);

        System.out.println("--- Caso positivo: todos los medicamentos agotados ---");
        System.out.println("ANTES  : Y01(stock=3) → Y02(stock=5) → Y03(stock=1)");
        System.out.println("Llamada: MedicamentosAgotados()");
        System.out.println("Esperado: [MedD, MedE, MedF]");
        String[] agotados4 = listaTodosAgotados.MedicamentosAgotados();
        System.out.println("DESPUÉS: Y01(stock=3) → Y02(stock=5) → Y03(stock=1) (sin cambios)");
        System.out.println("Resultado: " + Arrays.toString(agotados4));
        System.out.println();
        */

        // b) PromedioPrecioPorCategoria(String categoria)
        /*
        System.out.println("=== b) PromedioPrecioPorCategoria() ===");
        System.out.println();

        // --- Caso positivo 1: categoría con varios medicamentos ---
        System.out.println("--- Caso positivo: categoría ANTIBIÓTICO (2 medicamentos) ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANTIBIÓTICO\")");
        System.out.println("Esperado: 13.50");
        BigDecimal prom1 = lista.PromedioPrecioPorCategoria("ANTIBIÓTICO");
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + prom1);
        System.out.println();

        // --- Caso positivo 2: categoría con un solo medicamento ---
        System.out.println("--- Caso positivo: categoría ANALGÉSICO (1 medicamento) ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANALGÉSICO\")");
        System.out.println("Esperado: 2.50");
        BigDecimal prom2 = lista.PromedioPrecioPorCategoria("ANALGÉSICO");
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + prom2);
        System.out.println();

        // --- Caso negativo 1: categoría sin medicamentos ---
        System.out.println("--- Caso negativo: categoría sin medicamentos ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: PromedioPrecioPorCategoria(\"CATEGORIA_INEXISTENTE\")");
        System.out.println("Esperado: 0.0");
        BigDecimal prom3 = lista.PromedioPrecioPorCategoria("CATEGORIA_INEXISTENTE");
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + prom3);
        System.out.println();

        // --- Caso negativo 2: lista vacía ---
        System.out.println("--- Caso negativo: lista vacía ---");
        System.out.println("ANTES  : (lista vacía)");
        System.out.println("Llamada: PromedioPrecioPorCategoria(\"ANTIBIÓTICO\")");
        System.out.println("Esperado: 0.0");
        BigDecimal prom4 = listaVacia.PromedioPrecioPorCategoria("ANTIBIÓTICO");
        System.out.println("DESPUÉS: (lista vacía, sin cambios)");
        System.out.println("Resultado: " + prom4);
        System.out.println();

        // --- Caso negativo 3: categoría null ---
        System.out.println("--- Caso negativo: categoría null ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: PromedioPrecioPorCategoria(null)");
        System.out.println("Esperado: 0.0");
        BigDecimal prom5 = lista.PromedioPrecioPorCategoria(null);
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + prom5);
        System.out.println();
        */

        // c) ActualizarStock(String codigo, int cantidadAgregada)
        /*
        System.out.println("=== c) ActualizarStock() ===");
        System.out.println();

        // --- Caso positivo 1: código existente ---
        System.out.println("--- Caso positivo: código existente (M02, stock 3 + 10) ---");
        System.out.println("ANTES  : M02 (stock=3)");
        System.out.println("Llamada: ActualizarStock(\"M02\", 10)");
        System.out.println("Esperado: true, stock después = 13");
        boolean act1 = lista.ActualizarStock("M02", 10);
        System.out.println("DESPUÉS: M02 (stock=13)");
        System.out.println("Resultado: " + act1 + ", stock actual = " + n2.getDato().getStock());
        System.out.println();

        // --- Caso positivo 2: cantidad cero (operación válida sin efecto) ---
        System.out.println("--- Caso positivo: cantidad cero ---");
        System.out.println("ANTES  : M03 (stock=2)");
        System.out.println("Llamada: ActualizarStock(\"M03\", 0)");
        System.out.println("Esperado: true, stock después = 2");
        boolean act2 = lista.ActualizarStock("M03", 0);
        System.out.println("DESPUÉS: M03 (stock=2)");
        System.out.println("Resultado: " + act2 + ", stock actual = " + n3.getDato().getStock());
        System.out.println();

        // --- Caso negativo 1: código inexistente ---
        System.out.println("--- Caso negativo: código inexistente ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: ActualizarStock(\"M99\", 10)");
        System.out.println("Esperado: false");
        boolean act3 = lista.ActualizarStock("M99", 10);
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + act3);
        System.out.println();

        // --- Caso negativo 2: cantidad negativa ---
        System.out.println("--- Caso negativo: cantidad negativa ---");
        System.out.println("ANTES  : M02 (stock=13)");
        System.out.println("Llamada: ActualizarStock(\"M02\", -5)");
        System.out.println("Esperado: false, stock sin cambios");
        boolean act4 = lista.ActualizarStock("M02", -5);
        System.out.println("DESPUÉS: M02 (stock=13, sin cambios)");
        System.out.println("Resultado: " + act4 + ", stock actual = " + n2.getDato().getStock());
        System.out.println();

        // --- Caso negativo 3: código null ---
        System.out.println("--- Caso negativo: código null ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: ActualizarStock(null, 10)");
        System.out.println("Esperado: false");
        boolean act5 = lista.ActualizarStock(null, 10);
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + act5);
        System.out.println();

        // --- Caso negativo 4: lista vacía ---
        System.out.println("--- Caso negativo: lista vacía ---");
        System.out.println("ANTES  : (lista vacía)");
        System.out.println("Llamada: ActualizarStock(\"M01\", 10)");
        System.out.println("Esperado: false");
        boolean act6 = listaVacia.ActualizarStock("M01", 10);
        System.out.println("DESPUÉS: (lista vacía, sin cambios)");
        System.out.println("Resultado: " + act6);
        System.out.println();
        */

        // d) ListarPreciosInversos()
        System.out.println("=== d) ListarPreciosInversos() ===");
        System.out.println();

        System.out.println("--- Caso positivo: lista con 5 medicamentos ---");
        System.out.println("ANTES  : M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00)");
        System.out.println("Llamada: ListarPreciosInversos()");
        System.out.println("Esperado: [15.00, 5.00, 4.50, 12.00, 2.50]");
        BigDecimal[] inv1 = lista.ListarPreciosInversos();
        System.out.println("DESPUÉS: M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00) (restaurada)");
        System.out.println("Resultado: " + Arrays.toString(inv1));
        System.out.println();

        System.out.println("--- Caso negativo: lista vacía ---");
        System.out.println("ANTES  : (lista vacía)");
        System.out.println("Llamada: ListarPreciosInversos()");
        System.out.println("Esperado: []");
        BigDecimal[] inv2 = listaVacia.ListarPreciosInversos();
        System.out.println("DESPUÉS: (lista vacía, sin cambios)");
        System.out.println("Resultado: " + Arrays.toString(inv2));
        System.out.println();

        Medicamento z01 = new Medicamento("Z01", "MedG", Categoria.VITAMINA, 5, new BigDecimal("7.25"));
        Nodo zn1 = new Nodo(z01, null);
        ListaSimple listaUnNodo = new ListaSimple();
        listaUnNodo.setInicio(zn1);
        listaUnNodo.setCantidad(1);

        System.out.println("--- Caso borde: lista con un solo nodo ---");
        System.out.println("ANTES  : Z01(7.25)");
        System.out.println("Llamada: ListarPreciosInversos()");
        System.out.println("Esperado: [7.25]");
        BigDecimal[] inv3 = listaUnNodo.ListarPreciosInversos();
        System.out.println("DESPUÉS: Z01(7.25) (restaurada)");
        System.out.println("Resultado: " + Arrays.toString(inv3));
        System.out.println();

        // e) AplicarAumentoGeneral(double porcentaje)
        /*
        System.out.println("=== e) AplicarAumentoGeneral() ===");
        System.out.println();

        // --- Caso positivo 1: aumento del 10% ---
        System.out.println("--- Caso positivo: aumento del 10% ---");
        System.out.println("ANTES  : M01(2.50) → M02(12.00) → M03(4.50) → M04(5.00) → M05(15.00)");
        System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
        System.out.println("Esperado: 5 (todos modificados)");
        int mod1 = lista.AplicarAumentoGeneral(0.10);
        System.out.println("DESPUÉS: M01(2.75) → M02(13.20) → M03(4.95) → M04(5.50) → M05(16.50)");
        System.out.println("Resultado: " + mod1);
        System.out.println();

        // --- Caso negativo 1: porcentaje negativo ---
        System.out.println("--- Caso negativo: porcentaje negativo ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: AplicarAumentoGeneral(-0.10)");
        System.out.println("Esperado: 0");
        int mod2 = lista.AplicarAumentoGeneral(-0.10);
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + mod2);
        System.out.println();

        // --- Caso negativo 2: porcentaje cero ---
        System.out.println("--- Caso negativo: porcentaje cero ---");
        System.out.println("ANTES  : M01 → M02 → M03 → M04 → M05");
        System.out.println("Llamada: AplicarAumentoGeneral(0.0)");
        System.out.println("Esperado: 0");
        int mod3 = lista.AplicarAumentoGeneral(0.0);
        System.out.println("DESPUÉS: M01 → M02 → M03 → M04 → M05 (sin cambios)");
        System.out.println("Resultado: " + mod3);
        System.out.println();

        // --- Caso negativo 3: lista vacía ---
        System.out.println("--- Caso negativo: lista vacía ---");
        System.out.println("ANTES  : (lista vacía)");
        System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
        System.out.println("Esperado: 0");
        int mod4 = listaVacia.AplicarAumentoGeneral(0.10);
        System.out.println("DESPUÉS: (lista vacía, sin cambios)");
        System.out.println("Resultado: " + mod4);
        System.out.println();

        // --- Caso positivo 2: lista con un solo nodo ---
        System.out.println("--- Caso borde: lista con un solo nodo ---");
        System.out.println("ANTES  : Z01(7.25)");
        System.out.println("Llamada: AplicarAumentoGeneral(0.10)");
        System.out.println("Esperado: 1");
        int mod5 = listaUnNodo.AplicarAumentoGeneral(0.10);
        System.out.println("DESPUÉS: Z01(7.975)");
        System.out.println("Resultado: " + mod5);
        System.out.println();
        */
    }
}