package models;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Tauler d'un nivell carregat des de fitxer: cel·les fixes (paret, terra i interruptor), jugador,
 * caixes, torn actual i pila de moviments per poder-los desfer.
 *
 * Format del fitxer: # paret, ' ' terra, . interruptor, $ caixa
 * i @ jugador.
 *
 * Invariant: tauler rectangular amb jugador; almenys una caixa i un interruptor, i no més
 * interruptors que caixes; torn >= 0; jugador i caixes dins del tauler i fora de les parets;
 * cap caixa sobre el jugador ni dues caixes a la mateixa casella.
 */
public class Tauler {

	private char[][] m_tauler = new char[0][0];
	private int nFiles = 0;
	private int nCol = 0;
	private List<Caixa> m_caixes = new ArrayList<>();
	private int nInterruptors = 0;
	private Jugador m_jugador = null;
	private int m_torn = 0;
	private Stack<RegistreMoviment> m_registre = new Stack<RegistreMoviment>();

	private boolean invariants() {
		if ((m_tauler.length == 0) || (m_tauler.length != nFiles) || (m_tauler[0].length != nCol)
				|| (m_jugador == null)) {
			return false;
		}
		if ((m_caixes.size() == 0) || (nInterruptors == 0) || (m_caixes.size() < nInterruptors)) {
			return false;
		}
		if(m_torn < 0) { 
			return false;
		}
		
		boolean correcte = !hiHaParetInPosition(m_jugador.getPosX(), m_jugador.getPosY());
		
		// Caixes dins del tauler, no sobre paret, no sobre el jugador i no superposades
		for (int i = 0; i < m_caixes.size(); i++) {
			Caixa ci = m_caixes.get(i);
			correcte = correcte && !hiHaParetInPosition(ci.getPosX(), ci.getPosY());
			correcte = correcte && !(ci.getPosX() == m_jugador.getPosX() && ci.getPosY() == m_jugador.getPosY());
			for (int j = i + 1; j < m_caixes.size(); j++) {
				Caixa cj = m_caixes.get(j);
				correcte = correcte && !(ci.getPosX() == cj.getPosX() && ci.getPosY() == cj.getPosY());
			}
		}
		
		return correcte;
	}

	protected Tauler() {
	}

	/**
	 * Carrega el nivell "nivells/<nomNivell>.txt".
	 *
	 * Precondicions (dades externes, es validen amb excepció): el fitxer existeix i no és buit,
	 * té com a mínim 3x3 cel·les, és rectangular, només conté caràcters vàlids, té un sol jugador i
	 * compleix l'invariant.
	 * Postcondicions: es compleix l'invariant i el torn és 0.
	 *
	 * @param nomNivell ruta del nivell relativa a nivells/, sense extensió
	 * @throws IllegalArgumentException si no es compleix alguna precondició
	 */
	public Tauler(String nomNivell) {
		m_torn = 0;
		
		List<String> lineas = new ArrayList<>();
		try {
			lineas = Files.readAllLines(Path.of("nivells/" + nomNivell + ".txt"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Fitxer no trobat");
		}

		if (lineas.isEmpty()) {
			throw new IllegalArgumentException("Nivell buit");
		}

		nFiles = lineas.size();
		nCol = lineas.get(0).length();

		if (nFiles < 3 || nCol < 3) {
			throw new IllegalArgumentException("Nivell massa petit");
		}

		m_tauler = new char[nFiles][nCol];

		for (int f = 0; f < lineas.size(); f++) {
			String linea = lineas.get(f);
			
			if (linea.length() != nCol) {
				throw new IllegalArgumentException("Nivell no rectangular");
			}
			
			for (int c = 0; c < linea.length(); c++) {
				char caracter = linea.charAt(c);

				switch (caracter) {
				case '@':
					if (m_jugador == null) {
						m_jugador = new Jugador(this, c, f);
						m_tauler[f][c] = ' ';
					} else {
						throw new IllegalArgumentException("Ja existia un jugador");
					}
					break;
				case '$':
					Caixa novaCaixa = new Caixa(this, c, f);
					m_tauler[f][c] = ' ';
					m_caixes.add(novaCaixa);
					break;
				case '.':
					m_tauler[f][c] = '.';
					nInterruptors++;
					break;
				case ' ':
					m_tauler[f][c] = ' ';
					break;
				case '#':
					m_tauler[f][c] = '#';
					break;
				default:
					throw new IllegalArgumentException("Caracter invalid");
				}
			}
		}

		if (!invariants()) {
			throw new IllegalArgumentException("No es compleixen les invariants");
		}

	}

	/** @return matriu [fila][columna] de cel·les fixes ('#', ' ', '.'), sense peces */
	public char[][] getTauler() {
		return m_tauler;
	}

	/** @return nombre d'interruptors del nivell */
	public int getNInterrutors() {
		return nInterruptors;
	}

	/** @return caixes del tauler */
	public List<Caixa> getCaixes() {
		return m_caixes;
	}

	/** @return nombre de files del tauler */
	public int getFiles() {
		return nFiles;
	}

	/** @return nombre de columnes del tauler */
	public int getColumnes() {
		return nCol;
	}

	/** @return jugador del tauler */
	public Jugador getJugador() {
		return m_jugador;
	}

	/**
	 * Indica si una posició és intransitable: les caselles fora del tauler compten com a paret.
	 *
	 * @param x columna
	 * @param y fila
	 * @return true si hi ha paret o la posició és fora del tauler
	 */
	public boolean hiHaParetInPosition(int x, int y) {
		if (x < 0 || x >= nCol || y < 0 || y >= nFiles) {
			return true;
		}

		return (m_tauler[y][x] == '#');
	}

	/**
	 * Cerca la caixa situada en una posició.
	 *
	 * @param x columna
	 * @param y fila
	 * @return la caixa de la posició, o null si no n'hi ha cap
	 */
	public Caixa hiHaCaixaInPosition(int x, int y) {
		for (Caixa c : m_caixes) {
			if (c.getPosX() == x && c.getPosY() == y) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Desfà l'últim moviment del jugador i, si en aquell moviment va empènyer una caixa, també el de la caixa.
	 *
	 * Precondició: el registre del cim de la pila correspon al jugador.
	 * Postcondició: es compleix l'invariant.
	 *
	 * @return false si no hi havia cap moviment per desfer
	 */
	public boolean desferMoviment() {
		if(m_registre.empty()) {
			return false;
		}
		
		RegistreMoviment rm = m_registre.pop(); 
		Jugador j = (Jugador) rm.getSubjecte();
		
		switch (rm.getDireccio()) {
		case 'U':
			j.setPosY(j.getPosY() + 1);
			break;
		case 'D':
			j.setPosY(j.getPosY() - 1);
			break;
		case 'R':
			j.setPosX(j.getPosX() - 1);
			break;
		case 'L':
			j.setPosX(j.getPosX() + 1);
			break;
		default:
			throw new IllegalArgumentException("Direccio de jugador invalida al registre");
		}
		
		if(!m_registre.empty() && m_registre.peek().getSubjecte() instanceof Caixa) {
			rm = m_registre.pop(); 
			Caixa c = (Caixa) rm.getSubjecte();
			
			switch (rm.getDireccio()) {
			case 'U':
				c.setPosY(c.getPosY() + 1);
				break;
			case 'D':
				c.setPosY(c.getPosY() - 1);
				break;
			case 'R':
				c.setPosX(c.getPosX() - 1);
				break;
			case 'L':
				c.setPosX(c.getPosX() + 1);
				break;
			default:
				throw new IllegalArgumentException("Direccio de caixa invalida al registre");
			}			
		}
		assert(invariants());
		return true;
	}
	
	/**
	 * Afegeix un moviment a la pila de moviments que es poden desfer.
	 *
	 * Precondició: rm != null
	 *
	 * @param rm registre del moviment
	 */
	public void afegirRegistreMoviment(RegistreMoviment rm) {
		assert(rm != null);
		m_registre.push(rm);
	}
	
	/**
	 * Avança el torn en una unitat.
	 */
	public void avancaTorn() {
		m_torn++;
	}
	
	/** @return torn actual (nombre de moviments vàlids del jugador) */
	public int getTorn() {
		return m_torn;
	}
	
	/**
	 * Comprova la condició de victòria.
	 *
	 * @return true si hi ha tantes caixes sobre interruptors com interruptors
	 */
	public boolean estatGuanyador() {
		int nActius = 0;
		
		for(Caixa c : m_caixes) {
			if(m_tauler[c.getPosY()][c.getPosX()] == '.') {
				nActius++;
			}
		}
		
		return nActius == nInterruptors;
	}
}
