package enumeracao.kaue.main;

public class Import {

	public String importAll() {
		return importStatic() + "\n\n" + importSring() + "\n\n" + importUtil();
	}

	private String importStatic() {
		StringBuilder builder = new StringBuilder();
		builder.append("import static java.util.stream.Collectors.toList;");
		builder.append("\n");
		builder.append("import static org.springframework.beans.BeanUtils.copyProperties;");
		return builder.toString();
	}

	private String importUtil() {
		StringBuilder builder = new StringBuilder();
		builder.append("import java.util.ArrayList;\n");
		builder.append("import java.util.List;\n");
		builder.append("import java.util.stream.StreamSupport;");

		return builder.toString();
	}

	private String importSring() {
		StringBuilder builder = new StringBuilder();
		builder.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		builder.append("import org.springframework.stereotype.Component;");
		return builder.toString();
	}
}
