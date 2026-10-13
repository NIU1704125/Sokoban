import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

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
	
	
	public Tauler(String nomNivell) 
	{
		List<String> lineas = new ArrayList<String>();
		try
		{
			lineas = Files.readAllLines(Path.of("nivells/test/" + nomNivell + ".txt"));
		} catch (Exception e) { throw new IllegalArgumentException("Fitxer no trobat"); }        
        
        if(lineas.isEmpty())
        {
            throw new IllegalArgumentException("Nivell buit");
        }
        
        nFiles = lineas.size();
        nCol = lineas.get(0).length();
        
        m_tauler = new char[nFiles][nCol];
        
        for(int f = 0; f < lineas.size(); f++)
        {
            String linea = lineas.get(f);
            for(int c = 0; c < linea.length(); c++)
            {
                char caracter = linea.charAt(c);
                
                switch (caracter)
                {
                    case '@':
                        if(m_jugador == null)
                        {
                            m_jugador = new Jugador();
                            m_tauler[f][c] = '@';
                        }
                        else
                        {
                            throw new IllegalArgumentException("Ja existia un jugador");
                        }
                    break;
                    case '$':
                    	Caixa novaCaixa = new Caixa();
                    	m_caixes.add(novaCaixa);
                    	m_tauler[f][c] = '$';
                    	break;
                    case '.':
                    	m_tauler[f][c] = '.';
                    	nInterruptors++;
                    	break;
                    case ' ':
                    	m_tauler[f][c] = ' ';
                    	break;
                    case '#':
                    	m_tauler[f][c] = '#';
                    	break;
                    default:
                    	throw new IllegalArgumentException("Caracter invalid");
                }
            }
        }
        
        assert invariants();
	}
	
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
