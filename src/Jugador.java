
public class Jugador {
	//Variables
	private int m_posX, m_posY;
	
	//Constructors
	public Jugador() 
	{
		m_posX = 0;
		m_posY = 0;
	}
	
	public Jugador(int posX, int posY) 
	{
		m_posX = posX;
		m_posY = posY;
	}
	
	//Getters
	public int getPosX() {return m_posX;}
	public int getPosY() {return m_posY;}
	
	//Setters
	public void setPosX(int posX) {m_posX = posX;}
	public void setPosY(int posY) {m_posY = posY;}
	
	//Moviment
	public void move(char dir) 
	{
		return;
	} 
}
