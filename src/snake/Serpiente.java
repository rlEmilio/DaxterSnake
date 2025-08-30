package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Serpiente extends JPanel {

    //  -----CAMPOS CLASE-----
    //constante color de fondo que compartirán todas mis instancias de clase (por eso se usa static, que además ahorra memoria)
    private static final Color COLORSERPIENTE = Color.ORANGE;

    //el panel estará dividido en unidades o cuadritos de un tamaño específico
    private int tamanoPANEL, tamanoUNIDADES, cantidadUNIDADES, residuo;

    private ArrayList<int[]> snake = new ArrayList<int[]>();
    private int[] comida = new int[2];
    private ArrayList<int[]> enemigos = new ArrayList<int[]>();
    private String direccion = "de";
    private String direccionProxima = "de";
    private static final Color COLORCOMIDA = Color.RED;
    private Caminante camino;
    private Thread hilo;
    private int puntuacion = 0;
    private Vista miVista;
    private String rutaComer = "/music/comer.mp3";
    private String rutaDerrota = "/music/derrota.mp3";
    private String rutaTransformacion = "/music/transformacion.mp3";
    private MusicPlayer principal;
    private Image orbe;
    private Image daxter;
    private Image chapa;
    private Image jak;
    private Image jakOscuro;
    private Image bolaEnergia;
    private Image lurker;
    private Color colorNaranjaDaxter = new Color(255, 165, 79);
    private Color colorAmarilloDaxter = new Color(255, 223, 121);
    private Color colorAzulJak = new Color(61, 101, 205);
    private Color colorVerdejak = new Color(176, 203, 6);
    private Color colorGrisJak = new Color(190, 162, 209);
    private Color colorMoradoJak = new Color(169, 86, 192);
    private boolean enemigoGenerado;
    private boolean haComido = false;

    private ImageIcon imagenDerrota;
    private ImageIcon imagenContinuar;

    // --CONSTRUCTOR--
    public Serpiente(int tamanoPANEL, int cantidadUNIDADES, Vista miVista, MusicPlayer principal) {
        this.tamanoPANEL = tamanoPANEL;
        this.cantidadUNIDADES = cantidadUNIDADES;
        this.tamanoUNIDADES = tamanoPANEL / cantidadUNIDADES;
        this.residuo = tamanoPANEL % cantidadUNIDADES;
        int[] a = {cantidadUNIDADES / 2 - 1, cantidadUNIDADES / 2 - 1};
        int[] b = {cantidadUNIDADES / 2, cantidadUNIDADES / 2 - 1};
        snake.add(a);
        snake.add(b);
        this.miVista = miVista;
        this.principal = principal;
        enemigoGenerado = false;
        generarComida();

        camino = new Caminante(this, miVista);
        hilo = new Thread(camino);
        hilo.start();

        try {
            orbe = ImageIO.read(getClass().getResource("/img/orbe.png"));
            daxter = ImageIO.read(getClass().getResource("/img/daxter.png"));
            chapa = ImageIO.read(getClass().getResource("/img/chapa.png"));
            jak = ImageIO.read(getClass().getResource("/img/jak.png"));
            jakOscuro = ImageIO.read(getClass().getResource("/img/jakOscuro.png"));
            bolaEnergia = ImageIO.read(getClass().getResource("/img/bolaEnergia.png"));
            lurker = ImageIO.read(getClass().getResource("/img/lurker.png"));
        } catch (IOException e) {
            System.out.println("No se encuentra la imagen");
        }

        // Cargar imagen de derrota
InputStream inputStreamDerrota = getClass().getResourceAsStream("/img/kor.jpg");
if (inputStreamDerrota != null) {
    try {
        BufferedImage imageDerrota = ImageIO.read(inputStreamDerrota);
        imagenDerrota = new ImageIcon(imageDerrota);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            inputStreamDerrota.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} else {
    System.err.println("No se pudo cargar la imagen de derrota");
}

// Cargar imagen de continuar
InputStream inputStreamContinuar = getClass().getResourceAsStream("/img/continuar.jpg");
if (inputStreamContinuar != null) {
    try {
        BufferedImage imageContinuar = ImageIO.read(inputStreamContinuar);
        imagenContinuar = new ImageIcon(imageContinuar);
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            inputStreamContinuar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} else {
    System.err.println("No se pudo cargar la imagen de continuar");
}

    }

    //metodo de java.awt.component para pintar elementos graficos
    @Override
    public void paint(Graphics pintor) {
        //llamo a la clase padre (JPanel)
        super.paint(pintor);
        pintor.setColor(COLORSERPIENTE);

        //pintar serpiente
        if (puntuacion < 20) {
            for (int i = 0; i < snake.size(); i++) {
                int[] par = snake.get(i);
                if (i == snake.size() - 1) { // última posición de la serpiente
                    pintor.drawImage(daxter, (residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
                } else if (i % 2 == 0) {
                    pintor.setColor(colorNaranjaDaxter);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                } else {
                    pintor.setColor(colorAmarilloDaxter);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                }

            }

            pintor.drawImage(orbe, (residuo / 2) + (comida[0] * tamanoUNIDADES), (residuo / 2) + (comida[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
        } else if (puntuacion >= 20 && puntuacion < 40) {

            for (int i = 0; i < snake.size(); i++) {
                int[] par = snake.get(i);
                if (i == snake.size() - 1) { // última posición de la serpiente
                    pintor.drawImage(jak, (residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
                } else if (i % 2 == 0) {
                    pintor.setColor(colorAzulJak);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                } else {
                    pintor.setColor(colorVerdejak);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                }

            }

            pintor.drawImage(chapa, (residuo / 2) + (comida[0] * tamanoUNIDADES), (residuo / 2) + (comida[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
        } else {
            for (int i = 0; i < snake.size(); i++) {
                int[] par = snake.get(i);
                if (i == snake.size() - 1) { // última posición de la serpiente
                    pintor.drawImage(jakOscuro, (residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
                } else if (i % 2 == 0) {
                    pintor.setColor(colorMoradoJak);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                } else {
                    pintor.setColor(colorGrisJak);
                    pintor.fillRect((residuo / 2) + (par[0] * tamanoUNIDADES), (residuo / 2) + (par[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES);
                }

            }

            pintor.drawImage(bolaEnergia, (residuo / 2) + (comida[0] * tamanoUNIDADES), (residuo / 2) + (comida[1] * tamanoUNIDADES), tamanoUNIDADES, tamanoUNIDADES, this);
        }

        for (int[] enemigo : enemigos) {
            pintor.drawImage(lurker, (residuo / 2) + (enemigo[0] * tamanoUNIDADES), (residuo / 2) + (enemigo[1] * tamanoUNIDADES), tamanoUNIDADES + 5, tamanoUNIDADES + 5, this);

        }

    }

    public void avanzar() {

        igualarDireccion();
        int[] ultimo = snake.get(snake.size() - 1);
        int agregarX = 0;
        int agregarY = 0;

        switch (direccion) {
            case "de":
                agregarX = 1;
                break;
            case "iz":
                agregarX = -1;
                break;
            case "arr":
                agregarY = -1;
                break;
            case "aba":
                agregarY = 1;
                break;

        }

        //COMPROBAR CHOQUE CON EL BORDE
        boolean borde = false;
        //int[] nuevo = {Math.floorMod(ultimo[0] + agregarX, cantidadUNIDADES), Math.floorMod(ultimo[1] + agregarY, cantidadUNIDADES)};
        int[] nuevo = {ultimo[0] + agregarX, ultimo[1] + agregarY};

        if (nuevo[0] == cantidadUNIDADES || nuevo[0] == -1 || nuevo[1] == cantidadUNIDADES || nuevo[1] == -1) {
            borde = true;

        }

        //COMPROBAR CHOQUE CON LA MISMA SERPIENTE
        boolean existe = false;
        int i = 0;
        while (!existe && i < snake.size()) {

            if (nuevo[0] == snake.get(i)[0] && nuevo[1] == snake.get(i)[1]) {
                existe = true;
            }
            i++;

        }

        //COMPROBAR CHOQUE CON ENEMIGO
        boolean choqueEnemigo = false;

        for (int[] enemigo : enemigos) {
            if (nuevo[0] == enemigo[0] && nuevo[1] == enemigo[1]) {
                choqueEnemigo = true;
            }

        }

        //PERDER JUEGO
        if (existe || borde || choqueEnemigo) {
            principal.stop();
            camino.parar();
            MusicPlayer musicPlayer = new MusicPlayer(rutaDerrota);
            musicPlayer.playEffects();

            JLabel mensajeDerrota = new JLabel("Has perdido!!!"
                    + " Has conseguido " + puntuacion + " puntos");
            mensajeDerrota.setFont(new Font("Dialog", Font.BOLD, 18));
            JLabel mensajeContinuar = new JLabel("¿Quieres continuar el juego?");
            mensajeContinuar.setFont(new Font("Dialog", Font.BOLD, 18));

            JOptionPane.showMessageDialog(this, mensajeDerrota, "Derrota", JOptionPane.INFORMATION_MESSAGE, imagenDerrota);

            Object[] opciones = {"Sí", "No"};
            int respuesta = JOptionPane.showOptionDialog(this, mensajeContinuar, "Continuar",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, imagenContinuar, opciones, opciones[0]);

            if (respuesta == JOptionPane.YES_OPTION) {
                miVista.reiniciarJuego();
            } else {
                System.exit(0);
            }

            //COMER Y AGREGAR PUNTOS
        } else {
            if (nuevo[0] == comida[0] && nuevo[1] == comida[1]) {
                snake.add(nuevo);
                generarComida();
                puntuacion++;
                haComido = true;
                camino.aumentarVelocidad();

                if (puntuacion != 40) {
                    MusicPlayer musicPlayer = new MusicPlayer(rutaComer);
                    musicPlayer.playEffects();
                } else {
                    MusicPlayer musicPlayer = new MusicPlayer(rutaTransformacion);
                    musicPlayer.playEffects();
                }

                //MOVERSE SIN COMER
            } else {
                snake.add(nuevo);
                snake.remove(0);
            }
        }

        //GENERAR ENEMIGOS
        if (puntuacion >=10 && puntuacion < 20 && haComido) {
            generarEnemigo(1);
            haComido = false;
        }
       else if (puntuacion > 19 && puntuacion < 30 && haComido) {
            generarEnemigo(2);
            haComido = false;

        } else if (puntuacion >= 30 && puntuacion < 40 && haComido) {
            generarEnemigo(3);
            haComido = false;

        } else if (puntuacion >= 40 && haComido) {
            generarEnemigo(5);
            haComido = false;

        }

       

    }

    public void generarComida() {

        boolean existe = false;
        int x = (int) (Math.random() * cantidadUNIDADES);
        int y = (int) (Math.random() * cantidadUNIDADES);

        int i = 0;

        while (!existe && i < snake.size()) {

            if (snake.get(i)[0] == x && snake.get(i)[1] == y) {
                existe = true;
                generarComida();
            }
            i++;
        }

        if (!existe) {
            this.comida[0] = x;
            this.comida[1] = y;

        }

    }

    public void generarEnemigo(int cantidad) {
        // Reseteo enemigos
        for (int[] enemigo : enemigos) {
            enemigo[0] = -20;
            enemigo[1] = -20;
        }

        for (int i = 0; i < cantidad; i++) {
            boolean existe = true;
            int x = 0;
            int y = 0;
            int[] coordenadas = new int[2];

            // Continuar intentando generar coordenadas válidas hasta que no existan conflictos
            while (existe) {
                x = (int) (Math.random() * cantidadUNIDADES);
                y = (int) (Math.random() * cantidadUNIDADES);
                coordenadas[0] = x;
                coordenadas[1] = y;

                existe = false;

                // Comprobar si la coordenada generada coincide con la serpiente o la comida
                for (int[] segmento : snake) {
                    if (segmento[0] == x && segmento[1] == y) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    if (comida[0] == x && comida[1] == y) {
                        existe = true;
                    }
                }

                // Comprobar si la coordenada generada coincide con un enemigo existente
                for (int[] enemigo : enemigos) {
                    // Calcular la distancia entre la coordenada generada y la cabeza de la serpiente
                    int distanciaX = Math.abs(x - snake.get(snake.size() - 1)[0]);
                    int distanciaY = Math.abs(y - snake.get(snake.size() - 1)[1]);
                    double distancia = Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);

                    // Si la distancia es menor que 5, establecer existe a true
                    if (distancia < 5) {
                        existe = true;
                        break;
                    }

                    // Comprobar si la coordenada generada coincide con un enemigo existente
                    if (enemigo[0] == x && enemigo[1] == y) {
                        existe = true;
                        break;
                    }
                }
            }

            // Añadir el nuevo enemigo a la lista de enemigos
            enemigos.add(coordenadas);
        }
    }

    public void cambiarDireccion(String dir) {
        if ((this.direccion.equals("de") || this.direccion.equals("iz")) && (dir.equals("arr") || dir.equals("aba"))) {
            this.direccionProxima = dir;
        }
        if ((this.direccion.equals("arr") || this.direccion.equals("aba")) && (dir.equals("de") || dir.equals("iz"))) {
            this.direccionProxima = dir;
        }

    }

    public void igualarDireccion() {
        this.direccion = this.direccionProxima;
    }

    public int getPuntos() {
        return this.puntuacion;
    }

    public Caminante getCamino() {
        return this.camino;
    }

}
