package snake;

public class Caminante implements Runnable {

    private Serpiente panel;
    private boolean continuar = true;
    private Vista miVista;
    private int velocidad = 300;
    private int diferencia = 0;
    private int velocidadOriginal;
    private int contarTeclas=0;
   

    public Caminante(Serpiente panel, Vista miVista) {
        this.panel = panel;
        this.miVista = miVista;

    }

    @Override
    public void run() {
        while (continuar) {

            panel.avanzar();
            panel.repaint();

            miVista.actualizarPuntuacion(panel.getPuntos());
            //miVista.velocidadLabel(velocidad);

            try {
                Thread.sleep(velocidad);
            } catch (InterruptedException e) {
                System.out.println("Problema de hilo");

            }

        }
    }

    public void parar() {
        this.continuar = false;

    }

    public void aumentarVelocidad() {
        if (velocidad > 240) {
            this.velocidad -= 5;
        } else if (velocidad <= 240 && this.velocidad>210) {
            this.velocidad -= 3;
        } else if (velocidad <= 210 &&  this.velocidad>160 ) {
            this.velocidad -= 2;

        } else if (velocidad <= 160) {
            this.velocidad -= 1;
        }
 if (this.velocidad <= 50) {
                this.velocidad = 50;
                }
    }

    public void teclaMantenida(boolean tecla) {
        if (tecla && contarTeclas == 0) { // Solo si no hay teclas pulsadas actualmente
            velocidadOriginal = this.velocidad;
            this.velocidad -= 100;
            if (this.velocidad <= 50) {
                this.velocidad = 50;
                diferencia = velocidadOriginal - 50;
            }
            contarTeclas++;
        } else if (!tecla && contarTeclas > 0) { // Si se suelta una tecla que estaba pulsada
            contarTeclas--;
            if (contarTeclas == 0) { // Solo si no quedan teclas pulsadas
                if (this.velocidad <= 50) {
                    this.velocidad += diferencia;
                } else {
                    this.velocidad += 100;
                }
            }
        }
    }

    public int getVelocidad() {
        return this.velocidad;
    }

}
