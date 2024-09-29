package med.voll.api.domain.apresentacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.agremiacao.Agremiacao;
import med.voll.api.domain.ensaio.Ensaio;
import med.voll.api.domain.pergunta.Pergunta;

@Table(name = "apresentacao")
@Entity(name = "apresentacao")
public class Apresentacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private LocalDateTime dataHoraCadastro;
	private LocalDate dataApresentacao;
	private String estado;

	@ManyToOne()
    @NotNull
	private Ensaio ensaio;

	@ManyToOne()
    @NotNull
	private Agremiacao agremiacao;
	
    @OneToMany(mappedBy = "pergunta")
	private List<Pergunta> listaPerguntas = new ArrayList<Pergunta>();

	public Apresentacao(DadosCadastroApresentacao cadastroApresentacao) {
		super();
		this.id = cadastroApresentacao.id();
		if (cadastroApresentacao.dataApresentacao() !=null) {
			this.dataApresentacao = LocalDate.parse(cadastroApresentacao.dataApresentacao(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		
		this.estado = cadastroApresentacao.estado();
		this.ensaio = new Ensaio();
		this.ensaio.setId(cadastroApresentacao.codEnsaio());
		this.agremiacao = new Agremiacao();
		this.agremiacao.setId(cadastroApresentacao.codAgremiacao());
		
		
	}

	public Apresentacao() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Ensaio getEnsaio() {
		return ensaio;
	}

	public void setEnsaio(Ensaio ensaio) {
		this.ensaio = ensaio;
	}

	public Agremiacao getAgremiacao() {
		return agremiacao;
	}

	public void setAgremiacao(Agremiacao agremiacao) {
		this.agremiacao = agremiacao;
	}

	public LocalDateTime getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(LocalDateTime dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public LocalDate getDataApresentacao() {
		return dataApresentacao;
	}

	public void setDataApresentacao(LocalDate dataApresentacao) {
		this.dataApresentacao = dataApresentacao;
	}

	public List<Pergunta> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}
}
