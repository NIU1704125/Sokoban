
public class Tauler {
	
	private char[][] m_tauler = new char[0][0];
	private Caixa[] m_caixes = new Caixa[0];
	private Jugador m_jugador;	

	public Tauler(String fitxer) {}
	
	public char[][] getTauler()
	{
		return m_tauler;
	}
	
	public Caixa[] getCaixes()
	{
		return m_caixes;
	}
	
	public Jugador getJugador()
	{
		return m_jugador;
	}
}
