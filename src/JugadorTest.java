import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JugadorTest {

	@Test
	void testJugador() {
		//Creacio MockObject Tauler
		MockTauler mt = new MockTauler(""); 
		
		// Constructor amb parametres
		Jugador parJugador = new Jugador(mt, 2, 4);
		
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
	}
	
	@Test
	void testMoveSenseCaixes() {
		MockTauler mt = new MockTauler("JugadorMovimentSenseParets");
		Jugador j = new Jugador(mt, 0, 0);
		
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), -1);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		
		mt = new MockTauler("JugadorMovimentRodejatParets");
		j = new Jugador(mt, 0, 0);
		
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 0);
		
		mt = new MockTauler("JugadorMovimentParets3x3");
		j = new Jugador(mt, 0, 0);
		
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), 0);
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), 1);
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 0);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), -1);
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), -1);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 0);
		assertEquals(j.getPosY(), -1);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), -1);
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), -1);
		assertEquals(j.getPosY(), -1);
		
		try
		{
			j.move('h');
			assertTrue(false);
		}catch (Exception e) {}
		try
		{
			j.move('u');
			assertTrue(false);
		}catch (Exception e) {}
		try
		{
			j.move('d');
			assertTrue(false);
		}catch (Exception e) {}
		try
		{
			j.move('r');
			assertTrue(false);
		}catch (Exception e) {}
		try
		{
			j.move('l');
			assertTrue(false);
		}catch (Exception e) {}
	}
	
	@Test
	void testMoveAmbCaixes()
	{
		
	}
}
