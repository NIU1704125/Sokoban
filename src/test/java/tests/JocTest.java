package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import models.Joc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JocTest {

	Joc joc;
	
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
	
}
