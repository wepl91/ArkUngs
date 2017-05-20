package juego;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import entorno.Entorno;

public class Ladrillo {
	//variables de instancia
	private Point pos= new Point(0,0);
	private int ancho;
	private int alto;
	private Color color;
	private Random rand;
	public Recompensa recompensa;
	public Ladrillo(int x, int y,Color color){
		pos= new Point(x,y);
		this.ancho = 50;
		this.alto = 20;
		this.rand=new Random();
		if (rand.nextInt(7)==1){
			this.recompensa=new Recompensa(this.pos.x,this.pos.y);
		}
		else{
			this.recompensa=null;
		}
		this.color=color;
	}

	public void dibujarse(Entorno entorno){
		entorno.dibujarRectangulo(this.pos.x, this.pos.y, this.ancho, this.alto, 0, this.color);
	}

	public Recompensa entregaRecompensa(){
		return this.recompensa;
	}

	public void cambioDeColor(){
		if (this.color==java.awt.Color.darkGray){
			this.color=java.awt.Color.gray;
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////COLISION DE LOS DISPAROS Y LADRILLOS/////////////////////////
	public boolean disparoTocaConLadrillo(Municion p){
		//colision con inferior de ladrillo
		if(p.getPos().y-p.getRadio()>=this.pos.y+((this.alto/2)-2)){
			if(p.getPos().y-p.getRadio()<=this.pos.y+((this.alto/2))){
				if(p.getPos().x>=this.pos.x-((this.ancho/2))){
					if(p.getPos().x<=this.pos.x+((this.ancho/2)+0)){	
						p.reboteV();					
						return true;
					}
				}	
			}
		}	
		//	colision con esquinas
		else if((distancia(p.getPos(),this.TLCorner())<p.getRadio())){
			return true;
		}
		else if (distancia(p.getPos(),this.TRCorner())<p.getRadio()){
			return true;
		}
		else if (distancia(p.getPos(),this.BLCorner())<p.getRadio()){
			return true;
		}
		else if (distancia(p.getPos(),this.BRCorner())<p.getRadio()){
			return true;				
		}
		return false;
	}
	////////////////////////////COLISION DE LOS DISPAROS Y LADRILLOS/////////////////////////	
	/////////////////////////////////////////////////////////////////////////////////////////	
	////////////////////////////COLISION DE LA PELOTITA Y LOS LADRILLOS//////////////////////
	public boolean pelotitaRebotaConLadrillo(Pelotita p){
		//colision con laterales 
		if(p.getPos().x+p.getRadio()<=this.pos.x-((this.ancho/2)-2)){
			if(p.getPos().x+p.getRadio()>=this.pos.x-(this.ancho/2)){
				if(p.getPos().y<=this.pos.y+(this.alto/2)){
					if(p.getPos().y>=this.pos.y-(this.alto/2)){
						if(p.getSuperbola()){//Si es superbola, no rebota en los ladrillo
							return true;
						}
						else{//Si es pelotita normal, rebota	
							p.reboteH();
							return true;
						}
					}
				}
			}
		}
		else if(p.getPos().x-p.getRadio()>=this.pos.x+((this.ancho/2)-2)){
			if(p.getPos().x-p.getRadio()<=this.pos.x+((this.ancho/2))){
				if(p.getPos().y<=(this.pos.y+(this.alto/2))){
					if(p.getPos().y>=(this.pos.y-(this.alto/2))){
						if(p.getSuperbola()){//Si es superbola, no rebota en los ladrillo
							return true;
						}
						else{//Si es pelotita normal, rebota	
							p.reboteH();
							return true;
						}

					}	
				}
			}
		}
		//	colision con superior de ladrillo
		else if(p.getPos().y+p.getRadio()<=this.pos.y-((this.alto/2)-2)){
			if(p.getPos().y+p.getRadio()>=this.pos.y-(this.alto/2)){
				if(p.getPos().x>=this.pos.x-(this.ancho/2)){
					if(p.getPos().x<=this.pos.x+(this.ancho/2)){
						if(p.getSuperbola()){//Si es superbola, no rebota en los ladrillo
							return true;
						}
						else{//Si es pelotita normal, rebota	
							p.reboteV();
							return true;
						}	
					}
				}
			}
		}

		//	colision con inferior de ladrillo
		else if(p.getPos().y-p.getRadio()>=this.pos.y+((this.alto/2)-2)){
			if(p.getPos().y-p.getRadio()<=this.pos.y+(this.alto/2)){
				if(p.getPos().x>=this.pos.x-(this.ancho/2)){
					if(p.getPos().x<=this.pos.x+(this.ancho/2)){
						if(p.getSuperbola()){//Si es superbola, no rebota en los ladrillo
							return true;
						}
						else{//Si es pelotita normal, rebota	
							p.reboteV(); 				
							return true;
						}
					}
				}	
			}
		}	
		//	colision con esquinas
		else if((distancia(p.getPos(),this.TLCorner())<p.getRadio())){
			if(p.getSuperbola()){
				return true;
			}
			else{
				p.setDireccionX(-1);
				p.setDirecciony(1);
				return true;
			}
		}
		else if (distancia(p.getPos(),this.TRCorner())<p.getRadio()){
			if(p.getSuperbola()){
				return true;
			}
			else{
				p.setDireccionX(1);
				p.setDirecciony(1);
				return true;
			}
		}
		else if (distancia(p.getPos(),this.BLCorner())<p.getRadio()){
			if(p.getSuperbola()){
				return true;
			}
			else{
				p.setDireccionX(-1);
				p.setDirecciony(-1);
				return true;
			}
		}
		else if (distancia(p.getPos(),this.BRCorner())<p.getRadio()){
			if(p.getSuperbola()){
				return true;
			}
			else{
				p.setDireccionX(1);
				p.setDirecciony(-1);
				return true;
			}
		}
		return false;
	}
	////////////////////////////COLISION DE LA PELOTITA Y LOS LADRILLOS//////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////DISTANCIA ENTRE DOS PUNTOS////////////////////////////////////
	static double distancia(Point p1, Point p2){
		double distancia;
		distancia=Math.sqrt(((p1.x-p2.x)*(p1.x-p2.x))+((p1.y-p2.y)*(p1.y-p2.y)));
		return distancia;
	}
	///////////////////////////DISTANCIA ENTRE DOS PUNTOS////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////





	public Color getColor() {
		return color;
	}

	public Recompensa getRecompensa() {
		return recompensa;
	}

	private Point BLCorner(){
		return new Point(pos.x-(ancho/2),pos.y+(alto/2));
	}

	private Point BRCorner(){
		return new Point(pos.x+(ancho/2),pos.y+(alto/2));
	}

	private Point TLCorner(){
		return new Point(pos.x-(ancho/2),pos.y-(alto/2));
	}

	private Point TRCorner(){
		return new Point(pos.x+(ancho/2),pos.y-(alto/2));
	}

}