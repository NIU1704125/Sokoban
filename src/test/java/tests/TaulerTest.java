package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.Tauler;
import models.Jugador;

class TaulerTest {

	@Test
	void testCarregarFitxer() {

		Tauler tauler;

		// Fitxer no existent
		try {
			tauler = new Tauler("");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Fitxer buit
		try {
			tauler = new Tauler("test/Nivell_buit");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Caracters invalids
		try {
			tauler = new Tauler("test/CaracterInvalid");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Tauler massa petit
		try {
			tauler = new Tauler("test/NivellMassaPetit");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Sense jugador
		try {
			tauler = new Tauler("test/NoJugador");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Mes d'un jugador
		try {
			tauler = new Tauler("test/MassaJugadors");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Sense caixes
		try {
			tauler = new Tauler("test/NoCaixes");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Sense interruptors
		try {
			tauler = new Tauler("test/NoInterruptor");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Mes interruptors que caixes
		try {
			tauler = new Tauler("test/MassaInterruptors");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Tauler valid nivell 1
		tauler = new Tauler("test/Nivell_1");
		char[][] n1 = {
				{ '#', '#', '#', '#', '#', '#', '#' },
				{ '#', ' ', ' ', ' ', ' ', '.', '#' },
				{ '#', '#', '#', '#', '#', '#', '#' } };
		assertTrue(tauler.getJugador() != null);
		assertEquals(tauler.getCaixes().size(), 1);
		assertEquals(tauler.getNInterrutors(), 1);
		assertEquals(tauler.getFiles(), 3);
		assertEquals(tauler.getColumnes(), 7);
		assertArrayEquals(tauler.getTauler(), n1);

		// Tauler valid nivell 2
		tauler = new Tauler("test/Nivell_2");
		char[][] n2 = {
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
				{ '#', ' ', ' ', '.', ' ', ' ', '.', ' ', ' ', '.', ' ', ' ', '#' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
				{ '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' } };
		assertTrue(tauler.getJugador() != null);
		assertEquals(tauler.getCaixes().size(), 4);
		assertEquals(tauler.getNInterrutors(), 3);
		assertEquals(tauler.getFiles(), 5);
		assertEquals(tauler.getColumnes(), 13);
		assertArrayEquals(tauler.getTauler(), n2);

	}

	@Test
	void testHiHaParetInPosition() {
		// Tauler de prova per comprovar parets
		Tauler tauler = new Tauler("test/SenseAlgunesParets");
		/*
		 *  ###  ###
		 *   @$  . 
		 *  ###  ###
		 */

		// Comprovacions dins dels limits del tauler
		assertTrue(tauler.hiHaParetInPosition(0, 0));
		assertTrue(tauler.hiHaParetInPosition(1, 0));
		assertFalse(tauler.hiHaParetInPosition(0, 1));
		assertFalse(tauler.hiHaParetInPosition(1, 1));
		assertFalse(tauler.hiHaParetInPosition(2, 1));
		assertTrue(tauler.hiHaParetInPosition(6, 0));
		assertTrue(tauler.hiHaParetInPosition(0, 2));

		// Fora del tauler es considera paret
		assertTrue(tauler.hiHaParetInPosition(-1, 0));
		assertTrue(tauler.hiHaParetInPosition(0, -1));
		assertTrue(tauler.hiHaParetInPosition(-1, -1));
		assertTrue(tauler.hiHaParetInPosition(7, 0));
		assertTrue(tauler.hiHaParetInPosition(0, 3));
	}

	@Test
	void testHiHaCaixaInPosition() {
		// Tauler de prova per comprovar caixes
		Tauler tauler = new Tauler("test/Nivell_2");
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */

		// Comprovacions dins dels limits del tauler
		assertEquals(tauler.hiHaCaixaInPosition(0, 0), null);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2).getPosX() == 3);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2).getPosY() == 2);

		assertTrue(tauler.hiHaCaixaInPosition(6, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(6, 2).getPosX() == 6);
		assertTrue(tauler.hiHaCaixaInPosition(6, 2).getPosY() == 2);

		assertTrue(tauler.hiHaCaixaInPosition(9, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(9, 2).getPosX() == 9);
		assertTrue(tauler.hiHaCaixaInPosition(9, 2).getPosY() == 2);

		assertTrue(tauler.hiHaCaixaInPosition(7, 3) != null);
		assertTrue(tauler.hiHaCaixaInPosition(7, 3).getPosX() == 7);
		assertTrue(tauler.hiHaCaixaInPosition(7, 3).getPosY() == 3);

		// Comprovacions fora dels limits del tauler
		assertEquals(tauler.hiHaCaixaInPosition(-1, -1), null);
		assertEquals(tauler.hiHaCaixaInPosition(0, 5), null);
		assertEquals(tauler.hiHaCaixaInPosition(13, 0), null);
		assertEquals(tauler.hiHaCaixaInPosition(3, 1), null);
	}
	
	@Test
	void testDesferMoviments() {
		//La logica d'aquesta funcio no dependra de la direccio, o sigui que no cal probar-les totes
		//Tauler de prova per comprovar si els moviments es desfan correctament
		Tauler tauler = new Tauler("test/Nivell_2");
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */
		
		//Agafem la referencia al jugador
		Jugador j = tauler.getJugador();
		
		//Intentem defer amb la llista de moviments buida
		assertFalse(tauler.desferMoviment());
		
		//Jugador es mou cap a dalt i es desfa el moviment
		assertTrue(j.getPosX() == 1 && j.getPosY() == 3);
		tauler.getJugador().move('U');
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 1 && j.getPosY() == 3);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */
		
		//Jugador es mou cap a dalt dos cops i es desfa un moviment
		tauler.getJugador().move('U');
		tauler.getJugador().move('U');
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 1 && j.getPosY() == 2);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#@ $  $  $  # 
 		#     $     #
 		#############
		 */

		//Jugador es torna a moure 2 cops cap a dalt, ara xocant amb una pared al segon, i es desfa un moviment
		tauler.getJugador().move('U');
		tauler.getJugador().move('U');
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 1 && j.getPosY() == 2);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#@ $  $  $  # 
 		#     $     #
 		#############
		 */
		
		//Jugador es mou cap a la dreta fins a emputjar una caixa i es desfa el moviment
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(4, 2) == null);
		
		tauler.getJugador().move('R');
		tauler.getJugador().move('R');
		
		assertTrue(j.getPosX() == 3 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) == null);
		assertTrue(tauler.hiHaCaixaInPosition(4, 2) != null);
		
		assertTrue(tauler.desferMoviment());
		
		assertTrue(j.getPosX() == 2 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(4, 2) == null);
		
		/*
 		############# 
 		#  .  .  .  # 
 		# @$  $  $  # 
 		#     $     #
 		#############
		 */
		
		//Jugador es mou cap a la dreta fins que dues caixes xoquen i es desfa el moviment
		tauler.getJugador().move('R');
		tauler.getJugador().move('R');
		tauler.getJugador().move('R');
		
		assertTrue(j.getPosX() == 4 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(4, 2) == null);
		assertTrue(tauler.hiHaCaixaInPosition(5, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(6, 2) != null);
		
		assertTrue(tauler.desferMoviment());
		
		assertTrue(j.getPosX() == 3 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(4, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(5, 2) == null);
		assertTrue(tauler.hiHaCaixaInPosition(6, 2) != null);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#  @$ $  $  # 
 		#     $     #
 		#############
		 */
		
		//Es desfan tots els moviments
		assertTrue(tauler.desferMoviment());
		assertTrue(tauler.desferMoviment());
		assertTrue(tauler.desferMoviment());
		assertFalse(tauler.desferMoviment());
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(6, 2) != null);
		assertTrue(j.getPosX() == 1 && j.getPosY() == 3);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */
	}
}