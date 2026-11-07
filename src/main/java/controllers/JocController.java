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
		
		IInputController input = new InputConsola();
		while(true)	{

			joc.carregarNivell("Nivell_1");
			vistaTauler.mostrar(joc);
			char tecla = input.llegirTecla();
			System.out.print(tecla);
		}

	}
}
