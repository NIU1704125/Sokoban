package tests;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import org.junit.jupiter.api.Test;

import models.Tauler;
import models.Jugador;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		
		// Limit: 2 files (minim 3)
		try {
			tauler = new Tauler("test/NivellDuesFiles");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Limit: 2 columnes (minim 3)
		try {
			tauler = new Tauler("test/NivellDuesColumnes");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Limit: tauler minim valid 3x3
		tauler = new Tauler("test/NivellMinim3x3");
		char[][] minim = {
				{ '#', '#', '#' },
				{ ' ', ' ', '.' },
				{ '#', '#', '#' } };
		assertTrue(tauler.getJugador() != null);
		assertEquals(tauler.getCaixes().size(), 1);
		assertEquals(tauler.getNInterrutors(), 1);
		assertEquals(tauler.getFiles(), 3);
		assertEquals(tauler.getColumnes(), 3);
		assertArrayEquals(tauler.getTauler(), minim);

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

		try {
			tauler = new Tauler("test/TaulerNoRectangular");
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

	//Test realitzat utilitzant un data-driven approach
	@ParameterizedTest
	@CsvFileSource(resources = "/parets_test.csv", numLinesToSkip = 1)
	void testHiHaParetInPosition(int x, int y, boolean esperat) {
		Tauler tauler = new Tauler("test/SenseAlgunesParets");
		assertEquals(esperat, tauler.hiHaParetInPosition(x, y));
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
		//Precondicio: no es pot afegir un registre null
		assertThrows(AssertionError.class, () -> tauler.afegirRegistreMoviment(null));
		
		//Jugador es mou cap a dalt i es desfa el moviment
		assertTrue(j.getPosX() == 1 && j.getPosY() == 3);
		j.move('U');
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
		j.move('U');
		j.move('U');
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
		j.move('U');
		j.move('U');
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
		
		j.move('R');
		j.move('R');
		
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
		j.move('R');
		j.move('R');
		j.move('R');
		
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
		
		//Jugador es mou cap a l'esquerra i es desfa el moviment
		j.move('R');
		assertTrue(j.move('L'));
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 2 && j.getPosY() == 3);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		# @    $    #
 		#############
		 */
		
		//Jugador es mou cap avall i es desfa el moviment
		j.move('U');
		assertTrue(j.move('D'));
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 2 && j.getPosY() == 2);
		
		/*
 		############# 
 		#  .  .  .  # 
 		# @$  $  $  # 
 		#      $    #
 		#############
		 */
		
		//Jugador emputja una caixa cap a l'esquerra i es desfa el moviment
		j.move('D');
		j.move('R');
		j.move('R');
		j.move('U');
		assertTrue(j.getPosX() == 4 && j.getPosY() == 2);
		
		assertTrue(j.move('L'));
		assertTrue(j.getPosX() == 3 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(2, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) == null);
		
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 4 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(2, 2) == null);
		
		/*
 		############# 
 		#  .  .  .  # 
 		#  $@ $  $  # 
 		#      $    #
 		#############
		 */
		
		//Jugador emputja una caixa cap avall i es desfa el moviment
		j.move('U');
		j.move('L');
		assertTrue(j.getPosX() == 3 && j.getPosY() == 1);
		
		assertTrue(j.move('D'));
		assertTrue(j.getPosX() == 3 && j.getPosY() == 2);
		assertTrue(tauler.hiHaCaixaInPosition(3, 3) != null);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) == null);
		
		assertTrue(tauler.desferMoviment());
		assertTrue(j.getPosX() == 3 && j.getPosY() == 1);
		assertTrue(tauler.hiHaCaixaInPosition(3, 2) != null);
		assertTrue(tauler.hiHaCaixaInPosition(3, 3) == null);
		
		/*
 		############# 
 		#  .@ .  .  # 
 		#  $  $  $  # 
 		#      $    #
 		#############
		 */
	}
	
	@Test
	void testEstatGuanyador() {
		//Tauler de prova per comprovar si la verificacio de victoria salta quan toca
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
		
		//Comprovacio inicial
		assertFalse(tauler.estatGuanyador());
		
		//Col·loquem la primera caixa on toca i comprovem
		j.move('R');
		j.move('R');
		j.move('U');
		assertFalse(tauler.estatGuanyador());
		
		/*
 		############# 
 		#  $  .  .  # 
 		#  @  $  $  # 
 		#     $     #
 		#############
		 */
		
		//Fem el mateix amb la segona caixa
		j.move('D');
		j.move('R');
		j.move('R');
		j.move('R');
		j.move('U');
		assertFalse(tauler.estatGuanyador());
		
		/*
 		############# 
 		#  $  $  .  # 
 		#     @  $  # 
 		#      $    #
 		#############
		 */
		
		//Ara col·loquem la ultima i mirem que es guanyi
		j.move('D');
		j.move('R');
		j.move('R');
		j.move('R');
		j.move('U');
		assertTrue(tauler.estatGuanyador());
		
		/*
 		############# 
 		#  $  $  $  # 
 		#        @  # 
 		#         $ #
 		#############
		 */
		
		//Per ultim, movem alguna caixa i comprovem que l'estat ja no sigui el guanyador
		j.move('R');
		j.move('U');
		j.move('L');
		assertFalse(tauler.estatGuanyador());
		
		/*
 		############# 
 		#  $  $ $@  # 
 		#           # 
 		#         $ #
 		#############
		 */
	}
	
	@Test
	void testTorn() {
		Tauler tauler = new Tauler("test/Nivell_2");
		Jugador j = tauler.getJugador(); // (1,3)
		assertEquals(0, tauler.getTorn());

		// Moviments fallits contra paret: el torn no avança
		assertFalse(j.move('L'));
		assertEquals(0, tauler.getTorn());
		assertFalse(j.move('D'));
		assertEquals(0, tauler.getTorn());

		// Moviments valids sense caixa: avança 1 per moviment
		assertTrue(j.move('R')); // (2,3)
		assertEquals(1, tauler.getTorn());
		assertTrue(j.move('U')); // (2,2)
		assertEquals(2, tauler.getTorn());

		// Emputjar una caixa compta com un sol torn
		assertTrue(j.move('R')); // caixa (3,2)->(4,2)
		assertEquals(3, tauler.getTorn());
		assertTrue(j.move('R')); // caixa (4,2)->(5,2)
		assertEquals(4, tauler.getTorn());

		// Caixa bloquejada per una altra caixa (6,2): el torn no avança
		assertFalse(j.move('R'));
		assertEquals(4, tauler.getTorn());
	}
}