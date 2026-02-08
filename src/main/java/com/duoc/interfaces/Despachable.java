package com.duoc.interfaces;

/**
 * <h2>Interfaz Despachable</h2>
 * <p>
 * Define el comportamiento esencial para el inicio de la operaciones
 * de envío dentro del sistema SpeedFast.
 * </p>
 *
 * <p>
 * Las clases que implementan esta interfaz son responsables de coordinar
 * la transición de los pedidos desde la fase de almacenamiento a la fase
 * de transporte activo.
 * </p>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public interface Despachable {

    /** Inicia formalmente el proceso de despacho de pedidos hacia los repartidores. */
    void despachar();
}
