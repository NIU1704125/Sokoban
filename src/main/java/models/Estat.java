package models;

/**
 * Estats possibles del joc, que determinen quin controlador s'executa.
 */
public enum Estat {
	/** Selecció de nivell. */
	Selector,
	/** Partida en curs. */
	Jugant,
	/** Sortida de l'aplicació. */
	Final
}
