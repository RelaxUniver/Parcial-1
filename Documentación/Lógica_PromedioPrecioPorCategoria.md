## 1. Idea general

Se tienen los siguientes elementos principales para recorrer la lista y calcular el promedio:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| categoria | Parámetro de entrada: la categoría de la cual se quiere el promedio. |
| cant | Contador de cuántos medicamentos pertenecen a esa categoría. |
| cantTotal | Acumulador de la suma de los precios de los medicamentos de esa categoría. |

La operación consiste en recorrer toda la lista desde el inicio hasta `null`, y por cada nodo verificar si su categoría coincide con el parámetro recibido. Si coincide, se suma su precio al acumulador y se incrementa el contador. Al final, si el contador es 0 se devuelve `0.0`, y en caso contrario se devuelve el cociente entre la suma acumulada y el contador.

## 2. Aviso Importante

### Prevención de la división entre cero

El punto crítico del método es que **no se puede dividir directamente al final del recorrido**, porque si ningún medicamento pertenece a la categoría buscada, el contador `cant` queda en 0. Dividir `cantTotal / cant` en ese estado produciría una división por cero.

La solución es comprobar el contador antes de dividir:

```
SI (cant == 0) ENTONCES
    RETORNAR 0.0
FIN SI
RETORNAR cantTotal / cant
```

Esto cubre a la vez dos situaciones distintas: la lista vacía y la categoría sin medicamentos. En ambos casos la respuesta es `0.0`, tal como pide el enunciado.

### Nota sobre Java

En el pseudocódigo se usa `.equals()` para comparar el texto de las categorías. Esto es intencional: en Java, el operador `==` entre dos `String` compara referencias de memoria, no contenido. Con `.equals()` la comparación es por texto, que es lo que se necesita aquí. Escribir `==` compilaría sin errores, pero daría un bug silencioso cuando las cadenas vengan de fuentes distintas (por ejemplo, entrada del usuario o lectura de archivo).

## 3. Pseudocódigo 

```
PROCEDIMIENTO PromedioPrecioPorCategoria(categoria)

    SI (categoria == null) ENTONCES
        RETORNAR 0.0
    FIN SI

    actual = inicio
    cant = 0
    cantTotal = 0.0

    CICLO (actual != null) {

        SI (actual.getCategoria().equals(categoria)) ENTONCES
            cantTotal = cantTotal + actual.getPrecio()
            cant = cant + 1
        FIN SI

        actual = actual.siguiente
    }

    SI (cant == 0) ENTONCES
        RETORNAR 0.0
    FIN SI

    RETORNAR cantTotal / cant

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Estado inicial de punteros y acumuladores:

```
inicio    = N1
actual    = N1
cant      = 0
cantTotal = 0.0
categoria = "catA"
```

Representación gráfica:

```
[N1 | inicio, actual] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

**Iteración 1**

Condición:

```
SI (actual != null) ENTONCES
```

Evaluación:

- `actual = N1`
- `N1.getCategoria() = "catA"`, comparado con `"catA"` → verdadero
- `cantTotal = 0.0 + 10.0 = 10.0`
- `cant = 1`
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio] -> [N2 | actual] -> [N3] -> null
cant = 1
cantTotal = 10.0
```

**Iteración 2**

Evaluación:

- `actual = N2`
- `N2.getCategoria() = "catB"`, comparado con `"catA"` → falso
- No cambia nada en los acumuladores
- `actual = N2.siguiente = N3`

Estado:

```
[N1 | inicio] -> [N2] -> [N3 | actual] -> null
cant = 1
cantTotal = 10.0
```

**Iteración 3**

Evaluación:

- `actual = N3`
- `N3.getCategoria() = "catA"`, comparado con `"catA"` → verdadero
- `cantTotal = 10.0 + 15.0 = 25.0`
- `cant = 2`
- `actual = N3.siguiente = null`

Estado:

```
[N1 | inicio] -> [N2] -> [N3] -> null | actual
cant = 2
cantTotal = 25.0
```

El ciclo termina porque `actual == null`.

**Comprobación final**

- `cant == 0` → falso
- `RETORNAR cantTotal / cant = 25.0 / 2 = 12.5`

## 6. Resultado final

Lista inicial:

```
[N1: catA, precio=10.0] -> [N2: catB, precio=20.0] -> [N3: catA, precio=15.0] -> null
```

Resultado para `PromedioPrecioPorCategoria("catA")`:

```
12.5
```

Coincide con lo esperado: `(10.0 + 15.0) / 2 = 12.5`.

## 7. Casos borde

### Caso borde 1: lista vacía

```
inicio = null
```

- `actual = null`
- El ciclo no se ejecuta ni una vez.
- `cant = 0`, `cantTotal = 0.0`.
- La comprobación final detecta `cant == 0` y retorna `0.0`.

Resultado correcto sin necesidad de una comprobación inicial especial.

### Caso borde 2: categoría sin medicamentos

Ejemplo:

```
[N1: catA] -> [N2: catB] -> [N3: catA] -> null
```

Llamada: `PromedioPrecioPorCategoria("catC")`

- Ningún nodo coincide con `"catC"`.
- `cant = 0`, `cantTotal = 0.0`.
- La comprobación final detecta `cant == 0` y retorna `0.0`.

Evita la división por cero y cumple con lo que pide el enunciado.

### Caso borde 3: todos los medicamentos son de la categoría

Ejemplo:

```
[N1: catA, precio=10.0] -> [N2: catA, precio=20.0] -> [N3: catA, precio=30.0] -> null
```

Llamada: `PromedioPrecioPorCategoria("catA")`

- Los tres nodos coinciden.
- `cant = 3`, `cantTotal = 60.0`.
- Retorna `60.0 / 3 = 20.0`.

El promedio se calcula sobre la lista completa.

### Caso borde 4: categoría recibida como null

```
SI (categoria == null) ENTONCES
    RETORNAR 0.0
FIN SI
```

Si el parámetro llega nulo, no tiene sentido recorrer la lista buscando coincidencias. Esta guard clause se coloca al inicio del método y devuelve `0.0` inmediatamente, evitando un `NullPointerException` al llamar a `.equals()` dentro del ciclo.