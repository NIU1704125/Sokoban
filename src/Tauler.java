import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

public class Tauler {

	protected char[][] m_tauler = new char[0][0];
	protected int nFiles = 0;
	protected int nCol = 0;
	protected List<Caixa> m_caixes = new ArrayList<Caixa>();
	protected int nInterruptors = 0;
	protected Jugador m_jugador = null;	

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
        
        if(nFiles < 3 || nCol < 3)
        {
        	throw new IllegalArgumentException("Nivell massa petit");
        }
        
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
        
        if(!invariants())
        {
        	throw new IllegalArgumentException("No es compleixen les invariants");
        }

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
