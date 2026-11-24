package vista;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Finestra unica del joc. Les vistes (TaulerVista, SelectorVista) hi dibuixen i
 * InputFinestra n'escolta el teclat. Es crea la primera vegada que es demana.
 *
 * Swing nomes es pot tocar des del seu propi fil (EDT), mentre que el bucle del joc s'executa al fil
 * principal. Per aixo els metodes mostrar* deleguen el pintat a l'EDT amb invokeLater.
 */
public final class FinestraJoc {

	private static FinestraJoc s_instancia;

	private PanellJoc m_panell;

	public static synchronized FinestraJoc getInstance() {
		if (s_instancia == null) {
			s_instancia = new FinestraJoc();
		}
		return s_instancia;
	}

	private FinestraJoc() {
		if (SwingUtilities.isEventDispatchThread()) {
			crear();
			return;
		}
		try {
			SwingUtilities.invokeAndWait(this::crear);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("Interromput mentre es creava la finestra", e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException("No s'ha pogut crear la finestra", e.getCause());
		}
	}

	private void crear() {
		m_panell = new PanellJoc();
		m_panell.setFocusable(true);
		m_panell.setPreferredSize(new Dimension(800, 600));

		JFrame frame = new JFrame("Sokoban");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(m_panell);
		frame.pack();
		frame.setMinimumSize(new Dimension(520, 400));
		frame.setLocationRelativeTo(null);

		// Si la finestra perd i recupera el focus (per exemple en clicar la barra de titol),
		// el teclat ha de tornar al panell.
		frame.addWindowFocusListener(new WindowAdapter() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
				m_panell.requestFocusInWindow();
			}
		});

		frame.setVisible(true);
		m_panell.requestFocusInWindow();
	}

	/**
	 * Mostra el tauler. L'escena ha de ser una copia que ningu mes modifiqui (vegeu TaulerVista).
	 */
	public void mostrarTauler(char[][] escena, int caixesOk, int objectius, boolean victoria) {
		SwingUtilities.invokeLater(() -> m_panell.setTauler(escena, caixesOk, objectius, victoria));
	}

	public void mostrarNivells(List<String> nivells, int cursor) {
		List<String> copia = List.copyOf(nivells);
		SwingUtilities.invokeLater(() -> m_panell.setNivells(copia, cursor));
	}

	public void afegirKeyListener(KeyListener listener) {
		m_panell.addKeyListener(listener);
	}
}
