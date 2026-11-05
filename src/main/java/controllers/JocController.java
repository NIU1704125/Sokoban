package controllers;

import models.Joc;
import vista.TaulerVista;

public class JocController {

	Joc joc;
	TaulerVista vistaTauler;
	
	public void executar(){
		joc.carregarNivell("Nivell_1");
		
		vistaTauler.mostrar(null);
		

	}
}
