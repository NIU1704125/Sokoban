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
