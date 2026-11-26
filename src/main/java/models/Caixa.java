package models;

/**
 * Caixa del tauler, que el jugador pot empènyer una casella en qualsevol de les quatre direccions.
 */
public class Caixa {
	// Atributs
	private int m_posX, m_posY;
	private Tauler m_tauler;

	// Constructor
	/**
	 * Crea una caixa a la posició indicada; les coordenades fora del tauler s'ajusten a la vora més propera.
	 *
	 * Precondició: tauler != null
	 * Postcondició: 0 <= posX < columnes i 0 <= posY < files
	 *
	 * @param tauler tauler on es col·loca la caixa
	 * @param posX columna inicial
	 * @param posY fila inicial
	 */
	public Caixa(Tauler tauler, int posX, int posY) {
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
	/** @return columna actual de la caixa */
	public int getPosX() {
		return m_posX;
	}

	/** @return fila actual de la caixa */
	public int getPosY() {
		return m_posY;
	}

	/** @return tauler al qual pertany la caixa */
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
	 * Intenta moure la caixa una casella; només es mou si la destinació no és paret ni una altra caixa.
	 *
	 * Precondició: dir és 'U', 'D', 'R' o 'L'
	 * Postcondició: si retorna true, la caixa s'ha desplaçat una casella i el moviment
	 * s'ha registrat al tauler; si retorna false, la posició no canvia.
	 *
	 * @param dir direcció del moviment
	 * @return true si la caixa s'ha mogut
	 * @throws IllegalArgumentException si la direcció no és vàlida
	 */
	public boolean move(char dir) {
		boolean canMove = false;
		switch (dir) {
		case 'U':
			if (!m_tauler.hiHaParetInPosition(m_posX, m_posY - 1)
					&& m_tauler.hiHaCaixaInPosition(m_posX, m_posY - 1) == null) {
				m_posY--;
				canMove = true;
			}
			break;
		case 'D':
			if (!m_tauler.hiHaParetInPosition(m_posX, m_posY + 1)
					&& m_tauler.hiHaCaixaInPosition(m_posX, m_posY + 1) == null) {
				m_posY++;
				canMove = true;
			}
			break;
		case 'R':
			if (!m_tauler.hiHaParetInPosition(m_posX + 1, m_posY)
					&& m_tauler.hiHaCaixaInPosition(m_posX + 1, m_posY) == null) {
				m_posX++;
				canMove = true;
			}
			break;
		case 'L':
			if (!m_tauler.hiHaParetInPosition(m_posX - 1, m_posY)
					&& m_tauler.hiHaCaixaInPosition(m_posX - 1, m_posY) == null) {
				m_posX--;
				canMove = true;
			}
			break;
		default:
			throw new IllegalArgumentException("CaracterMoviment invalid");
		}

		if(canMove) {
			m_tauler.afegirRegistreMoviment(new RegistreMoviment(m_tauler.getTorn(), dir, this));
			return true;
		}
		
		return false;
	}
}
