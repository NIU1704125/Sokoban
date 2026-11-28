package controllers;

import models.Joc;
import controllers.JocController;
import controllers.SelectorController;
import vista.IVista;
import vista.TaulerVista;
import vista.SelectorVista;

/**
 * Controlador principal: construeix el model, les vistes i els controladors, i delega
 * el control segons l'estat del joc (selector de nivells o partida).
 */
public class GameController {
	Joc m_joc = new Joc();
	IInputController inputController = new InputFinestra();
	IVista taulerVista = new TaulerVista();
	IVista selectorVista = new SelectorVista();
	
	JocController jocController = new JocController(m_joc, taulerVista, inputController);
	SelectorController selectorController = new SelectorController(m_joc, selectorVista, inputController);
	
	/**
	 * Obre el selector de nivells i executa el bucle principal fins que l'estat és Final.
	 *
	 * Postcondició: en retornar, l'estat del joc és Final
	 */
	public void executar(){
		m_joc.setCarpeta("Oficials");
		m_joc.obrirSelector();
		while(true){
			switch (m_joc.getEstat()) {
		    case Jugant:
		    	jocController.executar();
		        break;
		    case Selector:
		    	selectorController.executar();
		        break;
		    case Final:
		        return;
			}
		}
	}
}
