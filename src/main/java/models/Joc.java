package models;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Model principal del joc: gestiona l'estat (selector, partida o final), la llista de nivells
 * de la carpeta activa, el cursor del selector i el tauler en joc.
 *
 * Invariant: m_nivells != null, m_cursor >= 0 i, si l'estat és
 * Jugant, m_tauler != null
 */
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
	
	/**
	 * Torna al selector i recarrega la llista de nivells txt de nivells/<carpeta>,
	 * ordenada alfabèticament. Si el cursor queda fora de la nova llista, es reinicia a 0.
	 *
	 * Precondició: la carpeta de nivells existeix.
	 * Postcondicions: l'estat és Selector, no hi ha victòria i, si la llista no és
	 * buida, m_cursor < m_nivells.size().
	 *
	 * @throws IllegalArgumentException si la carpeta no existeix
	 */
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
		
		// Postcondicions
		assert(estatJoc == Estat.Selector);
		assert(m_victoria == false);
		assert(m_nivells.isEmpty() || m_cursor < m_nivells.size());
		assert(invariant());
	}
	
	/**
	 * Carrega un nivell de la carpeta activa i, si és vàlid, comença la partida.
	 *
	 * Postcondicions: si retorna true, l'estat és Jugant i hi ha tauler;
	 * si retorna false, l'estat és Selector.
	 *
	 * @param nivell nom del fitxer de nivell, sense extensió
	 * @return true si el nivell s'ha carregat correctament
	 */
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
		
		// Postcondicions
		assert(!carregat || (estatJoc == Estat.Jugant && m_tauler != null));
		assert(carregat || estatJoc == Estat.Selector);
		assert(invariant());
		return carregat;
	}
	
	/**
	 * Carrega el nivell on apunta el cursor.
	 *
	 * Precondicions: l'estat és Selector i la llista de nivells no és buida.
	 *
	 * @return true si el nivell s'ha carregat correctament
	 */
	public boolean seleccionarNivell() {
		// Precondicions
		assert(estatJoc == Estat.Selector);
		assert(!m_nivells.isEmpty());
		
		return carregarNivell(m_nivells.get(m_cursor));
	}
	
	/**
	 * Indica la subcarpeta de nivells/ d'on es llegeixen els nivells.
	 *
	 * @param nomCarpeta nom de la subcarpeta
	 */
	public void setCarpeta(String nomCarpeta) {
		m_carpetaNivells = nomCarpeta;
	}
	
	/**
	 * Aplica una tecla a la partida: w/a/s/d mouen el jugador amunt/esquerra/avall/dreta, z desfà
	 * l'últim moviment i la resta de tecles s'ignoren. Després actualitza la condició de victòria.
	 *
	 * Precondicions: hi ha tauler, l'estat és Jugant i no hi ha victòria.
	 * Postcondició: getVictoria() coincideix amb estatGuanyador().
	 *
	 * @param tecla tecla premuda
	 */
	public void mouJugador(char tecla) {
		// Precondicions
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
	
	/**
	 * Mou el cursor del selector de forma circular: w puja i s baixa; la resta de tecles s'ignoren.
	 * Si no hi ha nivells, no fa res.
	 *
	 * Precondició: l'estat és Selector.
	 * Postcondició: si hi ha nivells, 0 <= m_cursor < m_nivells.size()
	 *
	 * @param tecla tecla premuda
	 */
	public void mouCursor(char tecla){
		// Precondicions
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
		
		// Postcondicions
		assert(m_cursor >= 0 && m_cursor < m_nivells.size());
	}
	
	/**
	 * Marca el joc com a acabat.
	 *
	 * Postcondició: l'estat és Final
	 */
	public void finalitzarJoc() {
		estatJoc = Estat.Final;
	}
	
	/** @return estat actual del joc */
	public Estat getEstat() {
		return estatJoc;
	}
	
	/** @return noms dels nivells de la carpeta activa, en ordre alfabètic */
	public List<String> getLevels() {
		return m_nivells;
	}
	
	/** @return true si la partida actual s'ha guanyat */
	public boolean getVictoria(){
		return m_victoria;
	}
	
	/** @return tauler actual, o null si encara no se n'ha carregat cap */
	public Tauler getTauler() {
		return m_tauler;
	}
	
	/**
	 * Assigna directament un tauler i comença la partida (permet injectar un mock als tests).
	 *
	 * <p><b>Postcondició:</b> l'estat és {@code Jugant}.</p>
	 *
	 * @param t tauler que es jugarà
	 */
	public void setTauler(Tauler t){
		m_tauler = t;
		estatJoc = Estat.Jugant;
	}
	
	/** @return índex del nivell seleccionat al selector */
	public int getCursor() {
		return m_cursor;
	}
}
