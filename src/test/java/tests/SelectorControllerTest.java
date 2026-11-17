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
import controllers.SelectorController;
import models.Joc;
import vista.SelectorVista;

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
	
	@Test
	void testSortirJoc() {
		mockInput.configurarTest("testSelectorSortir");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(1)).mostrar(any());
		verify(mockJoc).finalitzarJoc();
		verify(mockJoc, times(0)).seleccionarNivell(any());
		verify(mockJoc, times(0)).moureCursor(anyChar());
	}
	
	@Test
	void testEntrarNivell() {
		mockInput.configurarTest("testSelectorEntrarNivell");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(2)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell(any());
		verify(mockJoc, times(1)).moureCursor(anyChar());
	}
	
	@Test
	void testEntrarNivell2() {
		mockInput.configurarTest("testSelectorEntrarNivel2");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(4)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell(any());
		verify(mockJoc, times(4)).moureCursor(anyChar());
	}
	
	@Test
	void testLoop() {
		mockInput.configurarTest("testSelectorLoop");
		while(mockInput.hiHaMesInputs()){
			sc.executar();
		}
		verify(mockSelectorVista, times(4)).mostrar(any());
		verify(mockJoc, times(0)).finalitzarJoc();
		verify(mockJoc, times(1)).seleccionarNivell(any());
		verify(mockJoc, times(4)).moureCursor(anyChar());
	}
}
