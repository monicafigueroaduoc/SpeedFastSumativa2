package com.duoc.app;

import com.duoc.gestor.ControladorEnvios;
import com.duoc.gestor.ZonaDeCarga;
import com.duoc.concurrencia.GeneradorPedidos;
import com.duoc.concurrencia.Repartidor;
import com.duoc.modelo.PedidoComida;
import com.duoc.modelo.PedidoEncomienda;
import com.duoc.modelo.PedidoExpress;
import com.duoc.modelo.PrioridadPedido;
import com.duoc.concurrencia.MonitorEstado;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 * <h2>Sistema SpeedFast - Clase principal</h2>
 *
 * <p>
 *    Punto de entrada del sistema de gestión de envíos <b>SpeedFast</b>.
 *    Esta clase es responsable de inicializar los componentes principales,
 *    coordinar la ejecución concurrente de los hilos y gestionar el ciclo
 *    de vida completo de la aplicación.
 * </p>
 *
 * <p>
 *     El sistema implementa un modelo concurrente basado en el patrón
 *     <b>Productor - Consumidor</b>, donde los pedidos son generados,
 *     almacenados en una zona de carga compartida y procesados por múltiples
 *     repartidores al mismo tiempo.
 * </p>
 *
 * <h2> Componentes principales inicializados:</h2>
 * <ul>
 *     <li><b>{@link ZonaDeCarga}:</b> Recurso compartido que almacena los pedidos
 *     pendientes utilizando una cola priorizada y sincronizada.</li>
 *
 *     <li><b>{@link ControladorEnvios}:</b> Responsable de registrar las entregas y
 *     mantener el historial del sistema.</li>
 *
 *     <li><b>{@link GeneradorPedidos}:</b> Hilo consumidores que retiran pedidos desde
 *     la zona de carga y ejecutan su procesamiento y entrega.</li>
 *
 *     <li><b>{@link ExecutorService}:</b> Pool de hilos que gestiona la ejecución
 *     concurrente de todos los componentes activos</li>
 * </ul>
 *
 * <h2>Patrones y conceptos aplicados:</h2>
 * <ul>
 *     <li>Patrón Productor-Consumidor</li>
 *     <li>Patrón Template Method</li>
 *     <li>Programación concurrente con ExecutorService</li>
 *     <li>Sincronización mediante monitores y métodos synchronized</li>
 * </ul>
 *
 * <h2>Flujo general de ejecución</h2>
 * <ol>
 *     <li>Inicialización de recursos compartidos.</li>
 *     <li>Creación de repartidores (consumidores).</li>
 *     <li>Carga inicial manual de pedidos.</li>
 *     <li>Inicio del generador automático de pedidos (productor).</li>
 *     <li>Inicio del monitor de estado.</li>
 *     <li>Ejecución concurrente mediante pool de hilos.</li>
 *     <li>Finalización controlada del sistema.</li>
 *     <li>Visualización del historial de entregas.</li>
 * </ol>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada de la aplicación.
     *
     * <p>
     *    Este método inicializa todos los componentes del sistema, ejecuta el
     *    procesamiento concurrente de pedidos y coordina el cierre ordenado
     *    de los recursos.
     * </p>
     *
     * <p>
     *     Acciones que realiza:
     * </p>
     *
     * <ol>
     *     <li>Crea la zona de carga compartida.</li>
     *     <li>Inicializa el controlador de envíos.</li>
     *     <li>Configura el pool de hilos.</li>
     *     <li>Crea repartidores.</li>
     *     <li>Agrega pedidos iniciales manualmente.</li>
     *     <li>Inicia el generador automático de pedidos.</li>
     *     <li>Inicia el monitor de estado del sistema.</li>
     *     <li>Ejecuta todos los hilos concurrentemente.</li>
     *     <li>Espera la finalización del procesamiento.</li>
     *     <li>Muestra el historial final de entregas.</li>
     * </ol>
     *
     * @param args argumentos de la línea de comando (no utilizados)
     */
    public static void main(String[] args) {

        // ==============================
        // 1. Zona de descarga (Recurso compartido)
        // ==============================
        ZonaDeCarga zonaDeCarga= new ZonaDeCarga(10);

        // ==============================
        // 2. Controlador de envíos
        // ==============================
        ControladorEnvios controlador = new ControladorEnvios();

        // ===============================
        // 3. Pool de hilos
        //================================
        ExecutorService  executor = Executors.newFixedThreadPool(5);


        // ===============================
        // 4. Crear repartidores
        // ===============================
        Repartidor r1 = new Repartidor("Rogelio Hernandez", zonaDeCarga, controlador);
        Repartidor r2 = new Repartidor("Cecilia Matamala", zonaDeCarga, controlador);
        Repartidor r3 = new Repartidor("Rafael Bravo", zonaDeCarga, controlador);

        // ===============================
        // 5. Agregar pedidos
        // ===============================
        zonaDeCarga.agregarPedido(new PedidoComida(1001,
                "Av. Las Rosa 1470", 2, PrioridadPedido.ALTA));
        zonaDeCarga.agregarPedido(new PedidoExpress(1002,
                "Av. Manuel Rodriguez 780", 5, PrioridadPedido.MEDIA));

        zonaDeCarga.agregarPedido(new PedidoComida(1003,
                "Los Carrera 1890", 4, PrioridadPedido.ALTA));
        zonaDeCarga.agregarPedido(new PedidoEncomienda(1004,
                "Av Paicavi 1250", 6, 3, PrioridadPedido.BAJA));

        zonaDeCarga.agregarPedido(new PedidoExpress(1005,
                "Av Ohiggins 940", 3, PrioridadPedido.MEDIA));
        zonaDeCarga.agregarPedido(new PedidoComida(1006,
                "San Martin 520", 1, PrioridadPedido.ALTA));

        // ===================================
        // 6. Crear generador automático
        // ===================================
        GeneradorPedidos generador = new GeneradorPedidos(zonaDeCarga, 6);

        // ===================================
        // 7. Crear monitor de estado
        // ===================================
        MonitorEstado monitor = new MonitorEstado(zonaDeCarga);

        // ===================================
        // 8. Ejecutar hilos
        // ===================================
        executor.execute(r1);
        executor.execute(r2);
        executor.execute(r3);
        executor.execute(generador);
        executor.execute(monitor);

        // ===================================
        // 9. Cierre ordenado
        // ===================================
        // Indica al pool que no aceptará nuevas tareas, pero permitirá que las
        // tareas actuales finalicen.
        executor.shutdown();

        try {
            // Espera hasta 60 segundos a que todos los hilos terminen su ejecución.
            // Bloquea el hilo principal hasta que todos los hilos terminan, o
            // se cumple el tiempo límite, o el hilo principal es interrumpido.
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {

                // Si los hilos no terminaron en tiempo, se fuerza el cierre inmediato.
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
        // Si el hilo principal es interrumpido, se fuerza el cierre inmediato.
            executor.shutdownNow();
            // Restaura el estado de interrupción del hilo.
            Thread.currentThread().interrupt();
        }

        // =======================================
        // 10. Mostrar mensaje final
        // =======================================

        System.out.println("\nTodos los pedidos han sido entregados correctamente");

        // =========================================
        // 10. Mostrar historial
        // =========================================
        controlador.verHistorial();
    }
}



