package juego;
import java.awt.Color;
import java.util.Random;
import entorno.Entorno;
import entorno.InterfaceJuego;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.LinkedList;

public class Juego extends InterfaceJuego{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	// Variables y mÃ©todos propios de cada grupo
	private Pelotita pelotita;
	private Nave nave;
	private Ladrillo[] ladrillo;
	private Municion municion1;
	private LinkedList<Recompensa> recompensanue;
	private Color[] colores;
	private boolean lanzaPelotita,LanzaDisparo,pegoteActivado;
	private int vidas,puntaje;
	private Image fondoIzquierdo;
	private Random rand;

	// ...

	Juego(){
		//creacion de nro random

		//llenar de elementos el arreglo de colores
		this.colores=new Color[5];
		this.colores[0]=Color.BLUE;
		this.colores[1]=Color.RED;
		this.colores[2]=Color.DARK_GRAY;
		this.colores[3]=Color.GREEN;
		this.colores[4]=Color.WHITE;
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this,"ArkaUngs - Oviedo - Pereyra Lopez - Sauma", 1000, 600);

		// Inicializar lo que haga falta para el juego
		this.vidas=5;
		this.puntaje=0;
		this.fondoIzquierdo = new ImageIcon("img.jpg").getImage();
		this.lanzaPelotita=false;
		this.LanzaDisparo=false;
		this.pegoteActivado=false;
		this.rand=new Random();

		//creacion de objetos
		//creacion de nave
		this.nave = new Nave(100, entorno.alto()-70);
		//creacion de pelota
		//creacion de pelotita
		this.pelotita = new Pelotita(nave.getX(), nave.getY()-7, 10);
		//creacion de ladrillo
		int z=0;
		int x=30;
		int y=50;
		this.ladrillo=new Ladrillo[60];
		this.recompensanue= new LinkedList<Recompensa>();
		for(int i=0;i<4;i++){
			for(int j=0;j<15;j++){
				this.ladrillo[z]=new Ladrillo(x,y,colores[rand.nextInt(4)]);
				z++;
				x=x+55;
			}
			x=30;
			y=y+30;
		}

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el mÃ©todo tick() serÃ¡ ejecutado en cada instante y 
	 * por lo tanto es el mÃ©todo mÃ¡s importante de esta clase. AquÃ­ se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick(){
		//Procesamiento de un instante de tiempo
		//Dibujar elementos
		//Dibujar fondo de pantalla
		this.entorno.dibujarImagen(fondoIzquierdo,415,305,0);
		//Dibujar pelotita (si esta con "superbola" se dibuja de otro color)
		if (this.pelotita.getSuperbola()){
			this.pelotita.dibujarse(entorno,colores[0]);
		}
		else {
			this.pelotita.dibujarse(entorno,colores[4]);
		}
		//Dibujar nave                            
		this.nave.dibujarse(entorno);


		//Se tiene que presionar la tecla espacio para que la pelotita se separe de la nave//
		if(this.entorno.sePresiono(this.entorno.TECLA_ESPACIO)&& this.vidas>0){
			this.lanzaPelotita=true;
		}

		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////INICIO Y LANZAMIENTO DE PELOTITA/MENSAJES DE "GANASTE" Y "PERDISTE"//////
		int cont=0;
		if(!this.lanzaPelotita){
			this.pelotita.setPos(this.nave.getX(),this.nave.getY()-(int)(this.pelotita.getRadio()*2-4));
			this.entorno.cambiarFont("helvetica",24, Color.pink);
			if(this.vidas>0 && cont!=this.ladrillo.length){
				this.entorno.escribirTexto("Presiona espacio para lanzar la pelota",250,300);
			}
			else{
				this.entorno.escribirTexto("Perdiste..",350,300);
			}
		}
		else{
			this.pelotita.avanzar();
		}

		for (int i=0; i<this.ladrillo.length;i++){
			if (this.ladrillo[i]==null){
				cont=cont+1;
			}
			if(cont==this.ladrillo.length){
				this.entorno.cambiarFont("helvetica",24, Color.pink);
				this.entorno.escribirTexto("GANASTE!!",350,300);
				this.pelotita.setVelocidad(0);
			}
		}
		////////////////INICIO Y LANZAMIENTO DE PELOTITA/MENSAJES DE "GANASTE" Y "PERDISTE"//////
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////LADRILLOS/RECOMPENSAS/DISPAROS///////////////////////////////////////////
		for(int i=0; i<this.ladrillo.length;i++){
			if(this.ladrillo[i]!=null){
				this.ladrillo[i].dibujarse(entorno);
				if(this.ladrillo[i].pelotitaRebotaConLadrillo(this.pelotita)){
					if (this.ladrillo[i].getColor()==Color.darkGray && !this.pelotita.getSuperbola()){
						this.ladrillo[i].cambioDeColor();
						this.puntaje=this.puntaje+15;

					}
					else{
						if (this.ladrillo[i].getRecompensa()!=null){
							this.recompensanue.add(this.ladrillo[i].getRecompensa());
						}
						this.ladrillo[i]=null;
						this.puntaje=this.puntaje+15;
					}
				}

				//Desaparece con disparo
				else if(this.municion1!=null && this.ladrillo[i].disparoTocaConLadrillo(this.municion1)){
					if (this.ladrillo[i].getColor()!=Color.darkGray){
						this.ladrillo[i]=null;
						this.puntaje=this.puntaje+7;

					}
					else{
						this.ladrillo[i].cambioDeColor();
						this.puntaje=this.puntaje+7;

					}
					this.municion1=null;
				}
			}
		}
		for (int i=0; i<this.recompensanue.size();i++){
			this.recompensanue.get(i).dibujarse(this.entorno);
			this.recompensanue.get(i).caer();
			if(AtrapaRecompensa(this.recompensanue.get(i), this.nave)){
				this.superpoderesOff(this.nave);
				this.ActivarPoder(this.recompensanue.get(i).gettipo(), this.nave);
				this.recompensanue.remove(i);
			}
			else if(this.recompensanue.get(i).getY()>entorno.alto()-10){
				this.recompensanue.remove(i);
			}

		}
		////////////////LADRILLOS/RECOMPENSAS/DISPAROS///////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////COLISIONES///////////////////////////////////////////////////
		if(this.pelotita.getPos().x-this.pelotita.getRadio()<=0 ||
				this.pelotita.getPos().x+this.pelotita.getRadio()>=(this.entorno.getWidth()-200)){
			this.pelotita.reboteH();
		}
		if(this.pelotita.getPos().y-this.pelotita.getRadio()<=0){
			this.pelotita.reboteV();
		}

		//si la pelotita cae, vuelve a la barra
		if(this.pelotita.getPos().y+this.pelotita.getRadio()>=this.entorno.getHeight()){
			this.vidas-=1;
			this.lanzaPelotita=false;
		}

		//Colisiones con la nave
		if(this.pelotita.getPos().x-this.pelotita.getRadio()<this.nave.getX()+(this.nave.getAncho()/2) &&
				this.pelotita.getPos().x+this.pelotita.getRadio()>this.nave.getX()-(this.nave.getAncho()/2)){
			if(this.pelotita.getPos().y+this.pelotita.getRadio()>=this.nave.getY()-(this.nave.getAlto()/2)&&
					(this.pelotita.getPos().y+this.pelotita.getRadio()<=this.nave.getY()-(this.nave.getAlto()/2)+5)){
				this.pelotita.reboteV();
				if(this.pegoteActivado){
					this.pegote();
				}                  
			}
		}
		////////////////////////////COLISIONES///////////////////////////////////////////////////              
		/////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////MOVIMIENTO DE LA NAVE////////////////////////////////////////			
		if (this.entorno.estaPresionada(this.entorno.TECLA_IZQUIERDA) &&
				this.nave.getX()-(this.nave.getAncho()/2) > 0){
			this.nave.moverIzquierda();
		}
		if (this.entorno.estaPresionada(this.entorno.TECLA_DERECHA) &&
				this.nave.getX()+(this.nave.getAncho()/2) <this.entorno.ancho()-180 ){
			this.nave.moverDerecha();
		}
		////////////////////////////MOVIMIENTO DE LA NAVE////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////DISPARO//////////////////////////////////////////////////////
		if(this.municion1==null && this.entorno.sePresiono(this.entorno.TECLA_CTRL) &&
				this.lanzaPelotita && this.nave.getarmado()){
			this.LanzaDisparo=true;
			this.municion1 = new Municion(this.nave.getX(), this.nave.getY()-5, 5);
		}
		if(this.municion1!=null && this.LanzaDisparo && this.municion1.getPos().y>15){
			this.municion1.avanzar();
			this.municion1.dibujarse(entorno);
		}
		else{
			this.municion1=null;
		}
		////////////////////////////DISPARO//////////////////////////////////////////////////////       
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////MENSAJES POR PANTALLA////////////////////////////////////////////////////
		this.entorno.cambiarFont("helvetica",24, Color.MAGENTA);
		this.entorno.escribirTexto("ArkaUngs",850,100);
		this.entorno.escribirTexto("Vidas:"+this.vidas,855,150);
		this.entorno.cambiarFont("helvetica",24, Color.GREEN);
		this.entorno.escribirTexto("Puntaje",850,200);
		this.entorno.escribirTexto(""+this.puntaje,890,230);
		this.entorno.cambiarFont("helvetica",20, Color.MAGENTA);
		this.entorno.escribirTexto("Recompensas:",850,450);
		this.entorno.cambiarFont("helvetica",16, Color.PINK);
		this.entorno.escribirTexto("acelerar",865,470);
		this.entorno.cambiarFont("helvetica",16, Color.CYAN);
		this.entorno.escribirTexto("Relentizar",865,490);
		this.entorno.cambiarFont("helvetica",16, Color.BLUE);
		this.entorno.escribirTexto("Superbola",865,510);
		this.entorno.cambiarFont("helvetica",16, Color.GREEN);
		this.entorno.escribirTexto("Alargar",865,530);
		this.entorno.cambiarFont("helvetica",16, Color.GRAY);
		this.entorno.escribirTexto("Vida Extra",865,550);
		this.entorno.cambiarFont("helvetica",16, Color.RED);
		this.entorno.escribirTexto("Disparo",865,570);
		this.entorno.cambiarFont("helvetica",16, Color.ORANGE);
		this.entorno.escribirTexto("Pegote",865,590);
		if (this.nave.getarmado()==true){
			this.entorno.cambiarFont("helvetica",16, Color.WHITE);
			this.entorno.escribirTexto("CTRL para disparar",835,400);
		}
		////////////////MENSAJES POR PANTALLA////////////////////////////////////////////////////		
		/////////////////////////////////////////////////////////////////////////////////////////


		/////////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////MODO TRAMPA//////////////////////////////////////////////////
		if(this.entorno.sePresiono('1')){
			this.pegoteActivado=true;
		}
		if(this.entorno.sePresiono('2')){
			this.nave.alargar();
		}
		if(this.entorno.sePresiono('3')){
			this.pelotita.acelerar();
		}
		if(this.entorno.sePresiono('4')){
			this.pelotita.enlentecer();
		}
		if(this.entorno.sePresiono('5')){
			this.nave.setarmado(true);
		}
		if(this.entorno.sePresiono('6')){
			this.vidaExtra();
		}
		if (this.entorno.sePresiono('7')){
			this.pelotita.setSuperbola(true);
		}
		////////////////////////////MODO TRAMPA//////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////////////////////
	}


	///////////////////////////RECOMPENSAS///////////////////////////////////////////////////	
	public void vidaExtra(){
		this.vidas+=1;
	}

	public void pegote(){
		this.lanzaPelotita=false;
	}
	public void superpoderesOff(Nave nave){
		nave.setAncho(100);
		nave.setarmado(false);
		this.pelotita.setVelocidad(3);
		this.pegoteActivado=false; 
		this.pelotita.setSuperbola(false);
	}
	public boolean AtrapaRecompensa(Recompensa recompensa,Nave nave){
		if (recompensa!=null && recompensa.getY()>nave.getY()-5 &&
				recompensa.getY()<nave.getY()+5 && recompensa.getX()>nave.getX()-(nave.getAncho()/2) &&
				recompensa.getX()<nave.getX()+(nave.getAncho()/2)){
			return true;
		}
		else{
			return false;
		}                     
	}
	public void ActivarPoder(int poder,Nave nave){
		if (poder==1){  // Superpoder Acelerar
			this.pelotita.acelerar();			
		}
		else if (poder==2) { // Superpoder Relentizar
			this.pelotita.enlentecer();
		}
		else if (poder==3) { // Superpoder Vida Extra
			this.vidaExtra();
		}
		else if (poder==4) { // Superpoder Agrandar
			nave.alargar();
		}
		else if (poder==5) { // Superpoder Arma
			nave.setarmado(true);
		}
		else if (poder==6){ //Superdpoder BolaGrande
			this.pelotita.setSuperbola(true);                                                                                               
		}
		else if (poder==0){
			this.pegoteActivado=true;                                                                                               
		}
	}
	///////////////////////////RECOMPENSAS///////////////////////////////////////////////////		
	/////////////////////////////////////////////////////////////////////////////////////////

	////////////////////////////FUNCIONES////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unused")
	public static void main(String[] args){
		Juego juego =new Juego();
	}

}