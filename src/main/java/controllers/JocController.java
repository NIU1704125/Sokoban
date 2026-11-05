package controllers;

import models.Joc;
import vista.TaulerVista;

public class JocController {

	Joc joc;
	TaulerVista vistaTauler;
	
	public JocController(Joc newJoc, TaulerVista newTaulerVista){
		joc = newJoc;
		vistaTauler = newTaulerVista;
	}
	
	public void executar(){
		joc.carregarNivell("Nivell_1");
		vistaTauler.mostrar(joc);
	}
}
