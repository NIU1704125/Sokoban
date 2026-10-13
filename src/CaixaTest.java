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
		fail("Encara no s'ha implementat");
	}

}
