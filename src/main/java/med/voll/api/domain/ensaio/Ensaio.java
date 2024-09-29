package med.voll.api.domain.ensaio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "ensaio")
@Entity(name = "ensaio")
public class Ensaio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String nome;

	private boolean ativo = false;
	private LocalDateTime dataHorasCadastro;
	private LocalDateTime dataHorasAtualizacao;
	private String cor;

	public Ensaio(DadosCadastroEnsaio cadastroEnsaio) {
		super();
		this.id = cadastroEnsaio.id();
		this.nome = cadastroEnsaio.nome();
		if (cadastroEnsaio.dataHorasCadastro() !=null) {
			this.dataHorasCadastro = LocalDateTime.parse(cadastroEnsaio.dataHorasCadastro(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}
		if (cadastroEnsaio.dataHorasAtualizacao()!=null) {
			this.dataHorasAtualizacao = LocalDateTime.parse(cadastroEnsaio.dataHorasAtualizacao(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}
	
		this.cor= cadastroEnsaio.cor();
		this.ativo = cadastroEnsaio.ativo();
	}

	public Ensaio() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataHorasCadastro() {
		return dataHorasCadastro;
	}

	public void setDataHorasCadastro(LocalDateTime dataHorasCadastro) {
		this.dataHorasCadastro = dataHorasCadastro;
	}

	public LocalDateTime getDataHorasAtualizacao() {
		return dataHorasAtualizacao;
	}

	public void setDataHorasAtualizacao(LocalDateTime dataHorasAtualizacao) {
		this.dataHorasAtualizacao = dataHorasAtualizacao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

}
