package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import models.Caixa;

/**
 * Proves de Caixa, aïllada del fitxer de nivell amb el mock propi MockTauler.
 */
class CaixaTest {

	/**
	 * Constructor: ajust de la posició al tauler 5x5 del mock (rang vàlid 0..4 per eix).
	 * Tècniques: particions equivalents (< 0, 0..4, > 4 per eix), valors límit
	 * (0 i 4), valors frontera (-1, 1, 3 i 5) i disseny per contracte (precondició != null).
	 */
	@Test
	void testCaixa() {
		// Mock propi: tauler 5x5 sense escenari
		MockTauler mt = new MockTauler("");

		// Valor límit X = 0: mínim vàlid (Y fixa vàlida)
		Caixa parCaixa = new Caixa(mt, 0, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera X = 1: just dins del límit inferior
		parCaixa = new Caixa(mt, 1, 2);
		assertEquals(parCaixa.getPosX(), 1);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera X = -1: just fora del límit inferior → s'ajusta a 0
		parCaixa = new Caixa(mt, -1, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Partició no vàlida X < 0: valor allunyat → s'ajusta a 0
		parCaixa = new Caixa(mt, -100, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera X = 3: just dins del límit superior
		parCaixa = new Caixa(mt, 3, 2);
		assertEquals(parCaixa.getPosX(), 3);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor límit X = 4: màxim vàlid (columnes - 1)
		parCaixa = new Caixa(mt, 4, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera X = 5: just fora del límit superior → s'ajusta a 4
		parCaixa = new Caixa(mt, 5, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Partició no vàlida X > 4: valor allunyat → s'ajusta a 4
		parCaixa = new Caixa(mt, 500, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera Y = -1: just fora del límit inferior → s'ajusta a 0 (X fixa vàlida)
		parCaixa = new Caixa(mt, 2, -1);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Partició no vàlida Y < 0: valor allunyat → s'ajusta a 0
		parCaixa = new Caixa(mt, 2, -100);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor límit Y = 0: mínim vàlid
		parCaixa = new Caixa(mt, 2, 0);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera Y = 1: just dins del límit inferior
		parCaixa = new Caixa(mt, 2, 1);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 1);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera Y = 3: just dins del límit superior
		parCaixa = new Caixa(mt, 2, 3);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 3);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Valor límit Y = 4: màxim vàlid (files - 1)
		parCaixa = new Caixa(mt, 2, 4);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor frontera Y = 5: just fora del límit superior → s'ajusta a 4
		parCaixa = new Caixa(mt, 2, 5);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Partició no vàlida Y > 4: valor allunyat → s'ajusta a 4
		parCaixa = new Caixa(mt, 2, 500);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);

		// Cantonades: els dos eixos no vàlids alhora (frontera -1 i 5; particions no vàlides -100 i 500)
		parCaixa = new Caixa(mt, -1, -1);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, -100, -100);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 5, 5);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, 500, 500);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);
		
		// Disseny per contracte: precondició tauler != null → AssertionError
		assertThrows(AssertionError.class, () -> new Caixa(null, 2, 2));
	}

	/**
	 * Caixa.move(char) en els escenaris del MockTauler.
	 * Tècniques: particions equivalents (destinació lliure, amb paret o amb caixa; direcció vàlida o no)
	 * i caixa blanca: cobertura de decisions i de condicions (cada branca del switch i cada operand
	 * de !paret && caixa == null a cert i a fals en les quatre direccions).
	 */
	@Test
	void testMove() {
		// Mock propi: escenari sense parets al voltant de (2,2)
		MockTauler mt = new MockTauler("MovimentSenseParets");

		// Caixa inicial a (2,2)
		Caixa c = new Caixa(mt, 2, 2);

		// Partició vàlida: destinació lliure en vertical (U, D, D, U) → les dues condicions certes
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 1);
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 3);
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);

		// Partició vàlida: destinació lliure en horitzontal (R, L, L, R)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 2);
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 2);
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);

		// Mock propi: caixa envoltada de parets
		mt = new MockTauler("MovimentRodejatParets");
		// Caixa inicial a (2,2)
		c = new Caixa(mt, 2, 2);

		// Partició no vàlida: paret a la destinació en les 4 direccions → condició de paret falsa, no es mou
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);

		// Mock propi: recinte lliure de 3x3 envoltat de parets; es recorre fins a xocar en cada direcció
		mt = new MockTauler("MovimentParets3x3");
		// Caixa inicial a (2,2)
		c = new Caixa(mt, 2, 2);

		// Mou cap a l'esquerra (vàlid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 2);
		// Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 2);
		// Mou cap a dalt (vàlid)
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		// Mou cap a dalt (xoca)
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (vàlid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (vàlid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (xoca)
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 1);
		// Mou cap a baix (vàlid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 2);
		// Mou cap a baix (vàlid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 3);
		// Mou cap a baix (xoca)
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (vàlid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (vàlid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 3);

		// Mock propi: caixa envoltada d'altres caixes
		mt = new MockTauler("MovimentLinealCaixes");

		// Caixa inicial a (2,2)
		c = new Caixa(mt, 2, 2);

		// Partició no vàlida: caixa a la destinació en les 4 direccions → condició de caixa falsa, no es mou
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 2);

		// Partició no vàlida de direcció: minúscules i altres caràcters → IllegalArgumentException (default)
		try {
			c.move('h');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			c.move('u');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			c.move('d');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			c.move('r');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			c.move('l');
			assertTrue(false);
		} catch (Exception e) {
		}
	}
}