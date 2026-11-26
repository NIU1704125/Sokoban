package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import controllers.SelectorController;
import models.Joc;
import vista.SelectorVista;

/**
 * Proves de SelectorController.executar() amb Joc i SelectorVista simulats amb
 * Mockito i el mock propi MockInput. Cada crida a executar() consumeix una tecla.
 * Tècniques: particions equivalents de tecles ('q', Enter i la resta) i caixa blanca: cobertura
 * de decisions (cada branca de l'if / else if / else).
 */
@ExtendWith(MockitoExtension.class)
public class SelectorControllerTest {
	private MockInput mockInput;
	
	@Mock
	private Joc mockJoc;
	
	@Mock
	private SelectorVista mockSelectorVista;
		
	private SelectorController sc;
	
	@BeforeEach
	void setUp() {
		mockInput = new MockInput();
		sc = new SelectorController(mockJoc, mockSelectorVista, mockInput);
	}
	
	/** Branca 'q': finalitza el joc sense seleccionar nivell ni moure el cursor. */
	@Test
	void testSortirJoc() {
		mockInput.configurarTest("testSelectorSortir");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(1)).mostrar(any());
		verify(mockJoc).finalitzarJoc();
		verify(mockJoc, times(0)).seleccionarNivell();
		verify(mockJoc, times(0)).mouCursor(anyChar());
	}
	
	/** Branca "altres tecles" ('s') i branca Enter: mou el cursor un cop i selecciona el nivell. */
	@Test
	void testEntrarNivell() {
		mockInput.configurarTest("testSelectorEntrarNivell");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(2)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell();
		verify(mockJoc, times(1)).mouCursor(anyChar());
	}
	
	/** Seqüència de quatre moviments (s, s, s, w) i Enter: cada tecla es delega a mouCursor. */
	@Test
	void testEntrarNivell2() {
		mockInput.configurarTest("testSelectorEntrarNivell2");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(5)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell();
		verify(mockJoc, times(4)).mouCursor(anyChar());
	}
	
	/**
	 * Seqüència que puja per sobre del primer nivell i torna a baixar (w, w, s, s) i Enter. Amb Joc
	 * simulat només es verifica la delegació; el pas circular del cursor es prova a JocTest.testMouCursor.
	 */
	@Test
	void testLoop() {
		mockInput.configurarTest("testSelectorLoop");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(5)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell();
		verify(mockJoc, times(4)).mouCursor(anyChar());
	}
}
