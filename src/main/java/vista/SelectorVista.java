package vista;

import models.Joc;

/**
 * Vista gràfica del selector de nivells, dibuixada a FinestraJoc.
 */
public class SelectorVista implements IVista {

	/**
	 * Mostra la llista de nivells i ressalta el que apunta el cursor.
	 *
	 * @param joc model del qual s'obtenen els nivells i el cursor
	 */
	@Override
	public void mostrar(Joc joc) {
		FinestraJoc.getInstance().mostrarNivells(joc.getLevels(), joc.getCursor());
	}

}