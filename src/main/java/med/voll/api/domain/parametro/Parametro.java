package med.voll.api.domain.parametro;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Table(name = "parametro")
@Entity(name = "parametro")
public class Parametro {

	public Parametro(@NotNull String valor, @NotNull String nome) {
		super();
		this.valor = valor;
		this.nome = nome;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String valor;
	
	@NotNull
	private String nome;
	
	

	public Long getId() {
		return id;
	}

	public String getValor() {
		return valor;
	}

	public String getNome() {
		return nome;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
