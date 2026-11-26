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

/**
 * Proves del constructor de RegistreMoviment guiades per dades (data-driven) des de fitxers CSV.
 * Els subjectes Jugador i Caixa són mocks de Mockito; el MockTauler propi fa
 * de subjecte de tipus no permès.
 */
class RegistreMovimentTest {

	/**
	 * Data-driven amb registre_moviment_test.csv.
	 * Tècniques: particions equivalents (torn >= 0} / < 0}; direcció U/D/R/L, minúscula o altra
	 * lletra; subjecte Jugador, Caixa, altre tipus o null), valor límit del torn (0) i valor frontera
	 * (-1), disseny per contracte (precondicions -> IllegalArgumentException) i caixa blanca:
	 * cobertura de decisions i de condicions de les tres comprovacions del constructor.
	 */
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
	
	/**
	 * Pairwise testing amb registre_moviment_test_pairwise.csv: torn {-1, 0, 7} x direcció
	 * {U, d, X} x subjecte {JUGADOR, CAIXA, TAULER}. Array ortogonal L9: 9 casos en lloc de 27, cobrint
	 * tots els parells de valors. Reutilitza el cos de testConstructors.
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "/registre_moviment_test_pairwise.csv", numLinesToSkip = 1)
	void testConstructorsPairwise(int torn, char dir, String tipusSubjecte, boolean esValid) {
		testConstructors(torn, dir, tipusSubjecte, esValid);
	}

}
