package vista;

import models.Joc;

public class SelectorVista implements IVista {

	@Override
	public void mostrar(Joc joc) {
		FinestraJoc.getInstance().mostrarNivells(joc.getLevels());
	}

}