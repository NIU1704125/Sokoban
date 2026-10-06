import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JugadorTest {

	@Test
	void testJugador() {
		//Tests constructor per defecte
		Jugador defJugador = new Jugador();
		
		assertEquals(defJugador.getPosX(), 0);
		assertEquals(defJugador.getPosY(), 0);

		// Constructor amb parametre
		Jugador parJugador = new Jugador(2, 4);
		
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
	}
	
	@Test
	void testMove() {
		fail("Encara no s'ha implementat");
	}

}
