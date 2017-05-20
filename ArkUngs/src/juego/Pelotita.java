package juego;
import java.awt.Color;
import java.awt.Point;
import entorno.Entorno;

public class Pelotita{
	// Variables de instancia
	private Point pos= new Point(0,0);
	private double radio;
	private int direccionX;
	private int direccionY;
	private int velocidad;
	private boolean superbola;

	public Pelotita(int x, int y, int radio) {
		pos= new Point(x,y);
		this.radio=radio;
		this.velocidad=3;
		this.direccionX=1;
		this.direccionY=1;
		this.superbola=false;
	}

	public void dibujarse(Entorno entorno, Color p){
		entorno.dibujarCirculo(this.pos.x, this.pos.y, this.radio*2, p);
	}

	public void avanzar(){
		this.pos.x=this.pos.x+this.direccionX*this.velocidad;
		this.pos.y=this.pos.y-this.direccionY*this.velocidad;
	}

	public void reboteH(){
		this.direccionX=-1*this.direccionX;
	}

	public void reboteV(){
		this.direccionY=-1*this.direccionY;
	}

	public void enlentecer (){
		this.velocidad=2;
	}

	public void acelerar (){
		this.velocidad=5;
	}

	public int setVelocidad(int v){
		return this.velocidad=v;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}

	public double getRadio() {
		return radio;
	}

	public int getDireccionX() {
		return direccionX;
	}

	public void setDireccionX(int direccionX) {
		this.direccionX = direccionX;
	}

	public int getDireccionY() {
		return this.direccionY;
	}

	public void setDirecciony(int direcciony) {
		this.direccionY = direcciony;
	}

	public boolean getSuperbola() {
		return superbola;
	}

	public void setSuperbola(boolean superbola) {
		this.superbola = superbola;
	}
}