package controllers;

import models.Joc;
import vista.IVista;

public class JocController {

	private final Joc m_joc;
	private final IVista m_vistaTauler;
	private final IVista m_vistaSelector;
	private final IInputController m_input;
	
	public JocController(Joc newJoc, IVista newTaulerVista, IVista newSelectorVista, IInputController input){
		m_joc = newJoc;
		m_vistaTauler = newTaulerVista;
		m_vistaSelector = newSelectorVista;
		m_input = input;
	}
	
	public void executar(){
		
		m_joc.setCarpeta("test");

		m_joc.carregarNivell("Nivell_2");
		while(true)	{

			m_vistaTauler.mostrar(m_joc);
			char tecla = m_input.llegirTecla();
			System.out.print(tecla);
		}
		
		/*
		m_joc.obrirSelector();
		m_vistaSelector.mostrar(m_joc);
		*/
		
	}
}
