/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package snake;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Vista extends javax.swing.JFrame {

    private Serpiente snake;
    private MusicPlayer musicPlayer;
    private boolean teclaPulsada = false;
    private PanelFondo fondo;
    private Color color;
    private Font precursor;
    private Font signo;
    private JPanel panel;
    private ImageIcon fondoV;
    private Image imagenFondo;

    public Vista() {
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);

        ArrayList<String> songs = new ArrayList<>();
        songs.add("/music/song1.mp3");
        songs.add("/music/song2.mp3");
        songs.add("/music/song3.mp3");
        songs.add("/music/song4.mp3");
        songs.add("/music/song5.mp3");
        songs.add("/music/song6.mp3");
        songs.add("/music/song7.mp3");
        songs.add("/music/song8.mp3");
        songs.add("/music/song9.mp3");

        precursor = cargarFuente("/fonts/Ottselesque.otf", 26f);
        signo = cargarFuente("/fonts/Precursor.ttf", 26f);

        musicPlayer = new MusicPlayer(songs);
        musicPlayer.play();  // Iniciar la reproducción de música

        snake = new Serpiente(800, 30, this, musicPlayer);
        this.add(snake);
        snake.setBounds(10, 50, 800, 800);
        snake.setOpaque(false);

        fondo = new PanelFondo(800, 30);
        this.add(fondo);
        fondo.setBounds(10, 50, 800, 800);
        fondo.setBackground(new Color(216, 191, 137));

        panel = new JPanel() {

             @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo en el panel
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

// Cargar imagen de fondo
InputStream inputStreamFondo = getClass().getResourceAsStream("/img/fondoV.png");
if (inputStreamFondo != null) {
    try {
        BufferedImage imageFondo = ImageIO.read(inputStreamFondo);
        imagenFondo = imageFondo;  // Convertir a Image directamente si es necesario
        fondoV = new ImageIcon(imageFondo);  // Mantener fondoV como ImageIcon si lo necesitas
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            inputStreamFondo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} else {
    System.err.println("No se pudo cargar la imagen de fondo");
}
        panel.setBounds(0, 0, 830, 860);
        this.add(panel);
        panel.setBackground(new Color(216, 191, 137));

        this.requestFocus(true);

        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyReleased(e);
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        });
    }

    private void handleKeyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!teclaPulsada && (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)) {
            teclaPulsada = true;

            cambiarDireccion(keyCode);
            snake.getCamino().teclaMantenida(teclaPulsada);

        }
    }

    private void cambiarDireccion(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                snake.cambiarDireccion("iz");
                break;
            case KeyEvent.VK_RIGHT:
                snake.cambiarDireccion("de");
                break;
            case KeyEvent.VK_UP:
                snake.cambiarDireccion("arr");
                break;
            case KeyEvent.VK_DOWN:
                snake.cambiarDireccion("aba");
                break;
        }
    }

    private void handleKeyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            teclaPulsada = false;

            snake.getCamino().teclaMantenida(teclaPulsada);
        }
    }

   

    public static Font cargarFuente(String ruta, float tamaño) {
        try {
            InputStream is = Vista.class.getResourceAsStream(ruta);
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            return fuente.deriveFont(tamaño);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarPuntuacion(int puntos) {

        contador.setFont(precursor);
        contador.setText("Puntuacion: " + puntos);
        marca.setFont(signo);
        marca.setText("emi");
        marca2.setFont(signo);
        marca2.setText("jak");
    }

    /*  public void velocidadLabel(int velocidad){
        velocidadL.setText("Velocidad: "+ velocidad);
    }*/
    public void reiniciarJuego() {
        this.dispose();
        Vista mivista = new Vista();
        mivista.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contador = new javax.swing.JLabel();
        marca = new javax.swing.JLabel();
        marca2 = new javax.swing.JLabel();
        velocidadL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        contador.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        contador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contador.setText("Puntuación:");
        contador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        marca.setText("signo");

        marca2.setText("signo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(marca2)
                .addGap(284, 284, 284)
                .addComponent(contador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addComponent(velocidadL, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(marca)
                .addGap(14, 14, 14))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contador)
                    .addComponent(marca)
                    .addComponent(marca2)
                    .addComponent(velocidadL))
                .addContainerGap(819, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contador;
    private javax.swing.JLabel marca;
    private javax.swing.JLabel marca2;
    private javax.swing.JLabel velocidadL;
    // End of variables declaration//GEN-END:variables
}
