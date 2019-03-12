package Objetos;

import static java.lang.Math.*;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 *
 * @author Jhon
 */
public class Personaje {
    //private Scene scene;
    private double xref;
    private double yref;
    private int ancho;
    private int alto;
    private double xAbs;

    public Personaje(double xref, double yref, int ancho, int alto, double sAbs) {
        this.xref = xref;
        this.yref = yref;
        this.ancho = ancho;
        this.alto = alto;
        this.xAbs = sAbs;
    }

    public double getXref() {
        return xref;
    }

    public double getYref() {
        return yref;
    }

    public double getxAbs() {
        return xAbs;
    }

    public void setrefX(double xref) {
        this.xref = xref;
    }

    public void setrefY(double yref) {
        this.yref = yref;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setxAbs(double xAbs) {
        this.xAbs = xAbs;
    }
    
    public void moverDerecha(){
        xref = xref+2;
        System.out.println(xref);
    }
    
    public void moverIzquierda(){
        xref = xref-2;
        System.out.println(xref);
    }
    
    public void moverArriba(){
        this.yref = yref-3;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
    
    public void saltarmoviendose(){
        this.yref -= 5 + 110/20;
    }
    
    public void moverAbajo(){
        this.yref = this.yref + 4;
    }
}