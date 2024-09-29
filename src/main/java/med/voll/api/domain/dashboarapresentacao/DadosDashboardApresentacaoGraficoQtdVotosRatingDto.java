package med.voll.api.domain.dashboarapresentacao;

public class DadosDashboardApresentacaoGraficoQtdVotosRatingDto {

	public DadosDashboardApresentacaoGraficoQtdVotosRatingDto(String rating, Long qtdVotos) {
		super();
		this.qtdVotos = qtdVotos;
		this.rating = rating;
	}

	private Long qtdVotos;
	private String rating;

	public Long getQtdVotos() {
		return qtdVotos;
	}

	public String getRating() {
		return rating;
	}

	public void setQtdVotos(Long qtdVotos) {
		this.qtdVotos = qtdVotos;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
