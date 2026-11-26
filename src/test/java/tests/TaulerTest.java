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

/**
 * Proves de Tauler amb els nivells de nivells/test.
 */
class TaulerTest {

	/**
	 * Constructor Tauler.Tauler(String): càrrega i validació del fitxer de nivell.
	 * Tècniques: particions equivalents (una per cada motiu de rebuig i nivells vàlids), valors límit i
	 * frontera de mida (2 files i 2 columnes no vàlides; 3x3 mínim vàlid), disseny per contracte
	 * (precondicions i invariant validats amb excepció) i caixa blanca: prova de bucles aniuats
	 * files/columnes (mínim x mínim, mínim x típic, gran i sortides anticipades).
	 */
	@Test
	void testCarregarFitxer() {

		Tauler tauler;

		// Partició no vàlida: fitxer inexistent
		try {
			tauler = new Tauler("");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: fitxer buit
		try {
			tauler = new Tauler("test/Nivell_buit");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: caràcter no permès (loop testing: sortida anticipada del bucle intern)
		try {
			tauler = new Tauler("test/CaracterInvalid");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: tauler massa petit
		try {
			tauler = new Tauler("test/NivellMassaPetit");
			assertTrue(false);
		} catch (Exception e) {
		}
		
		// Valor frontera: 2 files (mínim 3)
		try {
			tauler = new Tauler("test/NivellDuesFiles");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Valor frontera: 2 columnes (mínim 3)
		try {
			tauler = new Tauler("test/NivellDuesColumnes");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Valor límit: tauler mínim vàlid 3x3 (loop testing: els dos bucles amb el mínim d'iteracions)
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

		// Partició no vàlida: sense jugador (invariant)
		try {
			tauler = new Tauler("test/NoJugador");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: més d'un jugador (loop testing: sortida anticipada del bucle intern)
		try {
			tauler = new Tauler("test/MassaJugadors");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: sense caixes (invariant)
		try {
			tauler = new Tauler("test/NoCaixes");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: sense interruptors (invariant)
		try {
			tauler = new Tauler("test/NoInterruptor");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Partició no vàlida: més interruptors que caixes (invariant)
		try {
			tauler = new Tauler("test/MassaInterruptors");
			assertTrue(false);
		} catch (Exception e) {
		}

		// Disseny per contracte: precondició de nivell rectangular (loop testing: sortida anticipada del bucle extern)
		try {
			tauler = new Tauler("test/TaulerNoRectangular");
			assertTrue(false);
		} catch (Exception e) {
		}
		
		// Partició vàlida: Nivell_1, 3x7 amb 1 caixa (loop testing: bucle extern mínim, intern típic)
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

		// Partició vàlida: Nivell_2, 5x13 amb 4 caixes i 3 interruptors (loop testing: bucle intern gran)
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

	/**
	 * Data-driven amb parets_test.csv sobre el nivell SenseAlgunesParets (3 files x 7 columnes).
	 * Tècniques: particions equivalents (paret, terra, interruptor i fora del tauler per cada costat),
	 * valors límit (x = 0 i 6; y = 0 i 2), valors frontera (x = -1 i 7; y = -1 i 3) i caixa blanca:
	 * cobertura de decisions i de condicions (cada operand de la condició de fora de rang a cert i a fals).
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/parets_test.csv", numLinesToSkip = 1)
	void testHiHaParetInPosition(int x, int y, boolean esperat) {
		Tauler tauler = new Tauler("test/SenseAlgunesParets");
		assertEquals(esperat, tauler.hiHaParetInPosition(x, y));
	}

	/**
	 * Tauler.hiHaCaixaInPosition(int, int) sobre Nivell_2 (caixes en ordre (3,2), (6,2), (9,2), (7,3)).
	 * Tècniques: particions equivalents (amb caixa, sense caixa i fora del tauler) i caixa blanca: prova del
	 * bucle simple (troba la caixa a la 1a, 2a, n-1 i n iteració, o recorre tota la llista sense trobar-la).
	 */
	@Test
	void testHiHaCaixaInPosition() {
		// Nivell_2: 4 caixes i 3 interruptors
		Tauler tauler = new Tauler("test/Nivell_2");
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */

		// Partició amb caixa: (0,0) no en té; les 4 caixes es troben a la 1a, 2a, n-1 i n iteració
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

		// Partició sense caixa: fora del tauler i casella buida (bucle complet sense coincidència)
		assertEquals(tauler.hiHaCaixaInPosition(-1, -1), null);
		assertEquals(tauler.hiHaCaixaInPosition(0, 5), null);
		assertEquals(tauler.hiHaCaixaInPosition(13, 0), null);
		assertEquals(tauler.hiHaCaixaInPosition(3, 1), null);
	}
	
	/**
	 * Tauler.desferMoviment() sobre Nivell_2.
	 * Tècniques: particions equivalents (pila buida, moviment del jugador sol, moviment empenyent una caixa
	 * i moviment fallit que no es registra), disseny per contracte (precondició rm != null
	 * d'afegirRegistreMoviment; l'invariant es comprova en cada desfer) i caixa blanca: cobertura de
	 * decisions (jugador sol en les 4 direccions i caixa en R, L i D).
	 */
	@Test
	void testDesferMoviments() {
		// Nivell_2: 4 caixes i 3 interruptors
		Tauler tauler = new Tauler("test/Nivell_2");
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */
		
		// Referència al jugador
		Jugador j = tauler.getJugador();
		
		// Partició pila buida: no hi ha res a desfer
		assertFalse(tauler.desferMoviment());
		// Disseny per contracte: precondició rm != null d'afegirRegistreMoviment → AssertionError
		assertThrows(AssertionError.class, () -> tauler.afegirRegistreMoviment(null));
		
		// Partició jugador sol (U): es desfà el moviment
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
		
		// Dos moviments (U, U): només es desfà l'últim
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

		// Partició moviment fallit: el xoc amb la paret no es registra; es desfà el moviment vàlid anterior
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
		
		// Partició jugador + caixa (R): es desfan els registres del jugador i de la caixa
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
		
		// Partició moviment fallit: caixa bloquejada per una altra caixa; es desfà l'empenta anterior
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
		
		// Es buida la pila: es torna a l'estat inicial i el següent desfer retorna false
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
		
		// Partició jugador sol (L)
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
		
		// Partició jugador sol (D)
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
		
		// Partició jugador + caixa (L)
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
		
		// Partició jugador + caixa (D)
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
	
	/**
	 * Tauler.estatGuanyador() sobre Nivell_2 (3 interruptors).
	 * Tècniques: particions equivalents (cap, algunes o totes les caixes col·locades, i pèrdua de la
	 * victòria) i valors frontera (2 de 3 interruptors coberts → no guanya; 3 de 3 → guanya).
	 */
	@Test
	void testEstatGuanyador() {
		// Nivell_2: 4 caixes i 3 interruptors
		Tauler tauler = new Tauler("test/Nivell_2");
		/*
 		############# 
 		#  .  .  .  # 
 		#  $  $  $  # 
 		#@    $     #
 		#############
		 */
		
		// Referència al jugador
		Jugador j = tauler.getJugador();
		
		// Partició: cap caixa sobre un interruptor
		assertFalse(tauler.estatGuanyador());
		
		// Partició: algunes caixes col·locades (1 de 3)
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
		
		// Valor frontera: 2 de 3, just abans de guanyar
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
		
		// Valor frontera: 3 de 3 → victòria
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
		
		// Partició: es treu una caixa col·locada → es perd la victòria
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
	
	/**
	 * Comptador de torns de Tauler a partir dels moviments del jugador sobre Nivell_2.
	 * Tècniques: particions equivalents (moviment vàlid, empènyer una caixa, xoc amb paret i caixa
	 * bloquejada) i valor límit inicial (torn 0).
	 */
	@Test
	void testTorn() {
		Tauler tauler = new Tauler("test/Nivell_2");
		Jugador j = tauler.getJugador(); // (1,3)
		// Valor límit: el torn comença a 0
		assertEquals(0, tauler.getTorn());

		// Partició no vàlida: moviments fallits contra paret, el torn no avança
		assertFalse(j.move('L'));
		assertEquals(0, tauler.getTorn());
		assertFalse(j.move('D'));
		assertEquals(0, tauler.getTorn());

		// Partició vàlida: moviments sense caixa, avança 1 per moviment
		assertTrue(j.move('R')); // (2,3)
		assertEquals(1, tauler.getTorn());
		assertTrue(j.move('U')); // (2,2)
		assertEquals(2, tauler.getTorn());

		// Partició vàlida: empènyer una caixa compta com un sol torn
		assertTrue(j.move('R')); // caixa (3,2)->(4,2)
		assertEquals(3, tauler.getTorn());
		assertTrue(j.move('R')); // caixa (4,2)->(5,2)
		assertEquals(4, tauler.getTorn());

		// Partició no vàlida: caixa bloquejada per una altra caixa (6,2), el torn no avança
		assertFalse(j.move('R'));
		assertEquals(4, tauler.getTorn());
	}
}