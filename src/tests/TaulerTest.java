package tests;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import models.Tauler;

class TaulerTest {
	
	@Test
	void testCarregarFitxer() { 
		
		Tauler tauler;
		
		//fitxer no existeix
		try
		{
			tauler = new Tauler("");
			assertTrue(false);
		} catch (Exception e) {}
		
		//fitxer buit
		try
		{
			tauler = new Tauler("Nivell_buit");
			assertTrue(false);
		}catch (Exception e) {}
		
		//tauler valid
		try
		{
			tauler = new Tauler("CaracterInvalid");
			assertTrue(false);
		}catch (Exception e) {}
		
		
		try
		{
			tauler = new Tauler("NivellMassaPetit");
			assertTrue(false);
		} catch (Exception e) {}
		
		try
		{
			tauler = new Tauler("NoJugador");
			assertTrue(false);
		}catch (Exception e) {}
		
		try
		{
			tauler = new Tauler("MassaJugadors");
			assertTrue(false);
		}catch (Exception e) {}
		
		try
		{
			tauler = new Tauler("NoCaixes");
			assertTrue(false);
		}catch (Exception e) {}
		
		try
		{
			tauler = new Tauler("NoInterruptor");
			assertTrue(false);
		}catch (Exception e) {}
		
		try
		{
			tauler = new Tauler("MassaInterruptors");
			assertTrue(false);
		}catch (Exception e) {}
		
		
		tauler = new Tauler("Nivell_1");
		char[][] n1 = {
			    {'#', '#', '#', '#', '#', '#', '#'},
			    {'#', ' ', ' ', ' ', ' ', '.', '#'},
			    {'#', '#', '#', '#', '#', '#', '#'}
			};
		assertTrue(tauler.getJugador() != null);
		assertEquals(tauler.getCaixes().size(), 1);
		assertEquals(tauler.getNInterrutors(), 1);
		assertEquals(tauler.getFiles(), 3);
		assertEquals(tauler.getColumnes(), 7);
		assertArrayEquals(tauler.getTauler(), n1);
		
		tauler = new Tauler("Nivell_2");
		char[][] n2 = {
			    {'#','#','#','#','#','#','#','#','#','#','#','#','#'},
			    {'#',' ',' ','.',' ',' ','.',' ',' ','.',' ',' ','#'},
			    {'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			    {'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#'},
			    {'#','#','#','#','#','#','#','#','#','#','#','#','#'}
			};
		assertTrue(tauler.getJugador() != null);
		assertEquals(tauler.getCaixes().size(),4);
		assertEquals(tauler.getNInterrutors(), 3);	
		assertEquals(tauler.getFiles(), 5);
		assertEquals(tauler.getColumnes(), 13);
		assertArrayEquals(tauler.getTauler(), n2);

	}
	
	@Test
	void testHiHaParetInPosition()
	{
		Tauler tauler = new Tauler("SenseAlgunesParets");
		/*
		### ###
		  $ .  
		### ###
		*/
		
		//dins de tauler
		assertTrue(tauler.hiHaParetInPosition(0, 0));
		assertTrue(tauler.hiHaParetInPosition(1, 0));
		assertFalse(tauler.hiHaParetInPosition(0, 1));
		assertFalse(tauler.hiHaParetInPosition(1, 1));
		assertFalse(tauler.hiHaParetInPosition(2, 1));
		assertTrue(tauler.hiHaParetInPosition(6, 0));
		assertTrue(tauler.hiHaParetInPosition(0, 2));
		
		//fora de tauler (conta com paret)
		assertTrue(tauler.hiHaParetInPosition(-1, 0));
		assertTrue(tauler.hiHaParetInPosition(0, -1));
		assertTrue(tauler.hiHaParetInPosition(-1, -1));
		assertTrue(tauler.hiHaParetInPosition(7, 0));
		assertTrue(tauler.hiHaParetInPosition(0, 3));
	}
	

	@Test
	void testHiHaCaixaInPosition()
	{
		Tauler tauler = new Tauler("Nivell_2");
		/*
		#############
		#  .  .  .  #
		#  $  $  $  #
		#@     $    #
		#############
		*/
		
		//dins de tauler
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

		//fora de tauler
		assertEquals(tauler.hiHaCaixaInPosition(-1, -1), null);
		assertEquals(tauler.hiHaCaixaInPosition(0, 5), null);
		assertEquals(tauler.hiHaCaixaInPosition(13, 0), null);
		assertEquals(tauler.hiHaCaixaInPosition(3, 1), null);
		
	}
	
}
