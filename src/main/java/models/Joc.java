package models;

public class Joc {	
	
	Tauler m_tauler;
	
	
	public boolean carregarNivell(String nivell) {
		
		try
		{
			m_tauler = new Tauler(nivell);
			return true;
		} catch(Exception e) {return false;}
		
	}
	
	public Tauler getTauler()
	{
		return m_tauler;;
	}
}
