package snake;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelFondo extends JPanel {

    //  -----CAMPOS CLASE-----
    //constante color de fondo que compartirán todas mis instancias de clase (por eso se usa static, que además ahorra memoria)
    private static final Color COLORFONDO = Color.BLACK;

    //el panel estará dividido en unidades o cuadritos de un tamaño específico
    private int tamanoPANEL, tamanoUNIDADES, cantidadUNIDADES, residuo;

    public PanelFondo(int tamanoPANEL, int cantidadUNIDADES) {
        this.tamanoPANEL = tamanoPANEL;
        this.cantidadUNIDADES = cantidadUNIDADES;
        this.tamanoUNIDADES = tamanoPANEL / cantidadUNIDADES;
        this.residuo = tamanoPANEL % cantidadUNIDADES;
    }

    //metodo de java.awt.component para pintar elementos graficos
    @Override
    public void paint(Graphics pintor) {
        //llamo a la clase padre (JPanel)
        super.paint(pintor);
        pintor.setColor(COLORFONDO);
        for (int i = 0; i < cantidadUNIDADES; i++) {
            for (int j = 0; j < cantidadUNIDADES; j++) {
                pintor.fillRect((residuo / 2) + (i * tamanoUNIDADES), (residuo / 2) + (j * tamanoUNIDADES), tamanoUNIDADES , tamanoUNIDADES );
            }
        }

    }
}
