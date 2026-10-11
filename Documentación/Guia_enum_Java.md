# Guía de uso de `enum` en Java

> **Objetivo:** comprender cómo declarar y utilizar enumeraciones, asociar datos a sus constantes y expresar un conjunto fijo de opciones de forma segura.
>
> **Idea clave:** cuando una variable solo debe tomar uno de varios valores conocidos, `enum` permite representar esas opciones como un tipo propio y evita muchos errores de cadenas arbitrarias.

## 1. ¿Qué es un `enum`?

Un `enum` (*enumeration* o enumeración) es un tipo de Java que define un conjunto fijo de constantes. Se usa, por ejemplo, para estados de un pedido, días de la semana, niveles de acceso, prioridades o modos de operación.

Sin una enumeración, es frecuente representar estados mediante cadenas:

```java
String estado = "PROCESNADO"; // una errata que el compilador permite

if (estado.equals("PROCESADO")) {
    System.out.println("Listo");
}
```

El error está en el dato, pero una cadena libre permite que el programa compile. Con `enum`, el compilador puede comprobar que el valor pertenezca a las constantes definidas.

## 2. Declarar una enumeración

La sintaxis básica enumera las constantes separadas por comas:

```java
public enum EstadoPedido {
    PENDIENTE,
    PROCESANDO,
    PROCESADO,
    CANCELADO
}
```

Después puedes declarar variables de ese tipo y asignar una de las constantes:

```java
EstadoPedido estado = EstadoPedido.PROCESADO;

if (estado == EstadoPedido.PROCESADO) {
    System.out.println("El pedido está procesado");
}
```

Las constantes se suelen escribir en mayúsculas y con guiones bajos entre palabras, siguiendo la convención habitual de Java.

### 2.1. Un archivo para el tipo `enum`

Cuando una enumeración es pública y se usa en varias clases, normalmente se declara en su propio archivo, con el mismo nombre que el tipo. Por ejemplo, `EstadoPedido.java` contiene `public enum EstadoPedido`.

Si la enumeración solo se utiliza dentro de una clase, también se puede declarar dentro de ella como tipo anidado.

### 2.2. Cuándo conviene y cuándo no

Usa `enum` cuando las opciones sean finitas y estén definidas por el programa. No suele ser la mejor elección para un catálogo cuyos valores pueden crearse, borrarse o administrarse dinámicamente por personas usuarias. Para esas necesidades, normalmente corresponde modelar los valores como datos, por ejemplo mediante entidades de una base de datos.

## 3. Comparar y asignar valores

Las constantes de una enumeración son instancias del tipo enum. Para comparar constantes del mismo tipo, normalmente se usa `==`:

```java
public enum NivelAcceso {
    INVITADO,
    USUARIO,
    ADMINISTRADOR
}

NivelAcceso nivel = NivelAcceso.USUARIO;

if (nivel == NivelAcceso.ADMINISTRADOR) {
    System.out.println("Acceso de administración");
}
```

El tipo de la variable restringe los valores posibles a los declarados en `NivelAcceso`. No puedes asignarle por accidente una cadena como `"ADMINISTRADOR"`; tendrías que convertir el texto explícitamente a la constante correspondiente.

También puedes usar `enum` como tipo de retorno o parámetro:

```java
public static boolean puedeEditar(NivelAcceso nivel) {
    return nivel == NivelAcceso.USUARIO
            || nivel == NivelAcceso.ADMINISTRADOR;
}

public static NivelAcceso nivelPredeterminado() {
    return NivelAcceso.INVITADO;
}
```

## 4. Usar `enum` con `switch`

`switch` es una forma común de ejecutar lógica según la constante. En un `case` de una enumeración, se escribe la constante sin repetir el nombre del tipo.

```java
EstadoPedido estado = EstadoPedido.PROCESANDO;

switch (estado) {
    case PENDIENTE:
        System.out.println("Esperando atención");
        break;
    case PROCESANDO:
        System.out.println("En curso");
        break;
    case PROCESADO:
        System.out.println("Finalizado");
        break;
    case CANCELADO:
        System.out.println("No se completará");
        break;
    default:
        throw new IllegalStateException("Estado no contemplado: " + estado);
}
```

El ejemplo usa la sintaxis clásica de `switch`, compatible con versiones antiguas de Java. Las versiones modernas también ofrecen expresiones `switch` y etiquetas con flechas (`->`); comprueba la versión configurada en el proyecto antes de utilizar esa sintaxis.

## 5. Asociar datos a las constantes

Un `enum` puede tener campos, un constructor y métodos. Esto permite asociar una etiqueta, una descripción, una prioridad numérica u otros atributos a cada constante.

```java
public enum EstadoPedido {
    PENDIENTE("Pendiente de revisión"),
    PROCESANDO("Preparando el pedido"),
    PROCESADO("Pedido completado"),
    CANCELADO("Pedido cancelado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
```

Uso:

```java
EstadoPedido estado = EstadoPedido.PROCESANDO;
System.out.println(estado.getDescripcion());
// Preparando el pedido
```

### 5.1. Por qué hay un punto y coma después de las constantes

Si el `enum` solo contiene constantes, el punto y coma final es opcional. Si se declaran campos, un constructor o métodos después de las constantes, se coloca un punto y coma para separar la lista de constantes del resto del cuerpo del tipo.

### 5.2. El constructor no se llama desde fuera

Los constructores de un enum no pueden ser públicos ni protegidos; normalmente se declaran sin modificador, como en el ejemplo. Java crea las instancias correspondientes a las constantes declaradas. No se crean constantes nuevas llamando manualmente al constructor.

### 5.3. Campos `private final`

Es una buena práctica usar campos privados y finales cuando describen propiedades estables de cada constante. De ese modo, los datos no pueden reasignarse después de construir el valor. Evita introducir estado mutable compartido sin una razón clara.

## 6. Añadir comportamiento a las constantes

Las enumeraciones también pueden definir métodos que expresen reglas del dominio:

```java
public enum EstadoPedido {
    PENDIENTE("Pendiente de revisión", false),
    PROCESANDO("Preparando el pedido", false),
    PROCESADO("Pedido completado", true),
    CANCELADO("Pedido cancelado", true);

    private final String etiqueta;
    private final boolean finalizado;

    EstadoPedido(String etiqueta, boolean finalizado) {
        this.etiqueta = etiqueta;
        this.finalizado = finalizado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public boolean estaFinalizado() {
        return finalizado;
    }
}
```

Uso:

```java
EstadoPedido estado = EstadoPedido.PROCESADO;

System.out.println(estado.getEtiqueta());
System.out.println("¿Finalizado? " + estado.estaFinalizado());
```

Centralizar esta regla en el tipo evita repetir la misma condición en múltiples partes del programa. Si el comportamiento varía radicalmente por constante, un enum puede incluso implementar un método abstracto y dar una implementación propia a cada constante; conviene hacerlo solo cuando mejore la claridad del diseño.

## 7. Métodos importantes de `Enum`

Java proporciona métodos útiles para recorrer y convertir enumeraciones.

| Método | Qué hace | Precaución |
|---|---|---|
| `values()` | Devuelve un array con todas las constantes, en el orden declarado. | No uses ese orden como identificador persistente. |
| `valueOf(String)` | Devuelve la constante cuyo nombre coincide exactamente con la cadena. | Distingue mayúsculas y minúsculas y puede lanzar `IllegalArgumentException`. |
| `name()` | Devuelve el nombre declarado de la constante. | Es un identificador técnico, no necesariamente una etiqueta para una interfaz. |
| `toString()` | Por defecto devuelve el nombre; puede sobrescribirse. | No dependas del formato mostrado si otra parte del programa puede personalizarlo. |
| `ordinal()` | Devuelve la posición de la constante, comenzando por cero. | No lo uses como identificador estable en archivos, bases de datos o protocolos. |

### 7.1. Recorrer todas las constantes

```java
for (EstadoPedido estado : EstadoPedido.values()) {
    System.out.println(estado.name());
}
```

`values()` devuelve una copia del array de constantes en el orden de declaración. Es útil, por ejemplo, para llenar una lista de opciones en una interfaz.

### 7.2. Convertir un nombre a una constante con `valueOf`

```java
EstadoPedido estado = EstadoPedido.valueOf("PROCESADO");
```

La cadena debe coincidir exactamente con el nombre de una constante. Si no existe, `valueOf` lanza `IllegalArgumentException`; si la cadena es `null`, lanza `NullPointerException`.

### 7.3. Por qué evitar `ordinal()` para persistencia

`ordinal()` representa la posición de una constante dentro de la declaración. Si alguien inserta o reordena constantes, las posiciones pueden cambiar. Guardar ese número en una base de datos puede hacer que un valor antiguo se interprete como otro estado tras una modificación del código.

Para persistencia, integraciones o protocolos, utiliza un nombre estable o un código explícito y documentado. No trates el orden de declaración como parte del contrato de datos.

## 8. Convertir texto externo de forma controlada

Las entradas externas pueden tener espacios, diferencias de mayúsculas y errores. Conviene validar el texto antes de convertirlo y decidir qué error debe recibir la capa que invoca el método.

```java
import java.util.Locale;

public static EstadoPedido parseEstado(String texto) {
    if (texto == null || texto.trim().isEmpty()) {
        throw new IllegalArgumentException("El estado es obligatorio");
    }

    try {
        return EstadoPedido.valueOf(
                texto.trim().toUpperCase(Locale.ROOT)
        );
    } catch (IllegalArgumentException ex) {
        throw new IllegalArgumentException(
                "Estado no reconocido: " + texto, ex
        );
    }
}
```

`Locale.ROOT` se usa para realizar una conversión de mayúsculas independiente de la configuración regional del usuario. Esto resulta apropiado para normalizar identificadores técnicos.

El método transforma el error a un mensaje más útil para la aplicación, conservando la excepción original como causa. En un servicio web o una interfaz, la capa de entrada puede capturar el error y convertirlo en un mensaje de validación adecuado.

No uses `toString()` para analizar texto externo si has sobrescrito ese método con una etiqueta de presentación. Usa un identificador claro —el nombre técnico o un código estable— para los intercambios de datos.

## 9. Colecciones especializadas: `EnumSet` y `EnumMap`

### 9.1. `EnumSet`: conjunto de constantes

`EnumSet` es una implementación de `Set` optimizada para enumeraciones. Es apropiada cuando quieres representar un grupo de constantes del mismo tipo.

```java
import java.util.EnumSet;

EnumSet<NivelAcceso> nivelesEdicion = EnumSet.of(
        NivelAcceso.USUARIO,
        NivelAcceso.ADMINISTRADOR
);

if (nivelesEdicion.contains(NivelAcceso.USUARIO)) {
    System.out.println("Este nivel está autorizado para editar");
}
```

Para incluir todas las constantes de un enum, puede usarse `EnumSet.allOf(TipoEnum.class)`. Para empezar con un conjunto vacío, `EnumSet.noneOf(TipoEnum.class)`.

### 9.2. `EnumMap`: asociar un dato a cada constante

`EnumMap` es una implementación de `Map` cuyas claves pertenecen a un único tipo enum. Es útil para asociar mensajes, configuraciones o contadores a estados conocidos.

```java
import java.util.EnumMap;
import java.util.Map;

Map<EstadoPedido, String> mensajes =
        new EnumMap<>(EstadoPedido.class);

mensajes.put(EstadoPedido.PENDIENTE, "Esperando atención");
mensajes.put(EstadoPedido.PROCESADO, "Pedido completado");

System.out.println(mensajes.get(EstadoPedido.PROCESADO));
```

Si el dato asociado forma parte intrínseca del significado de cada constante, considera incluirlo como campo del propio enum. Si el dato depende del contexto de la aplicación, `EnumMap` puede resultar más adecuado.

## 10. Ejemplo integrado: estado de un pedido

Este ejemplo reúne constantes, etiquetas, comportamiento y recorrido de todos los estados. El enum se coloca en un archivo `EstadoPedido.java` y la clase de demostración puede ir en `DemoPedido.java`.

**`EstadoPedido.java`**

```java
public enum EstadoPedido {
    PENDIENTE("Pendiente de revisión", false),
    PROCESANDO("Preparando el pedido", false),
    PROCESADO("Pedido completado", true),
    CANCELADO("Pedido cancelado", true);

    private final String etiqueta;
    private final boolean finalizado;

    EstadoPedido(String etiqueta, boolean finalizado) {
        this.etiqueta = etiqueta;
        this.finalizado = finalizado;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public boolean estaFinalizado() {
        return finalizado;
    }
}
```

**`DemoPedido.java`**

```java
public class DemoPedido {
    public static void main(String[] args) {
        EstadoPedido estado = EstadoPedido.PROCESADO;

        System.out.println(estado.getEtiqueta());
        System.out.println("¿Finalizado? " + estado.estaFinalizado());

        for (EstadoPedido posible : EstadoPedido.values()) {
            System.out.println(posible.name() + " -> "
                    + posible.getEtiqueta());
        }
    }
}
```

El diseño mantiene separadas tres responsabilidades: el identificador técnico (`PROCESADO`), la etiqueta legible (`Pedido completado`) y una regla de negocio (`estaFinalizado()`). La variable `estado` solo puede contener una constante válida de `EstadoPedido`.

## 11. Errores frecuentes y buenas prácticas

| Evita | Prefiere | Motivo |
|---|---|---|
| Usar cadenas libres para estados fijos | Declarar un `enum` | El compilador ayuda a limitar los valores válidos. |
| Comparar constantemente con `name()` | Comparar las constantes con `==` | Evita convertir el tipo a texto sin necesidad. |
| Guardar `ordinal()` en una base de datos | Guardar un nombre o código estable | El orden de declaración puede cambiar. |
| Usar `toString()` como protocolo de datos | Usar un identificador o código explícito | `toString()` se puede sobrescribir para presentación. |
| Usar `enum` para un catálogo dinámico administrado por usuarios | Modelar esos valores como datos | Las constantes enum se definen en el código. |
| Confiar en `valueOf()` con entrada externa sin validarla | Validar y traducir los errores | Evita mensajes técnicos poco útiles y fallos no controlados. |
| Repetir en muchas clases la misma lógica de estado | Añadir un método al enum cuando corresponda | La regla queda centralizada en el tipo que representa el estado. |
| Añadir campos mutables globales sin necesidad | Preferir campos `private final` para propiedades estables | Reduce cambios inesperados en valores compartidos. |

## 12. Regla mental para trabajar con `enum`

**Si las opciones son finitas y conocidas, exprésalas en el tipo. Si cada opción tiene datos o reglas propias, colócalos en la enumeración cuando pertenezcan a su significado. Para guardar valores fuera del programa, utiliza identificadores estables y no posiciones accidentales.**

## 13. Documentación oficial

- [`Enum` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Enum.html)
- [Java Language Specification: Enum Classes](https://docs.oracle.com/javase/specs/jls/se21/html/jls-8.html#jls-8.9)
- [`EnumSet` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/EnumSet.html)
- [`EnumMap` — Java SE API](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/EnumMap.html)
