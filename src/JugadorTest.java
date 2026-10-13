import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JugadorTest {

	@Test
	void testJugador() {
		//Creacio MockObject Tauler
		MockTauler mt = new MockTauler("arxiu");
		
		// Constructor amb parametres
		Jugador parJugador = new Jugador(mt, 2, 4);
		
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
	}
	
	@Test
	void testMove() {
		fail("Encara no s'ha implementat");
	}

}
