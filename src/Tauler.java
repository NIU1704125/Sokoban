import java.util.List;
import java.util.ArrayList;

public class Tauler {
	
	private char[][] m_tauler = new char[0][0];
	private int nFiles = 0;
	private int nCol = 0;
	private List<Caixa> m_caixes = new ArrayList<Caixa>();
	private int nInterruptors = 0;
	private Jugador m_jugador;	

	private boolean invariants()
	{
		if(m_tauler.length == 0) return false;
		if(m_tauler.length != nFiles) return false;
		if(m_tauler[0].length != nCol) return false;
		if(m_jugador == null) return false;
		if(m_caixes.size() == 0) return false;
		if(nInterruptors == 0) return false;
		if(m_caixes.size() < nInterruptors) return false;
		return true;
	}
	
	
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
