package com.duoc.concurrencia;

import com.duoc.gestor.ZonaDeCarga;


/**
 * <h2>MonitorEstado - Monitor concurrente del sistema SpeedFast</h2>
 *
 * <p>
 *     Clase encargada de supervisar periódicamente el estado de
 *     {@link ZonaDeCarga}, mostrando la cantidad de pedidos pendientes
 *     en el sistema.
 * </p>
 *
 * <p>
 *     Se ejecuta como un hilo independiente mediante la implementación de
 *     la interfaz {@link Runnable}, permitiendo la observación del sistema
 *     en tiempo real sin interferir en los procesos de generación y entrega
 *     de pedidos.
 * </p>
 *
 * <p>
 *     Forma parte del modelo concurrente del sistema, apoyando el monitoreo
 *     del patrón Productor-Consumidor.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Consultar periódicamente la cantidad de pedidos pendientes.</li>
 *     <li>Mostrar el estado actual del sistema por consola.</li>
 *     <li>Permitir su detención controlada.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class MonitorEstado implements Runnable {

    /** Zona de carga que será supervisada por el monitor. */
    public final ZonaDeCarga zonaDeCarga;

    /**
     * Controla si el monitor está activo.
     * Volatile asegura que los cambios sean visibles entre hilos.
     */
    private volatile boolean activo = true;

    /**
     * Constructor que inicializa el monitor.
     *
     * @param zonaDeCarga recurso compartido que contiene los pedidos
     * pendientes.
     */
    public MonitorEstado(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    /**
     * Detiene la ejecución en forma controlada.
     *
     * <p>
     *     Cambia el estado de la variable {@code activo} a false, lo
     *     que provoca la salida del bucle principal en el método run().
     * </p>
     */
    public void detener() {
        activo = false;
    }


    /**
     * Método principal del hilo monitor.
     *
     * <p>
     *     Ejecuta un bucle que consulta peródicamente la cantidad de pedidos
     *     pendientes en la zona de carga y muestra por consola.
     * </p>
     *
     * <p>
     *      El monitor se ejecuta hasta que:
     * </p>
     * <ul>
     *     <li>Se llame al método {@link #detener()}, o</li>
     *     <li>El hilo sea interrumpido.</li>
     * </ul>
     */
    @Override
    public void run() {
       // Bucle principal del monitor, se ejecuta mientras esté activo.
        while (activo) {

            // Obtiene la cantidad actual de pedidos pendientes.
            int pendientes = zonaDeCarga.getCantidadPedidosPendientes();

            System.out.println("[Monitor] Pedidos pendientes: " + pendientes);

            try {
                // Pausa el monitor 3 segundos antes de la siguiente verificación.
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Si el hilo es interrumpido, se restaura el estado de interrupción
                // y finaliza la ejecución del monitor.
                Thread.currentThread().interrupt();
                System.out.println("[Monitor] Interrumpido.");
                break;
            }
        }
        System.out.println("[Monitor] Finalizado.");
    }
}
