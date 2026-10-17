import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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
}
