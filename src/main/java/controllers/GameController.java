package controllers;

import models.Joc;
import controllers.JocController;
import controllers.SelectorController;
import vista.IVista;
import vista.TaulerVista;
import vista.SelectorVista;

public class GameController {
	Joc m_joc = new Joc();
	IInputController inputController = new InputFinestra();
	IVista taulerVista = new TaulerVista();
	IVista selectorVista = new SelectorVista();
	
	JocController jocController = new JocController(m_joc, taulerVista, inputController);
	SelectorController selectorController = new SelectorController(m_joc, selectorVista, inputController);
	
	public void executar(){
		m_joc.setCarpeta("test");
		m_joc.obrirSelector();
		while(true){
			switch (m_joc.getEstat()) {
		    case Victoria:
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
