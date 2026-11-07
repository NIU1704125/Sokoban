package vista;

import java.util.List;
import models.Joc;

public class SelectorVista implements IVista {

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
