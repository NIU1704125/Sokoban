package models;

/**
 * Registre immutable d'un moviment (torn, direcció i element mogut), utilitzat per desfer-lo.
 */
public class RegistreMoviment {
	private int m_torn;
	private char m_direccio;
	private Object m_subjecte;

	/**
	 * Crea un registre de moviment.
	 *
	 * Precondicions: torn >= 0; dir és 'U', 'D', 'R' o
	 * 'L'; subjecte és un Jugador o una Caixa.
	 *
	 * @param torn torn en què es produeix el moviment
	 * @param dir direcció del moviment
	 * @param subjecte element que s'ha mogut
	 * @throws IllegalArgumentException si no es compleix alguna precondició
	 */
	public RegistreMoviment(int torn, char dir, Object subjecte) {
		if (torn < 0) {
			throw new IllegalArgumentException("Torn no valid");
		}

		if (dir != 'U' && dir != 'D' && dir != 'R' && dir != 'L') {
			throw new IllegalArgumentException("Direccio no valida");
		}

		if (!(subjecte instanceof Jugador) && !(subjecte instanceof Caixa)) {
			throw new IllegalArgumentException("Tipus de subjecte no reconegut");
		}

		this.m_torn = torn;
		this.m_direccio = dir;
		this.m_subjecte = subjecte;
	}

	/** @return torn del moviment */
	public int getTorn() {
		return m_torn;
	}

	/** @return direcció del moviment ('U', 'D', 'R' o 'L') */
	public char getDireccio() {
		return m_direccio;
	}

	/** @return element mogut (Jugador o Caixa) */
	public Object getSubjecte() {
		return m_subjecte;
	}
}
