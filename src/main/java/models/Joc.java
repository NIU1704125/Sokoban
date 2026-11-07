package models;

import java.util.ArrayList;
import java.util.List;

public class Joc {	
	
	Tauler m_tauler;
	Estat estatJoc;
	List<String> m_nivells = new ArrayList<String>();
	String m_carpetaNivells;
	
	public void obrirSelector(){
		
	}
	
	public boolean carregarNivell(String nivell) {
		
		try{
			m_tauler = new Tauler(nivell);
			return true;
		} catch(Exception e) {
			return false;
		}
		
	}
	
	public void setCarpeta(String nomCarpeta) {
		m_carpetaNivells = nomCarpeta;
	}
	
	public Estat getEstat()
	{
		return estatJoc;
	}
	
	public List<String> getLevels()
	{
		return m_nivells;
	}
	
	public Tauler getTauler() {
		return m_tauler;
	}
}
