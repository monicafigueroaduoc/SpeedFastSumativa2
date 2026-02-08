# Sistema de Gestión de Pedidos SpeedFast

Proyecto desarrollado en Java que simula un sistema logístico de alto rendimiento.
Esta versión evoluciona el sistema previo integrando **Programación multihilo (concurrencia)**, el patrón `Productor-Consumidor`y un sistema de `Priorización dinámica`.
La aplicación demuestra cómo gestionar múltiples repartidores procesando pedidos simultáneamente desde una zona de carga compartida, manteniendo la integridad de los datos y una sincronización precisa en la consola.
---

## Descripción del proyecto

El sistema SpeedFast ahora opera bajo un modelo de concurrencia avanzada:
- Productores: Un generador de pedidos que inyecta carga al sistema de forma progresiva.
- Consumidores: Hilos de repartidor que compiten por pedidos, respetando niveles de prioridad.
- Monitoreo: Un hilo MonitorEstado que supervisa la carga del sistema en tiempo real sin interrumpir el flujo principal.
- Gestión de pedidos: Especialización polimórfica para Comida, Encomienda y Express, con lógica de negocio y tiempos de entrega diferenciados.


---

## Objetivos del proyecto

- **Dominio de Concurrencia**: Implementación robusta de `Thread`, `Runnable`, y administración mediante `ExecutorService`.
- **Patrón Productor-Consumidor**: Gestión de una cola de pedidos (ZonaDeCarga) para desacoplar la generación de la entrega, permitiendo que la producción y el consumo ocurran a ritmos independientes.
- **Sincronización**: Aplicar bloques `Synchronized`y mecanismos de comunicación entre hilos (wait/notifyAll) para evitar cindiciones de carrera.
- **Ordenamiento por Prioridad**: Implementación de la interfaz Comparable para asegurar que los pedidos con prioridad `ALTA` se despachen antes que los de prioridad `BAJA`.
- **Patrón Template Method**: Estandarizar el algoritmo de procesamiento de pedidos en un entorno concurrente.
- **Documentación**: Mantener el estándar profesional mediante un Javadoc detallado.

---

### Conceptos aplicados
- Multihilos.
- Sincronización de hilos (Thread-Safety).
- Pool de hilos (Executor Service).
- Arquitectura de Interfaces.

---

## Justificación técnica del diseño

### 1. Eficiencia y paralelismo

- `Contribución:` El paso de una ejecución secuencial a una concurrente permite que múltiples pedidos se procesen al mismo tiempo.
- `Impacto:`Se reduce drásticamente el tiempo de inactividad del sistema, simulando un entorno logístico real donde varios operadores trabajan en paralelo.
### 2. Sincronización y robustez
- `Contribución:` Uso de `synchronized`en métodos críticos y en la gestión del historial.
- `Impacto`: Se evita la corrupción de los datos y el caos en la consola (mensajes mezclados) cuando se acceden a recursos compartidos.
### 3. Gestión de recursos
- `Contribución:` Uso de **ExecutorService** con un pool de hilos fijos y cierre controlado mediante shutdown().
- `Impacto:` Previene el agotamiento de memoria del sistema y asegura que todos los procesos terminen antes de mostrar el reporte final.


## Estructura del proyecto
```SpeedFastSumativa2
├ .idea/
├ .mvn/
├ docs/
├ src/
|  └ main/
|     ├ java/
|     |  └ com.duoc/
|     |     ├ app/
|     |     |  └ Main.java
|     |     ├ concurrencia/
|     |     |  ├ GeneradorPedidos.java
|     |     |  ├ MonitorEstado.java
|     |     |  └ Repartidor.java
|     |     ├ gestor/
|     |     |  ├ ControladorEnvios.java
|     |     |  └ ZonaDeCarga.java
|     |     ├ interfaces/
|     |     |  ├ Cancelable.java
|     |     |  ├ Despachable.java
|     |     |  └ Rastreable.java
|     |     ├ modelo/
|     |     |  ├ EstadoPedido.java
|     |     |  ├ Pedido.java
|     |     |  ├ PedidoComida.java
|     |     |  ├ PedidoEncomienda.java
|     |     |  ├ PedidoExpress.java
|     |     |  └ PrioridadPedido.java
├ test/
├ target/
├ .gitignore
├ pom.xml
└ README.md
```

## Instrucciones para clonar y ejecutar el proyecto
1. Clonar el repositorio desde GitHub.
```bash
Git clone [https://github.com/monicafigueroaduoc/SpeedFastSumativa2.git]
```
2. Abre el proyecto en IntelliJ IDEA.
3. Ejecutar la clase com.duoc.app.Main
4. La aplicación se ejecutará por consola, mostrando:
- El inicio del monitor y los hilos repartidores.
- La generación dinámica de pedidos.
- El procesamiento prioritario de la carga.
- El reporte final consolidado con todos los pedidos en estado [ENTREGADO].

## Autor del proyecto
_**Nombre completo:** Mónica Figueroa
_**Sección:** 2
_**Analista Programador Computacional**

