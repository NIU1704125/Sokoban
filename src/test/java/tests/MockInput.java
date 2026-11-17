package tests;

import controllers.IInputController;

public class MockInput implements IInputController {

	char[] inputsSequence;
	int inputIndex;
	
	public void configurarTest(String test)
	{
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
	}
	
	@Override
	public char llegirTecla() {
		char currentChar = inputsSequence[inputIndex];
		inputIndex++;
		return currentChar;
	}
	
	public boolean hiHaMesInputs()
	{
		return inputIndex < inputsSequence.length;
	}

}
