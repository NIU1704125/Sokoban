package controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import vista.FinestraJoc;

/**
 * Input per teclat sobre la finestra Swing: cada tecla arriba a l'instant, sense Enter.
 *
 * Els events de Swing arriben pel seu propi fil (EDT) i el bucle del joc crida llegirTecla() des
 * del fil principal, aixi que es passen les tecles per una cua. Retorna els mateixos caracters que
 * InputConsola (en minuscula) i les fletxes es tradueixen a w/a/s/d, de manera que el controlador
 * no nota la diferencia.
 */
public class InputFinestra implements IInputController {

	private static final char CAP = '\0';

	private final BlockingQueue<Character> m_tecles = new LinkedBlockingQueue<>(32);

	public InputFinestra() {
		this(FinestraJoc.getInstance());
	}

	public InputFinestra(FinestraJoc finestra) {
		finestra.afegirKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char tecla = traduir(e);
				if (tecla != CAP) {
					m_tecles.offer(tecla);
				}
			}
		});
	}

	@Override
	public char llegirTecla() {
		try {
			return m_tecles.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return 'q';
		}
	}

	private static char traduir(KeyEvent e) {
		if (e.isControlDown() || e.isAltDown() || e.isMetaDown()) {
			return CAP;
		}

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			return 'w';
		case KeyEvent.VK_LEFT:
			return 'a';
		case KeyEvent.VK_DOWN:
			return 's';
		case KeyEvent.VK_RIGHT:
			return 'd';
		case KeyEvent.VK_ESCAPE:
			return 'q';
		case KeyEvent.VK_ENTER:
			return '\n';
		default:
			char c = e.getKeyChar();
			return Character.isLetterOrDigit(c) ? Character.toLowerCase(c) : CAP;
		}
	}
}
