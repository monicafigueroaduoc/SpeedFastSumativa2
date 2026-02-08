package com.duoc.concurrencia;

import com.duoc.gestor.ControladorEnvios;
import com.duoc.gestor.ZonaDeCarga;
import com.duoc.modelo.EstadoPedido;
import com.duoc.modelo.Pedido;

import java.util.Random;

/**
 * <h2>Repartidor - Consumidor concurrente de pedidos</h2>
 *
 * <p>
 *     Clase que representa un repartidor dentro del sistema SpeedFast.
 *     Actúa como el componente <b>Consumidor</b>  en el patrón Productor-Consumidor,
 *     retirando pedidos desde la {@link ZonaDeCarga} y ejecutando su entrega.
 * </p>
 *
 * <p>
 *     Se ejecuta como un hilo independiente mediante la interfaz {@link Runnable},
 *     permitiendo procesar múltiples pedidos en paralelo.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Retirar pedidos desde la zona de carga.</li>
 *     <li>Asignarse como repartidor del pedido.</li>
 *     <li>Procesar el pedido usando Template Method.</li>
 *     <li>Actualizar el estado del pedido.</li>
 *     <li>Registrar la entrega en el historial.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class Repartidor implements Runnable{

    /** Nombre del repartidor.*/
    private final String nombre;

    /** Zona de carga desde donde se retiran los pedidos.*/
    private final ZonaDeCarga zonaDeCarga;

    /** Controlador que registra las entregas realizadas. */
    private final ControladorEnvios controladorEnvios;

    /** Generador aleatorio para simular tiempos de entrega. */
    private final Random random = new Random();

    /**
     * Constructor que inicializa el repartidor con sus dependencias.
     *
     * @param nombre nombre del repartidor
     * @param zonaDeCarga zona de carga compartida
     * @param controladorEnvios controlador que registra entregas
     */
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga,
                      ControladorEnvios controladorEnvios) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.controladorEnvios = controladorEnvios;
    }

    /**
     * Método principal del hilo repartidor.
     *
     * <p>
     *     Retira pedidos desde la zona de carga, los procesa y registra su
     *     entrega hasta que el hilo es interrumpido o no hay más pedidos.
     * </p>
     */
    @Override
    public void run() {
        System.out.println("[Repartidor - " + nombre + "] Inicia su turno");

        // Bucle principal, se ejecuta mientras el hilo no sea interrumpido.
        while (!Thread.currentThread().isInterrupted()) {

            // Paso 1: Intenta retirar un pedido. Si la lista está vacía, el hilo
            // esperará automáticamente en la zona de carga hasta  que llegue uno.
            Pedido pedido = zonaDeCarga.retirarPedido();

            // Si no hay pedidos disponibles, finaliza el turno.
            if (pedido == null) {
                break;
            }

            try {

                // Paso 2: Cambia estado usando ENUM.
                pedido.setEstado(EstadoPedido.EN_REPARTO);

                pedido.asignarRepartidor(nombre);

                // Se imprime la prioridad para validar el sistema de prioridades
                // real implementado.
                System.out.println("[Repartidor - " + nombre +
                        "] pedido #" + pedido.getIdPedido() +
                        " [" + pedido.getPrioridad() + "] EN_REPARTO");

                // Paso 3: Procesa el pedido usando Template Method.
                pedido.procesarPedido();

                // Simula el tiempo de entrega entre 2 y 5 segundos.
                Thread.sleep(random.nextInt(3000) + 2000);

                // Paso 4: Estado final usando ENUM
                pedido.setEstado(EstadoPedido.ENTREGADO);

                // Se registra la entrega en el historial.
                controladorEnvios.registrarEntrega(pedido);
                System.out.println("[Repartidor - " + nombre +
                        "] pedido #" + pedido.getIdPedido() +
                        " ENTREGADO");

            } catch (InterruptedException e) {

                // Si el hilo es interrumpido, se restaura el estado y finaliza.
                System.out.println("[Repartidor - " + nombre + "] Interrumpido");

                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("[Repartidor - " + nombre + "] Finaliza su turno");
    }
}
