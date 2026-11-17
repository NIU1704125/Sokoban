import controllers.InputFinestra;
import controllers.JocController;
import models.Joc;
import vista.SelectorVista;
import vista.TaulerVista;

public class Main {
	public static void main(String[] arg){
		
		Joc joc = new Joc();
		JocController jc = new JocController(joc, new TaulerVista(), new SelectorVista(), new InputFinestra());

		jc.executar();
	}
}
