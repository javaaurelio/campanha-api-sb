package med.voll.api.domain.voto.carnaval;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import med.voll.api.domain.pergunta.Pergunta;
import med.voll.api.domain.voto.DadosCadastroVotos;

@Table(name = "VotoCarnaval")
@Entity(name = "VotoCarnaval")
public class VotoCarnaval {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int voto;    
    private LocalDate dataVoto;
    private LocalTime horaVoto;
    private String hash;
    
	@ManyToOne()
    private Pergunta pergunta;  
    
    public VotoCarnaval() {
   	}
       
       public VotoCarnaval(DadosCadastroVotos dadosCadastroVoto) {
   		super();

   		this.pergunta = new Pergunta();
   		this.pergunta.setId(dadosCadastroVoto.codpesquisa());
   		this.voto = dadosCadastroVoto.voto();
   		this.dataVoto = LocalDate.now();
   		this.horaVoto = LocalTime.now();
       }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}

	public LocalDate getDataVoto() {
		return dataVoto;
	}

	public void setDataVoto(LocalDate dataVoto) {
		this.dataVoto = dataVoto;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public LocalTime getHoraVoto() {
		return horaVoto;
	}

	public void setHoraVoto(LocalTime horaVoto) {
		this.horaVoto = horaVoto;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
