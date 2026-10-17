import java.util.ArrayList;
import java.util.List;

public class MockTauler extends Tauler {
	
	public MockTauler(String nivell) 
	{
		super();
	}
	
	public char[][] getTauler()
	{
		return new char[3][3];
	}
	
	public List<Caixa> getCaixes()
	{
		return new ArrayList<Caixa>();
	}
	
	public Jugador getJugador()
	{
		return new Jugador(this, 2, 2);
	}
}
