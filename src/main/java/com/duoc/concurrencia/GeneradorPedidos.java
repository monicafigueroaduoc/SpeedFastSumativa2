package com.duoc.concurrencia;

import com.duoc.gestor.ZonaDeCarga;
import com.duoc.modelo.*;
import com.duoc.modelo.PrioridadPedido;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h2>GeneradorPedidos - Productor concurrente de pedidos</h2>
 *
 * <p>
 *     Clase encargada de generar pedidos automáticamente y agregarlos a la
 *     {@link ZonaDeCarga}, actuando como el componente <b>Productor</b>
 *     dentro del patrón Productor-Consumidor del sistema SpeedFast.
 * </p>
 *
 * <p>
 *     Se ejecuta como un hilo independiente mediante la interfaz {@link Runnable},
 *     permitiendo la creación de pedidos en paralelo con su procesamiento por los
 *     repartidores.
 * </p>
 *
 * <h3>Responsabilidades principales:</h3>
 * <ul>
 *     <li>Generar pedidos con datos aleatorios.</li>
 *     <li>Asignar un identificador único a cada pedido.</li>
 *     <li> Agregar pedidos a la zona de carga compartida.</li>
 *     <li>Simular la llegada progresiva de pedidos.</li>
 * </ul>
 *
 * @author Monica Figueroa
 * @version 1.0
 */
public class GeneradorPedidos implements Runnable {

    /**
     * Contador global de pedidos.
     * AtomicInteger garantiza generación segura en entorno concurrente.
     */
    private static final AtomicInteger contadorPedidos = new AtomicInteger(2000);

    /** Zona de carga donde se agregan los pedidos generados. */
    private final ZonaDeCarga zonaDeCarga;

    /** Generador de valores aleatorios para simular pedidos reales*/
    private final Random random = new Random();

   /** Cantidad total de pedidos a generar.*/
    private final int cantidadPedidos;

    /**
     * Constructor que inicializa el generador con la zona de carga y cantidad de pedidos.
     *
     * @param zonaDeCarga zona donde se almacenarán los pedidos generados.
     * @param cantidadPedidos número total de pedidos a crear.
     */
    public GeneradorPedidos(ZonaDeCarga zonaDeCarga, int cantidadPedidos) {
        this.zonaDeCarga = zonaDeCarga;
        this.cantidadPedidos = cantidadPedidos;
    }

    /**
     * Método principal del hilo generador.
     *
     * <p>
     *     Genera pedidos aleatorios y los agrega a la zona de carga, simulando
     *     la llegada progresiva de pedidos al sistema.
     * </p>
     */
    @Override
    public void run() {
        System.out.println("[Generador] Iniciando generación de pedidos...");

        // Genera la cantidad definida de pedidos.
        for (int i = 1; i < cantidadPedidos; i++) {

            Pedido pedido;

            // Selecciona aleatoriamente el tipo de pedido.
            int tipoPedido = random.nextInt(3);

            // Genera un ID único y seguro para cada pedido.
            int idPedido = contadorPedidos.incrementAndGet();

            // Genera datos simulados del pedido.
            String direccion = "Direccion #" + idPedido;
            double distanciaKm = 1+ (10 * random.nextDouble());

           // Crea el pedido según el tipo seleccionado.
            switch (tipoPedido) {
                case 0:
                    pedido = new PedidoComida(idPedido, direccion,
                            distanciaKm, PrioridadPedido.ALTA);
                    break;

                    case 1:
                        pedido = new PedidoExpress(idPedido, direccion,
                                distanciaKm, PrioridadPedido.MEDIA);
                        break;

                case 2:
                    double pesoKg = 1 + (9 * random.nextDouble());
                    pedido = new PedidoEncomienda(idPedido, direccion,
                            distanciaKm, pesoKg, PrioridadPedido.BAJA);
                    break;
                default:
                throw new IllegalStateException("Tipo invalido");
            }
            zonaDeCarga.agregarPedido(pedido);

            try {
                // Pausa de 1 segundo para simular la llegada progresiva de pedidos.
                Thread.sleep( 1000);
            }
            catch (InterruptedException e) {
                // Si el hilo es interrumpido, se restaura el estado y finaliza.
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("[Generador] Finalizo la generación de pedidos.");
    }
}
