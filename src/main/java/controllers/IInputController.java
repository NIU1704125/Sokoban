package controllers;

/**
 * Font d'entrada del jugador, independent del mitjà (consola, finestra o mock de test).
 */
public interface IInputController {
	/**
	 * Espera i retorna la següent tecla premuda.
	 */
	char llegirTecla();
}
