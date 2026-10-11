## 1. Idea general

Se tienen los siguientes elementos principales para recorrer la lista y construir el resultado:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| arreglo | Arreglo auxiliar donde se almacenan los nombres de los medicamentos agotados. |
| i | Índice que lleva la cuenta de cuántos medicamentos agotados se han encontrado y almacenado. |
| cantidad | Atributo de la lista que indica cuántos nodos tiene en total. |

La operación consiste en recorrer la lista completa desde el inicio hasta `null`, y por cada nodo verificar si su stock es menor o igual a 5. Si cumple, se guarda su nombre en el arreglo y se incrementa el índice `i`. Al final, se devuelve un arreglo con exactamente los nombres encontrados.

## 2. Correcciones incorporadas

### Dimensión incorrecta del arreglo

No se debe crear el arreglo con el tamaño de la lista completa:

```
arreglo[cantidad]
```

porque `cantidad` es el número total de nodos, no el número de medicamentos agotados. Si solo algunos nodos cumplen la condición, el arreglo queda con posiciones vacías (`null`) al final, y el resultado no refleja bien cuántos medicamentos están agotados.

El tamaño correcto del arreglo es `i`, la cantidad real de medicamentos agotados encontrados durante el recorrido.

### Recorrido con conteo controlado

Como el tamaño del resultado no se conoce hasta terminar de recorrer, hay una forma limpia de resolverlo:

- **Dos pasadas:** una primera pasada cuenta cuántos cumplen la condición, se crea el arreglo con ese tamaño, y una segunda pasada llena el arreglo.

**El arreglo que se devuelve debe tener el tamaño de los resultados, no el tamaño de la lista**.

### Validación de lista vacía

Si `inicio == null`, la lista no tiene nodos. No hay que bloquear nada ni anunciar un error: el ciclo no se ejecuta, `i` queda en 0 y el método devuelve un arreglo vacío, que es la respuesta correcta ("no hay medicamentos agotados").

## 3. Pseudocódigo

```
PROCEDIMIENTO MedicamentosAgotados()

    SI (inicio == null) ENTONCES
        RETORNAR arreglo vacío
    FIN SI

    // Primera pasada: contar cuántos cumplen la condición
    
    contador = 0
    actual = inicio
    
    CICLO (actual != null) {
        SI (actual.getStock() <= 5) ENTONCES
            contador++;
        FIN SI
        actual = actual.siguiente
    }

    // Crear arreglo con el tamaño exacto del resultado
    
    arreglo = nuevo arreglo de tamaño contador
    i = 0

    // Segunda pasada: llenar el arreglo
    
    actual = inicio
    CICLO (actual != null) {
        SI (actual.getStock() <= 5) ENTONCES
            arreglo[i] = actual.getNombre()
            i++;
        FIN SI
        actual = actual.siguiente
    }

    RETORNAR arreglo

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Estado inicial de punteros:

```
inicio = N1
actual = N1
i = 0
contador = 0 (en la primera pasada)
```

Representación gráfica:

```
[N1 | inicio, actual] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

### Primera pasada: contar

**Iteración 1**

Condición:

```
SI (actual != null) ENTONCES
```

Evaluación:

- `actual = N1`
- `N1.getStock() = 3 <= 5` → verdadero
- `contador = 1`
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio] -> [N2 | actual] -> [N3] -> null
contador = 1
```

**Iteración 2**

Evaluación:

- `actual = N2`
- `N2.getStock() = 8 <= 5` → falso
- `actual = N2.siguiente = N3`

Estado:

```
[N1 | inicio] -> [N2] -> [N3 | actual] -> null
contador = 1
```

**Iteración 3**

Evaluación:

- `actual = N3`
- `N3.getStock() = 2 <= 5` → verdadero
- `contador = 2`
- `actual = N3.siguiente = null`

Estado:

```
[N1 | inicio] -> [N2] -> [N3] -> null | actual
contador = 2
```

El ciclo termina porque `actual == null`.

### Creación del arreglo

Con `contador = 2`, se crea un arreglo de tamaño 2:

```
arreglo = [_, _]
i = 0
```

### Segunda pasada: llenar

**Iteración 1**

- `actual = N1`
- `N1.getStock() = 3 <= 5` → verdadero
- `arreglo[0] = "n1"`, `i = 1`
- `actual = N2`

Estado:

```
arreglo = ["n1", _]
i = 1
```

**Iteración 2**

- `actual = N2`
- `N2.getStock() = 8 <= 5` → falso
- `actual = N3`

Estado:

```
arreglo = ["n1", _]
i = 1
```

**Iteración 3**

- `actual = N3`
- `N3.getStock() = 2 <= 5` → verdadero
- `arreglo[1] = "n3"`, `i = 2`
- `actual = null`

Estado:

```
arreglo = ["n1", "n3"]
i = 2
```

El ciclo termina porque `actual == null`.

## 6. Resultado final

Lista inicial:

```
[N1: stock=3] -> [N2: stock=8] -> [N3: stock=2] -> null
```

Arreglo resultante después de `MedicamentosAgotados()`:

```
["n1", "n3"]
```

Coincide con lo esperado: N1 (stock 3) y N3 (stock 2) son los únicos cuyo stock es menor o igual a 5.

## 7. Casos borde

### Caso borde 1: lista vacía

```
SI (inicio == null) ENTONCES
    RETORNAR arreglo vacío
FIN SI
```

No hay nodos que recorrer. El resultado correcto es un arreglo vacío, no un error ni un anuncio. `contador` queda en 0, se crea un arreglo de tamaño 0, y el método devuelve ese arreglo vacío.

### Caso borde 2: ningún medicamento cumple la condición

Ejemplo:

```
[N1: stock=10] -> [N2: stock=20] -> [N3: stock=15] -> null
```

Primera pasada: `contador` queda en 0.

Arreglo creado de tamaño 0.

Segunda pasada: `i` nunca se incrementa.

Resultado:

```
[]
```

Un arreglo vacío, que significa "no hay medicamentos agotados". 