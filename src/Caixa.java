
public class Caixa {
	//Variables
	private int m_posX, m_posY;
	private Tauler m_tauler;
	
	//Constructors	
	public Caixa(Tauler tauler, int posX, int posY) 
	{
		m_tauler = tauler;
		m_posX = posX;
		m_posY = posY;
	}
	
	//Getters
	public int getPosX() {return m_posX;}
	public int getPosY() {return m_posY;}
	public Tauler getTauler() {return m_tauler;}
	
	//Setters
	public void setPosX(int posX) {m_posX = posX;}
	public void setPosY(int posY) {m_posY = posY;}
	
	//Moviment
	public boolean move(char dir) 
	{
		return false;
	} 
}
