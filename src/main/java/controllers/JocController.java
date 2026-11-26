package controllers;

import models.Joc;
import vista.IVista;

/**
 * Controlador de la partida: mostra el tauler, llegeix una tecla i l'aplica al model.
 */
public class JocController {

	private final Joc m_joc;
	private final IVista m_vistaTauler;
	private final IInputController m_input;
	
	/**
	 * Crea el controlador amb les dependències injectades (permet substituir-les per mocks).
	 *
	 * @param newJoc model del joc
	 * @param newTaulerVista vista del tauler
	 * @param input font d'entrada de tecles
	 */
	public JocController(Joc newJoc, IVista newTaulerVista, IInputController input){
		m_joc = newJoc;
		m_vistaTauler = newTaulerVista;
		m_input = input;
	}
	
	/**
	 * Executa un cicle de partida: mostra el tauler i processa una tecla.
	 * 'q' torna al selector; qualsevol altra tecla es passa a mouJugador(char)
	 * només si encara no hi ha victòria.
	 *
	 * Precondició: l'estat del joc és Jugant
	 */
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
