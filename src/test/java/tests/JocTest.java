package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import models.Joc;
import models.Estat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import models.Jugador;

import java.io.File;

class JocTest {

	Joc joc;
	
	@BeforeEach
	void setup() {
		joc = new Joc();
	}
	
	@Test
	void testCarregarNivell() {
		//test carregar nivell valid
		joc.setCarpeta("test");
		assertTrue(joc.carregarNivell("Nivell_1"));
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		joc = new Joc();
		joc.setCarpeta("test");
		assertTrue(joc.carregarNivell("Nivell_2"));
		assertTrue(joc.getTauler() != null);
		
		joc = new Joc();
		joc.setCarpeta("test");
		//test carregar nivell invalid
		assertFalse(joc.carregarNivell(""));
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
	}
	
	@Test
	void testObrirSelector() {
		//test carpeta no existeix
		try{
			joc.setCarpeta("null");
			joc.obrirSelector();
			assertTrue(false);

		} catch (Exception e) {}

		
		File carpetaBuida = new File("nivells/carpetaBuida");
		carpetaBuida.mkdirs();
		
		//test carpeta buida
		joc.setCarpeta("carpetaBuida");
		joc.obrirSelector();
		assertEquals(0, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		
		//test 1 fitxer
		joc.setCarpeta("1fitxer");
		joc.obrirSelector();
		assertEquals(1, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		
		//test 2 fitxers
		joc.setCarpeta("2fitxer");
		joc.obrirSelector();
		assertEquals(2, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		
		//test 10 fitxers
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertEquals(10, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
		
		//test 20 fitxers
		joc.setCarpeta("20fitxer");
		joc.obrirSelector();
		assertEquals(20, joc.getLevels().size());
		assertEquals(Estat.Selector, joc.getEstat());
	}
	
	@Test
	void testMouCursor(){		
		//Comprovacio de control del valor inicial de la variable cursor
		assertEquals(joc.getCursor(), 0);
		
		//Intentem pujar o baixar sense haber carregat cap carpeta
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 0);
		
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 0);
		
		//Carreguem la carpeta de nivells
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		
		//Baixem dues posicions a la llista de nivells
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 1);
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 2);
		
		//Pujem dues posicions
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 1);
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 0);
		
		//Estant a 0 pujem dues posicions mes, comprovant el limit superior
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 9);
		joc.mouCursor('w');
		assertEquals(joc.getCursor(), 8);
		
		//Baixem dues posicions, comprovant el limit inferior
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 9);
		joc.mouCursor('s');
		assertEquals(joc.getCursor(), 0);
		
		//Provem a interactuar amb la funcio amb diferents caracters no valids
		joc.mouCursor(' ');
		assertEquals(joc.getCursor(), 0);

		joc.mouCursor('a');
		assertEquals(joc.getCursor(), 0);
		
		joc.mouCursor('W');
		assertEquals(joc.getCursor(), 0);

		joc.mouCursor('S');
		assertEquals(joc.getCursor(), 0);
	}
	
	@Test 
	void testSeleccionarNivell(){		
		
		//Triem el nivell 1
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.mouCursor('s');
		joc.mouCursor('s');
		assertTrue(joc.seleccionarNivell());
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		//Triem el nivell 2
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.mouCursor('s');
		joc.mouCursor('s');
		joc.mouCursor('s');
		assertTrue(joc.seleccionarNivell());
		assertTrue(joc.getTauler() != null);
		assertEquals(Estat.Jugant, joc.getEstat());
		
		//Triem nivells no valids
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell());
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
		
		joc = new Joc();
		joc.setCarpeta("test");
		joc.obrirSelector();
		joc.mouCursor('s');
		assertFalse(joc.seleccionarNivell());
		assertTrue(joc.getTauler() == null);
		assertEquals(Estat.Selector, joc.getEstat());
	}
	
	@Test
	void testMouJugador() 
	{
		Jugador mockJugador = mock(Jugador.class);
		
		MockTauler mockTauler = new MockTauler("MovimentSenseParets");
	    mockTauler.setJugador(mockJugador);
		
		//Tauler no carregat
		try{
			joc.mouJugador('w');
			assertTrue(false);
		} catch (AssertionError e) {}
		
		//Carreguem tauler
		joc.setCarpeta("test");
		joc.setTauler(mockTauler);
		
		//Tecles valides
		joc.mouJugador('w');
	    verify(mockJugador, times(1)).move('U');

	    joc.mouJugador('a');
	    verify(mockJugador, times(1)).move('L');

	    joc.mouJugador('s');
	    verify(mockJugador, times(1)).move('D');

	    joc.mouJugador('d');
	    verify(mockJugador, times(1)).move('R');
	    
	    //Altres tecles
	    joc.mouJugador('x');
	    joc.mouJugador('q');
	    joc.mouJugador(' ');
	    joc.mouJugador('1');
	    
	    verifyNoMoreInteractions(mockJugador);
		
	}
	
}
