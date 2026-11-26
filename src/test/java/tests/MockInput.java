package tests;

import controllers.IInputController;

/**
 * Mock propi d'IInputController: retorna una seqüència fixa de tecles segons el test configurat,
 * de manera que els controladors es poden provar sense teclat ni finestra.
 */
public class MockInput implements IInputController {

	char[] inputsSequence;
	int inputIndex;
	
	/**
	 * Selecciona la seqüència de tecles i reinicia la posició de lectura.
	 *
	 * @param test nom del test (coincideix amb el nom del mètode de prova)
	 */
	public void configurarTest(String test){
		inputIndex = 0;
		if(test == "testNormal") {
			inputsSequence = new char[] {'w','q'};
		}
		if(test == "testNormal2") {
			inputsSequence = new char[] {'w','w','w','w','w','w','q'};
		}
		if(test == "testVictoria") {
			inputsSequence = new char[] {'w', 'w', 'w', 'q'};
		}
		if(test == "testSortir") {
			inputsSequence = new char[] {'q'};
		}
		if(test == "testSelectorSortir") {
			inputsSequence = new char[] {'q'};
		}
		if(test == "testSelectorEntrarNivell") {
			inputsSequence = new char[] {'s','\n'};
		}
		if(test == "testSelectorEntrarNivell2") {
			inputsSequence = new char[] {'s','s','s','w','\n'};
		}
		if(test == "testSelectorLoop") {
			inputsSequence = new char[] {'w','w','s','s','\n'};
		}
	}
	
	/** @return la següent tecla de la seqüència */
	@Override
	public char llegirTecla() {
		char currentChar = inputsSequence[inputIndex];
		inputIndex++;
		return currentChar;
	}
	
	/** @return {@code true} si queden tecles per llegir */
	public boolean hiHaMesInputs(){
		return inputIndex < inputsSequence.length;
	}

}
