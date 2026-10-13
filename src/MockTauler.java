
public class MockTauler extends Tauler {
	public MockTauler(String fitxer) {}
			
	public char[][] getTauler()
	{
		return new char[3][3];
	}
	
	public Caixa[] getCaixes()
	{
		Caixa[] caixes = {
			    new Caixa(this, 0, 2),
			    new Caixa(this, 1, 3),
			    new Caixa(this, 1, 2)
		};

		return caixes;
	}
	
	public Jugador getJugador()
	{
		return new Jugador(this, 2, 2);
	}
}
