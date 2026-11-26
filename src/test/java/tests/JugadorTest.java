package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import models.Jugador;
import models.Caixa;
import models.Tauler;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Proves de Jugador amb el mock propi MockTauler i, per aïllar-lo de Caixa,
 * amb mocks de Mockito.
 */
class JugadorTest {

	/**
	 * Constructor: ajust de la posició al tauler 5x5 del mock (rang vàlid 0..4 per eix).
	 * Tècniques: particions equivalents (< 0, 0..4, > 4 per eix), valors límit
	 * (0 i 4), valors frontera (-1, 1, 3 i 5) i disseny per contracte (precondició tauler != null).
	 */
	@Test
	void testJugador() {
		// Mock propi: tauler 5x5 sense escenari
		MockTauler mt = new MockTauler("");

		// Valor límit Y = 4: màxim vàlid, files - 1 (X fixa vàlida)
		Jugador parJugador = new Jugador(mt, 2, 4);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);

		// Valor límit X = 0: mínim vàlid (Y fixa vàlida)
		parJugador = new Jugador(mt, 0, 2);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);
		
		// Valor frontera X = -1: just fora del límit inferior → s'ajusta a 0
		parJugador = new Jugador(mt, -1, 2);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);
		
		// Partició no vàlida X < 0: valor allunyat → s'ajusta a 0
		parJugador = new Jugador(mt, -100, 2);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera X = 1: just dins del límit inferior
		parJugador = new Jugador(mt, 1, 2);
		assertEquals(parJugador.getPosX(), 1);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera X = 3: just dins del límit superior
		parJugador = new Jugador(mt, 3, 2);
		assertEquals(parJugador.getPosX(), 3);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);

		// Valor límit X = 4: màxim vàlid (columnes - 1)
		parJugador = new Jugador(mt, 4, 2);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera X = 5: just fora del límit superior → s'ajusta a 4
		parJugador = new Jugador(mt, 5, 2);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);
		
		// Partició no vàlida X > 4: valor allunyat → s'ajusta a 4
		parJugador = new Jugador(mt, 500, 2);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 2);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera Y = -1: just fora del límit inferior → s'ajusta a 0 (X fixa vàlida)
		parJugador = new Jugador(mt, 2, -1);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);
		
		// Partició no vàlida Y < 0: valor allunyat → s'ajusta a 0
		parJugador = new Jugador(mt, 2, -100);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);

		// Valor límit Y = 0: mínim vàlid
		parJugador = new Jugador(mt, 2, 0);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);
		
		// Valor frontera Y = 1: just dins del límit inferior
		parJugador = new Jugador(mt, 2, 1);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 1);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera Y = 3: just dins del límit superior
		parJugador = new Jugador(mt, 2, 3);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 3);
		assertEquals(parJugador.getTauler(), mt);

		// Valor frontera Y = 5: just fora del límit superior → s'ajusta a 4
		parJugador = new Jugador(mt, 2, 5);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
		
		// Partició no vàlida Y > 4: valor allunyat → s'ajusta a 4
		parJugador = new Jugador(mt, 2, 500);
		assertEquals(parJugador.getPosX(), 2);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);

		// Cantonades: els dos eixos no vàlids alhora (frontera -1 i 5; particions no vàlides -100 i 500)
		parJugador = new Jugador(mt, -1, -1);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);
		
		parJugador = new Jugador(mt, -100, -100);
		assertEquals(parJugador.getPosX(), 0);
		assertEquals(parJugador.getPosY(), 0);
		assertEquals(parJugador.getTauler(), mt);

		parJugador = new Jugador(mt, 5, 5);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
		
		parJugador = new Jugador(mt, 500, 500);
		assertEquals(parJugador.getPosX(), 4);
		assertEquals(parJugador.getPosY(), 4);
		assertEquals(parJugador.getTauler(), mt);
		
		// Disseny per contracte: precondició tauler != null → AssertionError
		assertThrows(AssertionError.class, () -> new Jugador(null, 2, 2));
	}

	/**
	 * {@link Jugador#move(char)} sense caixes al camí.
	 * Tècniques: particions equivalents (destinació lliure o amb paret; direcció vàlida o no) i
	 * valors frontera lliure/paret en recórrer el perímetre d'un recinte 3x3.
	 */
	@Test
	void testMoveSenseCaixes() {
		// Mock propi: escenari sense parets al voltant de (2,2)
		MockTauler mt = new MockTauler("MovimentSenseParets");

		// Jugador inicial a (2,2)
		Jugador j = new Jugador(mt, 2, 2);

		// Partició vàlida: destinació lliure en vertical (U, D, D, U)
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

		// Partició vàlida: destinació lliure en horitzontal (R, L, L, R)
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

		// Mock propi: jugador envoltat de parets
		mt = new MockTauler("MovimentRodejatParets");
		// Jugador inicial a (2,2)
		j = new Jugador(mt, 2, 2);

		// Partició no vàlida: paret a la destinació en les 4 direccions → no es mou
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

		// Mock propi: recinte lliure de 3x3 envoltat de parets; es recorre fins a xocar en cada direcció
		mt = new MockTauler("MovimentParets3x3");
		// Jugador inicial a (2,2)
		j = new Jugador(mt, 2, 2);

		// Mou cap a l'esquerra (vàlid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		// Mou cap a l'esquerra (xoca)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		// Mou cap a dalt (vàlid)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		// Mou cap a dalt (xoca)
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 1);
		// Mou cap a la dreta (vàlid)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		// Mou cap a la dreta (vàlid)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 1);
		// Mou cap a la dreta (xoca)
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 1);
		// Mou cap a baix (vàlid)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		// Mou cap a baix (vàlid)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		// Mou cap a baix (xoca)
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		// Mou cap a l'esquerra (vàlid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		// Mou cap a l'esquerra (vàlid)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 3);
		// Mou cap a l'esquerra (xoca)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 3);

		// Partició no vàlida de direcció: minúscules i altres caràcters → IllegalArgumentException (default)
		try {
			j.move('h');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			j.move('u');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			j.move('d');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			j.move('r');
			assertTrue(false);
		} catch (Exception e) {
		}
		try {
			j.move('l');
			assertTrue(false);
		} catch (Exception e) {
		}
	}

	/**
	 * {@link Jugador#move(char)} empenyent caixes.
	 * Tècniques: particions equivalents en les 4 direccions (caixa amb casella lliure darrere, caixa amb
	 * paret darrere i caixa amb una altra caixa darrere) i, amb mocks de Mockito, verificació que
	 * només es registra el moviment i avança el torn quan la caixa es pot moure (postcondició).
	 */
	@Test
	void testMoveAmbCaixes() {
		// Mock propi: jugador a (2,2) amb una caixa a cada costat i una sola casella lliure darrere de cada caixa
		MockTauler mt = new MockTauler("MovimentLinealCaixes");
		// Jugador inicial a (2,2)
		Jugador j = new Jugador(mt, 2, 2);

		// Particions en les 4 direccions: caixa amb casella lliure darrere (vàlida) i amb paret darrere (no vàlida)
		// Moviment cap a dalt (vàlid, empeny la caixa)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		// Moviment cap a dalt (xoca, la caixa té paret darrere)
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		// Moviment cap a baix (torna al centre)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);

		// Moviment cap a baix (vàlid, empeny la caixa)
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		// Moviment cap a baix (xoca, la caixa té paret darrere)
		assertFalse(j.move('D'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 3);
		// Moviment cap a dalt (torna al centre)
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);

		// Moviment cap a la dreta (vàlid, empeny la caixa)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		// Moviment cap a la dreta (xoca, la caixa té paret darrere)
		assertFalse(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);
		// Moviment cap a l'esquerra (torna al centre)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);

		// Moviment cap a l'esquerra (vàlid, empeny la caixa)
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		// Moviment cap a l'esquerra (xoca, la caixa té paret darrere)
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 1);
		assertEquals(j.getPosY(), 2);
		// Moviment cap a la dreta (torna al centre)
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);

		// Mock propi: sala 3x5 amb dues caixes
		mt = new MockTauler("MovimentCaixes3x5");
		// Jugador inicial a (2,2)
		j = new Jugador(mt, 2, 2);
		// Partició no vàlida: la caixa de dalt té paret darrere; partició vàlida: empeny la de la dreta
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 2);

		// Envolta la caixa de la dreta per baix i l'empeny cap amunt
		assertTrue(j.move('D'));
		assertEquals(j.getPosX(), 3);
		assertEquals(j.getPosY(), 3);
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 3);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 2);

		// Falla en tornar-la a empènyer cap amunt: ara té paret darrere
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 2);

		// Torna a envoltar la caixa per la dreta i l'empeny cap a l'esquerra
		assertTrue(j.move('R'));
		assertEquals(j.getPosX(), 5);
		assertEquals(j.getPosY(), 2);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 5);
		assertEquals(j.getPosY(), 1);
		assertTrue(j.move('L'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 1);

		// Partició no vàlida: la caixa té una altra caixa darrere → no es mou
		assertFalse(j.move('L'));
		assertEquals(j.getPosX(), 4);
		assertEquals(j.getPosY(), 1);
		
		// Mocks de Mockito: Tauler i Caixa simulats per aïllar Jugador.move de Caixa.move
		// i verificar la interacció amb el tauler
		Tauler mockTauler = mock(Tauler.class);
		Caixa mockCaixa = mock(Caixa.class);
		when(mockTauler.getColumnes()).thenReturn(5);
		when(mockTauler.getFiles()).thenReturn(5);
		when(mockTauler.hiHaCaixaInPosition(2, 1)).thenReturn(mockCaixa);

		// Jugador inicial a (2,2) sobre el tauler simulat
		j = new Jugador(mockTauler, 2, 2);

		// Postcondició amb move = false: el jugador no es mou, no es registra res i el torn no avança
		when(mockCaixa.move('U')).thenReturn(false);
		assertFalse(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 2);
		verify(mockCaixa, times(1)).move('U');
		verify(mockTauler, never()).afegirRegistreMoviment(any());
		verify(mockTauler, never()).avancaTorn();

		// Postcondició amb move = true: el jugador avança, es registra el moviment i avança el torn
		when(mockCaixa.move('U')).thenReturn(true);
		assertTrue(j.move('U'));
		assertEquals(j.getPosX(), 2);
		assertEquals(j.getPosY(), 1);
		verify(mockCaixa, times(2)).move('U');
		verify(mockTauler, times(1)).afegirRegistreMoviment(any());
		verify(mockTauler, times(1)).avancaTorn();
	}
}