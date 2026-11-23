package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import models.Caixa;
import models.Jugador;
import models.RegistreMoviment;
import models.Tauler;

class RegistreMovimentTest {

	//Test realitzat utilitzant un data-driven approach
	@ParameterizedTest
	@CsvFileSource(resources = "/registre_moviment_test.csv", numLinesToSkip = 1)
	void testConstructors(int torn, char dir, String tipusSubjecte, boolean esValid) {
		Tauler t = new MockTauler("");
		Object subjecte;

		switch (tipusSubjecte) {
		case "JUGADOR":
			subjecte = new Jugador(t, 0, 0);
			break;
		case "CAIXA":
			subjecte = new Caixa(t, 1, 1);
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

}
