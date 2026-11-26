package models;

/**
 * Jugador del tauler: es mou en quatre direccions i pot empènyer una caixa si la casella següent és lliure.
 */
public class Jugador {
	// Atributs
	private int m_posX, m_posY;
	private Tauler m_tauler;

	// Constructor
	/**
	 * Crea un jugador a la posició indicada; les coordenades fora del tauler s'ajusten a la vora més propera.
	 *
	 * Precondició: tauler != null.
	 * Postcondició: 0 <= posX < columnes i 0 <= posY < files.
	 *
	 * @param tauler tauler on es col·loca el jugador
	 * @param posX columna inicial
	 * @param posY fila inicial
	 */
	public Jugador(Tauler tauler, int posX, int posY) {
		assert(tauler != null);
		if (posX < 0) {
			posX = 0;
		}

		if (posY < 0) {
			posY = 0;
		}

		if (posX >= tauler.getColumnes()) {
			posX = tauler.getColumnes() - 1;
		}

		if (posY >= tauler.getFiles()) {
			posY = tauler.getFiles() - 1;
		}

		m_tauler = tauler;
		m_posX = posX;
		m_posY = posY;
	}

	// Getters
	/** @return columna actual del jugador */
	public int getPosX() {
		return m_posX;
	}

	/** @return fila actual del jugador */
	public int getPosY() {
		return m_posY;
	}

	/** @return tauler al qual pertany el jugador */
	public Tauler getTauler() {
		return m_tauler;
	}

	// Setters
	/**
	 * Fixa la columna sense validar-la (s'utilitza per desfer moviments).
	 *
	 * @param posX nova columna
	 */
	public void setPosX(int posX) {
		m_posX = posX;
	}

	/**
	 * Fixa la fila sense validar-la (s'utilitza per desfer moviments).
	 *
	 * @param posY nova fila
	 */
	public void setPosY(int posY) {
		m_posY = posY;
	}

	// Moviment
	/**
	 * Intenta moure el jugador una casella. Si la destinació és paret no es mou; si hi ha una caixa,
	 * només avança quan la caixa es pot empènyer.
	 *
	 * Precondició: dir és 'U', 'D', 'R' o 'L'.
	 * Postcondició: si retorna true, el jugador s'ha desplaçat una casella, el moviment
	 * s'ha registrat i el torn ha avançat en 1; si retorna false, no canvia res.
	 *
	 * @param dir direcció del moviment
	 * @return true si el jugador s'ha mogut
	 * @throws IllegalArgumentException si la direcció no és vàlida
	 */
	public boolean move(char dir) {
		switch (dir) {
		case 'U':
			if (m_tauler.hiHaParetInPosition(m_posX, m_posY - 1)) {
				return false;
			}

			Caixa c = m_tauler.hiHaCaixaInPosition(m_posX, m_posY - 1);
			if (c != null) {
				if (c.move(dir)) {
					m_posY--;
					break;
				} else {
					return false;
				}
			} else {
				m_posY--;
				break;
			}
		case 'D':
			if (m_tauler.hiHaParetInPosition(m_posX, m_posY + 1)) {
				return false;
			}

			c = m_tauler.hiHaCaixaInPosition(m_posX, m_posY + 1);
			if (c != null) {
				if (c.move(dir)) {
					m_posY++;
					break;
				} else {
					return false;
				}
			} else {
				m_posY++;
				break;
			}
		case 'R':
			if (m_tauler.hiHaParetInPosition(m_posX + 1, m_posY)) {
				return false;
			}

			c = m_tauler.hiHaCaixaInPosition(m_posX + 1, m_posY);
			if (c != null) {
				if (c.move(dir)) {
					m_posX++;
					break;
				} else {
					return false;
				}
			} else {
				m_posX++;
				break;
			}
		case 'L':
			if (m_tauler.hiHaParetInPosition(m_posX - 1, m_posY)) {
				return false;
			}

			c = m_tauler.hiHaCaixaInPosition(m_posX - 1, m_posY);
			if (c != null) {
				if (c.move(dir)) {
					m_posX--;
					break;
				} else {
					return false;
				}
			} else {
				m_posX--;
				break;
			}
		default:
			throw new IllegalArgumentException("CaracterMoviment invalid");
		}
		
		m_tauler.afegirRegistreMoviment(new RegistreMoviment(m_tauler.getTorn(), dir, this));
		m_tauler.avancaTorn();
		return true;
	}
}
