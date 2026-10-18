import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RegistreMovimentTest {

	@Test
	void testConstructors() {
		Tauler t = new MockTauler("");
		Jugador j = new Jugador(t, 0, 0);
		Caixa c = new Caixa(t, 1, 1);
		
		//Moviments valids
		RegistreMoviment rm = new RegistreMoviment(5, 'R', j);
		assertEquals(rm.getTorn(), 5);
		assertEquals(rm.getDireccio(), 'R');
		assertEquals(rm.getSubjecte(), j);
		
		rm = new RegistreMoviment(2, 'U', c);
		assertEquals(rm.getTorn(), 2);
		assertEquals(rm.getDireccio(), 'U');
		assertEquals(rm.getSubjecte(), c);
		
		rm = new RegistreMoviment(120, 'L', c);
		assertEquals(rm.getTorn(), 120);
		assertEquals(rm.getDireccio(), 'L');
		assertEquals(rm.getSubjecte(), c);
		
		rm = new RegistreMoviment(6, 'D', j);
		assertEquals(rm.getTorn(), 6);
		assertEquals(rm.getDireccio(), 'D');
		assertEquals(rm.getSubjecte(), j);
		
		//Moviments no valids
		//Torn negatiu
		try {
			rm = new RegistreMoviment(-2, 'R', j);
			assertTrue(false);
		} catch (Exception e) {}
		
		//Direccio inexistent
		try {
			rm = new RegistreMoviment(5, 'r', j);
			assertTrue(false);
		} catch (Exception e) {}
		try {
			rm = new RegistreMoviment(5, 'H', j);
			assertTrue(false);
		} catch (Exception e) {}
		
		//La classe del objecte no es ni caixa ni jugador
		try {
			rm = new RegistreMoviment(5, 'R', t);
			assertTrue(false);
		} catch (Exception e) {}
	}

}
