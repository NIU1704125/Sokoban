package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Joc {	
	
	Tauler m_tauler;
	Estat estatJoc;
	List<String> m_nivells = new ArrayList<String>();
	String m_carpetaNivells = "nivells";
	
	public void obrirSelector(){
		File dir = new File("nivells/" + m_carpetaNivells);
		if(!dir.isDirectory()) {
			throw new IllegalArgumentException("La carpeta no existe");
		}
		
		estatJoc = Estat.Selector;
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
		
		return;
	}
	
	public boolean carregarNivell(String nivell) {
		
		try{
			m_tauler = new Tauler(m_carpetaNivells + "/" + nivell);
			estatJoc = Estat.Jugant;
			return true;
		} catch(Exception e) {
			estatJoc = Estat.Selector;
			return false;
		}
		
	}
	
	public boolean seleccionarNivell(String indexNivell) 
	{
		if (indexNivell == null || indexNivell.trim().isEmpty()) {
	        return false;
	    }
		
		try {	
			int indexFiltrat = Integer.parseInt(indexNivell.trim().split("\\s+")[0]);
			
			if (indexFiltrat >= 0 && indexFiltrat < m_nivells.size()) {
	            return carregarNivell(m_nivells.get(indexFiltrat));
	        } 
			else {
	            return false;
	        }
			
		} catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public void setCarpeta(String nomCarpeta) {
		m_carpetaNivells = nomCarpeta;
	}
	
	public Estat getEstat() {
		return estatJoc;
	}
	
	public List<String> getLevels() {
		return m_nivells;
	}
	
	public Tauler getTauler() {
		return m_tauler;
	}
}
