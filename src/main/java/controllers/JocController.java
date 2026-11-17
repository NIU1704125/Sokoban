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
		
		m_vistaTauler.mostrar(m_joc);
		char input = m_input.llegirTecla();
		
		if(input == 'q') {
			m_joc.obrirSelector();
		}else{
			if(!m_joc.getVictoria()){
				m_joc.mouJugador(input);
			}
		}
	}
}
