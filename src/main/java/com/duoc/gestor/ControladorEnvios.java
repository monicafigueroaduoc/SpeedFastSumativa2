package com.duoc.gestor;

import com.duoc.interfaces.*;
import com.duoc.modelo.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Controlador de envíos - Gestor de historial</h2>
 *
 * <p>
 *    Administra la colección de pedidos disponibles, permite registrar
 *    las entregas realizadas y mostrar el historial por consola.
 *    Esta clase centraliza las acciones operativas del sistema mediante
 *    la implementación de las interfaces {@link Despachable}, {@link Cancelable}
 *    y {@link Rastreable}.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 *     <ul>
 *         <li>Registrar pedidos entregados en el historial.</li>
 *         <li>Despachar pedidos.</li>
 *         <li>Cancelar pedidos.</li>
 *         <li>Mostrar el historial de entregas.</li>
 *     </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class ControladorEnvios implements Despachable, Cancelable, Rastreable {

    /** Lista que almacena los pedidos ya entregados. */
    private final List<Pedido> historial;

    /**
     * <p>
     * Constructor del controlador inicializando la lista de historial.
     * Instancia que podría ser utilizada para acceder a la cola de pedidos.
     * </p>
     */
    public ControladorEnvios() {
        this.historial = new ArrayList<>();
    }

    /**
     * Registra un pedido como entregado agregándolo al historial.
     *
     * @param pedido pedido que ha sido entregado y se agregará al
     * historial.
     */
    public synchronized void registrarEntrega(Pedido pedido) {
        // Sincronización a nivel de método.
        historial.add(pedido);
        System.out.println("[Controlador] Pedido #" + pedido.getIdPedido() +
                " registrado en historial por " + pedido.getRepartidorAsignado());
    }

    /**
     * Muestra por consola un resumen del historial de entregas.
     *
     * <p>
     * Utiliza un bloque <b>Synchronized(this)</b> para bloquear el acceso a la lista
     * mientras se genera el reporte, evitando que los repartidores añadan datos a mitad
     * de proceso.
     * </p>
     */
    @Override
    public void verHistorial() {
        // Se sincroniza sobre `this` para compartir el mismo monitor que registrarEntrega.
        synchronized (this) {
            System.out.println("\n=== REPORTE DE ENTREGAS SPEEDFAST ===");

            if (historial.isEmpty()) {
                System.out.println("[Controlador] No hay registros de entregas en el historial actual.");
            } else {
                // Recorre todos los pedidos almacenados en el historial.
                for (Pedido pedido : historial) {
                    System.out.println("- " + pedido.resumenCorto());

                    // Llama al historial interno del pedido
                    pedido.verHistorial();
                }
                System.out.println("\n[Controlador] Total de pedidos procesados:"
                        + historial.size());
            }
            System.out.println("==========================================\n");
        }
    }


    /**
     * Inicia el proceso de despacho de los pedidos.
     *
     * <p>
     *     Este método simula la acción con un mensaje por consola. En una
     *     implementación futura podría conctarse con la lógica del reparto.
     * </p>
     */
    @Override
    public void despachar() {

        System.out.println("\n[Controlador] Proceso de despacho iniciado.");
    }

    /** Cancela un pedido.*/
    @Override
    public void cancelar() {

        // Simulación de cancelación de pedido.
        System.out.println("\n[Controlador] Solicitud de cancelacion recibida.");
    }
}
