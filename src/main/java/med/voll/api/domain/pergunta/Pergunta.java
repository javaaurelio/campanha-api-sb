package med.voll.api.domain.pergunta;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.apresentacao.Apresentacao;
import med.voll.api.domain.evento.Evento;

@Table(name = "pergunta")
@Entity(name = "pergunta")
public class Pergunta {

	public Pergunta(DadosCadastroPergunta dadosCadastroPergunta) {
		super();
		this.pergunta  = dadosCadastroPergunta.pergunta();
		this.apresentacao = new Apresentacao();
		this.apresentacao.setId(dadosCadastroPergunta.idApresentacao());
		this.ordem = dadosCadastroPergunta.ordem();
		this.id = dadosCadastroPergunta.id();
	}
    
    public Pergunta() {
	}

    @ManyToOne()
    @NotNull
    private Apresentacao apresentacao;
    
    @NotNull
	private String pergunta;
    
    @NotNull
    private int ordem;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	public Apresentacao getApresentacao() {
		return apresentacao;
	}

	public void setApresentacao(Apresentacao apresentacao) {
		this.apresentacao = apresentacao;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
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

}
