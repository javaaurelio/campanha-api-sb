package med.voll.api.domain.voto.metadado;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.voto.DadosCadastroVotoCoordenadas;
import med.voll.api.domain.voto.Voto;

@Table(name = "metadadosvoto")
@Entity(name = "metadadosvoto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MetadadosVoto {

	public MetadadosVoto(String headerHttp, String latitude, String longitude, LocalDateTime datacriacao) {
		super();
		this.headerHttp = headerHttp;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public MetadadosVoto(DadosCadastroVotoCoordenadas coordenadas) {
		super();
		this.latitude = coordenadas.latitude();
		this.longitude = coordenadas.longitude();
		this.dataCriacao = LocalDateTime.now();
		this.headerHttp = coordenadas.descricaoHttp();
	}
	
	public MetadadosVoto() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
    private Evento evento;
	
    @OneToMany(mappedBy = "metadadosVoto")
    private List<Voto> listaVotos;

	@Column(length = 10000)
	private String headerHttp;
	private String latitude;
	private String longitude;
	private LocalDateTime dataCriacao;
	private String dataAberturaTela;
	private LocalDateTime dataRegistroVoto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHeaderHttp() {
		return headerHttp;
	}
	public void setHeaderHttp(String headerHttp) {
		this.headerHttp = headerHttp;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	public String getDataAberturaTela() {
		return dataAberturaTela;
	}
	public void setDataAberturaTela(String dataAberturaTela) {
		this.dataAberturaTela = dataAberturaTela;
	}
	public LocalDateTime getDataRegistroVoto() {
		return dataRegistroVoto;
	}
	public void setDataRegistroVoto(LocalDateTime dataRegistroVoto) {
		this.dataRegistroVoto = dataRegistroVoto;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public List<Voto> getListaVotos() {
		return listaVotos;
	}
	public void setListaVotos(List<Voto> listaVotos) {
		this.listaVotos = listaVotos;
	}
	
	

}
