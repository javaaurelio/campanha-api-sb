package med.voll.api.domain.evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.pesquisa.Pesquisa;

@Table(name = "evento")
@Entity(name = "evento")
public class Evento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    private String nome;
    private String descricao;

    @NotNull
    private String imagemUrl;
	@NotNull
    private LocalDate dataInicio;
	
	private boolean publicado=false;
	private LocalDate dataHorasPublicacao;
	private LocalDate dataHorasPublicacaoSuspensao;
	private String hash;
	private String urlPublicacao;
	private String layoutPainelVotacao;
	
	@NotNull
    private LocalDate dataFim;
	
    @OneToMany(mappedBy = "evento")
	private List<Pesquisa> listaPesquisa = new ArrayList<Pesquisa>();

    public Evento(DadosCadastroEvento cadastroEvento) {
		super();
		this.id = cadastroEvento.id();
		this.nome = cadastroEvento.campanha();
		this.descricao = cadastroEvento.descricao();
		this.imagemUrl = cadastroEvento.imagemUrl();
		this.dataInicio = LocalDate.parse(cadastroEvento.dataInicio(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.dataFim = LocalDate.parse(cadastroEvento.dataFim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.layoutPainelVotacao = cadastroEvento.layoutPainelVotacao();
	}
    
    public Evento() {
		// TODO Auto-generated constructor stub
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getImagemUrl() {
		return imagemUrl;
	}
	public void setImagemUrl(String imagemUrl) {
		this.imagemUrl = imagemUrl;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public List<Pesquisa> getListaPesquisa() {
		return listaPesquisa;
	}

	public void setListaPesquisa(List<Pesquisa> listaPesquisa) {
		this.listaPesquisa = listaPesquisa;
	}

	public boolean isPublicado() {
		return publicado;
	}

	public void setPublicado(boolean publicado) {
		this.publicado = publicado;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalDate getDataHorasPublicacao() {
		return dataHorasPublicacao;
	}

	public void setDataHorasPublicacao(LocalDate dataHorasPublicacao) {
		this.dataHorasPublicacao = dataHorasPublicacao;
	}

	public LocalDate getDataHorasPublicacaoSuspensao() {
		return dataHorasPublicacaoSuspensao;
	}

	public void setDataHorasPublicacaoSuspensao(LocalDate dataHorasPublicacaoSuspensao) {
		this.dataHorasPublicacaoSuspensao = dataHorasPublicacaoSuspensao;
	}

	public String getUrlPublicacao() {
		return urlPublicacao;
	}

	public void setUrlPublicacao(String urlPublicacao) {
		this.urlPublicacao = urlPublicacao;
	}

	public String getLayoutPainelVotacao() {
		return layoutPainelVotacao;
	}

	public void setLayoutPainelVotacao(String layoutPainelVotacao) {
		this.layoutPainelVotacao = layoutPainelVotacao;
	}
}
