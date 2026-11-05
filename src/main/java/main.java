
import controllers.*;
import models.*;
import vista.*;
public class main {
	public static void main(String[] arg)
	{
		Joc joc = new Joc();
		TaulerVista tv = new TaulerVista();
		
		JocController jc = new JocController(joc, tv);

		jc.executar();
	}
}
