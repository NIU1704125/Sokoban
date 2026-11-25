package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Joc {	
	
	Tauler m_tauler;
	Estat estatJoc = Estat.Selector;
	List<String> m_nivells = new ArrayList<String>();
	String m_carpetaNivells = "nivells";
	boolean m_victoria;
	int m_cursor = 0;
	
	private boolean invariant() {
		return m_nivells != null && m_cursor >= 0
				&& (estatJoc != Estat.Jugant || m_tauler != null);
	}
	
	public void obrirSelector(){
		File dir = new File("nivells/" + m_carpetaNivells);
		if(!dir.isDirectory()) {
			throw new IllegalArgumentException("La carpeta no existe");
		}
		
		estatJoc = Estat.Selector;
        m_victoria = false;
		m_nivells = new ArrayList<String>();

		File[] fitxers = dir.listFiles();
		if(fitxers == null) {
			return;
		}
		
		for(int i = 0; i < fitxers.length; i++) {
			String nom = fitxers[i].getName();
			if(fitxers[i].isFile() && nom.endsWith(".txt")){
				m_nivells.add(nom.substring(0, nom.length() - 4));
			}
		}
		
		m_nivells.sort(null);
		
		if(m_cursor >= m_nivells.size()) {
			m_cursor = 0;
		}
		
		//Postcondicions
		assert(estatJoc == Estat.Selector);
		assert(m_victoria == false);
		assert(m_nivells.isEmpty() || m_cursor < m_nivells.size());
		assert(invariant());
	}
	
	public boolean carregarNivell(String nivell) {
		boolean carregat;
		
		try{
			m_tauler = new Tauler(m_carpetaNivells + "/" + nivell);
			estatJoc = Estat.Jugant;
			carregat = true;
		} catch(Exception e) {
			estatJoc = Estat.Selector;
			carregat = false;
		}
		
		//Postcondicions
		assert(!carregat || (estatJoc == Estat.Jugant && m_tauler != null));
		assert(carregat || estatJoc == Estat.Selector);
		assert(invariant());
		return carregat;
	}
	
	public boolean seleccionarNivell() {
		//Precondicions
		assert(estatJoc == Estat.Selector);
		assert(!m_nivells.isEmpty());
		
		return carregarNivell(m_nivells.get(m_cursor));
	}
	
	public void setCarpeta(String nomCarpeta) {
		m_carpetaNivells = nomCarpeta;
	}
	
	public void mouJugador(char tecla) {
		//Precondicions
		assert(m_tauler != null);
		assert(estatJoc == Estat.Jugant);
		assert(m_victoria == false);
		
		switch(tecla) {
		case 'w':
			m_tauler.getJugador().move('U');
			break;
		case 's':
			m_tauler.getJugador().move('D');
			break;
		case 'a':
			m_tauler.getJugador().move('L');
			break;
		case 'd':
			m_tauler.getJugador().move('R');
			break;
        case 'z':
            m_tauler.desferMoviment();
            break;
		default:
			break;
		}
        m_victoria = m_tauler.estatGuanyador();
		assert(invariant());
	}
	
	public void mouCursor(char tecla){
		//Precondicions
		assert(estatJoc == Estat.Selector);
		
		if(m_nivells.size() == 0) {
			return;
		}
		
		switch(tecla){
		case 'w':
			m_cursor -= 1;
			if(m_cursor < 0) {
				m_cursor = m_nivells.size() - 1;
			}
			break;
		case 's':
			m_cursor += 1;
			if(m_cursor >= m_nivells.size()) {
				m_cursor = 0;
			}
			break;
		default:
			break;
		}
		
		//Postcondicions
		assert(m_cursor >= 0 && m_cursor < m_nivells.size());
	}
	
	public void finalitzarJoc() {
		estatJoc = Estat.Final;
	}
	
	public Estat getEstat() {
		return estatJoc;
	}
	
	public List<String> getLevels() {
		return m_nivells;
	}
	
	public boolean getVictoria(){
		return m_victoria;
	}
	
	public Tauler getTauler() {
		return m_tauler;
	}
	
	public void setTauler(Tauler t){
		m_tauler = t;
		estatJoc = Estat.Jugant;
	}
	
	public int getCursor() {
		return m_cursor;
	}
}
