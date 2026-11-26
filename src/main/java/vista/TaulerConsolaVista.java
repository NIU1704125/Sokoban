package vista;

import models.Caixa;
import models.Joc;
import models.Jugador;
import models.Tauler;

/**
 * Vista per consola del tauler: imprimeix el nivell amb caràcters ($ caixa, @ jugador).
 */
public class TaulerConsolaVista implements IVista {

	/**
	 * Imprimeix el tauler actual i les tecles de control.
	 *
	 * Precondició: el joc té un tauler carregat.
	 *
	 * @param joc model del qual s'obté el tauler
	 */
	public void mostrar(Joc joc) {
		
		Tauler tauler = joc.getTauler();
		char[][] base = tauler.getTauler();
		char[][] pantalla = new char[tauler.getFiles()][];
		for (int f = 0; f < pantalla.length; f++) {
			pantalla[f] = base[f].clone();
		}

		for (Caixa c : tauler.getCaixes()) {
			int x = c.getPosX();
			int y = c.getPosY();
			pantalla[y][x] = '$';
		}

		Jugador j = tauler.getJugador();
		pantalla[j.getPosY()][j.getPosX()] = '@';

		System.out.println();
		for (char[] fila : pantalla) {
			System.out.println(new String(fila));
		}
		System.out.println();
		System.out.print("w/a/s/d = moure, q = sortir > ");
	}
	
}
