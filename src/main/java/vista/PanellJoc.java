package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panell que dibuixa el joc (tauler o selector de nivells) amb suport per a sprites
 * i interfície millorada.
 *
 * Només s'ha de cridar des del fil d'events de Swing (EDT).
 */
public class PanellJoc extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int ALCADA_HUD = 48;
	private static final int MIDA_MAX_CEL = 96;

	// Paleta de colors (respaldo per a sprites i estil de la interfície)
	private static final Color FONS_DALT = new Color(122, 122, 122);
	private static final Color FONS_BAIX = new Color(79,79,79);
	private static final Color HUD_FONS = new Color(15, 17, 23);
	private static final Color HUD_VORA = new Color(52, 57, 72);
	private static final Color TARGETA_FONS = new Color(30, 34, 44, 235);
	private static final Color TARGETA_VORA = new Color(68, 75, 94);
	private static final Color ACCENT = new Color(240, 170, 60);
	private static final Color TECLA_FONS = new Color(44, 49, 62);
	private static final Color TECLA_VORA = new Color(82, 90, 110);

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
	private static final Color TEXT = new Color(235, 238, 245);
	private static final Color TEXT_SUAU = new Color(150, 158, 175);

	// Sprites del joc i de la interfície
	private transient BufferedImage m_imgParet;
	private transient BufferedImage m_imgSol;
	private transient BufferedImage m_imgObjectiu;
	private transient BufferedImage m_imgCaixa;
	private transient BufferedImage m_imgCaixaOk;
	private transient BufferedImage m_imgJugador;
	private transient BufferedImage m_imgLogo;

	// Fonts de la interfície
	private transient Font m_fontTitol;
	private transient Font m_fontSubtitol;
	private transient Font m_fontLlista;
	private transient Font m_fontHud;
	private transient Font m_fontPetit;

	// Estat del tauler: # paret, ' ' terra, . objectiu, $ caixa, * caixa sobre objectiu,
	// @ jugador, + jugador sobre objectiu
	private char[][] m_escena;
	private boolean[][] m_interior;
	private int m_caixesOk;
	private int m_objectius;

	// Llista de nivells del selector (null si no s'està mostrant)
	private transient List<String> m_nivells;

	public PanellJoc() {
		carregarRecursos();
	}

	// ------------------------------------------------------------------ Càrrega de recursos

	private void carregarRecursos() {
		m_imgParet = carregarImatge("wall.png");
		m_imgSol = carregarImatge("floor.png");
		m_imgObjectiu = carregarImatge("goal.png");
		m_imgCaixa = carregarImatge("crate.png");
		m_imgCaixaOk = carregarImatge("caixa_ok.png");
		m_imgJugador = carregarImatge("player.png");
		m_imgLogo = carregarImatge("logo.png");

		carregarFonts();
	}

	private BufferedImage carregarImatge(String nomFitxer) {
		try {
			// 1. Intenta carregar des del classpath (si 'resources' és Source Folder o dins de 'src')
			InputStream is = getClass().getResourceAsStream("/" + nomFitxer);
			if (is == null) {
				is = getClass().getResourceAsStream("/resources/" + nomFitxer);
			}
			if (is != null) {
				BufferedImage img = ImageIO.read(is);
				is.close();
				return img;
			}

			// 2. Intenta carregar des de la carpeta local 'resources/' del projecte
			File fitxer = new File("resources", nomFitxer);
			if (fitxer.exists()) {
				return ImageIO.read(fitxer);
			}
		} catch (IOException e) {
			System.err.println("Error carregant sprite '" + nomFitxer + "': " + e.getMessage());
		}
		return null;
	}

	private void carregarFonts() {
		Font base = null;
		try {
			InputStream is = getClass().getResourceAsStream("/font.ttf");
			if (is == null) {
				is = getClass().getResourceAsStream("/resources/font.ttf");
			}
			if (is != null) {
				base = Font.createFont(Font.TRUETYPE_FONT, is);
				is.close();
			} else {
				File fitxerFont = new File("resources", "font.ttf");
				if (fitxerFont.exists()) {
					base = Font.createFont(Font.TRUETYPE_FONT, fitxerFont);
				}
			}

			if (base != null) {
				GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(base);
			}
		} catch (Exception e) {
			System.err.println("No s'ha pogut carregar 'font.ttf', s'usarà la font per defecte.");
		}

		if (base != null) {
			m_fontTitol = base.deriveFont(Font.BOLD, 28f);
			m_fontSubtitol = base.deriveFont(Font.PLAIN, 13f);
			m_fontLlista = base.deriveFont(Font.PLAIN, 16f);
			m_fontHud = base.deriveFont(Font.BOLD, 14f);
			m_fontPetit = base.deriveFont(Font.PLAIN, 12f);
		} else {
			m_fontTitol = new Font(Font.SANS_SERIF, Font.BOLD, 32);
			m_fontSubtitol = new Font(Font.SANS_SERIF, Font.BOLD, 13);
			m_fontLlista = new Font(Font.SANS_SERIF, Font.PLAIN, 17);
			m_fontHud = new Font(Font.SANS_SERIF, Font.BOLD, 15);
			m_fontPetit = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		}
	}

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
	 * Marca com a "interior" les cel·les accessibles des del jugador. Així només es dibuixa terra
	 * dins de les parets i els espais de fora queden buits.
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

	// ------------------------------------------------------------------ Pintat general

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// Canvia a VALUE_INTERPOLATION_NEAREST_NEIGHBOR si fas servir sprites Pixel Art
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

		pintarFons(g2);

		if (m_escena != null) {
			pintarTauler(g2);
		} else if (m_nivells != null) {
			pintarSelector(g2);
		}
		g2.dispose();
	}

	private void pintarFons(Graphics2D g2) {
		GradientPaint degradat = new GradientPaint(0, 0, FONS_DALT, 0, getHeight(), FONS_BAIX);
		g2.setPaint(degradat);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

	// ------------------------------------------------------------------ Pintat del tauler

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
					pintarSol(g2, x, y, cel);
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
		if (m_imgSol != null) {
			g2.drawImage(m_imgSol, x, y, s, s, null);
		} else {
			g2.setColor(SOL);
			g2.fillRect(x, y, s, s);
			g2.setColor(SOL_LINIA);
			g2.setStroke(new BasicStroke(1f));
			g2.drawRect(x, y, s - 1, s - 1);
		}
	}

	private void pintarParet(Graphics2D g2, int x, int y, int s) {
		if (m_imgParet != null) {
			g2.drawImage(m_imgParet, x, y, s, s, null);
		} else {
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
	}

	private void pintarObjectiu(Graphics2D g2, int x, int y, int s) {
		if (m_imgObjectiu != null) {
			g2.drawImage(m_imgObjectiu, x, y, s, s, null);
		} else {
			int punt = Math.max(2, s / 6);
			g2.setColor(OBJECTIU);
			g2.fillOval(x + (s - punt) / 2, y + (s - punt) / 2, punt, punt);
			g2.setStroke(new BasicStroke(Math.max(1f, s / 18f)));
			g2.drawOval(x + s / 4, y + s / 4, s / 2, s / 2);
		}
	}

	private void pintarCaixa(Graphics2D g2, int x, int y, int s, boolean alObjectiu) {
		BufferedImage sprite = alObjectiu ? m_imgCaixaOk : m_imgCaixa;
		if (sprite != null) {
			g2.drawImage(sprite, x, y, s, s, null);
		} else {
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
	}

	private void pintarJugador(Graphics2D g2, int x, int y, int s) {
		if (m_imgJugador != null) {
			g2.drawImage(m_imgJugador, x, y, s, s, null);
		} else {
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
	}

	// ------------------------------------------------------------------ Interfície (HUD i Selector)

	private void pintarHud(Graphics2D g2) {
		int yTop = getHeight() - ALCADA_HUD;

		// Barra inferior estil·litzada
		g2.setColor(HUD_FONS);
		g2.fillRect(0, yTop, getWidth(), ALCADA_HUD);
		g2.setColor(HUD_VORA);
		g2.setStroke(new BasicStroke(1f));
		g2.drawLine(0, yTop, getWidth(), yTop);

		// Icona de caixa al marcador esquerre
		int midaIcona = 24;
		int yIcona = yTop + (ALCADA_HUD - midaIcona) / 2;
		pintarCaixa(g2, 16, yIcona, midaIcona, m_caixesOk == m_objectius && m_objectius > 0);

		g2.setFont(m_fontHud);
		FontMetrics fm = g2.getFontMetrics();
		int yText = yTop + (ALCADA_HUD + fm.getAscent() - fm.getDescent()) / 2;

		String comptador = "Caixes: " + m_caixesOk + " / " + m_objectius;
		Color colorComptador = (m_caixesOk == m_objectius && m_objectius > 0) ? CAIXA_OK : TEXT;
		pintarTextAmbSombra(g2, comptador, 48, yText, colorComptador);

		// Controls amb etiquetes tipus tecla a la dreta
		int xDreta = getWidth() - 16;
		xDreta = pintarControlTecla(g2, xDreta, yTop, "Q", "Sortir");
		xDreta -= 16;
		pintarControlTecla(g2, xDreta, yTop, "WASD / Fletxes", "Moure");
	}

	private int pintarControlTecla(Graphics2D g2, int xFinal, int yTop, String tecla, String accio) {
		g2.setFont(m_fontPetit);
		FontMetrics fmPetit = g2.getFontMetrics();

		int ampleAccio = fmPetit.stringWidth(accio);
		int ampleTecla = fmPetit.stringWidth(tecla) + 14;
		int altTecla = 22;
		int yTecla = yTop + (ALCADA_HUD - altTecla) / 2;
		int yText = yTop + (ALCADA_HUD + fmPetit.getAscent() - fmPetit.getDescent()) / 2;

		int xAccio = xFinal - ampleAccio;
		g2.setColor(TEXT_SUAU);
		g2.drawString(accio, xAccio, yText);

		int xTecla = xAccio - ampleTecla - 8;
		g2.setColor(TECLA_FONS);
		g2.fillRoundRect(xTecla, yTecla, ampleTecla, altTecla, 6, 6);
		g2.setColor(TECLA_VORA);
		g2.drawRoundRect(xTecla, yTecla, ampleTecla, altTecla, 6, 6);

		g2.setColor(TEXT);
		g2.drawString(tecla, xTecla + 7, yText);

		return xTecla;
	}

	private void pintarSelector(Graphics2D g2) {
		int n = m_nivells.size();
		int yCursor = 55;

		// Capçalera o logo
		if (m_imgLogo != null) {
			int maxAmple = Math.min(360, getWidth() - 60);
			int ample = Math.min(m_imgLogo.getWidth(), maxAmple);
			int alt = (int) ((double) m_imgLogo.getHeight() * ample / m_imgLogo.getWidth());
			g2.drawImage(m_imgLogo, (getWidth() - ample) / 2, 24, ample, alt, null);
			yCursor = 24 + alt + 26;
		} else {
			g2.setFont(m_fontTitol);
			pintarTextCentratAmbSombra(g2, "SOKOBAN", yCursor, ACCENT);
			yCursor += 24;

			g2.setFont(m_fontSubtitol);
			g2.setColor(TEXT_SUAU);
			pintarCentrat(g2, "SELECCIÓ DE NIVELLS", yCursor);
			yCursor += 24;
		}

		// Targeta central contenidora
		int margeLateral = Math.max(40, getWidth() / 6);
		int ampleTargeta = Math.min(480, getWidth() - 2 * margeLateral);
		int xTargeta = (getWidth() - ampleTargeta) / 2;
		int yTargeta = yCursor;
		int altTargeta = getHeight() - yTargeta - 65;

		g2.setColor(TARGETA_FONS);
		g2.fillRoundRect(xTargeta, yTargeta, ampleTargeta, altTargeta, 18, 18);
		g2.setColor(TARGETA_VORA);
		g2.setStroke(new BasicStroke(1.5f));
		g2.drawRoundRect(xTargeta, yTargeta, ampleTargeta, altTargeta, 18, 18);

		if (n == 0) {
			g2.setFont(m_fontLlista);
			g2.setColor(TEXT_SUAU);
			pintarCentrat(g2, "No hi ha nivells en aquesta carpeta", yTargeta + altTargeta / 2 - 10);

			int yEnrere = yTargeta + altTargeta - 28;
			pintarFilaNivell(g2, xTargeta + 24, yEnrere, ampleTargeta - 48, "0", "Enrere", true);
		} else {
			int espaiIntern = altTargeta - 36;
			int pas = Math.max(24, Math.min(40, espaiIntern / (n + 1)));
			float midaFont = Math.max(12f, Math.min(16f, pas * 0.48f));
			g2.setFont(m_fontLlista.deriveFont(midaFont));

			int yFila = yTargeta + 24 + pas / 2;
			for (int i = 0; i < n; i++) {
				pintarFilaNivell(g2, xTargeta + 24, yFila, ampleTargeta - 48,
						String.valueOf(i + 1), m_nivells.get(i), false);
				yFila += pas;
			}

			// Línia separadora abans de "0. Enrere"
			g2.setColor(TARGETA_VORA);
			g2.drawLine(xTargeta + 24, yFila - pas / 2, xTargeta + ampleTargeta - 24, yFila - pas / 2);
			pintarFilaNivell(g2, xTargeta + 24, yFila + 4, ampleTargeta - 48, "0", "Enrere", true);
		}

		// Peu d'ajuda
		g2.setFont(m_fontPetit);
		g2.setColor(TEXT_SUAU);
		pintarCentrat(g2, "Prem la tecla numèrica del nivell per començar", getHeight() - 26);
	}

	private void pintarFilaNivell(Graphics2D g2, int x, int y, int ample, String tecla, String nom, boolean esSortida) {
		FontMetrics fm = g2.getFontMetrics();
		int altBadge = Math.max(20, fm.getHeight() + 2);
		int ampleBadge = Math.max(26, fm.stringWidth(tecla) + 12);
		int yBadge = y - fm.getAscent() - (altBadge - fm.getHeight()) / 2;

		// Distintiu numèric
		g2.setColor(esSortida ? TECLA_FONS : new Color(ACCENT.getRed(), ACCENT.getGreen(), ACCENT.getBlue(), 45));
		g2.fillRoundRect(x, yBadge, ampleBadge, altBadge, 8, 8);
		g2.setColor(esSortida ? TECLA_VORA : ACCENT);
		g2.drawRoundRect(x, yBadge, ampleBadge, altBadge, 8, 8);

		int xNum = x + (ampleBadge - fm.stringWidth(tecla)) / 2;
		g2.setColor(esSortida ? TEXT_SUAU : ACCENT);
		g2.drawString(tecla, xNum, y);

		// Nom del nivell
		g2.setColor(esSortida ? TEXT_SUAU : TEXT);
		g2.drawString(nom, x + ampleBadge + 14, y);
	}

	// ------------------------------------------------------------------ Utilitats de text

	private void pintarCentrat(Graphics2D g2, String text, int y) {
		int amplada = g2.getFontMetrics().stringWidth(text);
		g2.drawString(text, (getWidth() - amplada) / 2, y);
	}

	private void pintarTextAmbSombra(Graphics2D g2, String text, int x, int y, Color color) {
		g2.setColor(Color.BLACK);
		g2.drawString(text, x + 1, y + 2);
		g2.setColor(color);
		g2.drawString(text, x, y);
	}

	private void pintarTextCentratAmbSombra(Graphics2D g2, String text, int y, Color color) {
		int amplada = g2.getFontMetrics().stringWidth(text);
		pintarTextAmbSombra(g2, text, (getWidth() - amplada) / 2, y, color);
	}
}