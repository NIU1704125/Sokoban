
public class RegistreMoviment {
	private int m_torn;
	private char m_direccio;
	private Object m_subjecte;
	
	public RegistreMoviment(int torn, char dir, Object subjecte)
	{
		if (torn < 0) 
			throw new IllegalArgumentException("Torn no valid");
		
		if (dir != 'U' && dir != 'D' && dir != 'R' && dir != 'L') 
			throw new IllegalArgumentException("Direccio no valida");
		
		if (!(subjecte instanceof Jugador) && !(subjecte instanceof Caixa)) 
			throw new IllegalArgumentException("Tipus de subjecte no reconegut");
		
		this.m_torn = torn;
		this.m_direccio = dir;
		this.m_subjecte = subjecte;
	}
	
	public int getTorn() 
	{
		return m_torn;
	}
	
	public char getDireccio() 
	{
		return m_direccio;
	}
	
	public Object getSubjecte() 
	{
		return m_subjecte;
	}
}
