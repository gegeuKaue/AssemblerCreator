package enumeracao.kaue.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Main {
	public static void main(String[] args) throws IOException {
		List<String> lines = FileUtils.readLines(new File("C:\\Users\\geovane.santos\\Desktop\\enum.java"));
		String classe = nomeClasse(lines);
		List<Atributo> atributosClasse = atributos(lines);

		lines = limpar(lines);
		lines = limpaCaracterePontoVirgula(lines);
		List<Atributo> atributos = new ArrayList<Atributo>();
		if (!atributos.isEmpty()) {
			for (String linha : lines) {
				String[] linhaQuebrada = linha.split("\\s");
				atributos.add(new Atributo(linhaQuebrada[1], linhaQuebrada[2]));
			}
		}
		ResourceAssembler assembler = new ResourceAssembler(classe);
		System.out.println(assembler.classeAssemblerAll(atributosClasse, atributos));
//		Formatacao formatacao;
//		System.out.println("toResource");
//		System.out.println("___________________________________________________________");
//		System.out.println("\n");
//
//		for (Atributo atributo : atributos) {
//			formatacao = new Formatacao(atributo);
//			System.out.println(formatacao.toResource());
//		}
//		System.out.println("\n");
//		System.out.println("toEntity");
//		System.out.println("___________________________________________________________");
//		System.out.println("\n");
//		for (Atributo atributo : atributos) {
//			formatacao = new Formatacao(atributo);
//			System.out.println(formatacao.toEntity());
//		}

	}

	private static List<Atributo> atributos(List<String> lines) {
		List<Atributo> atributos = new ArrayList<Atributo>();
		for (String string : lines) {

			if (!string.contains("private")) {
				continue;
			}
			if (string.contains("Integer")) {
				continue;
			}
			if (string.contains("int")) {
				continue;
			}
			if (string.contains("long")) {
				continue;
			}
			if (string.contains("double")) {
				continue;
			}
			if (string.contains("Double")) {
				continue;
			}

			if (string.contains("String")) {
				continue;
			}
			if (string.contains("Date")) {
				continue;
			}
			if (string.contains("Boolean")) {
				continue;
			}
			if (string.contains("Enum")) {
				continue;
			}
			if (string.trim().isEmpty()) {
				continue;
			}
			if (string.contains("import")) {
				continue;
			}
			if (string.contains("Paginacao")) {
				continue;
			}
			if (string.contains("*")) {
				continue;
			}
			string = string.replaceAll("^(\\s)+|;|(private)", "");
			String[] linhaQuebrada = string.split("\\s");
			atributos.add(new Atributo(linhaQuebrada[1], linhaQuebrada[2]));

		}

		return atributos;
	}

	private static List<String> limpaCaracterePontoVirgula(List<String> lines) {
		List<String> newLines = new ArrayList<String>();

		for (String string : lines) {
			newLines.add(string.replaceAll("^(\\s)+|;|(private)", ""));
		}
		return newLines;
	}

	private static String nomeClasse(List<String> lines) {
		for (String string : lines) {
			if (string.contains("public class")) {
				return limparNomeClasse(string.replace("public class ", ""));
			}
		}
		return null;
	}

	private static String limparNomeClasse(String classe) {
		int i = 0;
		for (; i < classe.length(); i++) {
			if (classe.charAt(i) == ' ') {
				break;
			}
		}
		return classe.substring(0, i);
	}

	private static List<String> limpar(List<String> lines) {
		List<String> newLines = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains("import")) {
				continue;
			}
			if (lines.get(i).contains("{")) {
				continue;
			}
			if (lines.get(i).contains("Enum")) {
				newLines.add(lines.get(i));
			}
		}
		return newLines;
	}
}
