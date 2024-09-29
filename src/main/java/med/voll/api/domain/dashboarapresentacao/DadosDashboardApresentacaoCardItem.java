package med.voll.api.domain.dashboarapresentacao;

import java.time.format.DateTimeFormatter;

import med.voll.api.domain.apresentacao.Apresentacao;

public record DadosDashboardApresentacaoCardItem(
		Long idApresentacao, 
		String dataApresentacao, 
		String estado, 
		Long codEnsaio, 
		String ensaio, 
		Long codAgremiacao, 
		String agremiacao,
		String bandeiraBase64Imagem,
		double mediaVotos,
		String descricao
		) {
	
	public DadosDashboardApresentacaoCardItem(Apresentacao aprentacao, long qtdVotos, double mediaVotos, long qtdPerguntas, String bandeiraBase64Imagem) {
		this(aprentacao.getId(), 
				(aprentacao.getDataApresentacao() != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(aprentacao.getDataApresentacao())
				: "")
				, 
				aprentacao.getEstado(), 
				aprentacao.getEnsaio().getId(),
				aprentacao.getEnsaio().getNome(),
				aprentacao.getAgremiacao().getId(),
				aprentacao.getAgremiacao().getAgremiacao(),
				aprentacao.getAgremiacao().getBandeiraBase64Html(),
				mediaVotos,
				aprentacao.getAgremiacao().getAgremiacaoDescriacao()
				);
	}

}
