package com.duoc.modelo;

/**
 * <h2>PrioridadPedido - Niveles de urgencia del sistema</h2>
 *
 * <p>
 * Enum que representa la prioridad lógica de un pedido dentro del sistema SpeedFast.
 * Define la jerarquía de importancia para el procesamiento y despacho de las unidades
 * de carga.
 * </p>
 *
 * <p>
 * A diferencia de un sistema puramente secuencial, este enum permite que el sistema
 * de despacho identifique y adelante pedidos con mayor criticidad durante el proceso
 * de ordenamiento en la zona de carga.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 *     <ul>
 *         <li>Categorizar la urgencia de los pedidos (ALTA, MEDIA y BAJA).</li>
 *         <li>Servir como criterio de ordenamiento para la interfaz <code>Comparable</code>.</li>
 *         <li>Facilitar la toma de decisiones en el despacho concurrente.</li>
 *     </ul>
 *
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public enum PrioridadPedido {
    /**
     * Prioridad máxima. Los pedidos con este nivel se anteponen en la cola de despacho
     * para una entrega inmediata.
     */
    ALTA,
    /**
     * Prioridad estándar. Refleja el flujo normal de pedidos del sistema sin requerimientos
     * de urgencia extrema.
     */
    MEDIA,
    /**
     * Prioridad reducida. Pedidos que pueden esperar en la zona de carga hasta que existan
     * pedidos de mayor importancia.
     */
    BAJA
}
