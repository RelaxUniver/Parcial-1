## 1. Idea general

Se tienen los siguientes elementos principales para recorrer la lista y calcular el promedio:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| categoria | Parámetro de entrada (`String`): el nombre de la categoría de la cual se quiere el promedio. Se compara con el nombre de la constante del `enum` `Categoria` (por ejemplo, `"ANTIBIÓTICO"`). |
| cantidadMedicamentos | Contador (`int`) de cuántos medicamentos pertenecen a esa categoría. |
| sumaPrecios | Acumulador (`BigDecimal`) de la suma de los precios de los medicamentos de esa categoría. |

La operación consiste en recorrer toda la lista desde el inicio hasta `null`, y por cada nodo verificar si el nombre de su categoría coincide con el parámetro recibido. Si coincide, se suma su precio al acumulador y se incrementa el contador. Al final, si el contador es 0 se devuelve `0.0`, y en caso contrario se devuelve el cociente entre la suma acumulada y el contador, redondeado a 2 decimales.

## 2. Aviso Importante

### Prevención de la división entre cero

El punto crítico del método es que **no se puede dividir directamente al final del recorrido**, porque si ningún medicamento pertenece a la categoría buscada, el contador `cantidadMedicamentos` queda en 0. Dividir `sumaPrecios / cantidadMedicamentos` en ese estado produciría una división por cero.

La solución es comprobar el contador antes de dividir:

```
SI (cantidadMedicamentos == 0) ENTONCES
    RETORNAR 0.0
FIN SI
RETORNAR sumaPrecios / cantidadMedicamentos
```

Esto cubre a la vez dos situaciones distintas: la lista vacía y la categoría sin medicamentos. En ambos casos la respuesta es `0.0`, tal como pide el enunciado.

### Comparación entre la categoría (`enum`) y el parámetro (`String`)

En el modelo, la categoría de un medicamento es una constante del `enum` `Categoria`, pero el método recibe el nombre de la categoría como `String`. Comparar directamente `getCategoria().equals(categoria)` **siempre da `false`**, porque un objeto `enum` nunca es igual a un `String`. El programa compila sin avisar de ningún error, pero ningún medicamento llegaría a coincidir y el resultado sería siempre `0.0`.

Por eso se compara el **nombre** de la constante con el parámetro:

```
actual.getDato().getCategoria().name().equals(categoria)
```

La comparación es exacta: distingue mayúsculas, minúsculas y tildes. `"ANALGÉSICO"` coincide, pero `"analgésico"` y `"ANALGESICO"` no.

### Nota sobre Java

En el pseudocódigo se usa `.equals()` para comparar el texto de las categorías. Esto es intencional: en Java, el operador `==` entre dos `String` compara referencias de memoria, no contenido. Con `.equals()` la comparación es por texto, que es lo que se necesita aquí. Escribir `==` compilaría sin errores, pero daría un bug silencioso cuando las cadenas vengan de fuentes distintas (por ejemplo, entrada del usuario o lectura de archivo).

### Aritmética con `BigDecimal` y redondeo

Los precios son `BigDecimal`, que es inmutable y no admite los operadores `+` ni `/`: cada operación devuelve un objeto nuevo. En Java el pseudocódigo se traduce así:

- La suma `sumaPrecios + precio` se escribe `sumaPrecios = sumaPrecios.add(precio)`.
- La división se escribe `sumaPrecios.divide(BigDecimal.valueOf(cantidadMedicamentos), 2, RoundingMode.HALF_UP)`.

Indicar la escala (2 decimales) y el modo de redondeo es necesario: sin ellos, `divide` lanza `ArithmeticException` cuando el cociente no tiene una expansión decimal finita (por ejemplo, `(10.00 + 10.00 + 10.01) / 3`). Con `HALF_UP`, las mitades se redondean hacia arriba (`0.015` pasa a `0.02`).

Consecuencia sobre el resultado: cuando hay coincidencias, el promedio siempre se devuelve con **2 decimales** (`12.50`, `20.00`); cuando no las hay, se devuelve `0.0`.

## 3. Pseudocódigo 

```
PROCEDIMIENTO PromedioPrecioPorCategoria(categoria)

    SI (categoria == null) ENTONCES
        RETORNAR 0.0
    FIN SI

    actual = inicio
    cantidadMedicamentos = 0
    sumaPrecios = 0.0

    CICLO (actual != null) {

        SI (actual.getDato().getCategoria().name().equals(categoria)) ENTONCES
            sumaPrecios = sumaPrecios + actual.getDato().getPrecio()
            cantidadMedicamentos = cantidadMedicamentos + 1
        FIN SI

        actual = actual.siguiente
    }

    SI (cantidadMedicamentos == 0) ENTONCES
        RETORNAR 0.0
    FIN SI

    RETORNAR sumaPrecios / cantidadMedicamentos    // 2 decimales, redondeo HALF_UP

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Los nombres `catA`, `catB` y `catC` representan nombres de constantes de `Categoria` (por ejemplo, `ANALGÉSICO`, `ANTIBIÓTICO` y `VITAMINA`), y los precios son valores `BigDecimal`.

Estado inicial de punteros y acumuladores:

```
inicio               = N1
actual               = N1
cantidadMedicamentos = 0
sumaPrecios          = 0.0
categoria            = "catA"
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
- `N1.getDato().getCategoria().name() = "catA"`, comparado con `"catA"` → verdadero
- `sumaPrecios = 0.0 + 10.0 = 10.0`
- `cantidadMedicamentos = 1`
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio] -> [N2 | actual] -> [N3] -> null
cantidadMedicamentos = 1
sumaPrecios = 10.0
```

**Iteración 2**

Evaluación:

- `actual = N2`
- `N2.getDato().getCategoria().name() = "catB"`, comparado con `"catA"` → falso
- No cambia nada en los acumuladores
- `actual = N2.siguiente = N3`

Estado:

```
[N1 | inicio] -> [N2] -> [N3 | actual] -> null
cantidadMedicamentos = 1
sumaPrecios = 10.0
```

**Iteración 3**

Evaluación:

- `actual = N3`
- `N3.getDato().getCategoria().name() = "catA"`, comparado con `"catA"` → verdadero
- `sumaPrecios = 10.0 + 15.0 = 25.0`
- `cantidadMedicamentos = 2`
- `actual = N3.siguiente = null`

Estado:

```
[N1 | inicio] -> [N2] -> [N3] -> null | actual
cantidadMedicamentos = 2
sumaPrecios = 25.0
```

El ciclo termina porque `actual == null`.

**Comprobación final**

- `cantidadMedicamentos == 0` → falso
- `RETORNAR sumaPrecios / cantidadMedicamentos = 25.0 / 2 = 12.50` (2 decimales, `HALF_UP`)

## 6. Resultado final

Lista inicial:

```
[N1: catA, precio=10.0] -> [N2: catB, precio=20.0] -> [N3: catA, precio=15.0] -> null
```

Resultado para `PromedioPrecioPorCategoria("catA")`:

```
12.50
```

Coincide con lo esperado: `(10.0 + 15.0) / 2 = 12.5`, devuelto con 2 decimales (`12.50`). La lista no se modifica.

## 7. Casos borde

### Caso borde 1: lista vacía

```
inicio = null
```

- `actual = null`
- El ciclo no se ejecuta ni una vez.
- `cantidadMedicamentos = 0`, `sumaPrecios = 0.0`.
- La comprobación final detecta `cantidadMedicamentos == 0` y retorna `0.0`.

Resultado correcto sin necesidad de una comprobación inicial especial.

### Caso borde 2: categoría sin medicamentos

Ejemplo:

```
[N1: catA] -> [N2: catB] -> [N3: catA] -> null
```

Llamada: `PromedioPrecioPorCategoria("catC")`

- Ningún nodo coincide con `"catC"`.
- `cantidadMedicamentos = 0`, `sumaPrecios = 0.0`.
- La comprobación final detecta `cantidadMedicamentos == 0` y retorna `0.0`.

Evita la división por cero y cumple con lo que pide el enunciado.

### Caso borde 3: todos los medicamentos son de la categoría

Ejemplo:

```
[N1: catA, precio=10.0] -> [N2: catA, precio=20.0] -> [N3: catA, precio=30.0] -> null
```

Llamada: `PromedioPrecioPorCategoria("catA")`

- Los tres nodos coinciden.
- `cantidadMedicamentos = 3`, `sumaPrecios = 60.0`.
- Retorna `60.0 / 3 = 20.00`.

El promedio se calcula sobre la lista completa.

### Caso borde 4: categoría recibida como null

```
SI (categoria == null) ENTONCES
    RETORNAR 0.0
FIN SI
```

Si el parámetro llega nulo, no tiene sentido recorrer la lista buscando coincidencias. Esta guard clause se coloca al inicio del método y devuelve `0.0` inmediatamente. El resultado sería el mismo sin ella (ningún nombre de categoría es igual a `null`, así que `cantidadMedicamentos` quedaría en 0), pero la guarda deja explícita la intención y evita recorrer la lista inútilmente.

### Caso borde 5: categoría escrita con otras mayúsculas o sin tilde

Llamadas: `PromedioPrecioPorCategoria("analgésico")` o `PromedioPrecioPorCategoria("ANALGESICO")`

- La comparación con `name().equals(categoria)` es exacta, por lo que ningún nombre de constante coincide.
- `cantidadMedicamentos = 0`.
- Retorna `0.0`.

Para obtener el promedio, el nombre debe escribirse exactamente como la constante: `"ANALGÉSICO"`.

### Caso borde 6: promedio con decimales no exactos

Ejemplo:

```
[N1: catA, precio=10.00] -> [N2: catA, precio=10.00] -> [N3: catA, precio=10.01] -> null
```

Llamada: `PromedioPrecioPorCategoria("catA")`

- `cantidadMedicamentos = 3`, `sumaPrecios = 30.01`.
- `30.01 / 3 = 10.00333…`, que no tiene expansión decimal finita.
- Con escala 2 y `HALF_UP`, el método retorna `10.00` sin lanzar `ArithmeticException`.
