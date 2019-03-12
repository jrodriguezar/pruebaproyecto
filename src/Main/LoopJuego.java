package Main;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
import Objetos.Personaje;
import Objetos.Enemigosimple;

/**
 *
 * @author Estudiante
 */
public class LoopJuego extends AnimationTimer {

    private Scene escena; //Para controlar los eventos del teclado y para el cambio de nivel.
    private GraphicsContext lapiz;

    private Personaje ninja;
    private Image fondo;
    private Image fondo2;
    private Image fondo3;
    private Image esqueletoim;
    private Image fantasmaImD;
    private Image fantasmaImI;
    private Enemigosimple esqueleto;
    private Enemigosimple fantasma;
    private Image ninjaImD;
    private Image ninjaImI;
    private boolean comprobacion = false;
    private int secuencia = 0;
    private int secuencia2 = 0;
    private int numero;
    private int contador = 100;
    private String marca = "RIGHT";
    private ArrayList<String> pulsacionTeclado = null;
    private Image heart;
    private int salto = 0; //Indicador del salto
    private boolean pausa = false; //Indicador de pausa
    private int debounceP = 0; //Antirrebote para la pausa

    public LoopJuego(Scene escena, GraphicsContext lapiz) {
        this.lapiz = lapiz;
        this.escena = escena;
        this.ninja = new Personaje(0.0, 420.0, 40, 52, 0);//ubicación del ninjaImI v;
        this.esqueleto = new Enemigosimple(720, 478, 45, 57);
        this.fantasma = new Enemigosimple(720, 300, 34, 31);
        this.heart = new Image("Images/heart.png");
        this.fondo = new Image("Images/CITY_MEGA sin fondo.png");
        this.fondo2 = new Image("Images/Nivel2.png");
        this.fondo3 = new Image("Images/Nivel3.png");
        this.ninjaImD = new Image("Images/rogue spritesheet calciumtrice.png");
        this.ninjaImI = new Image("Images/rogue spritesheet calciumtrice IZ.png");
        this.esqueletoim = new Image("Images/rpgcritter update formatted transparent.png");
        this.fantasmaImD = new Image("Images/ghostIce_all.png");
        this.fantasmaImD = new Image("Images/ghostIce_all IZQUIERDA.png");
        pulsacionTeclado = new ArrayList<>();

        escena.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!pulsacionTeclado.contains(code)) {
                    pulsacionTeclado.add(code);
                }
            }
        });

        escena.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                pulsacionTeclado.remove(code);
            }
        });

    }

    @Override
    public void handle(long now) {
        if (pulsacionTeclado.contains("P") && debounceP == 0) { //oprimir P para pausar
            pausa = !pausa;
            debounceP = 10;
        }
        if (!pausa) {
            lapiz.clearRect(0, 0, 1800, 520);

            if (this.numero % 10 == 0) {
                if (this.secuencia == 9) {
                    this.secuencia = 0;
                } else {
                    this.secuencia++;
                }
            }

            if (this.numero % 10 == 0) {
                if (this.secuencia2 == 2) {
                    this.secuencia2 = 0;
                } else {
                    this.secuencia2++;
                }
            }
            //shape del personaje
            Shape sNinja = new Rectangle(ninja.getXref() + 5, ninja.getYref(), ninja.getAncho()-5, ninja.getAlto());
            Shape sEsqueleto = new Rectangle(esqueleto.getXref(), esqueleto.getYref(), 23, 38);
            Shape sFantasma = new Rectangle(esqueleto.getXref(), esqueleto.getYref(), 33, 31);

            //Permite dibujar una imagen de fondo
            //permite hacer que el escenario vaya moviendose en la ubicación 
            if (ninja.getxAbs() < 796) {
                if (ninja.getxAbs() == 794) {
                    ninja.setrefX(760);
                }
                Shape pared = new Rectangle(-20, 400, 2, 150);
                Shape pared2 = new Rectangle(804, 400, 2, 150);
                
                Shape interseccionFantasma1 = SVGPath.intersect(sFantasma, pared);
                Shape interseccionFantasma2 = SVGPath.intersect(sFantasma, pared2);

                Shape interseccion = SVGPath.intersect(sNinja, pared);
                Shape interseccion2 = SVGPath.intersect(sNinja, pared2);
                
                Shape inter = SVGPath.intersect(sNinja, sFantasma);
                
                lapiz.drawImage(fondo, 43, 2400, 696, 320, 0, 0, 796, 520);
                lapiz.drawImage(fondo, 43, 2068, 696, 268, 0, 89, 796, 438);
                lapiz.drawImage(fondo, 43, 1621, 696, 235, 0, 204, 796, 330);
                
               /* if (interseccion.getBoundsInLocal().getWidth() != -1) {
                    System.out.println("esta en colicion");
                    System.out.println(interseccion.getBoundsInLocal().getWidth());
                    ninja.setrefX(-22);
                    ninja.setxAbs(-20);
                }
                //Activar pared2 escenario º
                 if (interseccion2.getBoundsInLocal().getWidth() !=-1) {
                    ninja.setrefX(760);
                    ninja.setxAbs(758);
                     System.out.println("esta en colision");
                     System.out.println(interseccion2.getBoundsInLocal().getWidth());
                    
               }
                /*if (!comprobacion) {
                    fantasma.moverizquierda();
                    lapiz.drawImage(fantasmaImI, 256-34*this.secuencia2, 32, 33, 31, fantasma.getXref(), fantasma.getYref(), 33, 34);//animacion esqueleto
                    if (interseccionFantasma1.getBoundsInLocal().getWidth() != -1) {
                        comprobacion = true;
                    }
                } else {
                    fantasma.moverderecha();
                    lapiz.drawImage(fantasmaImD, 1 + 34 * this.secuencia2, 35, 31, 30, fantasma.getXref(), fantasma.getYref(), 33, 34);
                    if (interseccionFantasma2.getBoundsInLocal().getWidth() != -1) {
                        comprobacion = false;
                    }
                }*/
            } else if (ninja.getxAbs() >= 796 && ninja.getxAbs() < 1592) {
                if (ninja.getxAbs() == 796) {//796
                    ninja.setrefX(0);//0
                }
                //las paredes se crean segun la actualizacion del personaje
                //el personaje reinicia refx al cruzar la division
                //el personaje se posiciona en abs
                Shape pared = new Rectangle(4, 400, 2, 150);
                Shape pared2 = new Rectangle(804, 400, 2, 150);  
                
                Shape interseccion = SVGPath.intersect(sNinja, pared);
                Shape interseccion2 = SVGPath.intersect(sNinja, pared2);
                
                Shape interseccionEsqueleto1 = SVGPath.intersect(sEsqueleto, pared);
                Shape interseccionEsqueleto2 = SVGPath.intersect(sEsqueleto, pared2);

                Shape inter = SVGPath.intersect(sNinja, sEsqueleto);
                
                lapiz.drawImage(fondo2, 0,0, 769, 286, 0, 0, 796, 520);
                //lapiz.drawImage(fondo, 736, 2400, 696, 320, 0, 0, 796, 520);
                //lapiz.drawImage(fondo, 736, 2068, 696, 268, 0, 89, 796, 438);
                //lapiz.drawImage(fondo, 736, 1621, 696, 235, 0, 204, 796, 330);
                
              
                //Activar paredes escenario 2
                /*if (interseccion.getBoundsInLocal().getWidth() != -1) {
                    System.out.println("esta en colicion");
                    System.out.println(interseccion.getBoundsInLocal().getWidth());
                    ninja.setrefX(5);
                    ninja.setxAbs(798);
                } 
                if (interseccion2.getBoundsInLocal().getWidth() !=-1) {
                    ninja.setrefX(760);
                    ninja.setxAbs(1563);
                     System.out.println("esta en colision");
                     System.out.println(interseccion2.getBoundsInLocal().getWidth());
                    
               }*/
                //Esqueleto se mueve i - d, d - i
                if (!comprobacion) {
                    esqueleto.moverizquierda();
                    lapiz.drawImage(esqueletoim, 96 + 16 * this.secuencia2, 111, 16, 17, esqueleto.getXref(), esqueleto.getYref(), 26, 27);//animacion esqueleto
                    if (interseccionEsqueleto1.getBoundsInLocal().getWidth() != -1) {
                        comprobacion = true;
                    }
                } else {
                    esqueleto.moverderecha();
                    lapiz.drawImage(esqueletoim, 96 + 16 * this.secuencia2, 79, 16, 17, esqueleto.getXref(), esqueleto.getYref(), 26, 27);
                    if (interseccionEsqueleto2.getBoundsInLocal().getWidth() != -1) {
                        System.out.println("fantasma!");
                        comprobacion = false;
                    }
                }
                /*if (interseccion.getBoundsInLocal().getWidth() != -1) {
                    ninja.setrefX(2);
                }*/
                if (inter.getBoundsInLocal().getWidth() != -1) {
                    System.out.println("Holi");
                }
            } else if (ninja.getxAbs() >= 1592 && ninja.getxAbs() < 2388) {
                if (ninja.getxAbs() == 1592) {
                    ninja.setrefX(0);
                }

                Shape pared = new Rectangle(4, 400, 2, 150);
                Shape pared2 = new Rectangle(788, 400, 2, 150);
                
                Shape interseccion = SVGPath.intersect(sNinja, pared);
                Shape interseccion2 = SVGPath.intersect(sNinja, pared2);
                
                lapiz.drawImage(fondo3, 0, 0, 492, 233, 0, 0, 796, 520);
                //lapiz.drawImage(fondo, 43, 2400, 696, 320, 0, 0, 796, 520);
                //lapiz.drawImage(fondo, 43, 1836, 696, 20, 0, 507, 796, 20);
               
                //Activar paredes escenario 3
                if (interseccion.getBoundsInLocal().getWidth() != -1) {
                    System.out.println("esta en colicion");
                    System.out.println(ninja.getxAbs());
                    System.out.println(interseccion.getBoundsInLocal().getWidth());
                    ninja.setrefX(6);
                    ninja.setxAbs(1600);
                }
                if (interseccion2.getBoundsInLocal().getWidth() != -1) {
                    ninja.setrefX(750);
                    System.out.println(ninja.getxAbs());
                    System.out.println("esta en colision");
                    ninja.setxAbs(2342);
                    System.out.println(interseccion2.getBoundsInLocal().getWidth());
                    System.out.println(ninja.getxAbs());
                }
            }

            //lapiz.strokeRect(ninja.getXref() + 5, ninja.getYref(), ninja.getAncho(), ninja.getAlto());
            //Obstaculos (imagen)
            //lapiz.fillRect(100, 100, 20, 20);
            //lapiz.strokeRect(0, 507, 1800, 20);
            //movimiento de "gravedad"
            ninja.moverAbajo();

            //Validando colision
            //Obstaculos(programados)
            Shape sObstaculo = new Rectangle(100, 100, 20, 20);
            Shape bObstaculo = new Rectangle(0, 503, 1800, 20);
            //Calculando la Interseccion
            Shape interseccion = SVGPath.intersect(sNinja, bObstaculo);

            if (interseccion.getBoundsInLocal().getWidth() != -1) {
                ninja.setrefY(455);
            }

            //Acciones de teclado
            if (marca == "RIGHT") {
                lapiz.drawImage(ninjaImD, 32 * this.secuencia, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
            }
            if (marca == "LEFT") {
                lapiz.drawImage(ninjaImI, 32 * this.secuencia, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
            }

            if (pulsacionTeclado.contains("LEFT")) {
                //
                marca = "LEFT";
                ninja.setxAbs(ninja.getxAbs() - 2);
                //            System.out.println(ninja.getXref());
                //            System.out.println("ABS: " + ninja.getxAbs());
                ninja.moverIzquierda();
                lapiz.drawImage(ninjaImI, 32 * this.secuencia, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
            }
            if (pulsacionTeclado.contains("RIGHT")) {
                marca = "RIGHT";
                ninja.setxAbs(ninja.getxAbs() + 2);
                //System.out.println(ninja.getXref());
                //System.out.println("ABS: " + ninja.getxAbs());
                ninja.moverDerecha();
                lapiz.drawImage(ninjaImD, 32 * this.secuencia, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
            }
            if ((pulsacionTeclado.contains("UP")) && (ninja.getYref() == 455.0)) {
                salto = 20;

            }
            if (salto > 0) {
                ninja.saltarmoviendose();
                if (marca == "RIGHT") {
                    lapiz.drawImage(ninjaImD, 32, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
                }
                if (marca == "LEFT") {
                    lapiz.drawImage(ninjaImI, 32, 64, 32, 32, ninja.getXref(), ninja.getYref(), 52, 52);
                }
                this.salto--;
            }
            if (pulsacionTeclado.contains("DOWN")) {
                ninja.moverAbajo();
            }

            //imagen de la puntuacion
            lapiz.drawImage(heart, 0, 0, 64, 64, 50, 5, 20, 20);
            int vidas = 3;
            lapiz.strokeText("= " + vidas, 72, 17);

            this.numero++;
        } else {
            lapiz.strokeText("PAUSA, presiona P para continuar", 100, 50);
        }
        if (debounceP > 0) { //Antirrebote para la tecla P
            debounceP--;
        }
    }
}
