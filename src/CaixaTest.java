import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CaixaTest {

	@Test
	void testCaixa() {
		//Tests constructor per defecte
		Caixa defCaixa = new Caixa();
		
		assertEquals(defCaixa.getPosX(), 0);
		assertEquals(defCaixa.getPosY(), 0);

		// Constructor amb parametre
		Caixa parCaixa = new Caixa(3, 2);
		
		assertEquals(parCaixa.getPosX(), 3);
		assertEquals(parCaixa.getPosY(), 2);
	}
	
	@Test
	void testMove() {
		fail("Encara no s'ha implementat");
	}

}
