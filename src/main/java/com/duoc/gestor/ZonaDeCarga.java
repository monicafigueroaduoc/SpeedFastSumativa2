package com.duoc.gestor;
import com.duoc.modelo.Pedido;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * <h2>ZonaDeCarga - Recurso compartido (Monitor)</h2>
 *
 * <p>
 * Clase que actúa como el buffer intermedio en el patrón <b>Productor-Consumidor</b>.
 * Implementa un monitor sincronizado para gestionar el almacenamiento temporal
 * de pedidos antes de su despacho, permitiendo la comunicación entre hilos.
 * </p>
 *
 * <p>
 * <b>Control de concurrencia:</b> Utiliza bloques sincronizados y mecanismos de
 * espera pasiva (<code>wait/notifyAll</code>) para garantizar que los hilos no excedan la
 * capacidad máxima ni retiren elementos de una lista vacía.
 * </p>
 *
 * <p>
 * <b>Sistema de prioridades:</b> A diferencia de una cola FIFO convencional,
 * esta clase organiza los pedidos basándose en su nivel de urgencia antes de cada retiro,
 * asegurando un despacho eficiente.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Gestionar el acceso seguro de múltiples hilos a la lista de pedidos.</li>
 *     <li>Controlar el flujo de producción y consumo mediante estados de espera.</li>
 *     <li>Garantizar el ordenamiento por prioridad antes de cada despacho.</li>
 *     <li>Exponer el estado actual de la carga para el monitoreo del sistema.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class ZonaDeCarga {

    /** Estructura de almacenamiento interna protegida por el monitor. */
    private final List<Pedido> listaPedidos;

    /** Límite máximo de pedidos que la zona de carga puede contener */
    private final int capacidadMaxima;


    /**
     * Constructor que inicializa la zona de carga con una capacidad definida.
     *
     * @param capacidadMaxima número máximo de pedidos permitidos en bodega
     * simultáneamente.
     */
    public ZonaDeCarga(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
        this.listaPedidos = new ArrayList<>();
    }

    /**
     * <p>
     * Agrega un pedido a la lista de forma sincronizada.
     * Si la zona de carga está llena, el hilo productor entrará en estado de espera
     * hasta que se libere espacio.
     * </p>
     *
     * @param pedido el pedido generado que se desea ingresar al sistema.
     */
    public synchronized void agregarPedido(Pedido pedido) {
        // Mecanismo de bloqueo, espera mientras la bodega esté al máximo de su capacidad.
        while (listaPedidos.size() >= capacidadMaxima) {
            try {
                wait();
            } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               return;
            }
        }
        listaPedidos.add(pedido);
        System.out.println("[ZonaDeCarga] Pedido #" + pedido.getIdPedido() + " agregado");

        // Notifica a los hilos en espera (repartidores) que hay nueva carga disponible.
        notifyAll();
    }

    /**
     * <p>
     *  Retira el pedido con mayor prioridad disponible en la lista.
     * Si no hay pedidos, el hilo repartidor esperará hasta que un pedido sea agregado.
     * Antes del retiro, se realiza un ordenamiento de la lista.
     * </p>
     *
     * @return el {@link Pedido} con la prioridad más alta (ejemplo, ALTA antes que BAJA).
     */
    public synchronized Pedido retirarPedido() {
        // Mecanismo de bloqueo, espera mientras no existan pedidos para procesar.
        while (listaPedidos.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        // Lógica de prioridad: Se ordena la lista según el criterio definido en el pedido.
        // esto garantiza que el sistema respete las urgencias (Express vs Encomienda vs Comida)
        Collections.sort(listaPedidos);

        // Se extrae el primer elemento, que tras el ordenamiento es el más prioritario.
        Pedido pedido = listaPedidos.remove(0);
        System.out.println("[ZonaDeCarga] Pedido #" + pedido.getIdPedido() + " retirado");

        // Notifica a los hilos en espera (generadores) que se ha liberado un espacio en
        // la bodega.
        notifyAll();
        return pedido;
    }

    /**
     * Obtiene el número actual de pedidos que esperan ser despachados.
     *
     * @return cantidad de pedidos en la lista.
     */
    public synchronized int getCantidadPedidosPendientes() {
        return listaPedidos.size();
    }
}
