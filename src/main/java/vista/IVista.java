package vista;

import models.Joc;
/**
 * Vista del joc: representa l'estat del model sense modificar-lo.
 */
public interface IVista {
	/**
	 * Mostra l'estat actual del joc.
	 *
	 * @param joc model que es representa
	 */
	void mostrar(Joc joc);
}
