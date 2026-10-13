## 1. Idea general

Se tienen los siguientes elementos principales para construir el arreglo de precios en orden inverso:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| anterior | Puntero auxiliar usado por `invertir()` para reasignar los enlaces. |
| siguiente | Puntero auxiliar usado por `invertir()` para no perder el resto de la lista. |
| totalNodos | Contador con el número real de nodos de la lista, obtenido con una primera pasada de conteo. |
| arreglo | Arreglo auxiliar (`BigDecimal[]`, llamado `preciosInvertidos` en el código) donde se almacenan los precios. |
| i | Índice que recorre el arreglo mientras se llena. |

La operación se apoya en un método auxiliar `invertir()` que da vuelta la lista en su lugar. El método público sigue cuatro pasos:

1. Contar los nodos reales de la lista (`totalNodos`).
2. Invertir la lista con `invertir()`.
3. Recorrer la lista ya invertida y guardar cada precio en un arreglo de tamaño `totalNodos`.
4. Invertir la lista otra vez para restaurarla a su orden original.

Al final se devuelve el arreglo con los precios en orden inverso al que fueron ingresados, y la lista de medicamentos queda exactamente como estaba antes de la llamada.

## 2. Aviso Importante

### Patrón mutar–extraer–restaurar

El método modifica temporalmente la lista original para aprovechar el recorrido natural de la estructura y luego la restaura. Esto tiene dos consecuencias importantes:

- **Ventaja:** no se necesita memoria extra para guardar una copia de la lista. El arreglo de precios se construye sobre la lista ya invertida.
- **Riesgo:** la restauración depende de que la segunda llamada a `invertir()` se ejecute. Si el ciclo intermedio falla, la lista queda invertida de forma permanente, alterando el estado que el resto del sistema espera encontrar.

Por eso, la corrección de `invertir()` y del ciclo intermedio es crítica: **no basta con que la primera inversión funcione, ambas deben funcionar siempre**.

### Tamaño correcto del arreglo

En este método, el arreglo se crea con `arreglo[totalNodos]`. Esto es correcto porque **cada nodo tiene exactamente un precio** y todos se incluyen en el resultado, sin filtros. Es lo contrario de lo que ocurría en `MedicamentosAgotados()`, donde solo algunos nodos cumplían la condición y el arreglo debía dimensionarse con el número de resultados, no con la cantidad total de nodos.

**Independencia del atributo `cantidad`:** el tamaño del arreglo no se toma del atributo `cantidad` de la lista, sino de un conteo de los nodos reales hecho **antes** de invertir. Así el método es correcto aunque `cantidad` esté desactualizado (por ejemplo, si se olvidó llamar a `setCantidad`): un valor menor que el número real de nodos ya no provoca `ArrayIndexOutOfBoundsException` ni deja la lista invertida de forma permanente, que era el riesgo descrito en la sección anterior.

### Ausencia de casos borde especiales

A diferencia de otros métodos, aquí no se requiere ninguna guard clause adicional. La lista vacía y la lista con un solo nodo caen naturalmente en la lógica general:

- Con lista vacía, el conteo da 0, `invertir()` no hace nada, el arreglo se crea de tamaño 0, el ciclo no corre, `invertir()` no hace nada otra vez, y se retorna un arreglo vacío.
- Con un solo nodo, el conteo da 1, `invertir()` no cambia nada, el arreglo se crea de tamaño 1, y el ciclo guarda ese único precio.

## 3. Pseudocódigo

```
PROCEDIMIENTO invertir()

    actual = inicio
    anterior = null

    CICLO (actual != null) {
        siguiente = actual.siguiente
        actual.siguiente = anterior
        anterior = actual
        actual = siguiente
    }

    inicio = anterior

FIN PROCEDIMIENTO


PROCEDIMIENTO ListarPreciosInversos()

    // Paso 1: contar los nodos reales
    totalNodos = 0
    actual = inicio

    CICLO (actual != null) {
        totalNodos++
        actual = actual.siguiente
    }

    // Paso 2: invertir la lista
    invertir()

    // Paso 3: extraer los precios
    arreglo = nuevo arreglo de tamaño totalNodos
    i = 0
    actual = inicio

    CICLO (actual != null) {
        arreglo[i] = actual.getDato().getPrecio()
        i++
        actual = actual.siguiente
    }

    // Paso 4: restaurar la lista
    invertir()

    RETORNAR arreglo

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Estado inicial:

```
inicio = N1
```

Representación gráfica:

```
[N1 | inicio] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

### Paso 1: contar los nodos

```
totalNodos = 0
actual = N1
```

- Iteración 1: `actual = N1`, `totalNodos = 1`, `actual = N2`
- Iteración 2: `actual = N2`, `totalNodos = 2`, `actual = N3`
- Iteración 3: `actual = N3`, `totalNodos = 3`, `actual = null`

El ciclo termina porque `actual == null`. Resultado: `totalNodos = 3`.

### Paso 2: primera llamada a `invertir()`

Estado inicial de `invertir()`:

```
actual   = N1
anterior = null
```

**Iteración 1**

- `siguiente = N1.siguiente = N2`
- `N1.siguiente = null` (antes apuntaba a N2)
- `anterior = N1`
- `actual = N2`

Estado:

```
[N1] -> null    (N1 ya apunta a null)
[N2 | actual] -> [N3] -> null
anterior = N1
```

**Iteración 2**

- `siguiente = N2.siguiente = N3`
- `N2.siguiente = N1`
- `anterior = N2`
- `actual = N3`

Estado:

```
[N2] -> [N1] -> null
[N3 | actual] -> null
anterior = N2
```

**Iteración 3**

- `siguiente = N3.siguiente = null`
- `N3.siguiente = N2`
- `anterior = N3`
- `actual = null`

Estado:

```
[N3] -> [N2] -> [N1] -> null
anterior = N3
actual   = null
```

El ciclo termina porque `actual == null`.

**Actualización de la cabecera:**

```
inicio = anterior = N3
```

Lista después de la primera inversión:

```
[N3 | inicio] -> [N2] -> [N1] -> null
```

### Paso 3: crear arreglo y recorrer

```
arreglo = nuevo arreglo de tamaño totalNodos = 3
i = 0
actual = inicio = N3
```

**Iteración 1**

- `arreglo[0] = N3.getPrecio() = 15.0`
- `i = 1`
- `actual = N3.siguiente = N2`

**Iteración 2**

- `arreglo[1] = N2.getPrecio() = 20.0`
- `i = 2`
- `actual = N2.siguiente = N1`

**Iteración 3**

- `arreglo[2] = N1.getPrecio() = 10.0`
- `i = 3`
- `actual = N1.siguiente = null`

El ciclo termina porque `actual == null`.

Estado:

```
arreglo = [15.0, 20.0, 10.0]
i = 3
```

### Paso 4: segunda llamada a `invertir()`

Estado inicial de `invertir()`:

```
actual   = N3
anterior = null
```

Se aplica el mismo procedimiento que en el paso 2. Al terminar:

```
inicio = N1
```

Lista restaurada:

```
[N1 | inicio] -> [N2] -> [N3] -> null
```

## 6. Resultado final

Lista inicial:

```
[N1: precio=10.0] -> [N2: precio=20.0] -> [N3: precio=15.0] -> null
```

Arreglo retornado por `ListarPreciosInversos()`:

```
[15.0, 20.0, 10.0]
```

Coincide con lo esperado: los precios quedan en orden inverso al que fueron ingresados.

Lista después de la llamada:

```
[N1: precio=10.0] -> [N2: precio=20.0] -> [N3: precio=15.0] -> null
```

La lista queda igual que al inicio, porque la segunda inversión la restauró.

## 7. Casos borde

### Caso borde 1: lista vacía

```
inicio = null
```

- Conteo: `actual = null`, el ciclo no corre, `totalNodos = 0`.
- Primera llamada a `invertir()`: `actual = null`, el ciclo no corre, `inicio = anterior = null`. No cambia nada.
- `arreglo = nuevo arreglo de tamaño 0`.
- `actual = inicio = null`, el ciclo no corre ni una vez.
- Segunda llamada a `invertir()`: mismo caso que la primera, no cambia nada.
- Retorna `[]`.

Resultado correcto sin necesidad de código especial.

### Caso borde 2: lista con un solo nodo

Ejemplo:

```
[N1: precio=10.0] -> null
```

- Conteo: `totalNodos = 1`.
- Primera llamada a `invertir()`:
  - `actual = N1`, `anterior = null`
  - Iteración 1: `siguiente = null`, `N1.siguiente = null`, `anterior = N1`, `actual = null`
  - `inicio = N1` (no cambia)
- `arreglo = nuevo arreglo de tamaño 1`.
- Iteración única: `arreglo[0] = 10.0`, `i = 1`, `actual = null`.
- Segunda llamada a `invertir()`: mismo caso, no cambia nada.
- Retorna `[10.0]`.

El único precio se devuelve tal cual, porque el orden inverso de una lista de un elemento es ella misma.

### Caso borde 3: el atributo `cantidad` no coincide con los nodos

Ejemplo:

```
[Q1: precio=1.00] -> [Q2: precio=2.00] -> null
cantidad = 1        (el valor real sería 2)
```

- Conteo: recorre los nodos y obtiene `totalNodos = 2`; el atributo `cantidad` no se consulta.
- `arreglo = nuevo arreglo de tamaño 2`.
- La lista se invierte, se extraen `[2.00, 1.00]` y se vuelve a invertir.
- Retorna `[2.00, 1.00]` y la lista queda restaurada.

Si el arreglo se dimensionara con `cantidad` (1), la segunda iteración intentaría escribir en `arreglo[1]` y lanzaría `ArrayIndexOutOfBoundsException`, dejando la lista invertida. Contar los nodos evita ese problema. El caso se cubre en `Main.java` con el bloque *"el atributo cantidad no coincide con los nodos"*.