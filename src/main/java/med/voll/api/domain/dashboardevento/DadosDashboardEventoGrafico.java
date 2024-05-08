package med.voll.api.domain.dashboardevento;

import java.util.List;

public record DadosDashboardEventoGrafico(List<String> dataVoto,
		List<DadosDashboardEventoGraficoVotoPorData> dadosDashboardEventoGraficoVotoPorDatas) {
	
}
