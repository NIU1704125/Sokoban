import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaixaTest {

	@Test
	void testCaixa() {
		//Creacio MockObject Tauler
		MockTauler mt = new MockTauler("arxiu");
		
		// Constructor amb parametres
		Caixa parCaixa = new Caixa(mt, 3, 2);
		
		assertEquals(parCaixa.getPosX(), 3);
		assertEquals(parCaixa.getPosY(), 2);
		assertEquals(parCaixa.getTauler(), mt);
	}
	
	@Test
	void testMove() {
		//Creacio MockObject Tauler sense parets
		MockTauler mt = new MockTauler("MovimentSenseParets");
		
		//Creacio nova caixa situada a x=0, y=0 en el tauler 
		Caixa c = new Caixa(mt, 0, 0);
		
		//Moviment vertical i horitzontal valid (Amunt, avall, avall, amunt)
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 1);
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), -1);
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		
		//Moviments laterals valids (Dreta, esquerra, esquerra, dreta)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 0);
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), 0);
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		
		//Creacio MockObject Tauler caixa rodejada de parets
		mt = new MockTauler("MovimentRodejatParets");
		//Creacio nova caixa situada a x=0, y=0 en el tauler 
		c = new Caixa(mt, 0, 0);
		
		//Comprobacio de que ara no es mou en cap de les direccions
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		
		//Creacio MockObject Tauler de 3x3
		mt = new MockTauler("MovimentParets3x3");
		//Creacio nova caixa situada a x=0, y=0 en el tauler 
		c = new Caixa(mt, 0, 0);
		
		//Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), 0);
		//Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), 0);
		//Mou cap a dalt (valid)
		assertTrue(c.move('U'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), 1);
		//Mou cap a dalt (xoca)
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), 1);
		//Mou cap a la dreta (valid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 1);
		//Mou cap a la dreta (valid)
		assertTrue(c.move('R'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		//Mou cap a la dreta (xoca)
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 1);
		//Mou cap a baix (valid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), 0);
		//Mou cap a baix (valid)
		assertTrue(c.move('D'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), -1);
		//Mou cap a baix (xoca)
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 1);
		assertEquals(c.getPosY(), -1);
		//Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), -1);
		//Mou cap a l'esquerra (valid)
		assertTrue(c.move('L'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), -1);
		//Mou cap a l'esquerra (xoca)
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), -1);
		assertEquals(c.getPosY(), -1);
		
		//Creacio MockObject Tauler sense parets
		mt = new MockTauler("MovimentLinealCaixes");
		
		//Creacio nova caixa situada a x=0, y=0 en el tauler 
		c = new Caixa(mt, 0, 0);
		
		//Comprovem que no es mou en cap direcció si xoca amb una altre caixa
		assertFalse(c.move('U'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('D'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('R'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		assertFalse(c.move('L'));
		assertEquals(c.getPosX(), 0);
		assertEquals(c.getPosY(), 0);
		
		//Prova altres caracters i minuscules com a inputs
		try {
			c.move('h');
			assertTrue(false);
		} catch (Exception e) {}
		try {
			c.move('u');
			assertTrue(false);
		} catch (Exception e) {}
		try {
			c.move('d');
			assertTrue(false);
		} catch (Exception e) {}
		try {
			c.move('r');
			assertTrue(false);
		} catch (Exception e) {}
		try {
			c.move('l');
			assertTrue(false);
		} catch (Exception e) {}
	}

}
