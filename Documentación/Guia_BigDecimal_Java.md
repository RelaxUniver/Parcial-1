# Guía de uso de `BigDecimal` en Java

> **Objetivo:** aprender a representar números decimales, realizar cálculos, controlar la escala y aplicar reglas de redondeo explícitas con `BigDecimal`.
>
> **Paquete:** `java.math`  
> **Idea clave:** mantén el valor como `BigDecimal` durante los cálculos y decide el redondeo según las reglas del problema, no por costumbre.

## 1. ¿Qué es `BigDecimal`?

`BigDecimal` es una clase de Java que representa números decimales con precisión arbitraria limitada por los recursos disponibles. Además, permite controlar explícitamente cuándo y cómo redondear resultados.

Se utiliza cuando la representación decimal y las reglas de redondeo son importantes, por ejemplo en importes monetarios, facturación, impuestos, tasas, porcentajes y cálculos contables.

No significa que `double` sea incorrecto. `double` suele ser adecuado para cálculos científicos, gráficos y otros usos donde la coma flotante binaria es conveniente. `BigDecimal` es preferible cuando se necesitan reglas decimales explícitas y resultados reproducibles conforme a esas reglas.

### Por qué `double` puede producir resultados inesperados

Los tipos `float` y `double` representan números en coma flotante binaria. Muchos decimales finitos en base 10 no tienen una representación finita exacta en base 2.

```java
double a = 0.1;
double b = 0.2;
System.out.println(a + b); // normalmente imprime 0.30000000000000004
```

El resultado no es un fallo de Java: es una consecuencia de la representación binaria de coma flotante.

Con `BigDecimal`, los valores decimales escritos como texto se pueden representar como se espera:

```java
import java.math.BigDecimal;

BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");
System.out.println(a.add(b)); // 0.3
```

## 2. ¿Cómo representa internamente un valor?

Conceptualmente, un `BigDecimal` combina un **entero sin escala** (*unscaled value*) y una **escala** (*scale*). La escala indica cuántas posiciones decimales se consideran a la derecha del separador cuando es positiva.

Por ejemplo, `123.45` puede representarse mediante el entero sin escala `12345` y una escala de `2`.

```java
BigDecimal valor = new BigDecimal("123.450");

System.out.println(valor.unscaledValue()); // 123450
System.out.println(valor.scale());         // 3
System.out.println(valor.precision());     // 6
```

Tres conceptos que no deben confundirse:

- **Valor numérico:** la cantidad matemática representada.
- **Escala (`scale`)**: cuántas cifras decimales representa el objeto según su escala.
- **Precisión (`precision`)**: cantidad de dígitos del entero sin escala, según la definición de `BigDecimal`.

Por eso `12.5`, `12.50` y `12.500` pueden representar el mismo valor numérico y mantener escalas distintas.

## 3. Crear objetos `BigDecimal`

### 3.1. Recomendado: construir desde `String`

Cuando un decimal procede de un formulario, un archivo, una configuración, JSON o una cantidad escrita en el código, la opción más clara suele ser construirlo desde una cadena.

```java
BigDecimal precio = new BigDecimal("19.99");
BigDecimal tasa = new BigDecimal("0.075");
BigDecimal cantidad = new BigDecimal("12");
```

La cadena permite conservar exactamente el decimal que se ha escrito. El separador decimal debe ser un punto, no una coma.

### 3.2. Usar `BigDecimal.valueOf(double)` cuando la fuente ya es `double`

```java
BigDecimal valor = BigDecimal.valueOf(0.1);
System.out.println(valor); // 0.1
```

`valueOf(double)` utiliza la representación decimal canónica del `double`; suele ser preferible al constructor directo con `double`. Sin embargo, no puede reconstruir una intención decimal original que ya se haya perdido durante cálculos anteriores en coma flotante. Si la fuente original es texto o una columna decimal, conserva esa fuente y crea el `BigDecimal` directamente desde ella.

### 3.3. Evitar `new BigDecimal(double)` para decimales escritos

```java
BigDecimal problemático = new BigDecimal(0.1);
System.out.println(problemático); // muestra la aproximación binaria completa
```

Este constructor representa exactamente el valor binario que contiene el `double`; ese valor no es exactamente el decimal `0.1` que normalmente se pretendía indicar.

### 3.4. Constantes habituales

```java
BigDecimal cero = BigDecimal.ZERO;
BigDecimal uno = BigDecimal.ONE;
BigDecimal diez = BigDecimal.TEN;
```

Estas constantes resultan útiles para valores comunes. Para otros valores, usa una cadena o `valueOf`, según el origen del dato.

### 3.5. Validar texto externo

El constructor lanza `NumberFormatException` si el texto no es un decimal válido. Valida los datos en la frontera de entrada y comunica el error de forma adecuada; no conviertas silenciosamente una entrada incorrecta en cero.

```java
String texto = "12.50";

try {
    BigDecimal valor = new BigDecimal(texto);
    System.out.println(valor);
} catch (NumberFormatException ex) {
    System.out.println("El texto no es un decimal válido");
}
```

## 4. Inmutabilidad: cada operación devuelve un resultado

`BigDecimal` es **inmutable**: sus métodos no modifican el objeto original, sino que devuelven otro objeto con el resultado. Si necesitas conservar el nuevo resultado en una variable, debes asignarlo.

```java
BigDecimal importe = new BigDecimal("12.345");

importe.setScale(2, java.math.RoundingMode.HALF_UP); // el resultado se descarta
importe = importe.setScale(2, java.math.RoundingMode.HALF_UP);

System.out.println(importe); // 12.35
```

Este comportamiento también se aplica a `add`, `subtract`, `multiply`, `divide` y otros métodos.

## 5. Operaciones aritméticas

No se usan los operadores aritméticos `+`, `-`, `*` o `/` directamente entre objetos `BigDecimal`. Se emplean métodos que devuelven el resultado.

```java
import java.math.BigDecimal;

BigDecimal a = new BigDecimal("10.50");
BigDecimal b = new BigDecimal("2.25");

BigDecimal suma = a.add(b);          // 12.75
BigDecimal resta = a.subtract(b);    // 8.25
BigDecimal producto = a.multiply(b); // 23.6250
```

Observa que el producto puede conservar una escala distinta de la que se suele mostrar en una moneda. **El valor numérico, la escala interna y el formato de presentación son conceptos relacionados, pero no idénticos.**

### 5.1. División: hay que definir qué hacer con los decimales

Una división puede producir un decimal periódico o una expansión infinita, como `1 / 3`. Si la división no tiene una representación decimal exacta y llamas a `divide(divisor)` sin definir una precisión o un redondeo, se puede lanzar `ArithmeticException`.

```java
import java.math.BigDecimal;
import java.math.RoundingMode;

BigDecimal uno = new BigDecimal("1");
BigDecimal tres = new BigDecimal("3");

BigDecimal resultado = uno.divide(tres, 2, RoundingMode.HALF_UP);
System.out.println(resultado); // 0.33
```

También se puede definir una precisión significativa mediante `MathContext` cuando ese sea el requisito. La elección entre una escala fija y una cantidad de cifras significativas depende del cálculo.

### 5.2. Ejemplo: precio unitario por cantidad

```java
BigDecimal precio = new BigDecimal("7.25");
BigDecimal cantidad = new BigDecimal("4");

BigDecimal subtotal = precio.multiply(cantidad);
System.out.println(subtotal); // 29.00
```

No asumas que toda multiplicación monetaria debe redondearse inmediatamente. La política debe indicar si se redondea cada línea, cada impuesto o solamente el total; distintas políticas pueden producir resultados finales diferentes.

## 6. Escala, precisión y redondeo

### 6.1. Métodos básicos

| Método | Qué devuelve | Ejemplo conceptual |
|---|---|---|
| `scale()` | Escala actual | `new BigDecimal("12.340").scale()` devuelve `3` |
| `precision()` | Número de dígitos de precisión | `new BigDecimal("12.340").precision()` devuelve `5` |
| `setScale(n, modo)` | Valor con la escala solicitada y regla de redondeo | `12.345` a dos decimales con `HALF_UP` da `12.35` |
| `stripTrailingZeros()` | Valor equivalente sin ceros finales innecesarios, cuando procede | `12.340` pasa a una representación numéricamente equivalente a `12.34` |

`setScale` puede añadir ceros sin cambiar el valor numérico, o necesitar redondear si hay que descartar dígitos. Si se intenta reducir la escala sin indicar un modo de redondeo y es necesario descartar cifras no nulas, puede lanzar `ArithmeticException`.

### 6.2. `RoundingMode`

La regla de redondeo no es universal: depende del contrato, las reglas del negocio, el estándar contable y la normativa que se aplique.

| Modo | Comportamiento resumido |
|---|---|
| `UP` | Se aleja de cero cuando hay que descartar cifras. |
| `DOWN` | Se acerca a cero; equivale a descartar las cifras sobrantes. |
| `CEILING` | Redondea hacia el infinito positivo. |
| `FLOOR` | Redondea hacia el infinito negativo. |
| `HALF_UP` | Redondea hacia el más cercano; los empates se alejan de cero. |
| `HALF_DOWN` | Redondea hacia el más cercano; los empates se acercan a cero. |
| `HALF_EVEN` | Redondea hacia el más cercano; los empates van al último dígito par. |
| `UNNECESSARY` | Exige que no se necesite redondeo; si hace falta, lanza una excepción. |

Ejemplo:

```java
import java.math.BigDecimal;
import java.math.RoundingMode;

BigDecimal monto = new BigDecimal("8.765");

System.out.println(monto.setScale(2, RoundingMode.HALF_UP));   // 8.77
System.out.println(monto.setScale(2, RoundingMode.HALF_EVEN)); // 8.76
```

El resultado puede variar según el modo elegido. No selecciones uno únicamente porque sea el más conocido: documenta la regla que exige el problema.

## 7. Comparar objetos: `equals()` y `compareTo()`

Esta distinción es especialmente importante con `BigDecimal`:

- `equals()` considera tanto el valor como la escala.
- `compareTo()` compara el valor numérico y trata como equivalentes los valores que solo difieren por escala.

```java
BigDecimal x = new BigDecimal("5.0");
BigDecimal y = new BigDecimal("5.00");

System.out.println(x.equals(y));        // false
System.out.println(x.compareTo(y) == 0); // true
```

Para comprobar igualdad numérica, normalmente se usa `compareTo(...) == 0`. Para comprobar igualdad del objeto según el contrato de `equals`, la escala también cuenta. Esta diferencia importa, además, al usar `BigDecimal` como clave en estructuras basadas en `equals` y `hashCode`.

## 8. Mostrar y formatear valores

`toString()` puede usar notación científica según el valor y su escala. `toPlainString()` genera una representación sin notación exponencial.

```java
BigDecimal numero = new BigDecimal("1234.50");
System.out.println(numero.toString());      // representación textual del valor
System.out.println(numero.toPlainString()); // 1234.50
```

Ninguno de esos métodos aplica automáticamente el formato monetario de un país. Para mostrar separadores, símbolos monetarios y reglas regionales, usa una herramienta de formato, como `NumberFormat` o `DecimalFormat`, según las necesidades de la aplicación.

La presentación debe ser una etapa separada del cálculo: conserva el `BigDecimal` para seguir operando y produce texto formateado al mostrar el resultado.

## 9. Ejemplo integrado: subtotal, descuento e impuesto

El siguiente ejemplo muestra dónde se declaran las reglas de redondeo. En una aplicación real, confirma si la regla de negocio exige redondear cada etapa o solo el resultado final.

```java
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoCompra {
    private static final int ESCALA_MONETARIA = 2;
    private static final RoundingMode REDONDEO = RoundingMode.HALF_UP;

    public static void main(String[] args) {
        BigDecimal precioUnitario = new BigDecimal("19.99");
        BigDecimal cantidad = new BigDecimal("3");
        BigDecimal tasaDescuento = new BigDecimal("0.10"); // 10 %
        BigDecimal tasaImpuesto = new BigDecimal("0.075"); // 7.5 %

        BigDecimal subtotal = precioUnitario.multiply(cantidad);
        BigDecimal descuento = subtotal.multiply(tasaDescuento)
                .setScale(ESCALA_MONETARIA, REDONDEO);
        BigDecimal baseImponible = subtotal.subtract(descuento);
        BigDecimal impuesto = baseImponible.multiply(tasaImpuesto)
                .setScale(ESCALA_MONETARIA, REDONDEO);
        BigDecimal total = baseImponible.add(impuesto)
                .setScale(ESCALA_MONETARIA, REDONDEO);

        System.out.println("Subtotal: " + subtotal.toPlainString());
        System.out.println("Descuento: " + descuento.toPlainString());
        System.out.println("Impuesto: " + impuesto.toPlainString());
        System.out.println("Total: " + total.toPlainString());
    }
}
```

Aspectos importantes del ejemplo:

1. Los decimales se crean desde `String`, sin pasar por `double`.
2. Las tasas se representan como decimales: 10 % es `0.10` y 7.5 % es `0.075`.
3. La escala y el modo de redondeo están declarados como una política común.
4. Cada resultado se asigna porque `BigDecimal` es inmutable.
5. El programa conserva los números como `BigDecimal` durante el cálculo y solo los convierte a texto al mostrarlos.

## 10. Errores frecuentes y buenas prácticas

| Evita | Prefiere | Motivo |
|---|---|---|
| `new BigDecimal(0.1)` para representar el decimal escrito | `new BigDecimal("0.1")` | Evita capturar directamente la aproximación binaria del `double`. |
| Usar `+` o `*` entre objetos `BigDecimal` | `add()` o `multiply()` | La API devuelve nuevos valores inmutables. |
| Ignorar el resultado de `setScale()` | Asignarlo a una variable | Los objetos no se modifican. |
| Dividir sin definir precisión cuando el resultado puede ser periódico | Indicar escala y `RoundingMode`, o usar `MathContext` | La división requiere una política explícita si no es exacta. |
| Usar `equals()` como única prueba de igualdad numérica | Considerar `compareTo() == 0` cuando la escala no importa | `equals()` también compara la escala. |
| Usar un modo de redondeo por costumbre | Seguir la regla documentada del dominio | El redondeo puede cambiar resultados finales. |
| Usar texto formateado para continuar calculando | Conservar el `BigDecimal` y formatear solo para mostrar | Separa el cálculo de la presentación. |
| Convertir entradas inválidas silenciosamente a cero | Validar y comunicar el error | Evita esconder datos incorrectos. |

## 11. Regla mental para trabajar con `BigDecimal`

**Primero conserva el decimal correctamente; después calcula; aplica el redondeo que exige el problema; al final, formatea el resultado para presentarlo.**

## 12. Documentación oficial

- [`BigDecimal` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/math/BigDecimal.html)
- [`RoundingMode` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/math/RoundingMode.html)
- [`MathContext` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/math/MathContext.html)
