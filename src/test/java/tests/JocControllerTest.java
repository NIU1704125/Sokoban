package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
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
	void testExecutar() {
		//es testeja que al executar el joc controller, ejecuta les funciones de joc corresponents y la vista
		
		//test entrar, moure 1 vegada i sortir
		mockInput.configurarTest("testRapid");
		while(mockInput.hiHaMesInputs())
		{
			jc.executar();
		}
		verify(mockTaulerVista, times(2)).mostrar(any());
		verify(mockJoc).mouJugador(anyChar());
		verify(mockJoc).obrirSelector();

	}

}
