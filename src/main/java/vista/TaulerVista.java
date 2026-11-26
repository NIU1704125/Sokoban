package vista;

import models.Caixa;
import models.Joc;
import models.Jugador;
import models.Tauler;

/**
 * Vista gràfica del tauler, dibuixada a FinestraJoc.
 */
public class TaulerVista implements IVista {

	/**
	 * Construeix una còpia de l'escena (cel·les fixes, caixes i jugador) i l'envia a la finestra.
	 *
	 * Precondició: el joc té un tauler carregat.
	 *
	 * @param joc model del qual s'obté el tauler
	 */
	@Override
	public void mostrar(Joc joc) {
		Tauler tauler = joc.getTauler();

		// Es treballa sobre una còpia: el pintat es fa en un altre fil i el model pot
		// canviar mentrestant.
		char[][] base = tauler.getTauler();
		char[][] escena = new char[tauler.getFiles()][];
		for (int f = 0; f < escena.length; f++) {
			escena[f] = base[f].clone();
		}

		// '*' = caixa sobre un objectiu, '+' = jugador sobre un objectiu
		int caixesOk = 0;
		for (Caixa c : tauler.getCaixes()) {
			if (escena[c.getPosY()][c.getPosX()] == '.') {
				escena[c.getPosY()][c.getPosX()] = '*';
				caixesOk++;
			} else {
				escena[c.getPosY()][c.getPosX()] = '$';
			}
		}

		Jugador j = tauler.getJugador();
		boolean sobreObjectiu = escena[j.getPosY()][j.getPosX()] == '.';
		escena[j.getPosY()][j.getPosX()] = sobreObjectiu ? '+' : '@';

		FinestraJoc.getInstance().mostrarTauler(escena, caixesOk, tauler.getNInterrutors(), joc.getVictoria());
	}

}