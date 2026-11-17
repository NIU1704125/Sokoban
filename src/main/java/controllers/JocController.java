package controllers;

import models.Joc;
import vista.IVista;

public class JocController {

	private final Joc m_joc;
	private final IVista m_vistaTauler;
	private final IInputController m_input;
	
	public JocController(Joc newJoc, IVista newTaulerVista, IInputController input){
		m_joc = newJoc;
		m_vistaTauler = newTaulerVista;
		m_input = input;
	}
	
	public void executar(){
		
		
	}
}
