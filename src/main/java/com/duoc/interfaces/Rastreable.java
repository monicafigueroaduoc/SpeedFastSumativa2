package com.duoc.interfaces;

/**
 * <h2>Interfaz Rastreable</h2>
 *
 * <p>
 * Define el comportamiento de visualización y seguimiento de historial
 * dentro del sistema SpeedFast.
 * </p>
 *
 * <p>
 * Su objetivo principal es permitir el desacoplamiento de la lógica de reporte,
 * facilitando que diferentes componentes puedan mostrar el estado y la trayectoria
 * de los pedidos de manera estandarizada.
 * </p>
 */
public interface Rastreable {

    /** Muestra o visualiza el registro histórico de los pedidos procesados. */
    void verHistorial();
}