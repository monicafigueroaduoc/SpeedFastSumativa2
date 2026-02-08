package com.duoc.modelo;

/**
 * <h2>PedidoEncomienda - Especialización para logística de paquetería.</h2>
 *
 * <p>
 * Representa a un pedido de encomienda dentro del sistema SpeedFast. Este tipo
 * de pedido incorpora el atributo adicional {@code pesoKg}, el cual es un factor
 * determinante en la logística y el tiempo estimado de entrega.
 * </p>
 *
 * <p>
 * Implementa la lógica específica del patrón <b>Template Method</b> al sobrescribir
 * los métodos abstractos de {@link Pedido}, adaptando la asignación del personal y
 * los algoritmos de tiempo a la carga física del envío.
 * </p>
 *
 * <h3>Responsabilidades principales</h3>
 * <ul>
 *     <li>Gestionar la validación de peso y embalaje durante la asignación.</li>
 *     <li>Calcular el tiempo de entrega aplicando factores de carga (peso)
 *     y distancia.</li>
 *     <li>Mantener la integridad de los datos específicos de paquetería mediante el
 *     atributo pesoKg.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class PedidoEncomienda extends Pedido {

    /** Peso de la encomienda expresado en kilogramos. */
    private double pesoKg;

    /**
     * Constructor de un pedido de encomienda con sus datos básicos y peso asociado.
     *
     * @param idPedido identificador de pedido.
     * @param direccion dirección de entrega.
     * @param distanciaKm distancia estimada en kilometros.
     * @param pesoKg peso de la encomienda en kilogramos.
     * @param prioridad nivel de prioridad asignado (usualmente BAJA).
     */
    public PedidoEncomienda(int idPedido, String direccion,
                            double distanciaKm, double pesoKg, PrioridadPedido prioridad) {
        super(idPedido, direccion, distanciaKm,prioridad);
        this.pesoKg = pesoKg;
    }

    /**
     * Realiza la asignación automática de un repartidor para un pedido de encomienda.
     *
     * <p>
     * Concreta la definición de  {@link Pedido#asignarRepartidor()}, incorporando pasos
     * adicionales de validación técnica relacionados con el volumen y peso.
     * </p>
     */
    @Override
    protected void asignarRepartidor() {
        // Simulación del proceso de asignación automática.
        System.out.println("Asignando repartidor...");
        System.out.println("Validando peso y embalaje...OK");

    }

    /**
     * Gestiona la asignación manual de un repartidor al pedido de encomienda.
     *
     * <p>
     * Delega en la implementación de la clase base a través de <code>super</code>,
     * permitiendo que el nombre del repartidor se registre en el modelo común.
     * </p>
     *
     * @param nombre nombre del repartidor asignado manualmente.
     */
    @Override
    public void asignarRepartidor(String nombre) {

        super.asignarRepartidor(nombre); // Guarda el nombre en el padre.
    }

    /**
     * <p>
     * Calcula el tiempo estimado de entrega considerando la distancia recorrida y el peso
     * de la encomienda.
     * </p>
     *
     * <p>
     * Aplica la fórmula: 20 minutos base + (1.5 * distancia) + (0.5 * peso), penalizando
     * el tiempo de entrega según la carga.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    protected double calcularTiempoEntrega() {

        // Cálcuo del tiempo considerando distancia y peso.
        return (20 + (1.5 * distanciaKm + (0.5 * pesoKg)));
    }
}
