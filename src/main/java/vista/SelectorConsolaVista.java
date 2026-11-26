package vista;

import java.util.List;
import models.Joc;

/**
 * Vista per consola del selector de nivells: llista numerada dels nivells disponibles.
 */
public class SelectorConsolaVista implements IVista {

	/**
	 * Imprimeix la llista de nivells de la carpeta activa.
	 *
	 * @param joc model del qual s'obtenen els nivells
	 */
	public void mostrar(Joc joc) {
		List<String> nivells = joc.getLevels();

		System.out.println();
		System.out.println("=== NIVELLS ===");
		for (int i = 0; i < nivells.size(); i++) {
			System.out.println((i + 1) + ". " + nivells.get(i));
		}
		System.out.println("0. Enrere");
		System.out.print("> ");
	}

}
