package med.voll.api.domain.dashboardevento;

import med.voll.api.domain.evento.Evento;

public record DadosDashboardEventoPainelQtdVotos(String nomeEvento, Long quantidadeVotos, Integer aumento) {
	
	public DadosDashboardEventoPainelQtdVotos(Evento evento, Long quantidadeVotos, Integer aumento) {
		this(evento.getNome(), quantidadeVotos, aumento);
	}
}
