package med.voll.api.domain.voto;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.pesquisado.Pesquisado;
import med.voll.api.domain.voto.metadado.MetadadosVoto;

@Table(name = "voto")
@Entity(name = "voto")
public class Voto {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int voto;    
    private LocalDateTime dataVoto;
    
	@ManyToOne()
    private Pesquisa pesquisa;  
    
    @OneToOne(cascade = CascadeType.ALL)
    private Pesquisado pesquisado;
    
    @ManyToOne()
    private MetadadosVoto metadadosVoto;
    
    public Voto() {
   	}
       
       public Voto(DadosCadastroVotos dadosCadastroVoto) {
   		super();

   		this.pesquisa = new Pesquisa();
   		this.pesquisa.setId(dadosCadastroVoto.codpesquisa());
   		this.voto = dadosCadastroVoto.voto();
   		this.dataVoto = LocalDateTime.now();
       }

	public Pesquisa getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(Pesquisa pesquisa) {
		this.pesquisa = pesquisa;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public LocalDateTime getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(LocalDateTime dataVoto) {
		this.dataVoto = dataVoto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pesquisado getPesquisado() {
		return pesquisado;
	}

	public void setPesquisado(Pesquisado pesquisado) {
		this.pesquisado = pesquisado;
	}

	public MetadadosVoto getMetadadosVoto() {
		return metadadosVoto;
	}

	public void setMetadadosVoto(MetadadosVoto metadadosVoto) {
		this.metadadosVoto = metadadosVoto;
	}

}
