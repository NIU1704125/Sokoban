package vista;

import models.Caixa;
import models.Joc;
import models.Jugador;
import models.Tauler;

public class TaulerVista {

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
