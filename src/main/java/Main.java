
import controllers.JocController;
import models.Joc;
import vista.TaulerVista;

public class Main {
	public static void main(String[] arg){
		Joc joc = new Joc();
		TaulerVista tv = new TaulerVista();
		
		JocController jc = new JocController(joc, tv);

		jc.executar();
	}
}
