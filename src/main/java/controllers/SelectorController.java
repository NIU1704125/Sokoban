package controllers;

import models.Joc;
import vista.IVista;

/**
 * Controlador del selector de nivells: mostra la llista, llegeix una tecla i l'aplica al model.
 */
public class SelectorController {
	
	private final Joc m_joc;
	private final IVista m_vistaSelector;
	private final IInputController m_input;
	
	/**
	 * Crea el controlador amb les dependències injectades (permet substituir-les per mocks).
	 *
	 * @param newJoc model del joc
	 * @param newSelectorVista vista del selector
	 * @param input font d'entrada de tecles
	 */
	public SelectorController(Joc newJoc, IVista newSelectorVista, IInputController input){
		m_joc = newJoc;
		m_vistaSelector = newSelectorVista;
		m_input = input;
	}
	
	/**
	 * Executa un cicle del selector: 'q' finalitza el joc, Enter carrega el nivell
	 * seleccionat i qualsevol altra tecla mou el cursor.
	 *
	 * Precondició: l'estat del joc és Selector}
	 */
	public void executar(){
		m_vistaSelector.mostrar(m_joc);
		char input = m_input.llegirTecla();
		
		if(input == 'q') {
			m_joc.finalitzarJoc();
		}else if(input == '\n'){
			m_joc.seleccionarNivell();
		}else {
			m_joc.mouCursor(input);
		}
	}
}
