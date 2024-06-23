package med.voll.api.domain.dashboardevento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import med.voll.api.domain.evento.Evento;

public record DadosDashboardEventoGeralGraficoItem(Long idEvento, String nomeEvento, String dataInicio, String dataFim, 
		boolean publicado, long vidaUtil, long qtdVotos, float mediaVotos) {
	
	public DadosDashboardEventoGeralGraficoItem(Evento evento, long qtdVotos, float mediaVotos) {
		this(evento.getId(),
				evento.getNome(), 
				evento.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
				evento.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
				evento.isPublicado(),
				Math.abs(
        				Math.abs(
        						ChronoUnit.DAYS.between(evento.getDataInicio(), LocalDate.now())) * 100 / 
        				Math.abs(
        						ChronoUnit.DAYS.between(evento.getDataInicio(), evento.getDataFim()))),
				qtdVotos, mediaVotos);
	}

}
