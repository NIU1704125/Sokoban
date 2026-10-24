package models;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
//import java.util.Stack;

public class Tauler {

	private char[][] m_tauler = new char[0][0];
	private int nFiles = 0;
	private int nCol = 0;
	private List<Caixa> m_caixes = new ArrayList<>();
	private int nInterruptors = 0;
	private Jugador m_jugador = null;
	// private int m_torn;
	// private Stack<RegistreMoviment> m_registre = new Stack<RegistreMoviment>();

	private boolean invariants() {
		if ((m_tauler.length == 0) || (m_tauler.length != nFiles) || (m_tauler[0].length != nCol)
				|| (m_jugador == null)) {
			return false;
		}
		if ((m_caixes.size() == 0) || (nInterruptors == 0) || (m_caixes.size() < nInterruptors)) {
			return false;
		}
		return true;
	}

	protected Tauler() {
	}

	public Tauler(String nomNivell) {
		List<String> lineas = new ArrayList<>();
		try {
			lineas = Files.readAllLines(Path.of("nivells/test/" + nomNivell + ".txt"));
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

	public char[][] getTauler() {
		return m_tauler;
	}

	public int getNInterrutors() {
		return nInterruptors;
	}

	public List<Caixa> getCaixes() {
		return m_caixes;
	}

	public int getFiles() {
		return nFiles;
	}

	public int getColumnes() {
		return nCol;
	}

	public Jugador getJugador() {
		return m_jugador;
	}

	public boolean hiHaParetInPosition(int x, int y) {
		if (x < 0 || x >= nCol || y < 0 || y >= nFiles) {
			return true;
		}

		return (m_tauler[y][x] == '#');
	}

	public Caixa hiHaCaixaInPosition(int x, int y) {
		for (Caixa c : m_caixes) {
			if (c == null) {
				continue;
			}

			if (c.getPosX() == x && c.getPosY() == y) {
				return c;
			}
		}
		return null;
	}
}
