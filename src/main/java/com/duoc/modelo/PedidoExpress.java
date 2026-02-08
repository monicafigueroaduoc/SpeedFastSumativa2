package com.duoc.modelo;

/**
 * <h2>PedidoExpress - Especialización para entregas de alta prioridad.</h2>
 *
 * <p>
 * Representa un pedido de tipo express dentro del sistema SpeedFast.
 * Este tipo de servicio está diseñado para maximizar la eficiencia ,
 * priorizando la velocidad de asignación y reduciendo los tiempos de
 * entrega al mínimo posible.
 * </p>
 *
 * <p>
 * Implementa la lógica específica del patrón <b>Template Method</b> al
 * sobrescribir los métodos abstractos de {@link Pedido}, adaptando la
 * selección de los repartidores y los algoritmos de tiempo a un modelo
 * de urgencia.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Gestionar la asignación inmediata de repartidores por cercanía.</li>
 *     <li>Calcular el tiempo de entrega basado en una tarifa plana de urgencia</li>
 *     <li>Garantizar el cumplimiento de los estándares de velocidad del servicio Express.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class PedidoExpress extends Pedido {

    /**
     * Constructor de un pedido de tipo express con sus datos básicos.
     *
     * @param idPedido identificador del pedido.
     * @param direccion direccion de la entrega.
     * @param distanciaKm distancia estimada en kilómetros.
     * @param  prioridad nivel de prioridad asignado (usualmente ALTA).
     */
    public PedidoExpress(int idPedido, String direccion, double distanciaKm, PrioridadPedido prioridad) {
        super(idPedido, direccion, distanciaKm, prioridad);

    }

    /**
     * Realiza la asignación automática de un repartidor para un pedido express.
     *
     * <p>
     * Concreta la definición de {@link Pedido#asignarRepartidor()}, simulando un
     * sistema de búsqueda por geolocalización de disponibilidad inmediata.
     * </p>
     */
    @Override
    protected void asignarRepartidor() {

        // Simulación del proceso de asignación automática para entrega urgente.
        System.out.println("Asignando repartidor...");
        System.out.println("-> Repartidor mas cercano con disponibilidad inmediata encontrado");
    }

    /**
     * <p>
     *     Sobrescribe la versión sobrecargada de asignación manual de repartidor.
     *     Se delega al comportamiento común del padre, manteniendo el polimorfismo
     *     y permitiendo futuras extensiones sin modificar la clase base.
     * </p>
     */
    @Override
    public void asignarRepartidor(String nombre) {

        super.asignarRepartidor(nombre); // Guarda el nombre en el padre.
    }

    /**
     * Calcula el tiempo estimado de entrega priorizando la rapidez.
     *
     * <p>
     * Aplica la fórmula: 10 minutos base, sumando un recargo único de 5 minutos, sólo
     * si la distancia excede los 5 km, ignorando factores de peso o preparación extendida.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    protected double calcularTiempoEntrega() {

        // Tiempo base fijo más recargo adicional si la distancia supera los 5 kilómetros.
        return (10 + (distanciaKm > 5.0 ? 5 : 0));
    }
}

