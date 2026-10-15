# FarmaVida

**Sistema de gestión de inventario farmacéutico · Parcial 1 · Estructuras de Datos**

[![Java](https://img.shields.io/badge/Java-8%2B-007396?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/es/)

[![Estado](https://img.shields.io/badge/Estado-completo%20(5%2F5)-green?style=flat-square)](#operaciones)

[![Último commit](https://img.shields.io/github/last-commit/RelaxUniver/Parcial-1?style=flat-square)](https://github.com/RelaxUniver/Parcial-1/commits/master/)

Aplicación de consola en **Java** (solo JDK, sin dependencias) que administra el inventario de medicamentos de una farmacia sobre una **lista simplemente enlazada** implementada desde cero.

## Problemática

La farmacia **FarmaVida** necesita controlar los medicamentos que tiene disponibles. De cada uno se conoce su código, nombre, categoría (Analgésico, Antibiótico, Antiinflamatorio o Vitamina), stock y precio. Con esos datos se debe poder saber qué medicamentos se están agotando, calcular precios promedio por categoría, reabastecer un medicamento, listar los precios en orden inverso al de ingreso y subir todos los precios en un porcentaje.

## Estructura de datos empleada

Los medicamentos se guardan en una **lista simplemente enlazada**: cada nodo (`Nodo`) contiene un `Medicamento` y una referencia al nodo siguiente, y la lista (`ListaSimple`) solo conoce el primero (`inicio`). Se eligió porque el inventario cambia con frecuencia y la lista crece sin necesidad de reservar espacio de antemano ni mover elementos. Todas las operaciones pedidas recorren los medicamentos de principio a fin, y para eso la lista es suficiente: cada una cuesta O(n).

Como solo hay enlace hacia adelante, no se puede recorrer la lista hacia atrás. Por eso, para listar los precios en orden inverso, la lista se invierte, se leen los precios y se vuelve a invertir para dejarla como estaba.

## Operaciones

Todas están en [`ListaSimple.java`](FarmaVida/src/ListaSimple.java):

| | Método | Qué hace | Documentación |
|---|---|---|---|
| a | `MedicamentosAgotados()` | Nombres de medicamentos con stock ≤ 5. | [Lógica](Documentaci%C3%B3n/L%C3%B3gica_MedicamentosAgotados.md) |
| b | `PromedioPrecioPorCategoria(String)` | Precio promedio de una categoría (2 decimales; `0.0` si no hay). | [Lógica](Documentaci%C3%B3n/L%C3%B3gica_PromedioPrecioPorCategoria.md) |
| c | `ActualizarStock(String, int)` | Suma stock a un medicamento buscado por código. | [Lógica](Documentaci%C3%B3n/L%C3%B3gica_ActualizarStock.md) |
| d | `ListarPreciosInversos()` | Precios en orden inverso, dejando la lista intacta. | [Lógica](Documentaci%C3%B3n/L%C3%B3gica_ListarPreciosInversos.md) |
| e | `AplicarAumentoGeneral(double)` | Aumenta todos los precios y devuelve cuántos modificó. | [Lógica](Documentaci%C3%B3n/L%C3%B3gica_AplicarAumentoGeneral.md) |

## Decisiones de diseño documentadas

- **Porcentaje como fracción decimal (método e).** El enunciado habla del «porcentaje dado» sin fijar el formato. Se decidió recibirlo como fracción: `0.10` = 10 %, `0.25` = 25 %. El precio se multiplica por `1 + porcentaje`. Por tanto, `AplicarAumentoGeneral(10)` aplicaría +1000 % (precio × 11), no +10 %. Las pruebas usan `0.10`.
- **Categoría como nombre exacto del enum (método b).** El parámetro debe ser el nombre de la constante, en mayúsculas y con tilde (p. ej. `"ANTIBIÓTICO"`). Otras escrituras, como `"Antibiótico"`, no coinciden y el método devuelve `0.0`.
- **Construcción de la lista.** `ListaSimple` ofrece sus *getters*/*setters* y las cinco operaciones; no define método de inserción. Las listas de prueba se arman enlazando nodos y asignando `cantidad` con `setCantidad`. Como `cantidad` puede desalinearse, ninguna operación depende de él.
- **Retornos con varios motivos (método c).** `ActualizarStock` devuelve `false` si el código no existe, pero también si es `null`, la cantidad es negativa o la suma desborda un `int`. En cada caso imprime la causa.
- **Precios inversos como arreglo (método d).** Se devuelve un `BigDecimal[]` con el tamaño exacto de la lista.
- **Precios con `BigDecimal`**, para evitar errores de redondeo del punto flotante; `Medicamento` rechaza campos `null`, stock negativo y precios negativos con `IllegalArgumentException`.

## Requisitos

- **JDK 8 o superior** ([Adoptium](https://adoptium.net/es/)).
- Opcional: Visual Studio Code con el [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

## Cómo ejecutar

```bash
git clone https://github.com/RelaxUniver/Parcial-1.git
cd Parcial-1/FarmaVida

# Compilar (UTF-8 por las tildes en identificadores como ANALGÉSICO)
javac -encoding UTF-8 -d bin src/*.java

# Ejecutar la batería de pruebas
java -cp bin Main
```

En VS Code basta con abrir la carpeta `FarmaVida/` y ejecutar `Main.java`.

> Si las tildes o flechas (`→`) se ven como `?`, usa UTF-8 en la consola (Windows: `chcp 65001`).

## Casos de prueba ejecutados

[`Main.java`](FarmaVida/src/Main.java) ejecuta **27 casos** (7 positivos, 15 negativos y 5 borde), un bloque independiente por operación, con los datos sugeridos por la guía (M01–M05). Cada caso imprime el estado antes, la llamada, el resultado **esperado** y el **obtenido**; se verifica comparándolos. En la última ejecución todos coincidieron con lo esperado. La salida completa está en el [informe](Documentaci%C3%B3n/Informe_FarmaVida.pdf).

| Op. | Tipo | Caso |
|---|---|---|
| a | positivo | Algunos medicamentos agotados |
| a | negativo | Lista vacía |
| a | negativo | Ningún medicamento con stock ≤ 5 |
| a | borde | Todos los medicamentos agotados |
| b | positivo | Categoría con 2 medicamentos (13.50) |
| b | positivo | Categoría con 1 medicamento (2.50) |
| b | negativo | Categoría sin medicamentos |
| b | negativo | Lista vacía |
| b | negativo | Categoría `null` |
| c | positivo | Código existente (M02: 3 + 10) |
| c | positivo | Cantidad cero |
| c | negativo | Código inexistente |
| c | negativo | Cantidad negativa |
| c | negativo | Código `null` |
| c | negativo | Lista vacía |
| c | borde | La suma excede el stock máximo |
| d | positivo | Lista con 5 medicamentos |
| d | negativo | Lista vacía |
| d | borde | Lista con un solo nodo |
| d | borde | `cantidad` no coincide con los nodos |
| e | positivo | Aumento del 10 % (`0.10`) |
| e | negativo | Porcentaje negativo |
| e | negativo | Porcentaje cero |
| e | negativo | Lista vacía |
| e | negativo | Porcentaje `NaN` |
| e | negativo | Porcentaje infinito |
| e | borde | Lista con un solo nodo |

La verificación es visual (no se usa JUnit). Las validaciones de `Medicamento` (stock o precio negativo, campos `null`) están implementadas, pero la batería no tiene casos propios para ellas.

## Estructura

```
Parcial-1/
├── Documentación/        # Lógica de cada operación, guías de BigDecimal y enum, informe (PDF)
└── FarmaVida/
    └── src/              # Categoria, Medicamento, Nodo, ListaSimple, Main
```

`bin/` se genera al compilar y no se versiona.

## Presentación para la exposición oral

La presentación de la exposición es el [**informe del equipo (PDF)**](Documentaci%C3%B3n/Informe_FarmaVida.pdf), que sigue el orden de la exposición:

1. Problemática y modelo de datos (secciones 1–2)
2. Estructura de datos empleada y arquitectura (secciones 3–4)
3. Decisiones de diseño documentadas (sección 4)
4. Razonamiento de cada método, con su responsable (sección 5)
5. Pruebas y resultados de la ejecución (sección 6)
6. Declaración de uso de IA, reflexión y conclusiones (secciones 7–9)

## Documentación

- [`Documentación/`](Documentaci%C3%B3n): un documento por operación (idea general, pseudocódigo, trazado de punteros y casos borde), más guías de [`BigDecimal`](Documentaci%C3%B3n/Guia_BigDecimal_Java.md) y [`enum`](Documentaci%C3%B3n/Guia_enum_Java.md).
- [`Informe_FarmaVida.pdf`](Documentaci%C3%B3n/Informe_FarmaVida.pdf): informe y presentación del equipo (ver la sección anterior).

## Declaración de uso de Inteligencia Artificial

El equipo declara el uso de asistentes de IA (**Claude, DeepSeek y Gemini**) como apoyo académico. La IA no generó el código a ciegas ni sustituyó el razonamiento del equipo: cada integrante diseñó y trazó a mano su método antes de programarlo.

| Parte del proyecto | Uso de la IA |
|---|---|
| Diseño y código de las operaciones | Diseño manual del equipo; sin generación de código a ciegas. |
| Casos borde | Apoyo para identificarlos (desbordamiento de `int`, `NaN` e infinitos, división por cero). |
| Pseudocódigo | Revisión socrática de la lógica ya diseñada por los integrantes. |
| Pruebas (`Main.java`) | Apoyo en la elaboración de la batería; el equipo la ejecutó y verificó los resultados. |
| Guías de `BigDecimal` y `enum` | Apoyo en su elaboración. |
| Redacción de la documentación | Apoyo en estructura y corrección de estilo. |
| Refactorización | No se declara uso de IA. |
| README e informe PDF (octubre de 2026) | Redactados con **Claude (Anthropic)** a partir del código del repositorio y de la ejecución real de las pruebas, y revisados por el equipo. |

El uso fue de apoyo y no se mide de forma cuantitativa. El equipo es responsable del contenido final.

## Equipo

| Integrante | Operación |
|---|---|
| Jorge Luis Reyes Pérez | (a) `MedicamentosAgotados()` |
| Geonel Sánchez | (b) `PromedioPrecioPorCategoria(String)` |
| Merlyn Sosa Borges | (c) `ActualizarStock(String, int)` |
| Yaisel Pinos Durán | (d) `ListarPreciosInversos()` |
| Ray Sanjudo Mesa | (e) `AplicarAumentoGeneral(double)` |

---

*Proyecto de uso académico · Parcial 1 · Estructuras de Datos · Curso 2026-2027*