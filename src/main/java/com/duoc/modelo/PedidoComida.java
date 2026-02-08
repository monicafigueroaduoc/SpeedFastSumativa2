package com.duoc.modelo;

/**
 * <h2>PedidoComida - Especialización para entregas gastronómicas</h2>
 *
 * <p>
 * Representa un pedido de tipo comida dentro del sistema SpeedFast.
 * Este tipo de pedido prioriza condiciones especiales del transporte,
 * asegurando la integridad del producto mediante el uso de mochila térmica
 * para mantener la temperatura.
 * </p>
 *
 * <p>
 * Implementa la lógica específica del patrón <b>Template Method</b> al
 * sobrescribir los pasos varaibles definidos en la clase base {@link Pedido},
 * adaptando la asignación y el cálculo de tiempos a las necesidades del rubro.
 * </p>
 *
 * <h3>Responsabilidades principales</h3>
 * <ul>
 *     <li>Gestionar la logística de asignación incluyendo validación de mochila
 *     térmica.</li>
 *     <li>Calcular el tiempo estimado de entrega basado en factores de preparación
 *     y distancia.</li>
 *     <li>Permitir la asignación manual o automática del personal de reparto.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class PedidoComida extends Pedido{

    /**
     * Constructor de un pedido de comida con sus datos básicos.
     *
     * @param idPedido identificador del pedido.
     * @param direccion dirección de entrega.
     * @param distanciaKm distancia estimada en kilómetros.
     * @param prioridad nivel de urgencia (usualmente MEDIA).
     */
    public PedidoComida(int idPedido, String direccion,
                        double distanciaKm, PrioridadPedido prioridad) {
        super(idPedido, direccion, distanciaKm, prioridad);
    }

    /**
     * Asigna automáticamente un repartidor para un pedido de comida.
     *
     * <p>
     * Este método concreta la definición abstracta de {@link Pedido#asignarRepartidor()},
     * incorporando la verificación de equipamiento necesario para el transporte de alimentos.
     * </p>
     */
    @Override
    protected void asignarRepartidor() {
        // Simula el proceso de asignación automática.
        System.out.println("Asignando repartidor...");

        // Verificación de la condición técnica obligatoria para este tipo de pedido..
        System.out.println("Verificando mochila termica...OK");
    }

    /**
     * <p>
     * Gestiona la asignación manual de un repartidor alpedido de comida.
     * Se mapoya en la implementación de la clase padre mediante <code>super</code>,
     * garantizando que el nombre del repartidor se registre correctamente en el modelo.
     * </p>
     *
     * @param nombre nombre del repartidor asignado manualmente.
     */
    @Override
    public void asignarRepartidor(String nombre) {

        super.asignarRepartidor(nombre); // Guarda el nombre en el padre.
    }

    /**
     * Calcula tiempo estimado de entrega considerando la distancia y preparación.
     *
     * <p>
     * Aplica la fórmula específica: 15 minutos base de preparación más 2 minutos por
     * cada kilómetro de distancia.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    @Override
    protected double calcularTiempoEntrega() {
        return (15 + (2 * distanciaKm));
    }
}
