package tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import models.Caixa;

class CaixaTest {

	@Test
	void testCaixa() {
		// Creacio MockObject Tauler
		MockTauler mt = new MockTauler("");

		// Valor limit X = 0 (Y fixa valida)
		Caixa parCaixa = new Caixa(mt, 0, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valor limit X = 1 (Y fixa valida)
		parCaixa = new Caixa(mt, 1, 2);
		assertEquals(parCaixa.getPosX(), 1);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valors limit de X (Y fixa valida)
		parCaixa = new Caixa(mt, -1, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, -100, 2);
		assertEquals(parCaixa.getPosX(), 0);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 3, 2);
		assertEquals(parCaixa.getPosX(), 3);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 4, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 5, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, 500, 2);
		assertEquals(parCaixa.getPosX(), 4);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);

		// Valors limit de Y (X fixa valida)
		parCaixa = new Caixa(mt, 2, -1);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, 2, -100);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 2, 0);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 0);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 2, 1);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 1);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 2, 3);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 3);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, 2, 4);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);

		parCaixa = new Caixa(mt, 2, 5);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);
		
		parCaixa = new Caixa(mt, 2, 500);
		assertEquals(parCaixa.getPosX(), 2);
		assertEquals(parCaixa.getPosY(), 4);
		assertEquals(parCaixa.getTauler(), mt);

		// Cantonades (els dos eixos fora per un)
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
		
		assertThrows(AssertionError.class, () -> new Caixa(null, 2, 2));
	}

	@Test
	void testMove() {
		// Creacio MockObject Tauler sense parets
		MockTauler mt = new MockTauler("MovimentSenseParets");

		// Creacio nova caixa situada a x=2, y=2 en el tauler
		Caixa c = new Caixa(mt, 2, 2);

		// Moviment vertical i horitzontal valid (Amunt, avall, avall, amunt)
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

		// Moviments laterals valids (Dreta, esquerra, esquerra, dreta)
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

		// Creacio MockObject Tauler caixa rodejada de parets
		mt = new MockTauler("MovimentRodejatParets");
		// Creacio nova caixa situada a x=2, y=2 en el tauler
		c = new Caixa(mt, 2, 2);

		// Comprobacio de que ara no es mou en cap de les direccions
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

		// Creacio MockObject Tauler de 3x3
		mt = new MockTauler("MovimentParets3x3");
		// Creacio nova caixa situada a x=2, y=2 en el tauler
		c = new Caixa(mt, 2, 2);

		// Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 2);
		// Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 2);
		// Mou cap a dalt (valid)
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		// Mou cap a dalt (xoca)
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (valid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (valid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 1);
		// Mou cap a la dreta (xoca)
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 1);
		// Mou cap a baix (valid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 2);
		// Mou cap a baix (valid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 3);
		// Mou cap a baix (xoca)
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 3);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 2);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 3);
		// Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 3);

		// Creacio MockObject Tauler sense parets
		mt = new MockTauler("MovimentLinealCaixes");

		// Creacio nova caixa situada a x=2, y=2 en el tauler
		c = new Caixa(mt, 2, 2);

		// Comprovem que no es mou en cap direcció si xoca amb una altre caixa
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

		// Prova altres caracters i minuscules com a inputs
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