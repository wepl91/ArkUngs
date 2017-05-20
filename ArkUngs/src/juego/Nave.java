package juego;

import java.awt.Color;
import entorno.Entorno;

public class Nave
{
	// Variables de instancia
	private int x;
	private int y;
	private int ancho;
	private int alto;
	private boolean armado;

	public Nave(int x, int y) {
		this.x = x;
		this.y = y;
		this.ancho = 100;
		this.alto = 10;
		this.armado=false;
	}

	public void moverIzquierda(){
		this.x -= 8;
	}

	public void moverDerecha(){
		this.x += 8;
	}

	public void dibujarse(Entorno entorno){
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.yellow);
	}

	public void alargar(){
		this.ancho=200;
	}
	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean getarmado() {
		return this.armado;
	}

	public void setarmado(boolean x) {
		this.armado = x;
	}
}