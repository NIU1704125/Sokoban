import java.util.List;
import java.util.ArrayList;

public class Tauler {
	
	private char[][] m_tauler = new char[0][0];
	private int nFiles = 0;
	private int nCol = 0;
	private List<Caixa> m_caixes = new ArrayList<Caixa>();
	private int nInterruptors = 0;
	private Jugador m_jugador;	

	public Tauler(String fitxer) {}
	
	public char[][] getTauler()
	{
		return m_tauler;
	}
	
	public int getNInterrutors()
	{
		return nInterruptors;
	}
	
	public List<Caixa> getCaixes()
	{
		return m_caixes;
	}
	
	public int getFiles()
	{
		return nFiles;
	}
	
	public int getColumnes()
	{
		return nCol;
	}
	
	public Jugador getJugador()
	{
		return m_jugador;
	}
}
