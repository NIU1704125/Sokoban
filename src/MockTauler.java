import java.util.Map;

public class MockTauler extends Tauler {
	
	//string posicio = (xy)
	Map<String, Map<String, Boolean>> mockParets = Map.of(
			"JugadorMovimentSenseParets", Map.of(
					"00", false,
					"01", false,
					"0-1", false,
					"10", false,
					"-10", false
					), 
			"JugadorMovimentRodejatParets", Map.of(
					"00", false,
					"01", true,
					"0-1", true,
					"10", true,
					"-10", true
					),
			"JugadorMovimentParets3x3", Map.ofEntries(
				    Map.entry("00", false),
				    Map.entry("-10", false),
				    Map.entry("-20", true),
				    Map.entry("-11", false),
				    Map.entry("-12", true),
				    Map.entry("01", false),
				    Map.entry("11", false),
				    Map.entry("21", true),
				    Map.entry("10", false),
				    Map.entry("1-1", false),
				    Map.entry("1-2", true),
				    Map.entry("0-1", false),
				    Map.entry("-1-1", false),
				    Map.entry("-2-1", true)
				)
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
		return mockParets.get(mockTaulerActual).get(pos);
	}
	
}
