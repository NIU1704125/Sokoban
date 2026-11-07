package controllers;

import java.io.InputStream;
import java.util.Scanner;

public class InputConsola implements IInputController {

	private final Scanner m_scanner;

	public InputConsola() {
		this(System.in);
	}

	public InputConsola(InputStream entrada) {
		m_scanner = new Scanner(entrada);
	}

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