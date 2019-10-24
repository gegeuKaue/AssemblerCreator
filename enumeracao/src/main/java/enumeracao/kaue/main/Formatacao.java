package enumeracao.kaue.main;

import static org.apache.commons.lang3.StringUtils.capitalize;

import org.apache.commons.lang3.StringUtils;

/**
 * @author geovane.santos
 *
 */
/**
 * @author geovane.santos
 *
 */
public class Formatacao {
	private Atributo atributo;

	public Formatacao(Atributo atributo) {
		this.atributo = atributo;
	}

	public Atributo getAtributo() {
		return atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

	public String toEntity() {
		String from = "resource";
		String to = "entity";
		return condicao(from) + "\n" + colocandoEnum(to, from) + "\n" + fechandoChave();
	}

	public String toResource() {
		String from = "entity";
		String to = "resource";
		return condicao(from) + "\nresource.set" + capitalize(atributo.getNome()) + "(entity.get"
				+ capitalize(atributo.getNome()) + "().name());\n" + fechandoChave();
	}

	private String condicao(String from) {
		return " if (" + from + ".get" + StringUtils.capitalize(atributo.getNome()) + "() != null) {";
	}

	private String colocandoEnum(String to, String from) {
		String valor = to.equals("resource") ? ".name()" : "";
		return "\t" + to + ".set" + capitalize(atributo.getNome()) + "(" + atributo.getTipo() + ".valueOf(" + from
				+ ".get" + capitalize(atributo.getNome()) + "())" + valor + ");";
	}

	private String fechandoChave() {
		return "}";
	}

}
