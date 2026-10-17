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
				)
			/*
			PPPPPPPPPP
			PP      PP
			PP  JJ  PP
			PP      PP
			PPPPPPPPPP  
			*/
			);
	
	String mockTaulerActual = "";
	
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
	
}
