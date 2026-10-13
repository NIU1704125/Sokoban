
public class Tauler {
	
	protected char[][] m_tauler = new char[0][0];
	protected Caixa[] m_caixes = new Caixa[0];
	protected Jugador m_jugador;	

	public Tauler() {}
	
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
