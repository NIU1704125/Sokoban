package tests;

import controllers.IInputController;

public class MockInput implements IInputController {

	char[] inputsSequence;
	int inputIndex;
	
	public void configurarTest(String test)
	{
		inputIndex = 0;
		if(test == "testRapid")
		{
			inputsSequence = new char[] {'w','q'};
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
		return inputIndex != inputsSequence.length;
	}

}
