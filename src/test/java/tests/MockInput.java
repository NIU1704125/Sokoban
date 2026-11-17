package tests;

import controllers.IInputController;

public class MockInput implements IInputController {

	char[] inputsSequence;
	int inputIndex;
	
	public void configurarTest(String test)
	{
		inputIndex = 0;
		if(test == "test1")
		{
			inputsSequence = new char[] {'a','b'};
		}
	}
	
	@Override
	public char llegirTecla() {
		char currentChar = inputsSequence[inputIndex];
		inputIndex++;
		return currentChar;
	}

}
