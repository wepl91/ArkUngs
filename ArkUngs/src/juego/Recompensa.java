package juego;

import entorno.Entorno;
import java.awt.Color;
import java.util.Random;
public class Recompensa {
	Random rand=new Random();
	// Variables de instancia
	private int x, y;
	private int ancho;
	private int alto;
	private int tipo;

	public Recompensa(int x, int y){
		this.x = x;
		this.y = y;
		this.ancho = 10;
		this.alto = 10;
		this.tipo= rand.nextInt(7);
	}

	public void dibujarse(Entorno entorno){
		if (this.tipo==6){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.BLUE);
		}
		else if (this.tipo==2){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.CYAN);			
		}
		else if (this.tipo==3){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.GRAY);			
		}
		else if (this.tipo==4){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.GREEN);	
		}
		else if (this.tipo==5){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.RED);	
		}
		else if (this.tipo==0){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.ORANGE);	
		}
		else if (this.tipo==1){
			entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0,Color.PINK);	
		}
	}

	public void caer(){ 
		this.y=this.y+2;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public int gettipo(){
		return this.tipo;
	}

}