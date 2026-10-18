import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JugadorTest {

	@Test
	void testJugador() {
		//Creacio MockObject Tauler 10x10
		MockTauler mt = new MockTauler(""); 
				
		//Valors valids
		Jugador parJugador = new Jugador(mt, 2, 4);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
		
		parJugador = new Jugador(mt, 0, 1);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 1);
		assertEquals(parJugador.getTauler(), mt);
		
		//Valors invalids
		parJugador = new Jugador(mt, -99, 250);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
		
		parJugador = new Jugador(mt, 120, -5);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);
		}
	
	@Test
	void testMoveSenseCaixes() {
		//Creacio MockObject Tauler sense parets
		MockTauler mt = new MockTauler("MovimentSenseParets");
		
		//Creacio nou jugador situat a x=2, y=2 en el tauler 
		Jugador j = new Jugador(mt, 2, 2);
		
		//Moviment vertical i horitzontal valid (Amunt, avall, avall, amunt)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Moviments laterals valids (Dreta, esquerra, esquerra, dreta)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Creacio MockObject Tauler jugador rodejat de parets
		mt = new MockTauler("MovimentRodejatParets");
		//Creacio nou jugador situat a x=2, y=2 en el tauler 
		j = new Jugador(mt, 2, 2);
		
		//Comprobacio de que ara no es mou en cap de les direccions
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Creacio MockObject Tauler de 3x3
		mt = new MockTauler("MovimentParets3x3");
		//Creacio nou jugador situat a x=2, y=2 en el tauler 
		j = new Jugador(mt, 2, 2);
		
		//Mou cap a l'esquerra (valid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		//Mou cap a l'esquerra (xoca)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		//Mou cap a dalt (valid)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		//Mou cap a dalt (xoca)
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		//Mou cap a la dreta (valid)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		//Mou cap a la dreta (valid)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 1);
		//Mou cap a la dreta (xoca)
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 1);
		//Mou cap a baix (valid)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		//Mou cap a baix (valid)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		//Mou cap a baix (xoca)
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		//Mou cap a l'esquerra (valid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		//Mou cap a l'esquerra (valid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 3);
		//Mou cap a l'esquerra (xoca)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 3);
		
		//Prova altres caracters i minuscules com a inputs
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
		//Creacio MockObject Tauler
		MockTauler mt = new MockTauler("MovimentLinealCaixes");
		//Creacio nou jugador situat a x=2, y=2 en el tauler 
		Jugador j = new Jugador(mt, 2, 2);
		
		//Moviment cap a dalt (valid, emputja caixa)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		//Moviment cap a dalt (xoca, la caixa té paret darrere)
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		//Moviment cap a baix (torna al centre)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Moviment cap a baix (valid, emputja caixa)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		//Moviment cap a baix (xoca, la caixa té paret darrere)
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		//Moviment cap a dalt (torna al centre)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Moviment cap a la dreta (valid, emputja caixa)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		//Moviment cap a la dreta (xoca, la caixa té paret darrere)
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		//Moviment cap a l'esquerra (torna al centre)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Moviment cap a l'esquerra (valid, emputja caixa)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		//Moviment cap a l'esquerra (xoca, la caixa té paret darrere)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		//Moviment cap a la dreta (torna al centre)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		
		//Creacio MockObject Tauler
		mt = new MockTauler("MovimentCaixes3x5");
		//Creacio nou jugador situat a x=2, y=2 en el tauler 
		j = new Jugador(mt, 2, 2);
		//Falla al emputjar la de dalt (te pared darrere), i emputja la de la dreta
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		
		//Rota al voltant de la caixa de la dreta desde baix i l'emputja cap a amunt
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 3);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 2);
		
		//Falla al tornarla a emputjar cap a amunt ja que ara te pared darrere
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 2);
		
		//Torna a rotar al voltant la caixa, ara cap a la dreta, i l'emputja cap a l'esquerra
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 5);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 5);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 1);
		
		//Moviment cap a l'esquerra (xoca, la caixa té una altra caixa darrere)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 1);
	}
}