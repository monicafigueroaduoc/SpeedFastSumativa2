package com.duoc.interfaces;

/**
 * <h2>Interfaz Cancelable</h2>
 *
 * <p>
 *     Define el comportamiento para la anulación de procesos de envío dentro
 *     del sistema SpeedFast.
 * </p>
 *
 * <p> Las clases que implementen esta interfaz deben proveer la lógica necesaria
 * para detener o revertir un pedido de forma segura.
 * </p>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public interface Cancelable {

    /** Ejecuta la acción de cancelación de un pedido o proceso. */
    void cancelar();
}