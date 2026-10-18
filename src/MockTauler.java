import java.util.List;
import java.util.Map;

public class MockTauler extends Tauler {
	
	//string posicio = (xy)
	Map<String, Map<String, Boolean>> mockParets = Map.of(
			"MovimentSenseParets", Map.ofEntries(
					 Map.entry("00", false),
					 Map.entry("10", false),
					 Map.entry("-10", false),
					 Map.entry("01", false),
					 Map.entry("0-1", false)
					), 
			/*
			PPPPPPPPPP  
			PPPP  PPPP  
			PP  JJ  PP
			PPPP  PPPP
			PPPPPPPPPP 
			*/
			"MovimentRodejatParets", Map.ofEntries(
				    Map.entry("00", false)
					),
			/*
			PPPPPP
			PPJJPP
			PPPPPP
			*/
			"MovimentParets3x3", Map.ofEntries(
				    Map.entry("00", false),
				    Map.entry("-10", false),
				    Map.entry("-11", false),
				    Map.entry("01", false),
				    Map.entry("11", false),
				    Map.entry("10", false),
				    Map.entry("1-1", false),
				    Map.entry("0-1", false),
				    Map.entry("-1-1", false)
				),
			/*
			PPPPPPPPPP
			PP      PP
			PP  JJ  PP
			PP      PP
			PPPPPPPPPP  
			*/
			"MovimentLinealCaixes", Map.ofEntries(
					 Map.entry("00", false),
					 Map.entry("10", false),
					 Map.entry("20", false),
					 Map.entry("-10", false),
					 Map.entry("-20", false),
					 Map.entry("01", false),
					 Map.entry("02", false),
					 Map.entry("0-1", false),
					 Map.entry("0-2", false)
					), 
			/*
			PPPPPPPPPPPPPP
			PPPPPP  PPPPPP
			PPPPPP[]PPPPPP
			PP  []JJ[]  PP
			PPPPPP[]PPPPPP
			PPPPPP  PPPPPP
			PPPPPPPPPPPPPP 
			*/
			"MovimentCaixes3x5", Map.ofEntries(
				    Map.entry("00", false),
				    Map.entry("-10", false),
				    Map.entry("-11", false),
				    Map.entry("01", false),
				    Map.entry("11", false),
				    Map.entry("10", false),
				    Map.entry("1-1", false),
				    Map.entry("0-1", false),
				    Map.entry("-1-1", false),
				    Map.entry("20", false),
				    Map.entry("2-1", false),
				    Map.entry("21", false),
				    Map.entry("30", false),
				    Map.entry("3-1", false),
				    Map.entry("31", false)
				)
			/*
			PPPPPPPPPPPPPP
			PP  []      PP
			PP  JJ[]    PP
			PP          PP
			PPPPPPPPPPPPPP
			*/
			);
	
	String mockTaulerActual = "";
	
	Map<String, List<Caixa>> mockCaixes = Map.of(
			"MovimentLinealCaixes", List.of(
					new Caixa(this, 1, 0),
					new Caixa(this, -1, 0),
					new Caixa(this, 0, 1),
					new Caixa(this, 0, -1)
					),
			/*
			PPPPPPPPPPPPPP
			PPPPPP  PPPPPP
			PPPPPP[]PPPPPP
			PP  []JJ[]  PP
			PPPPPP[]PPPPPP
			PPPPPP  PPPPPP
			PPPPPPPPPPPPPP 
			*/
			"MovimentCaixes3x5", List.of(
					new Caixa(this, 0, 1),
					new Caixa(this, 1, 0)
					)
			/*
			PPPPPPPPPPPPPP
			PP  []      PP
			PP  JJ[]    PP
			PP          PP
			PPPPPPPPPPPPPP
			*/

			);
	
	public List<Caixa> getCaixes()
	{
		return mockCaixes.getOrDefault(mockTaulerActual, null);
	}
	
	public MockTauler(String nivell)  
	{
		super();
		mockTaulerActual = nivell;
	}
	
	public boolean hiHaParetInPosition(int x, int y)
	{
		String pos = String.valueOf(x) + String.valueOf(y);
		return mockParets.get(mockTaulerActual).getOrDefault(pos, true);
	}
	
	public Caixa hiHaCaixaInPosition(int x, int y) 
	{
		for (Caixa c : mockCaixes.get(mockTaulerActual)) {
	        if (c.getPosX() == x && c.getPosY() == y) {
	            return c;
	        }
	    }
	    return null;
	}
}
