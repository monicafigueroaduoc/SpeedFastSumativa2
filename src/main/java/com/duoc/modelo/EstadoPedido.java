package com.duoc.modelo;

/**
 * <h2>EstadoPedido - Estados del ciclo de vida</h2>
 *
 * <p>
 * Define los estados posibles de un pedido dentro del sistema SpeedFast.
 * El uso de este Enum garantiza la integridad de los datos, evitando estados
 * inválidos y mejorando la mantenibilidad del sistema.
 * </p>
 *
 * @author Monica figueroa
 * Version 1.0
 */
public enum EstadoPedido {
    /** El pedido está registrado pero aún no ha sido procesado. */
    PENDIENTE,
    /** El pedido está en manos de un repartidor hacia su destino. */
    EN_REPARTO,
    /** El pedido fue entregado satisfactoriamente. */
    ENTREGADO,
    /** El pedido ha sido anulado y no se procesará. */
    CANCELADO
}