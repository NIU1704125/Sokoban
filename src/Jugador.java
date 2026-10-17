
public class Jugador {
	//Variables
	private int m_posX, m_posY;
	private Tauler m_tauler;
	
	//Constructor
	public Jugador(Tauler tauler, int posX, int posY) 
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
		switch(dir)
		{
		case 'U':
			if(m_tauler.hiHaParetInPosition(m_posX, m_posY + 1))
			{
				m_posY++;
				return true;
			}
			break;
		case 'D':
			if(m_tauler.hiHaParetInPosition(m_posX, m_posY - 1))
			{
				m_posY--;
				return true;
			}
			break;
		case 'R':
			if(m_tauler.hiHaParetInPosition(m_posX + 1, m_posY))
			{
				m_posX++;
				return true;
			}
			break;
		case 'L':
			if(m_tauler.hiHaParetInPosition(m_posX - 1, m_posY))
			{
				m_posX--;
				return true;
			}
			break;
		default:
			throw new IllegalArgumentException("CaracterMoviment invalid");
		}
		
		return false;
	} 
}
