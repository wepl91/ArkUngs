package juego;
import java.awt.Color;
import java.awt.Point;
import entorno.Entorno;

public class Municion {
	private Point pos= new Point(0,0);
	private double radio;
	private int direccionY;
	private int velocidad;

	public Municion(int x, int y, int radio) {
		pos= new Point(x,y);
		this.radio=radio;
		this.velocidad=3;
		this.direccionY=-1;
	}
	public void dibujarse(Entorno entorno){
		entorno.dibujarCirculo(this.pos.x, this.pos.y, this.radio*2, Color.white);
	}
	public void avanzar(){
		this.pos.y=this.pos.y+this.direccionY*this.velocidad;
	}
	public void reboteV(){
		this.direccionY=-1*this.direccionY;
	}

	public Point getPos() {
		return pos;
	}

	public int getDireccionY() {
		return direccionY;
	}

	public void setDireccionY(int direccionY) {
		this.direccionY = direccionY;
	}

	public double getRadio() {
		return radio;
	}

}

