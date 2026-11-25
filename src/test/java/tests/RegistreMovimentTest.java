package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import models.Caixa;
import models.Jugador;
import models.RegistreMoviment;
import models.Tauler;
import static org.mockito.Mockito.mock;

class RegistreMovimentTest {

	//Test realitzat utilitzant un data-driven approach
	@ParameterizedTest
	@CsvFileSource(resources = "/registre_moviment_test.csv", numLinesToSkip = 1)
	void testConstructors(int torn, char dir, String tipusSubjecte, boolean esValid) {
		Tauler t = new MockTauler("");
		Object subjecte;

		switch (tipusSubjecte) {
		case "JUGADOR":
			subjecte = mock(Jugador.class);
			break;
		case "CAIXA":
			subjecte = mock(Caixa.class);
			break;
		case "TAULER":
			subjecte = t;
			break;
		default:
			subjecte = null;
			break;
		}

		if (esValid) {
			RegistreMoviment rm = new RegistreMoviment(torn, dir, subjecte);
			assertEquals(torn, rm.getTorn());
			assertEquals(dir, rm.getDireccio());
			assertEquals(subjecte, rm.getSubjecte());
		} else {
			assertThrows(IllegalArgumentException.class, () -> {
				new RegistreMoviment(torn, dir, subjecte);
			});
		}
	}
	
	//Test pairwise: torn {-1, 0, 7} x dir {U, d, X} x subjecte {JUGADOR, CAIXA, TAULER}
	//Array ortogonal L9: 9 casos en lloc de 27, cobrint tots els parells de valors
	@ParameterizedTest
	@CsvFileSource(resources = "/registre_moviment_test_pairwise.csv", numLinesToSkip = 1)
	void testConstructorsPairwise(int torn, char dir, String tipusSubjecte, boolean esValid) {
		testConstructors(torn, dir, tipusSubjecte, esValid);
	}

}
