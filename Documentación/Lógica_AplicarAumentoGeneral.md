## 1. Idea general

Se tienen los siguientes elementos principales para recorrer la lista y aplicar el aumento:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| porcentaje | Parámetro de entrada (`double`): el aumento a aplicar, expresado como fracción decimal (`0.25` = 25%). |
| factor | `BigDecimal` con el multiplicador `1 + porcentaje`. Se calcula una sola vez, antes del recorrido. |
| med | Medicamento del nodo actual (`actual.getDato()`), cuyo precio se actualiza. |
| modificados | Lleva la cuenta de cuántos medicamentos fueron modificados. |

La operación consiste en validar el porcentaje, calcular una sola vez el factor `1 + porcentaje`, y recorrer toda la lista desde el inicio hasta `null`, multiplicando en cada nodo el precio del medicamento por ese factor. Se actualiza el medicamento, se incrementa `modificados`, y se avanza al siguiente nodo. Al final se retorna `modificados`.

## 2. Aviso Importante

### Cuádruple significado del valor `0`

El método retorna `0` en **cuatro situaciones distintas**:

1. La lista estaba vacía (ningún medicamento que modificar).
2. El porcentaje recibido no era un número finito (`NaN` o infinito, rechazado).
3. El porcentaje recibido era negativo (rechazado).
4. El porcentaje recibido era `0.0` (no hubo cambios reales).

Como los cuatro casos devuelven el mismo valor numérico, quien llame al método no puede distinguirlos solo con el retorno. Por eso, cuando el porcentaje es no finito, negativo o cero se imprime un mensaje informativo antes de retornar. En el caso de la lista vacía no se imprime ningún mensaje: el ciclo simplemente no se ejecuta y se retorna `0`.

```
ESCRIBIR "El porcentaje debe ser un número finito"
ESCRIBIR "El porcentaje no puede ser negativo"
ESCRIBIR "No hubo cambios: el porcentaje es 0"
```

### Convención del parámetro

El porcentaje se recibe como **fracción decimal**: `0.25` representa un aumento del 25%. No se recibe como puntos porcentuales (`25`). Esta decisión se documenta aquí para evitar ambigüedad al llamar al método.

### Fórmula usada

El nuevo precio se calcula como:

```
nuevoPrecio = precioActual * (1 + porcentaje)
```

Esta forma es algebraicamente equivalente a `precio + precio * porcentaje`, pero hace una sola operación. Con `porcentaje = 0.25`, ambas dan el mismo resultado.

En el código el factor se calcula una única vez antes del ciclo y se aplica a cada precio con `multiply`:

```
factor = BigDecimal.ONE.add(BigDecimal.valueOf(porcentaje))
med.setPrecio(med.getPrecio().multiply(factor))
```

Se usa `BigDecimal.valueOf(porcentaje)` (y no `new BigDecimal(porcentaje)`) para que, por ejemplo, `0.10` se convierta exactamente en `0.1` y no en su representación binaria aproximada.

### Escala de los precios resultantes

El método **no redondea**. Al multiplicar dos `BigDecimal`, la escala del resultado es la suma de las escalas de los operandos, por lo que los precios quedan con más decimales que antes. Por ejemplo, con `porcentaje = 0.10` (`factor = 1.1`):

```
10.0 × 1.1  = 11.00
2.50 × 1.1  = 2.750
12.00 × 1.1 = 13.200
```

El valor es numéricamente el mismo que se obtendría redondeando (`2.750` equivale a `2.75`), pero el `BigDecimal` conserva los decimales adicionales.

### Valores no finitos (`NaN` e infinito)

`BigDecimal.valueOf(double)` no admite `Double.NaN` ni `Double.POSITIVE_INFINITY` (ni `NEGATIVE_INFINITY`) y lanzaría `NumberFormatException`. Además, `NaN` no cumple `porcentaje < 0` ni `porcentaje == 0.0`, por lo que superaría las otras dos validaciones.

Por eso la primera guard clause del método comprueba `Double.isFinite(porcentaje)`: si el valor no es finito, se imprime un mensaje y se retorna `0` **antes** de calcular el factor, sin modificar ningún precio y sin lanzar ninguna excepción.

### Contador redundante con la lógica actual

Como los porcentajes no finitos, `0` y negativos se rechazan al inicio, todos los nodos que se recorren en el ciclo **se modifican sin excepción**. Eso significa que `modificados` termina valiendo siempre el número de nodos de la lista (que coincide con `cantidad` cuando este atributo está actualizado). Se deja igualmente porque documenta la intención del ciclo y hace el método más robusto ante cambios futuros.

## 3. Pseudocódigo

```
PROCEDIMIENTO AplicarAumentoGeneral(porcentaje)

    SI (porcentaje no es un número finito) ENTONCES
        ESCRIBIR "El porcentaje debe ser un número finito"
        RETORNAR 0
    FIN SI

    SI (porcentaje < 0) ENTONCES
        ESCRIBIR "El porcentaje no puede ser negativo"
        RETORNAR 0
    FIN SI

    SI (porcentaje == 0.0) ENTONCES
        ESCRIBIR "No hubo cambios: el porcentaje es 0"
        RETORNAR 0
    FIN SI

    factor = 1 + porcentaje
    actual = inicio
    modificados = 0

    CICLO (actual != null) {
        med = actual.getDato()
        med.setPrecio(med.getPrecio() * factor)
        modificados++
        actual = actual.siguiente
    }

    RETORNAR modificados

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Llamada que vamos a trazar:

```
AplicarAumentoGeneral(0.10)
```

Estado inicial de punteros y parámetros:

```
inicio      = N1
actual      = N1
modificados = 0
porcentaje  = 0.10
```

Representación gráfica:

```
[N1 | inicio, actual] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

**Validación 1: porcentaje finito**

- `porcentaje = 0.10` → es un número finito, continúa.

**Validación 2: porcentaje no negativo**

- `porcentaje = 0.10` → `0.10 < 0` es falso, continúa.

**Validación 3: porcentaje distinto de cero**

- `porcentaje = 0.10` → `0.10 == 0.0` es falso, continúa.

**Cálculo del factor**

- `factor = 1 + 0.10 = 1.1`

**Iteración 1**

Condición:

```
SI (actual != null) ENTONCES
```

Evaluación:

- `actual = N1`, `med = N1.getDato()`
- `precio nuevo = 10.0 * 1.1 = 11.00`
- `med.setPrecio(11.00)`
- `modificados = 1`
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio, precio=11.00] -> [N2 | actual] -> [N3] -> null
modificados = 1
```

**Iteración 2**

Evaluación:

- `actual = N2`, `med = N2.getDato()`
- `precio nuevo = 20.0 * 1.1 = 22.00`
- `med.setPrecio(22.00)`
- `modificados = 2`
- `actual = N2.siguiente = N3`

Estado:

```
[N1] -> [N2 | precio=22.00] -> [N3 | actual] -> null
modificados = 2
```

**Iteración 3**

Evaluación:

- `actual = N3`, `med = N3.getDato()`
- `precio nuevo = 15.0 * 1.1 = 16.50`
- `med.setPrecio(16.50)`
- `modificados = 3`
- `actual = N3.siguiente = null`

Estado:

```
[N1] -> [N2] -> [N3 | precio=16.50] -> null | actual
modificados = 3
```

El ciclo termina porque `actual == null`.

**Retorno:**

```
RETORNAR modificados = 3
```

## 6. Resultado final

Lista inicial:

```
[N1: precio=10.0] -> [N2: precio=20.0] -> [N3: precio=15.0] -> null
```

Llamada:

```
AplicarAumentoGeneral(0.10)
```

Resultado del método:

```
3
```

Lista después del aumento:

```
[N1: precio=11.00] -> [N2: precio=22.00] -> [N3: precio=16.50] -> null
```

Cada precio fue aumentado en un 10% (los decimales adicionales provienen de la escala de `BigDecimal`, no de un cambio en el valor) y el método confirma que se modificaron los tres medicamentos.

## 7. Casos borde

### Caso borde 1: porcentaje negativo

Llamada: `AplicarAumentoGeneral(-0.10)`

- `porcentaje = -0.10`, y `-0.10 < 0` es verdadero.
- Se imprime `"El porcentaje no puede ser negativo"`.
- Retorna `0` sin modificar la lista.

La lista queda intacta.

### Caso borde 2: porcentaje igual a cero

Llamada: `AplicarAumentoGeneral(0.0)`

- `porcentaje = 0.0`, es finito y `0.0 < 0` es falso, así que pasa las dos primeras validaciones.
- `0.0 == 0.0` es verdadero.
- Se imprime `"No hubo cambios: el porcentaje es 0"`.
- Retorna `0` sin modificar la lista.

La lista queda intacta.

### Caso borde 3: lista vacía

```
inicio = null
```

Llamada: `AplicarAumentoGeneral(0.10)`

- Las tres validaciones del porcentaje pasan (el porcentaje es válido).
- `factor = 1.1`; `actual = inicio = null`, el ciclo no se ejecuta ni una vez.
- `modificados` queda en 0.
- Retorna `0`, sin imprimir ningún mensaje.

Resultado correcto: no hay medicamentos que modificar, así que se modifica ninguno.

### Caso borde 4: lista con un solo nodo

Ejemplo:

```
[N1: precio=10.0] -> null
```

Llamada: `AplicarAumentoGeneral(0.10)`

- Iteración única: `precio nuevo = 10.0 * 1.1 = 11.00`, `med.setPrecio(11.00)`, `modificados = 1`.
- Retorna `1`.

El único medicamento se modifica correctamente.

### Caso borde 5: porcentaje muy grande

Llamada: `AplicarAumentoGeneral(1.0)`

- Representa un aumento del 100%, es decir, se duplica el precio.
- `factor = 1 + 1.0 = 2.0`, y el precio nuevo es `precio * 2.0` (por ejemplo, `10.0 * 2.0 = 20.00`).
- Se aplica normalmente a cada nodo y se retorna la cantidad modificada.

No se requiere validación adicional: el porcentaje sigue siendo válido aunque sea alto, siempre que se exprese como fracción decimal.

### Caso borde 6: porcentaje `NaN` o infinito

Llamadas: `AplicarAumentoGeneral(Double.NaN)` o `AplicarAumentoGeneral(Double.POSITIVE_INFINITY)`

- `Double.isFinite(porcentaje)` es falso en ambos casos.
- Se imprime `"El porcentaje debe ser un número finito"`.
- Retorna `0` antes de calcular el factor, sin lanzar ninguna excepción.

La lista queda intacta: ningún precio se modifica. Ambos casos se cubren en `Main.java`.