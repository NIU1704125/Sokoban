package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import models.Joc;
import models.Estat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void testSeleccionarNivell(){		
		//Seleccio correcte de nivell
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("0"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("4"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("8"));
		
		//Nivell fora de rang
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("-1"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("10"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("-10"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("30"));
		
		//String buida
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell(""));
		
		//String nul·la
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell(null));
		
		//String espai
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell(" "));
		
		//Obrir amb strings de lletres
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("A"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("Prova"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("Prova 5"));
		
		//Nombre decimal
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("1.2"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("0.000001"));
		
		//Combinacio de numeros i lletres
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("2 Prova"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertFalse(joc.seleccionarNivell("7A426B"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("7 A426B"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("5 8 9"));
		
		joc = new Joc();
		joc.setCarpeta("10fitxer");
		joc.obrirSelector();
		assertTrue(joc.seleccionarNivell("  8 9"));
	}
	
}
