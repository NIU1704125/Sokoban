package tests;

import java.util.List;
import java.util.Map;

import models.Caixa;
import models.RegistreMoviment;
import models.Tauler;
import models.Jugador;

/**
 * Mock propi de Tauler: escenaris de parets i caixes en memòria (sense fitxers) per provar
 * Caixa i Jugador de manera aïllada. Dimensions fixes de 5x5; anul·la el registre de
 * moviments, el torn i la victòria, i compta les crides a desferMoviment().
 */
public class MockTauler extends Tauler {
	private Jugador mockJugador;
	
	// Clau de posició: x i y concatenats ("21" = x 2, y 1); false = casella lliure, posició absent = paret
	Map<String, Map<String, Boolean>> mockParets = Map.of("MovimentSenseParets",
			Map.ofEntries(Map.entry("22", false), Map.entry("32", false), Map.entry("12", false),
					Map.entry("21", false), Map.entry("23", false)),
			/*
			 * PPPPPPPPPP 
			 * PPPP  PPPP 
			 * PP  JJ  PP 
			 * PPPP  PPPP 
			 * PPPPPPPPPP
			 */
			"MovimentRodejatParets", Map.ofEntries(Map.entry("22", false)),
			/*
			 * PPPPPP 
			 * PPJJPP 
			 * PPPPPP
			 */
			"MovimentParets3x3",
			Map.ofEntries(Map.entry("22", false), Map.entry("12", false), Map.entry("11", false),
					Map.entry("21", false), Map.entry("31", false), Map.entry("32", false), Map.entry("33", false),
					Map.entry("23", false), Map.entry("13", false)),
			/*
			 * PPPPPPPPPP
			 * PP      PP 
			 * PP  JJ  PP
			 * PP      PP 
			 * PPPPPPPPPP
			 */
			"MovimentLinealCaixes",
			Map.ofEntries(Map.entry("22", false), Map.entry("32", false), Map.entry("42", false),
					Map.entry("12", false), Map.entry("02", false), Map.entry("21", false), Map.entry("20", false),
					Map.entry("23", false), Map.entry("24", false)),
			/*
			 * PPPPPPPPPPPPPP 
			 * PPPPPP  PPPPPP 
			 * PPPPPP[]PPPPPP 
			 * PP  []JJ[]  PP 
			 * PPPPPP[]PPPPPP 
			 * PPPPPP  PPPPPP 
			 * PPPPPPPPPPPPPP
			 */
			"MovimentCaixes3x5",
			Map.ofEntries(Map.entry("22", false), Map.entry("12", false), Map.entry("11", false),
					Map.entry("21", false), Map.entry("31", false), Map.entry("32", false), Map.entry("33", false),
					Map.entry("23", false), Map.entry("13", false), Map.entry("42", false), Map.entry("43", false),
					Map.entry("41", false), Map.entry("52", false), Map.entry("53", false), Map.entry("51", false))
	/*
	 * PPPPPPPPPPPPPP 
	 * PP  []      PP 
	 * PP  JJ[]    PP
	 * PP          PP
	 * PPPPPPPPPPPPPP
	 */
	);

	String mockTaulerActual = "";

	Map<String, List<Caixa>> mockCaixes = Map.of("MovimentLinealCaixes",
			List.of(new Caixa(this, 3, 2), new Caixa(this, 1, 2), new Caixa(this, 2, 1), new Caixa(this, 2, 3)),
			/*
			 * PPPPPPPPPPPPPP 
			 * PPPPPP  PPPPPP 
			 * PPPPPP[]PPPPPP 
			 * PP  []JJ[]  PP 
			 * PPPPPP[]PPPPPP 
			 * PPPPPP  PPPPPP 
			 * PPPPPPPPPPPPPP
			 */
			"MovimentCaixes3x5", List.of(new Caixa(this, 2, 1), new Caixa(this, 3, 2))
	/*
	 * PPPPPPPPPPPPPP 
	 * PP  []      PP 
	 * PP  JJ[]    PP
	 * PP          PP
	 * PPPPPPPPPPPPPP
	 */

	);

	@Override
	public List<Caixa> getCaixes() {
		return mockCaixes.getOrDefault(mockTaulerActual, null);
	}

    private int nMovimentsDesfets;
	/**
	 * Crea el mock amb un escenari predefinit.
	 *
	 * @param nivell nom de l'escenari (cadena buida quan només calen les dimensions)
	 */
	public MockTauler(String nivell) {
		super();
		nMovimentsDesfets = 0;
		mockTaulerActual = nivell;
	}

	@Override
	public boolean hiHaParetInPosition(int x, int y) {
		String pos = String.valueOf(x) + String.valueOf(y);
		return mockParets.get(mockTaulerActual).getOrDefault(pos, true);
	}

	@Override
	public Caixa hiHaCaixaInPosition(int x, int y) {
		List<Caixa> caixesActuals = mockCaixes.get(mockTaulerActual);

		if (caixesActuals == null) {
			return null;
		}

		for (Caixa c : caixesActuals) {
			if (c.getPosX() == x && c.getPosY() == y) {
				return c;
			}
		}
		return null;
	}

	@Override
	public int getFiles() {
		return 5;
	}

	@Override
	public int getColumnes() {
		return 5;
	}
	
	@Override
	public void afegirRegistreMoviment(RegistreMoviment rm) {
		return;
	}
	
	@Override
	public void avancaTorn() {
		return;
	}
	
	@Override
	public int getTorn() {
		return 0;
	}
	
	@Override
	public boolean estatGuanyador() {
		return false;
	}
	
	/**
	 * Injecta el jugador que retornarà getJugador().
	 *
	 * @param jugador jugador (real o simulat)
	 */
	public void setJugador(Jugador jugador) {
        this.mockJugador = jugador;
    }

    @Override
    public Jugador getJugador() {
        return this.mockJugador;
    }
    
    @Override
	public boolean desferMoviment() {
    	nMovimentsDesfets += 1;
    	return true;
    }
    
    /** @return nombre de crides a desferMoviment() */
    public int getNombreMovimentsDesfets() {
    	return nMovimentsDesfets;
    }
}