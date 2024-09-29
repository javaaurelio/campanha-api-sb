package med.voll.api.domain.dashboarapresentacao;

import med.voll.api.domain.apresentacao.Apresentacao;

public record DadosDashboardApresentacaoPainelQtdVotos(String nomeEvento, Long quantidadeVotos, Integer aumento) {
	
	public DadosDashboardApresentacaoPainelQtdVotos(Apresentacao apresentacao, Long quantidadeVotos, Integer aumento) {
		this(apresentacao.getEnsaio().getNome(), quantidadeVotos, aumento);
	}
}
