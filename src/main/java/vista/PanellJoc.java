package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.swing.JPanel;

/**
 * Panell que dibuixa el joc (tauler o selector de nivells).
 *
 * Nomes s'ha de cridar des del fil d'events de Swing (EDT). FinestraJoc ja s'encarrega de fer-ho.
 */
public class PanellJoc extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int ALCADA_HUD = 40;
	private static final int MIDA_MAX_CEL = 96;

	private static final Color FONS = new Color(28, 30, 36);
	private static final Color SOL = new Color(62, 66, 78);
	private static final Color SOL_LINIA = new Color(52, 55, 66);
	private static final Color PARET = new Color(138, 92, 74);
	private static final Color PARET_LINIA = new Color(86, 54, 44);
	private static final Color OBJECTIU = new Color(240, 170, 60);
	private static final Color CAIXA = new Color(190, 135, 62);
	private static final Color CAIXA_LINIA = new Color(112, 72, 28);
	private static final Color CAIXA_OK = new Color(110, 185, 100);
	private static final Color CAIXA_OK_LINIA = new Color(50, 110, 52);
	private static final Color JUGADOR = new Color(72, 140, 232);
	private static final Color JUGADOR_LLUM = new Color(160, 200, 255);
	private static final Color TEXT = new Color(225, 228, 235);
	private static final Color TEXT_SUAU = new Color(140, 146, 160);

	// Foto del tauler. Caracters: # paret, ' ' terra, . objectiu, $ caixa, * caixa sobre objectiu,
	// @ jugador, + jugador sobre objectiu
	private char[][] m_escena;
	private boolean[][] m_interior;
	private int m_caixesOk;
	private int m_objectius;

	// Llista de nivells del selector (null si no s'esta mostrant)
	private transient List<String> m_nivells;

	// ------------------------------------------------------------------ Estat

	public void setTauler(char[][] escena, int caixesOk, int objectius) {
		m_escena = escena;
		m_interior = calcularInterior(escena);
		m_caixesOk = caixesOk;
		m_objectius = objectius;
		m_nivells = null;
		repaint();
	}

	public void setNivells(List<String> nivells) {
		m_nivells = nivells;
		m_escena = null;
		m_interior = null;
		repaint();
	}

	/**
	 * Marca com a "interior" les cel.les accessibles des del jugador. Aixi nomes es dibuixa terra
	 * dins de les parets i els espais de fora (que al fitxer son ' ' igualment) queden buits.
	 */
	private static boolean[][] calcularInterior(char[][] escena) {
		int files = escena.length;
		int cols = escena[0].length;
		boolean[][] interior = new boolean[files][cols];
		Deque<int[]> pendents = new ArrayDeque<>();

		for (int f = 0; f < files; f++) {
			for (int c = 0; c < cols; c++) {
				if (escena[f][c] == '@' || escena[f][c] == '+') {
					pendents.push(new int[] { f, c });
				}
			}
		}

		while (!pendents.isEmpty()) {
			int[] p = pendents.pop();
			int f = p[0];
			int c = p[1];
			if (f < 0 || f >= files || c < 0 || c >= cols || interior[f][c]) {
				continue;
			}
			if (escena[f][c] == '#' || escena[f][c] == '\0') {
				continue;
			}
			interior[f][c] = true;
			pendents.push(new int[] { f + 1, c });
			pendents.push(new int[] { f - 1, c });
			pendents.push(new int[] { f, c + 1 });
			pendents.push(new int[] { f, c - 1 });
		}
		return interior;
	}

	// ---------------------------------------------------------------- Pintat

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2.setColor(FONS);
		g2.fillRect(0, 0, getWidth(), getHeight());

		if (m_escena != null) {
			pintarTauler(g2);
		} else if (m_nivells != null) {
			pintarSelector(g2);
		}
		g2.dispose();
	}

	private void pintarTauler(Graphics2D g2) {
		int files = m_escena.length;
		int cols = m_escena[0].length;
		int alcadaZona = getHeight() - ALCADA_HUD;
		int cel = Math.min(MIDA_MAX_CEL, Math.min(getWidth() / cols, alcadaZona / files));
		cel = Math.max(cel, 1);
		int x0 = (getWidth() - cel * cols) / 2;
		int y0 = (alcadaZona - cel * files) / 2;

		for (int f = 0; f < files; f++) {
			for (int c = 0; c < cols; c++) {
				int x = x0 + c * cel;
				int y = y0 + f * cel;
				char ch = m_escena[f][c];

				if (ch == '#') {
					pintarParet(g2, x, y, cel);
				} else if (m_interior[f][c]) {
					pintarCasella(g2, ch, x, y, cel);
				}
			}
		}
		pintarHud(g2);
	}

	private void pintarCasella(Graphics2D g2, char ch, int x, int y, int s) {
		pintarSol(g2, x, y, s);
		switch (ch) {
		case '.':
			pintarObjectiu(g2, x, y, s);
			break;
		case '$':
			pintarCaixa(g2, x, y, s, false);
			break;
		case '*':
			pintarCaixa(g2, x, y, s, true);
			break;
		case '@':
			pintarJugador(g2, x, y, s);
			break;
		case '+':
			pintarObjectiu(g2, x, y, s);
			pintarJugador(g2, x, y, s);
			break;
		default:
			break;
		}
	}

	private void pintarSol(Graphics2D g2, int x, int y, int s) {
		g2.setColor(SOL);
		g2.fillRect(x, y, s, s);
		g2.setColor(SOL_LINIA);
		g2.setStroke(new BasicStroke(1f));
		g2.drawRect(x, y, s - 1, s - 1);
	}

	private void pintarParet(Graphics2D g2, int x, int y, int s) {
		int m = s / 2;
		g2.setColor(PARET);
		g2.fillRect(x, y, s, s);
		g2.setColor(PARET_LINIA);
		g2.setStroke(new BasicStroke(Math.max(1f, s / 24f)));
		g2.drawLine(x, y + m, x + s - 1, y + m);
		g2.drawLine(x + m, y, x + m, y + m);
		g2.drawLine(x + s / 4, y + m, x + s / 4, y + s - 1);
		g2.drawLine(x + 3 * s / 4, y + m, x + 3 * s / 4, y + s - 1);
		g2.drawRect(x, y, s - 1, s - 1);
	}

	private void pintarObjectiu(Graphics2D g2, int x, int y, int s) {
		int punt = Math.max(2, s / 6);
		g2.setColor(OBJECTIU);
		g2.fillOval(x + (s - punt) / 2, y + (s - punt) / 2, punt, punt);
		g2.setStroke(new BasicStroke(Math.max(1f, s / 18f)));
		g2.drawOval(x + s / 4, y + s / 4, s / 2, s / 2);
	}

	private void pintarCaixa(Graphics2D g2, int x, int y, int s, boolean alObjectiu) {
		int marge = Math.max(2, s / 10);
		int costat = s - 2 * marge;
		int arrodoniment = s / 8;

		g2.setColor(alObjectiu ? CAIXA_OK : CAIXA);
		g2.fillRoundRect(x + marge, y + marge, costat, costat, arrodoniment, arrodoniment);

		g2.setColor(alObjectiu ? CAIXA_OK_LINIA : CAIXA_LINIA);
		g2.setStroke(new BasicStroke(Math.max(1.5f, s / 14f)));
		g2.drawRoundRect(x + marge, y + marge, costat, costat, arrodoniment, arrodoniment);
		g2.drawLine(x + marge, y + marge, x + marge + costat, y + marge + costat);
		g2.drawLine(x + marge + costat, y + marge, x + marge, y + marge + costat);
	}

	private void pintarJugador(Graphics2D g2, int x, int y, int s) {
		int diametre = s * 7 / 10;
		int px = x + (s - diametre) / 2;
		int py = y + (s - diametre) / 2;

		g2.setColor(JUGADOR);
		g2.fillOval(px, py, diametre, diametre);
		g2.setColor(JUGADOR_LLUM);
		g2.fillOval(px + diametre / 5, py + diametre / 6, diametre / 3, diametre / 3);
		g2.setColor(JUGADOR.darker());
		g2.setStroke(new BasicStroke(Math.max(1.5f, s / 20f)));
		g2.drawOval(px, py, diametre, diametre);
	}

	private void pintarHud(Graphics2D g2) {
		int y = getHeight() - ALCADA_HUD / 2 + 5;
		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		g2.setColor(TEXT);
		g2.drawString("Caixes a lloc: " + m_caixesOk + " / " + m_objectius, 16, y);

		String ajuda = "WASD / fletxes: moure   |   Q: sortir";
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		g2.setColor(TEXT_SUAU);
		g2.drawString(ajuda, getWidth() - 16 - g2.getFontMetrics().stringWidth(ajuda), y);
	}

	private void pintarSelector(Graphics2D g2) {
		int n = m_nivells.size();

		g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g2.setColor(TEXT);
		pintarCentrat(g2, "NIVELLS", 70);

		if (n == 0) {
			g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
			g2.setColor(TEXT_SUAU);
			pintarCentrat(g2, "No hi ha nivells en aquesta carpeta", 130);
		}

		// Una linia per nivell mes la de "0. Enrere"; si n'hi ha molts, la lletra es fa mes petita
		int pas = Math.max(16, Math.min(34, (getHeight() - 200) / (n + 1)));
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, Math.max(12, pas * 2 / 3)));
		FontMetrics fm = g2.getFontMetrics();

		int amplada = fm.stringWidth("0. Enrere");
		for (int i = 0; i < n; i++) {
			amplada = Math.max(amplada, fm.stringWidth((i + 1) + ". " + m_nivells.get(i)));
		}
		int x = (getWidth() - amplada) / 2;

		int y = (n == 0) ? 180 : 120;
		g2.setColor(TEXT);
		for (int i = 0; i < n; i++) {
			g2.drawString((i + 1) + ". " + m_nivells.get(i), x, y);
			y += pas;
		}
		g2.setColor(TEXT_SUAU);
		g2.drawString("0. Enrere", x, y);

		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
		pintarCentrat(g2, "Prem la tecla del nivell", getHeight() - 20);
	}

	private void pintarCentrat(Graphics2D g2, String text, int y) {
		int amplada = g2.getFontMetrics().stringWidth(text);
		g2.drawString(text, (getWidth() - amplada) / 2, y);
	}
}
