package med.voll.api.domain.evento;

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
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.voto.metadado.MetadadosVoto;

@Table(name = "evento")
@Entity(name = "evento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Evento {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    private String nome;
    private String descricao;

    @NotNull
    private String imagemUrl;
	@NotNull
    private String dataInicio;
	@NotNull
    private String dataFim;
	
    @OneToMany(mappedBy = "evento")
	private List<Pesquisa> listaPesquisa = new ArrayList<Pesquisa>();

    public Evento(DadosCadastroEvento cadastroEvento) {
		super();
		this.id = cadastroEvento.id();
		this.nome = cadastroEvento.campanha();
		this.descricao = cadastroEvento.descricao();
		this.imagemUrl = cadastroEvento.imagemUrl();
		this.dataInicio = cadastroEvento.dataInicio();
		this.dataFim = cadastroEvento.dataFim();
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

	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getDataFim() {
		return dataFim;
	}

	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public List<Pesquisa> getListaPesquisa() {
		return listaPesquisa;
	}

	public void setListaPesquisa(List<Pesquisa> listaPesquisa) {
		this.listaPesquisa = listaPesquisa;
	}
}
