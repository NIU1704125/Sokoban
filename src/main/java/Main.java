import controllers.GameController;

/**
 * Punt d'entrada de l'aplicació: crea el controlador principal i arrenca el joc.
 */
public class Main {
	/**
	 * Executa el bucle principal del joc i tanca l'aplicació quan acaba.
	 *
	 * @param arg arguments de línia d'ordres (no s'utilitzen)
	 */
	public static void main(String[] arg){
		
		GameController gc = new GameController();
		gc.executar();
		
		System.exit(0);
	}
}
