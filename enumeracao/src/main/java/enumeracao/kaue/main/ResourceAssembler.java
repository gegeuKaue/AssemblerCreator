package enumeracao.kaue.main;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ResourceAssembler {
	private String nomeClasse;

	public ResourceAssembler(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public String classeAssemblerAll(List<Atributo> classes, List<Atributo> enume) {
		StringBuilder builder = new StringBuilder();
		Import import1 = new Import();
		builder.append(import1.importAll());
		builder.append("\n");
		builder.append('\n');
		builder.append(component());
		builder.append('\n');
		builder.append(publicClass());
		if (classes != null && !classes.isEmpty()) {
			for (Atributo atributo : classes) {
				builder.append("\n");
				builder.append(autowired());
				builder.append("\n");
				builder.append(classesResourceAssembler(atributo.getTipo()));
				builder.append("\n");
			}
		}

		builder.append('\n');
		builder.append(override());
		builder.append('\n');
		builder.append(toEntity(nomeClasse));
		builder.append('\n');
		builder.append('\n');
		builder.append(declaracaoEntity(nomeClasse));
		builder.append('\n');
		builder.append(condicaoEntity());
		builder.append('\n');
		builder.append(copyPropertiesEntity());
		if (classes != null && !classes.isEmpty()) {
			for (Atributo atributo : classes) {
				builder.append("\n");
				builder.append(setClassesEntity(atributo.getTipo()));
			}
		}
		builder.append("\n");
		Formatacao formatacao;
		if (enume != null && !enume.isEmpty()) {
			for (Atributo atributo : enume) {
				formatacao = new Formatacao(atributo);
				builder.append("\n");
				builder.append(formatacao.toEntity());
			}
		}
		builder.append('\n');
		builder.append(fecharChaves());

		builder.append('\n');
		builder.append(returnAssembler("entity"));
		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');

		builder.append('\n');
		builder.append(listSMetodoEntities(nomeClasse));
		builder.append('\n');
		builder.append(listReturnEntities());

		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');

		builder.append('\n');
		builder.append(override());
		builder.append('\n');
		builder.append(toResource(nomeClasse));
		builder.append('\n');
		builder.append(declaracaoResource(nomeClasse));
		builder.append('\n');
		builder.append(condicaoResource());
		builder.append('\n');
		builder.append(copyPropertiesResource());
		if (classes != null && !classes.isEmpty()) {
			for (Atributo atributo : classes) {
				builder.append("\n");
				builder.append(setClassesResource(atributo.getTipo()));
			}
		}
		builder.append("\n");
		if (enume != null && !enume.isEmpty()) {
			for (Atributo atributo : enume) {
				formatacao = new Formatacao(atributo);
				builder.append("\n");
				builder.append(formatacao.toResource());
			}
		}
		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');
		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');

		builder.append('\n');
		builder.append(listSMetodoResources(nomeClasse));
		builder.append('\n');
		builder.append(resourcesResources());

		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');

		builder.append('\n');
		builder.append(fecharChaves());
		builder.append('\n');

		return builder.toString();
	}

	private String toResource(String nomeClasse) {
		return "public " + nomeClasse + "Resource toResource(" + nomeClasse + " entity) {";
	}

	private String component() {
		return "@Component";
	}

	private String autowired() {
		return "@Autowired";
	}

	private String publicClass() {
		return "public class " + nomeClasse + "ResourceAssembler implements ResourceAssembler<" + nomeClasse + ", "
				+ nomeClasse + "Resource> {";
	}

	private String override() {
		return "@Override";
	}

	private String classesResourceAssembler(String resourceAssembler) {
		return "private " + resourceAssembler + "ResourceAssembler " + StringUtils.uncapitalize(resourceAssembler)
				+ "ResourceAssembler;";
	}

	private String toEntity(String resource) {
		return "public " + resource + " toEntity(" + resource + "Resource resource) {";
	}

	private String declaracaoEntity(String value) {
		return declaracao(value, "entity");
	}

	private String declaracaoResource(String value) {
		return declaracao(value + "Resource", "resource");
	}

	private String declaracao(String value, String tipo) {
		return value + " " + tipo + " = null;";
	}

	private String condicao(String value) {
		return "if (" + value + " != null) {";
	}

	private String condicaoResource() {
		return condicao("entity");
	}

	private String condicaoEntity() {
		return condicao("resource");
	}

	private String copyPropertiesEntity() {
		return copyProperties("resource", "entity");
	}

	private String copyPropertiesResource() {
		return copyProperties("entity", "resource");
	}

	private String copyProperties(String x, String y) {
		return "copyProperties(" + x + ", " + y + ");";
	}

	// resource.setMunicipio(municipioResourceAssembler.toResource(entity.getMunicipio()));
	private String setClassesResource(String classe) {
		return setClasses("resource", "entity", classe);
	}

	private String setClassesEntity(String classe) {
		return setClasses("entity", "resource", classe);
	}

	private String setClasses(String type, String to, String classe) {
		return type + ".set" + StringUtils.capitalize(classe) + "(" + StringUtils.uncapitalize(classe)
				+ "ResourceAssembler.to" + StringUtils.capitalize(type) + "(" + StringUtils.uncapitalize(to) + ".get"
				+ classe + "()));";
	}

	private String fecharChaves() {
		return "}";
	}

	private String returnAssembler(String type) {
		return "return " + type + ";";
	}

	private String listSMetodoEntities(String classe) {
		return "public List<" + classe + "> toEntity(List<" + classe + "Resource> resources) {";
	}

	private String listSMetodoResources(String classe) {
		return " public List<" + classe + "Resource> toResources(List<" + classe + "> entities) {";
	}

	private String listSMetodo(String from, String to, String classes) {
		return "public List<" + classes + "> to" + to + "(List<" + classes + "Resource> " + from + ") {";
	}

	public String resourcesResources() {
		return "return entities != null ? StreamSupport.stream(entities.spliterator(), false).map(this::toResource).collect(toList()) : new ArrayList<>();";
	}

	public String listReturnEntities() {
		return "return resources != null ? StreamSupport.stream(resources.spliterator(), false).map(this::toEntity).collect(toList()) : new ArrayList<>();";
	}

	public String listReturn(String from, String to) {
		return "return " + to + " != null ? StreamSupport.stream(" + from + ".spliterator(), false).map(this::to" + to
				+ ").collect(toList()) : new ArrayList<>();";
	}

}
