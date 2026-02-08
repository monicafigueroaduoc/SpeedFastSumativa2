package com.duoc.modelo;

import com.duoc.interfaces.Cancelable;
import com.duoc.interfaces.Despachable;
import com.duoc.interfaces.Rastreable;

/**
 * <h2>Pedido - Clase base y Template del sistema de entregas</h2>
 * <p>
 * Clase abstracta que representa la entidad fundamental dentro del
 * sistema SpeedFast.
 * Contiene atributos y comportamientos comunes a todos los tipos de pedidos,
 * actuando como la base del modelo de datos.
 * </p>
 *
 * <p>
 * Implementa el patrón de diseño <b>Template Method</b> a través del método
 * {@link #procesarPedido()}, donde define el flujo general de procesamiento
 * de un pedido, mientras se delega los pasos variables a las subclases.
 * </p>
 *
 * <p>
 * Se integra con el modelo concurrente mediante la implementación de {@link Comparable}
 * permitiendo el ordenamiento por prioridad dentro de la zona de carga compartida.
 * </p>
 *
 * <h3>Responsabilidades principales</h3>
 * <ul>
 *     <li>Gestionar el ciclo de vida del pedido (Estados).</li>
 *     <li>Definir el flujo de procesamiento mediante Template Method</li>
 *     <li>Proveer mecanismos de comparación para la priorización.</li>
 *     <li>Implementar las interfaces de control (Cancelable, Rastreable,
 *     Despachable)</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public abstract class Pedido implements Cancelable, Rastreable, Despachable, Comparable<Pedido> {


    /** Identificador único del pedido. */
    protected int idPedido;

    /** Dirección de entrega del pedido. */
    protected String direccion;

    /** Distancia estimada en kilómetros hasta destino. */
    protected double distanciaKm;

    /** Nombre del repartidor asignado al pedido. */
    protected String repartidorAsignado;

    /** Estado actual del pedido dentro de su ciclo de vida*/
    protected EstadoPedido estado = EstadoPedido.PENDIENTE;

    /** Prioridad lógica del pedido.*/
    protected PrioridadPedido prioridad;

    /**
     * Constructor del pedido con datos básicos.
     * @param idPedido identificador del pedido.
     * @param direccion dirección de entrega
     * @param distanciaKm distancia estimada en kilómetros.
     * @param prioridad nivel de prioridad del pedido.
     */
    public Pedido( int idPedido, String direccion, double distanciaKm,
                   PrioridadPedido prioridad) {
        this.idPedido = idPedido;
        this.direccion = direccion;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.prioridad = prioridad;
    }

    //===================== INTERFACES======================
    /**
     * <p>
     *     Implementación del comportamiento de la interfaz {@link Despachable}.
     *     Indica que el pedido ha salido hacia destino.
     * </p>
     */
    @Override
    public void despachar() {
        this.estado = EstadoPedido.EN_REPARTO;
        System.out.println("[Pedido #" + idPedido + "] Saliendo de bodega hacia destino.");
    }

    /**
     * <p>
     *     Implementación del comportamiento de la interfaz {@link Cancelable}.
     *     Permite al objeto gestionar su propia anulación, cambiando su estado.
     * </p>
     */
    @Override
    public void cancelar() {
        this.estado = EstadoPedido.CANCELADO;
        System.out.println("\n -> El pedido #" + idPedido +
                " ha sido anulado por el cliente.\n");
    }

    /**
     * <p>
     *     Implementación del comportamiento de la interfaz {@link Rastreable}.
     *     Muestra la trazabilidad de estados por los que ha pasado este pedido.
     * </p>
     */
    @Override
    public void verHistorial() {
        System.out.println(" | Estado: [" + estado.name() + "]");
    }

    // ================== TEMPLATE METHOD======================

    /**
     * <p>
     *     Ejecuta el flujo completo de procesamiento de un pedido.
     *     Define el algoritmo base que sigue siempre el mismo orden:
     * </p>
     *
     * <ul>
     *     <li>Mostrar encabezado del pedido.</li>
     *     <li>Validar datos del pedido.</li>
     *     <li>Asignar repartidor.</li>
     *     <li>Calcular tiempo esperado de entrega.</li>
     *     <li>Mostrar resumen final del pedido.</li>
     * </ul>
     *
     * <p>
     *     El método es <b>final</b> para evitar que las subclases alteren el
     *     flujo del algoritmo, manteniendo la consistencia en el procedimiento.
     * </p>
     */
    public final void procesarPedido() {
        synchronized (System.out) {
            mostrarEncabezado();  // Procesar tipo de pedido.
            validarPedido();  // Validacion básica de datos.
            asignarRepartidor(); // Lógica específica de la subclase.
            double tiempo = calcularTiempoEntrega(); // Cálculo polimórfico.
            mostrarResumen(tiempo); // Visualización final.
        }
    }

    // PASOS COMUNES

    /** Muestra en consola el encabezado del pedido según tipo. */
    protected void mostrarEncabezado() {
        System.out.println("\n[" +
                getClass().getSimpleName() + "]");
    }

    /**
     * Valida los datos básicos del pedido y permite verificar que los atributos
     * obligatorios existan antes de continuar el procesamiento.
     */
    protected void validarPedido() {
        System.out.println("Validando datos del pedido");
    }


    /**
     * <p>
     * Muestra en consola un resumen detallado con los datos finales del pedido y
     * el tiempo de entrega calculado.
     * </p>
     *
     * @param tiempo el tiempo estimado de entrega en minutos.
     */
    protected void mostrarResumen(double tiempo) {
        System.out.println(
                "\nPedido #" + idPedido +
                        "\nDireccion: " + direccion +
                        "\nDistancia: " + distanciaKm + " Km" +
                        "\nRepartidor Asignado: " + repartidorAsignado +
                        "\nTiempo estimado: " + (int) Math.round(tiempo) + " minutos\n"
        );
    }

    // ===================== PASOS VARIABLES =============================

    /**
     * <p>
     *     Este método es <b>abstract</b> porque cada tipo de pedido requiere una
     *     lógica de asignación diferente, por eso, debe ser <b>sobrescrito</b>
     *     obligatoriamente por las subclases.
     * </p>
     */
    protected abstract void asignarRepartidor();

    /**
     * <p>
     * Este método es <b>abstract</b> porque el cálculo depende del tipo de pedido
     * , por eso, debe ser <b>sobrescrito</b> por las subclases para definir su
     * propia fórmula de cálculo.
     * </p>
     *
     * @return tiempo estimado de entrega en minutos.
     */
    protected abstract double calcularTiempoEntrega();

    // SOBRECARGA

    /**
     * <p>
     * Permite asignar manualmente un repartidor al pedido. Este método puede
     * ser sobrescrito por las subclases para incorporar validaciones o
     * comportamientos específicos según el tipo de pedido.
     * </p>
     *
     * @param nombre nombre del repartidor asignado.
     */
    public void asignarRepartidor(String nombre) {
        this.repartidorAsignado = nombre;
    }

    // ================UTILIDADES / HISTORIAL ===============================

    /**
     * Retorna una representación corta del pedido del historial.
     *
     * @return resumen corto del pedido.
     */
    public String resumenCorto() {
        return getClass().getSimpleName() + " #" + idPedido + " - Repartidor: " +
                repartidorAsignado;
    }

    // ================PRIORIDAD (Comparable)==================================

    /**
     * <p>
     *  Compara este pedido con otro basándose en su nivel de prioridad.
     *  Permite que el sistema de despacho priorice pedidos ALTA sobre BAJA.
     * </p>
     *
     * @param otro el otro pedido con el que se va a comparar.
     * @return un valor negativo si este pedido tiene mayor prioridad, cero
     * si son iguales, o un valor positivo si tiene menor prioridad.1
     */
    @Override
    public int compareTo(Pedido otro) {
        return this.prioridad.compareTo(otro.prioridad);
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return id del pedido.
     */
    public int getIdPedido() {
        return idPedido;
    }

    /**
     * <p>Obtiene el nombre del repartidor que ha sido asignado para procesar y
     * entregar el pedido.
     * </p>
     *
     * @return el nombre del repartidor asignado, o <code>null</code> si aún
     * no tiene uno.
     */
    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    /**
     * Obtiene el estado actual del pedido.
     *
     * @return estado del pedido (PENDIENTE, EN_REPARTO, ENTREGADO O CANCELADO)
     */
    public EstadoPedido getEstado() {
        return estado;
    }

    /**
     * <p>
     * Actualiza el estado del pedido de forma sincronizada para garantizar la
     * visibilidad de los cambios entre diferentes hilos de ejecución.
     * </p>
     *
     * @param nuevoEstado el nuevo estado ({@link EstadoPedido}) que se asignará.
     */
    public void setEstado(EstadoPedido nuevoEstado) {
        synchronized (System.out) {
            this.estado = nuevoEstado;
            System.out.println("Pedido #" + idPedido + " cambiado a estado:" + nuevoEstado);
        }
    }

    /**
     * Obtiene el nivel de prioridad asignado al pedido para su ordenamiento.
     *
     * @return la {@link PrioridadPedido} actual del pedido.
     */
    public PrioridadPedido getPrioridad() {
        return prioridad;
    }

    /**
     * Devuelve una representación en cadena de texto de los detalles del pedido.
     *
     * <p>
     * Incluye información clave como el ID, dirección, distancia, estado actual y
     * el nivel de prioridad para facilitar la trazabilidad.
     * </p>
     *
     * @return una cadena formateada con los atributos del pedido.
     */
    @Override
    public String toString() {
        return "Pedido{id=" + idPedido + "," +
                " Direccion=" + direccion + ", " +
                "Distancia=" + distanciaKm + ", " +
                "Estado=" + estado + "," +
                " Prioridad=" + prioridad + '}';
    }
}


