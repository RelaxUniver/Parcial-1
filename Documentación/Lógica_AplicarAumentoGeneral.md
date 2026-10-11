## 1. Idea general

Se tienen los siguientes elementos principales para recorrer la lista y aplicar el aumento:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| porcentaje | Parámetro de entrada: el aumento a aplicar, expresado como fracción decimal (`0.25` = 25%). |
| nuevoPrecio | Variable auxiliar que guarda el resultado del cálculo antes de asignarlo al nodo. |
| contador | Lleva la cuenta de cuántos medicamentos fueron modificados. |

La operación consiste en recorrer toda la lista desde el inicio hasta `null`, y por cada nodo calcular su nuevo precio multiplicándolo por `(1 + porcentaje)`. Se actualiza el nodo, se incrementa el contador, y se avanza al siguiente. Al final se retorna el contador.

## 2. Aviso Importante

### Triple significado del valor `0`

El método retorna `0` en **tres situaciones distintas**:

1. La lista estaba vacía (ningún medicamento que modificar).
2. El porcentaje recibido era negativo (rechazado).
3. El porcentaje recibido era `0.0` (no hubo cambios reales).

Como los tres casos devuelven el mismo valor numérico, quien llame al método no puede distinguirlos solo con el retorno. Por eso, en cada caso se imprime un mensaje informativo antes de retornar.

### Convención del parámetro

El porcentaje se recibe como **fracción decimal**: `0.25` representa un aumento del 25%. No se recibe como puntos porcentuales (`25`). Esta decisión se documenta aquí para evitar ambigüedad al llamar al método.

### Fórmula usada

El nuevo precio se calcula como:

```
nuevoPrecio = precioActual * (1 + porcentaje)
```

Esta forma es algebraicamente equivalente a `precio + precio * porcentaje`, pero hace una sola operación. Con `porcentaje = 0.25`, ambas dan el mismo resultado.

### Contador redundante con la lógica actual

Como los porcentajes `0` y negativos se rechazan al inicio, todos los nodos que se recorren en el ciclo **se modifican sin excepción**. Eso significa que `contador` termina valiendo siempre `cantidad`. Se deja igualmente porque documenta la intención del ciclo y hace el método más robusto ante cambios futuros.

## 3. Pseudocódigo

```
PROCEDIMIENTO AplicarAumentoGeneral(porcentaje)

    SI (porcentaje < 0) ENTONCES
        ESCRIBIR "El porcentaje no puede ser negativo"
        RETORNAR 0
    FIN SI

    SI (porcentaje == 0.0) ENTONCES
        ESCRIBIR "No hubo cambios: el porcentaje es 0"
        RETORNAR 0
    FIN SI

    actual = inicio
    contador = 0

    CICLO (actual != null) {
        nuevoPrecio = actual.getPrecio() * (1 + porcentaje)
        actual.setPrecio(nuevoPrecio)
        contador++
        actual = actual.siguiente
    }

    RETORNAR contador

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
inicio     = N1
actual     = N1
contador   = 0
porcentaje = 0.10
```

Representación gráfica:

```
[N1 | inicio, actual] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

**Validación 1: porcentaje no negativo**

- `porcentaje = 0.10` → `0.10 < 0` es falso, continúa.

**Validación 2: porcentaje distinto de cero**

- `porcentaje = 0.10` → `0.10 == 0.0` es falso, continúa.

**Iteración 1**

Condición:

```
SI (actual != null) ENTONCES
```

Evaluación:

- `actual = N1`
- `nuevoPrecio = 10.0 * (1 + 0.10) = 10.0 * 1.10 = 11.0`
- `N1.setPrecio(11.0)`
- `contador = 1`
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio, precio=11.0] -> [N2 | actual] -> [N3] -> null
contador = 1
```

**Iteración 2**

Evaluación:

- `actual = N2`
- `nuevoPrecio = 20.0 * 1.10 = 22.0`
- `N2.setPrecio(22.0)`
- `contador = 2`
- `actual = N2.siguiente = N3`

Estado:

```
[N1] -> [N2 | precio=22.0] -> [N3 | actual] -> null
contador = 2
```

**Iteración 3**

Evaluación:

- `actual = N3`
- `nuevoPrecio = 15.0 * 1.10 = 16.5`
- `N3.setPrecio(16.5)`
- `contador = 3`
- `actual = N3.siguiente = null`

Estado:

```
[N1] -> [N2] -> [N3 | precio=16.5] -> null | actual
contador = 3
```

El ciclo termina porque `actual == null`.

**Retorno:**

```
RETORNAR contador = 3
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
[N1: precio=11.0] -> [N2: precio=22.0] -> [N3: precio=16.5] -> null
```

Cada precio fue aumentado en un 10% y el método confirma que se modificaron los tres medicamentos.

## 7. Casos borde

### Caso borde 1: porcentaje negativo

Llamada: `AplicarAumentoGeneral(-0.10)`

- `porcentaje = -0.10`, y `-0.10 < 0` es verdadero.
- Se imprime `"El porcentaje no puede ser negativo"`.
- Retorna `0` sin modificar la lista.

La lista queda intacta.

### Caso borde 2: porcentaje igual a cero

Llamada: `AplicarAumentoGeneral(0.0)`

- `porcentaje = 0.0`, y `0.0 < 0` es falso, así que pasa la primera validación.
- `0.0 == 0.0` es verdadero.
- Se imprime `"No hubo cambios: el porcentaje es 0"`.
- Retorna `0` sin modificar la lista.

La lista queda intacta.

### Caso borde 3: lista vacía

```
inicio = null
```

Llamada: `AplicarAumentoGeneral(0.10)`

- Las dos validaciones del porcentaje pasan (el porcentaje es válido).
- `actual = inicio = null`, el ciclo no se ejecuta ni una vez.
- `contador` queda en 0.
- Retorna `0`.

Resultado correcto: no hay medicamentos que modificar, así que se modifica ninguno.

### Caso borde 4: lista con un solo nodo

Ejemplo:

```
[N1: precio=10.0] -> null
```

Llamada: `AplicarAumentoGeneral(0.10)`

- Iteración única: `nuevoPrecio = 10.0 * 1.10 = 11.0`, `N1.setPrecio(11.0)`, `contador = 1`.
- Retorna `1`.

El único medicamento se modifica correctamente.

### Caso borde 5: porcentaje muy grande

Llamada: `AplicarAumentoGeneral(1.0)`

- Representa un aumento del 100%, es decir, se duplica el precio.
- `nuevoPrecio = precio * (1 + 1.0) = precio * 2`.
- Se aplica normalmente a cada nodo y se retorna la cantidad modificada.

No se requiere validación adicional: el porcentaje sigue siendo válido aunque sea alto, siempre que se exprese como fracción decimal.