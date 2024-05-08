package med.voll.api.domain.pesquisa;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.evento.Evento;

@Table(name = "pesquisa")
@Entity(name = "pesquisa")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pesquisa {

	public Pesquisa(DadosCadastroPesquisa dadosCadastroPesquisa) {
		super();
		this.pesquisa = dadosCadastroPesquisa.pesquisa();
		this.evento = new Evento();
		this.evento.setId(dadosCadastroPesquisa.idEvento());
		this.ordem = dadosCadastroPesquisa.ordem();
		this.id = dadosCadastroPesquisa.id();
	}
    
    public Pesquisa() {
	}

    @ManyToOne()
    @NotNull
    private Evento evento;
    
    @NotNull
	private String pesquisa;
    
    @NotNull
    private int ordem;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
