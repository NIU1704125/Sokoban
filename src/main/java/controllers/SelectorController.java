package controllers;

import models.Joc;
import vista.IVista;

public class SelectorController {
	
	private final Joc m_joc;
	private final IVista m_vistaSelector;
	private final IInputController m_input;
	
	public SelectorController(Joc newJoc, IVista newSelectorVista, IInputController input){
		m_joc = newJoc;
		m_vistaSelector = newSelectorVista;
		m_input = input;
	}
	
	public void executar(){
	//crida a les funcions per controlar el selector de nivells
	}
}
