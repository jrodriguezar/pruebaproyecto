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
import modelo.Carro;

/**
 *
 * @author Estudiante
 */
public class LoopJuego extends AnimationTimer{
    private Scene escena; //Para controlar los eventos del teclado y para el cambio de nivel.
    private GraphicsContext lapiz;
    
    private Carro carro;
    private Image fondo ;   
    private Image edificios;
    private Image edificio;
    private Image mensito2;
    private Image mensito;
    private int secuencia =0;
    private int numero ; 
    private int contador = 100;
    private String marca = "RIGHT";
    private ArrayList<String> pulsacionTeclado = null;

    public LoopJuego(Scene escena, GraphicsContext lapiz) {
        this.lapiz = lapiz;
        this.escena = escena;
        this.carro = new Carro(0, 420, 30, 42);//ubicación del mensito v;
        this.fondo = new Image( "image/CITY_MEGA sin fondo.png" );
        this.edificios = new Image( "image/CITY_MEGA sin fondo.png" );
        this.edificio = new Image( "image/CITY_MEGA sin fondo.png" );
        this.mensito2 = new Image("image/rogue spritesheet calciumtrice.png");
        this.mensito = new Image("image/rogue spritesheet calciumtrice IZ.png");
        pulsacionTeclado = new ArrayList<>();
                
        
        escena.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    if ( !pulsacionTeclado.contains(code) )
                        pulsacionTeclado.add( code );
                }
            });

        escena.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    pulsacionTeclado.remove( code );
                }
            });
        
    }
    
    @Override
    public void handle(long now) {
         
        //Carro
        lapiz.clearRect(0, 0, 1800, 520);
        
        //Permite dibujar una imagen de fondo
        lapiz.drawImage(fondo, 43, 2400, 1600, 320, 0, 0, 1800, 527);
        lapiz.drawImage(edificios, 43, 2068, 1600, 268, 0, 89, 1800, 438);
        lapiz.drawImage(edificio, 43, 1621, 1600, 235, 0, 297, 1800, 230);
                           
        lapiz.strokeRect(carro.getXref()+5, carro.getYref(), carro.getAncho(), carro.getAlto());
                      
        //Obstaculos (imagen)
        lapiz.fillRect(100, 100, 20, 20);
        lapiz.strokeRect(0, 507, 1800, 20);
        
        if(this.numero % 10 == 0){
                if(this.secuencia == 9){
                  this.secuencia = 0;
                }else{
                  this.secuencia++;
                }
        }
        
        //movimiento de "gravedad"
        carro.moverAbajo();
        
        //Validando colision
        Shape sChasis = new Rectangle(carro.getXref()+5, carro.getYref(), carro.getAncho(), carro.getAlto());
         //Obstaculos(programados)
        Shape sObstaculo = new Rectangle(100, 100, 20, 20);
        Shape bObstaculo = new Rectangle(0,507,1800, 20);
        //Calculando la Interseccion
        Shape interseccion = SVGPath.intersect(sChasis, bObstaculo);
        Shape intersection = SVGPath.intersect(sChasis, sObstaculo);
        
        if (intersection.getBoundsInLocal().getWidth() != -1) {
            contador = contador - 1; 
            //stop();
        }
        
        if (interseccion.getBoundsInLocal().getWidth() != -1) {
            carro.setrefY(465);
        }
        
        //Acciones de teclado
        if (marca == "RIGHT")
            lapiz.drawImage(mensito2, 32*this.secuencia, 64, 32, 32, carro.getXref(), carro.getYref(), 42, 42);
        if (marca == "LEFT")
            lapiz.drawImage(mensito, 32*this.secuencia, 64, 32, 32, carro.getXref(), carro.getYref(), 42, 42);
        if (pulsacionTeclado.contains("LEFT")){
            marca = "LEFT";
            carro.moverIzquierda();
            lapiz.drawImage(mensito, 32*this.secuencia, 64, 32, 32, carro.getXref(), carro.getYref(), 42, 42);
        }
        if (pulsacionTeclado.contains("RIGHT")){
            marca = "RIGHT";
            carro.moverDerecha();
            lapiz.drawImage(mensito2, 32*this.secuencia, 64, 32, 32, carro.getXref(), carro.getYref(), 42, 42);
        }
        if (pulsacionTeclado.contains("UP"))
            carro.moverArriba();
        if (pulsacionTeclado.contains("DOWN"))
            carro.moverAbajo();
        
        //imagen de la puntuacion
        lapiz.strokeText("Puntaje: " + contador, 200, 10); 
        
        this.numero++;
    }
}