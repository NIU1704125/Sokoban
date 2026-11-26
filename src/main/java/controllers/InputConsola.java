package controllers;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Entrada per consola: llegeix línies i en pren el primer caràcter no blanc.
 */
public class InputConsola implements IInputController {

	private final Scanner m_scanner;

	/**
	 * Crea una entrada que llegeix de l'entrada estàndard.
	 */
	public InputConsola() {
		this(System.in);
	}

	/**
	 * Crea una entrada que llegeix del flux indicat (permet injectar-ne un als tests).
	 *
	 * @param entrada flux d'entrada
	 */
	public InputConsola(InputStream entrada) {
		m_scanner = new Scanner(entrada);
	}

	/**
	 * Retorna el primer caràcter, en minúscula, de la primera línia no buida; les línies buides s'ignoren.
	 *
	 * @return caràcter llegit, o '.' si el flux s'acaba sense cap línia vàlida
	 */
	public char llegirTecla() {
		while (m_scanner.hasNextLine()) {
			String linia = m_scanner.nextLine().trim();
			if (!linia.isEmpty()) {
				return Character.toLowerCase(linia.charAt(0));
			}
		}
		return '.';
	}
}