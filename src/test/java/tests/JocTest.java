package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import models.Joc;
import models.Estat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import models.Jugador;

import java.io.File;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Proves de Joc amb els nivells reals de nivells/ i, per a mouJugador,
 * amb el mock propi MockTauler i un Jugador simulat amb Mockito.
 */
class JocTest {

	Joc joc;
	
	@BeforeEach
	void setup() {
		joc = new Joc();
	}
	
	/**
	 * Joc.carregarNivell(String)}.
	 * Tècniques: particions equivalents (nivell vàlid / no vàlid) i disseny per contracte
	 * (postcondicions: Jugant amb tauler si carrega, Selector si no).
	 */
	@Test
	void testCarregarNivell() {
		// Partició vàlida: Nivell_1 → estat Jugant i tauler carregat
		joc.setCarpeta("test");
		assertTrue(joc.carregarNivell("Nivell_1"));
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		joc = new Joc();
		joc.setCarpeta("test");
		// Partició vàlida: Nivell_2
		assertTrue(joc.carregarNivell("Nivell_2"));
		assertTrue(joc.getTauler() != null);
		
		joc = new Joc();
		joc.setCarpeta("test");
		// Partició no vàlida: nivell inexistent → estat Selector i sense tauler
		assertFalse(joc.carregarNivell(""));
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
	}
	
	/**
	 * Joc.obrirSelector().
	 * Tècniques: disseny per contracte (precondició de carpeta existent; postcondicions d'estat
	 * Selector i sense victòria) i caixa blanca: prova de bucles (loop testing) del bucle simple
	 * sobre els fitxers de la carpeta amb 0, 1, 2, 10 (típic) i 20 iteracions.
	 */
	@Test
	void testObrirSelector() {
		// Disseny per contracte: carpeta inexistent → IllegalArgumentException (no s'arriba al bucle)
		try{
			joc.setCarpeta("null");
			joc.obrirSelector();
			assertTrue(false);

		} catch (Exception e) {}

		
		File carpetaBuida = new File("nivells/carpetaBuida");
		carpetaBuida.mkdirs();
		
		// Loop testing: 0 iteracions (carpeta buida)
		joc.setCarpeta("carpetaBuida");
		joc.obrirSelector();
		assertEquals(0, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
		
		// Loop testing: 1 iteració
		joc.setCarpeta("1fitxer");
		joc.obrirSelector();
		assertEquals(1, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
		
		// Loop testing: 2 iteracions
		joc.setCarpeta("2fitxer");
		joc.obrirSelector();
		assertEquals(2, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
		
		// Loop testing: 10 iteracions (valor típic)
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertEquals(10, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
		
		// Loop testing: 20 iteracions (valor gran)
		joc.setCarpeta("20fitxer");
		joc.obrirSelector();
		assertEquals(20, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
	}
	
	/**
	 * Joc.mouCursor(char).
	 * Tècniques: caixa blanca: cobertura de camins (camins P1-P7; P8, la violació de la postcondició,
	 * és infactible), disseny per contracte (precondició d'estat Selector), valors límit del
	 * cursor (0 i n-1, amb pas circular) i particions equivalents de tecles (w/s vàlides, la resta no).
	 */
	@Test
	void testMouCursor(){		
		// Disseny per contracte (camí P1): moure el cursor mentre es juga → AssertionError
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.carregarNivell("Nivell_1");
		assertThrows(AssertionError.class, () -> joc.mouCursor('s'));
		
		joc = new Joc();
		
		// Valor inicial del cursor
		assertEquals(joc.getCursor(), 0);
		
		// Camí P2: llista de nivells buida → el cursor no es mou
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 0);
		
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 0);
		
		// Carpeta amb 10 nivells (índexs 0..9)
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		
		// Camí P5: 's' sense passar de l'últim nivell
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 1);
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 2);
		
		// Camí P3: 'w' sense passar del primer nivell
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 1);
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 0);
		
		// Valor límit 0 (camí P4): 'w' des del primer nivell salta a l'últim
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 9);
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 8);
		
		// Valor límit n-1 = 9 (camí P6): 's' des de l'últim nivell salta al primer
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 9);
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 0);
		
		// Partició no vàlida de tecles (camí P7): espai, altres lletres i majúscules no mouen el cursor
		joc.mouCursor(' ');
		assertEquals(joc.getCursor(), 0);

		joc.mouCursor('a');
		assertEquals(joc.getCursor(), 0);
		
		joc.mouCursor('W');
		assertEquals(joc.getCursor(), 0);

		joc.mouCursor('S');
		assertEquals(joc.getCursor(), 0);
		
		// Disseny per contracte: postcondició d'obrirSelector, el cursor fora de rang (9 amb 2 nivells) torna a 0
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 9);
		joc.setCarpeta("2fitxer");
		joc.obrirSelector();
		assertEquals(joc.getCursor(), 0);
	}
	
	/**
	 * Joc.seleccionarNivell() sobre la carpeta test (15 nivells en ordre alfabètic).
	 * Tècniques: particions equivalents (nivell vàlid / no vàlid) i disseny per contracte
	 * (precondicions d'estat Selector i de llista no buida).
	 */
	@Test 
	void testSeleccionarNivell(){		
		/*
		 Nivells de Test:
		 	0  CaracterInvalid
		 	1  MassaInterruptors
		 	2  MassaJugadors
		 	3  NivellDuesColumnes
		 	4  NivellDuesFiles
		 	5  NivellMassaPetit
		 	6  NivellMinim3x3
		 	7  Nivell_1
		 	8  Nivell_2
		 	9  Nivell_buit
		 	10 NoCaixes
		 	11 NoInterruptor
		 	12 NoJugador
		 	13 SenseAlgunesParets
		 	14 TaulerNoRectangular
		 */
		
		// Disseny per contracte: seleccionar mentre es juga → AssertionError
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.carregarNivell("Nivell_1");
		assertThrows(AssertionError.class, () -> joc.seleccionarNivell());
		
		// Partició vàlida: Nivell_1 (índex 7)
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		for (int i = 0; i < 7; i++) {
			joc.mouCursor('s');
		}
		assertTrue(joc.seleccionarNivell());
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		// Partició vàlida: Nivell_2 (índex 8)
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		for (int i = 0; i < 8; i++) {
			joc.mouCursor('s');
		}
		assertTrue(joc.seleccionarNivell());
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		// Partició no vàlida: CaracterInvalid (índex 0)
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell());
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
		
		// Partició no vàlida: NoInterruptor (índex 11)
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		for (int i = 0; i < 11; i++) {
			joc.mouCursor('s');
		}
		assertFalse(joc.seleccionarNivell());
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
		
		// Disseny per contracte: carpeta buida, precondició de llista no buida → AssertionError
		joc = new Joc();
		new File("nivells/carpetaBuida").mkdirs();
		joc.setCarpeta("carpetaBuida");
		joc.obrirSelector();
		assertThrows(AssertionError.class, () -> joc.seleccionarNivell());
	}
	
	/**
	 * Joc.mouJugador(char) amb un MockTauler propi i un Jugador de Mockito.
	 * Tècniques: particions equivalents de tecles (w/a/s/d, z per desfer i la resta) i disseny per
	 * contracte (precondicions de tauler carregat i d'estat Jugant).
	 */
	@Test
	void testMouJugador(){
		Jugador mockJugador = mock(Jugador.class);
		
		MockTauler mockTauler = new MockTauler("MovimentSenseParets");
	    mockTauler.setJugador(mockJugador);
		
		// Disseny per contracte: sense tauler carregat → AssertionError
	    assertThrows(AssertionError.class, () -> joc.mouJugador('w'));
		
		// Disseny per contracte: hi ha tauler però l'estat és Selector → AssertionError
		joc.setCarpeta("test");
		joc.carregarNivell("Nivell_1");
		joc.obrirSelector();
		assertThrows(AssertionError.class, () -> joc.mouJugador('w'));
		
		// S'injecta el MockTauler amb el jugador simulat
		joc = new Joc();
		joc.setCarpeta("test");
		joc.setTauler(mockTauler);
		
		// Partició vàlida: w/a/s/d es tradueixen a U/L/D/R
		joc.mouJugador('w');
	    verify(mockJugador, times(1)).move('U');

	    joc.mouJugador('a');
	    verify(mockJugador, times(1)).move('L');

	    joc.mouJugador('s');
	    verify(mockJugador, times(1)).move('D');

	    joc.mouJugador('d');
	    verify(mockJugador, times(1)).move('R');
	    
	    // Partició vàlida: z delega a Tauler.desferMoviment
	    assertEquals(mockTauler.getNombreMovimentsDesfets(), 0);
	    joc.mouJugador('z');
	    assertEquals(mockTauler.getNombreMovimentsDesfets(), 1);
	    
	    
	    // Partició no vàlida: la resta de tecles (incloses majúscules) no interactuen amb el jugador
	    joc.mouJugador('x');
	    joc.mouJugador('q');
	    joc.mouJugador(' ');
	    joc.mouJugador('1');
	    joc.mouJugador('W');
	    
	    
	    verifyNoMoreInteractions(mockJugador);
		
	}
	
	/**
	 * Condició de victòria de Joc sobre Nivell_1 (una caixa i un interruptor a la mateixa fila).
	 * Tècniques: valors frontera (un moviment abans de guanyar i el moviment que guanya) i disseny per
	 * contracte (precondició de no victòria en moure; postcondició d'obrirSelector que la reinicia).
	 */
	@Test
	void testCondicioVictoria(){
		// Nivell_1 de la carpeta de test
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.carregarNivell("Nivell_1");
		
		// Valors frontera: sense victòria fins que la caixa arriba a l'interruptor al tercer moviment
		joc.mouJugador('d');
		assertEquals(joc.getVictoria(), false);

		joc.mouJugador('d');
		assertEquals(joc.getVictoria(), false);

		joc.mouJugador('d');
		assertEquals(joc.getVictoria(), true);
		
		// Disseny per contracte: moure després de guanyar → AssertionError
		assertThrows(AssertionError.class, () -> joc.mouJugador('d'));
		
		// Disseny per contracte: postcondicions d'obrirSelector (estat Selector i victòria reiniciada)
		joc.obrirSelector();
		assertEquals(15, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		assertEquals(joc.getVictoria(), false);
	}
	
	/**
	 * Joc#finalitzarJoc().
	 * Tècnica: disseny per contracte (postcondició d'estat Final).
	 */
	@Test
	void testFinalitzar()
	{
		assertEquals(joc.getEstat(), Estat.Selector);
		joc.finalitzarJoc();
		assertEquals(joc.getEstat(), Estat.Final);
	}
	
}
