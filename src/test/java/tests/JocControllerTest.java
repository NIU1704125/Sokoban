package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import controllers.JocController;
import models.Joc;
import vista.TaulerVista;

/**
 * Proves de JocController.executar() amb Joc i TaulerVista simulats amb Mockito
 * i el mock propi MockInput. Cada crida a executar() consumeix una tecla.
 * Tècnica general: caixa blanca, cobertura de camins (P1 'q'; P2 moure sense victòria;
 * P3 tecla de moviment amb victòria).
 */
@ExtendWith(MockitoExtension.class)
class JocControllerTest {

	private MockInput mockInput;
	
	@Mock
	private Joc mockJoc;
	
	@Mock
	private TaulerVista mockTaulerVista;
		
	private JocController jc;
	
	@BeforeEach
	void setUp() {
		mockInput = new MockInput();
		jc = new JocController(mockJoc, mockTaulerVista, mockInput);
	}
	
	/** Camins P2 + P1: un moviment ('w') i sortida ('q') cap al selector. */
	@Test
	void testNormal() {
		mockInput.configurarTest("testNormal");
		while(mockInput.hiHaMesInputs()){
			jc.executar();
		}
		verify(mockTaulerVista, times(2)).mostrar(any());
		verify(mockJoc).mouJugador(anyChar());
		verify(mockJoc).obrirSelector();
	}
	
	/** Camí P2 repetit sis vegades i P1: cada tecla consulta la victòria i mou el jugador. */
	@Test
	void testNormal2() {
		mockInput.configurarTest("testNormal2");
		when(mockJoc.getVictoria()).thenReturn(false);
		while(mockInput.hiHaMesInputs()){
			jc.executar();
		}
		verify(mockTaulerVista, times(7)).mostrar(any());
		verify(mockJoc, times(6)).getVictoria();
		verify(mockJoc, times(6)).mouJugador(anyChar());
		verify(mockJoc).obrirSelector();
	}
	
	/** Camí P1: 'q' torna al selector sense consultar la victòria ni moure el jugador. */
	@Test
	void testSortir() {
		mockInput.configurarTest("testSortir");
		while(mockInput.hiHaMesInputs()){
			jc.executar();
		}
		verify(mockTaulerVista, times(1)).mostrar(any());
		verify(mockJoc, times(0)).getVictoria();
		verify(mockJoc, times(0)).mouJugador(anyChar());
		verify(mockJoc).obrirSelector();
	}
	
	/** Camins P2, P3, P3 i P1: després de guanyar, les tecles de moviment ja no mouen el jugador. */
	@Test
	void testVictoria() {
		mockInput.configurarTest("testVictoria");
		when(mockJoc.getVictoria()).thenReturn(false, true);
		while(mockInput.hiHaMesInputs()){
			jc.executar();
		}
		verify(mockTaulerVista, times(4)).mostrar(any());
		verify(mockJoc, times(3)).getVictoria();
		verify(mockJoc).mouJugador(anyChar());
		verify(mockJoc).obrirSelector();
	}

}
