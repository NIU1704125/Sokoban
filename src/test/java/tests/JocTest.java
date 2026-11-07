package tests;

import static org.junit.jupiter.api.Assertions.*;


import models.Joc;
import models.Estat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;

class JocTest {

	Joc joc;
	
	@TempDir
	Path carpeta;
	
	@BeforeEach
	void setup() {
		joc = new Joc();
	}
	
	@Test
	void testCarregarNivell() {
		//test carregar nivell valid
		assertTrue(joc.carregarNivell("Nivell_1"));
		assertTrue(joc.getTauler() != null);
		
		joc = new Joc();
		
		assertTrue(joc.carregarNivell("Nivell_2"));
		assertTrue(joc.getTauler() != null);
		
		joc = new Joc();
		//test carregar nivell invalid
		assertFalse(joc.carregarNivell(""));
		assertTrue(joc.getTauler() == null);
	}
	
	@Test
	void testObrirSelector() {
		

		//test carpeta no existeix
		try{
			joc.setCarpeta("null");
			joc.obrirSelector();
			assertTrue(false);

		} catch (Exception e) {}

		
		//test carpeta buida
		joc.setCarpeta(carpeta.toString());
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
	
}
