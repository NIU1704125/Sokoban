package models;

import java.util.ArrayList;
import java.util.List;

enum Estat{
	Menu,
	Selector,
	Jugant
}

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
	
	public Tauler getTauler() {
		return m_tauler;
	}
}
