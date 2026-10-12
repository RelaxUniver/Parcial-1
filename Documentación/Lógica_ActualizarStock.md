## 1. Idea general

Se tienen los siguientes elementos principales para buscar el medicamento y actualizar su stock:

| Elemento | Función |
|---|---|
| inicio | Referencia al primer nodo de la lista de medicamentos. |
| actual | Puntero auxiliar que recorre la lista nodo a nodo. |
| codigo | Parámetro de entrada: el código del medicamento a actualizar. |
| cantidadAgregada | Parámetro de entrada: la cantidad que se suma al stock actual. |
| aumento | Variable auxiliar que guarda el resultado de la suma antes de asignarlo al nodo. |

La operación consiste en recorrer la lista desde el inicio hasta `null`, comparando el código de cada nodo con el código recibido. Si se encuentra coincidencia, se calcula el nuevo stock sumando `cantidadAgregada` al stock actual, se actualiza el nodo y se retorna `true`. Si el ciclo termina sin encontrar el medicamento, se retorna `false`.

## 2. Aviso Importante

### Significados del valor `false`

El método devuelve `false` en cuatro situaciones distintas:

1. El código recibido es `null`.
2. La cantidad recibida es negativa (no tiene sentido restar stock con este método).
3. El medicamento con ese código no existe en la lista.
4. La suma excedería `Integer.MAX_VALUE`, el mayor stock representable por el tipo `int`.

Como todos los casos devuelven el mismo valor booleano, quien llame al método no puede distinguirlos solo con el retorno. Por eso, se imprime un mensaje informativo antes de retornar:

```
ESCRIBIR "El código no existe, no se realizarán cambios"
ESCRIBIR "La cantidad es negativa, se rechaza la solicitud"
ESCRIBIR "Medicamento no encontrado"
ESCRIBIR "El stock excede el máximo permitido"
```

Esto no cambia el retorno, pero le permite al usuario saber qué pasó realmente.

## 3. Pseudocódigo

```
PROCEDIMIENTO ActualizarStock(codigo, cantidadAgregada)

    SI (codigo == null) ENTONCES
        ESCRIBIR "El código no existe, no se realizarán cambios"
        RETORNAR false
    FIN SI

    SI (cantidadAgregada < 0) ENTONCES
        ESCRIBIR "La cantidad es negativa, se rechaza la solicitud"
        RETORNAR false
    FIN SI

    actual = inicio

    CICLO (actual != null) {

        SI (actual.getCodigo().equals(codigo)) ENTONCES
            aumento = actual.getStock() + cantidadAgregada usando un tipo que no desborde
            SI (aumento > Integer.MAX_VALUE) ENTONCES
                ESCRIBIR "El stock excede el máximo permitido"
                RETORNAR false
            FIN SI
            actual.setStock((int) aumento)
            RETORNAR true
        FIN SI

        actual = actual.siguiente
    }

    ESCRIBIR "Medicamento no encontrado"
    RETORNAR false

FIN PROCEDIMIENTO
```

## 4. Ejemplo de ejecución

Lista inicial:

```
[N1: c1, n1, catA, stock=3, precio=10.0] -> [N2: c2, n2, catB, stock=8, precio=20.0] -> [N3: c3, n3, catA, stock=2, precio=15.0] -> null
```

Llamada que vamos a trazar:

```
ActualizarStock("c2", 5)
```

Estado inicial de punteros y parámetros:

```
inicio           = N1
actual           = N1
codigo           = "c2"
cantidadAgregada = 5
```

Representación gráfica:

```
[N1 | inicio, actual] -> [N2] -> [N3] -> null
```

## 5. Trazado por iteraciones

**Validación 1: código no nulo**

- `codigo = "c2"` → no es null, continúa.

**Validación 2: cantidad no negativa**

- `cantidadAgregada = 5` → `5 < 0` es falso, continúa.

**Iteración 1**

Condición:

```
SI (actual != null) ENTONCES
```

Evaluación:

- `actual = N1`
- `N1.getCodigo() = "c1"`, comparado con `"c2"` → falso
- `actual = N1.siguiente = N2`

Estado:

```
[N1 | inicio] -> [N2 | actual] -> [N3] -> null
```

**Iteración 2**

Evaluación:

- `actual = N2`
- `N2.getCodigo() = "c2"`, comparado con `"c2"` → verdadero
- `aumento = N2.getStock() + 5 = 8 + 5 = 13`
- `N2.setStock(13)`
- `RETORNAR true`

Estado:

```
[N1 | inicio] -> [N2 | actual, stock=13] -> [N3] -> null
```

El método termina en este punto, sin recorrer el resto de la lista.

## 6. Resultado final

Lista inicial:

```
[N1: c1, stock=3] -> [N2: c2, stock=8] -> [N3: c3, stock=2] -> null
```

Llamada:

```
ActualizarStock("c2", 5)
```

Resultado del método:

```
true
```

Lista después de la actualización:

```
[N1: c1, stock=3] -> [N2: c2, stock=13] -> [N3: c3, stock=2] -> null
```

Coincide con lo esperado: el stock de N2 pasó de 8 a 13.

## 7. Casos borde

### Caso borde 1: lista vacía

```
inicio = null
```

- `actual = null`
- El ciclo no se ejecuta ni una vez.
- Se imprime `"Medicamento no encontrado"`.
- Retorna `false`.

Resultado correcto: no hay nada que actualizar.

### Caso borde 2: medicamento no existe

Ejemplo:

```
[N1: c1] -> [N2: c2] -> [N3: c3] -> null
```

Llamada: `ActualizarStock("c9", 5)`

- Ningún nodo coincide con `"c9"`.
- El ciclo recorre los tres nodos.
- Se imprime `"Medicamento no encontrado"`.
- Retorna `false`.

La lista queda intacta.

### Caso borde 3: código recibido como null

```
SI (codigo == null) ENTONCES
    ESCRIBIR "El código no existe, no se realizarán cambios"
    RETORNAR false
FIN SI
```

Si el código es `null`, no tiene sentido recorrer la lista buscando coincidencias. Esta guard clause se coloca al inicio del método y devuelve `false` inmediatamente, imprimiendo el mensaje correspondiente.

### Caso borde 4: cantidad negativa

Llamada: `ActualizarStock("c2", -3)`

- `cantidadAgregada = -3`, y `-3 < 0` es verdadero.
- Se imprime `"La cantidad es negativa, se rechaza la solicitud"`.
- Retorna `false` sin modificar la lista.

### Caso borde 5: cantidad igual a cero

Llamada: `ActualizarStock("c2", 0)`

- `cantidadAgregada = 0`, y `0 < 0` es falso, así que continúa.
- Se busca el medicamento normalmente.
- Si existe, `aumento = stock + 0 = stock`, se asigna el mismo valor y retorna `true`.

Es una operación sin efecto real, pero válida: el stock no cambia y el método confirma que el medicamento existe.

### Caso borde 6: suma superior al máximo de `int`

Si el stock actual más `cantidadAgregada` supera `Integer.MAX_VALUE`, el método imprime `"El stock excede el máximo permitido"`, retorna `false` y conserva el stock original. La suma se calcula como `long` antes de convertirla a `int`, para evitar que el desbordamiento produzca un stock negativo.