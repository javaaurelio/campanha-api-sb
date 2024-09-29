package med.voll.api.domain.agremiacao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Table(name = "agremiacao")
@Entity(name = "agremiacao")
public class Agremiacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String agremiacao;
	private String agremiacaoDescriacao;
	private boolean ativo = false;
	private LocalDateTime dataHorasCadastro;
	private LocalDateTime dataHorasAtualizacao;
	@Lob
	private byte[] bandeira;

	public Agremiacao(DadosCadastroAgremiacao cadastroAgremiacao) {
		super();
		this.id = cadastroAgremiacao.id();
		this.agremiacao = cadastroAgremiacao.agremiacao();
		if (cadastroAgremiacao.dataHorasCadastro() != null) {
			this.dataHorasCadastro = LocalDateTime.parse(cadastroAgremiacao.dataHorasCadastro(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}
		if (cadastroAgremiacao.dataHorasAtualizacao() != null) {
			this.dataHorasAtualizacao = LocalDateTime.parse(cadastroAgremiacao.dataHorasAtualizacao(),
					DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		}

		this.bandeira = cadastroAgremiacao.bandeiraBase64Imagem().getBytes();
		this.ativo = cadastroAgremiacao.ativo();
		this.agremiacaoDescriacao = cadastroAgremiacao.agremiacaoDescricao();
	}

	public Agremiacao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAgremiacao() {
		return agremiacao;
	}

	public void setAgremiacao(String agremiacao) {
		this.agremiacao = agremiacao;
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

	public byte[] getBandeira() {
		return bandeira;
	}

	public String getBandeiraBase64Html() {
		if (bandeira != null) {
			return new String(bandeira);
		}
		return "";
	}

	public void setBandeira(byte[] bandeira) {
		this.bandeira = bandeira;
	}

	public String getAgremiacaoDescriacao() {
		return agremiacaoDescriacao;
	}

	public void setAgremiacaoDescriacao(String agremiacaoDescriacao) {
		this.agremiacaoDescriacao = agremiacaoDescriacao;
	}

}
