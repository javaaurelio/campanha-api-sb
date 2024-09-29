package med.voll.api.service.dashboardapresentacao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.apresentacao.Apresentacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoCardItem;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotos;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotosRating;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotosRatingDto;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoListaCard;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoPainelQtdVotos;
import med.voll.api.domain.pergunta.PerguntaRepository;
import med.voll.api.domain.voto.DadosGraficoRadar;
import med.voll.api.domain.voto.carnaval.VotoCarnaval;
import med.voll.api.domain.voto.carnaval.VotoCarnavalRepository;
import med.voll.api.domain.voto.metadado.DadosMetadadosVotoGraficoBarra;

@Service
public class DashboardApresentacaoService {

	@Autowired
	private ApresentacaoRepository apresentacaoRepository;
	
	@Autowired
	private VotoCarnavalRepository carnavalVotoRepository;

	public DadosDashboardApresentacaoListaCard obterApresentacaoListaCards() {
		
		List<Apresentacao> all = apresentacaoRepository.findAll();		
		
		List<DadosDashboardApresentacaoCardItem> collect = 
				all.stream().map(item -> 
				new DadosDashboardApresentacaoCardItem(
						item, 
						0, 
						obterMediaVotacao(item.getId())
						, 0, item.getAgremiacao().getBandeiraBase64Html())).collect(Collectors.toList());
		
		DadosDashboardApresentacaoListaCard apresentacaoListaCard 
			= new DadosDashboardApresentacaoListaCard(collect, collect.size()); 
		
		return apresentacaoListaCard;
	}
	
	public DadosDashboardApresentacaoPainelQtdVotos qtdTotalVotos(Long idApresentacao) {
		
		Apresentacao referenceById = apresentacaoRepository.getReferenceById(idApresentacao);
		long count = carnavalVotoRepository.totalVotosPorApresentacao(idApresentacao);
		
		DadosDashboardApresentacaoPainelQtdVotos dadosDashboardApresentacaoPainelQtdVotos 
			= new DadosDashboardApresentacaoPainelQtdVotos(referenceById, count, 0);
		
		return dadosDashboardApresentacaoPainelQtdVotos;
	}
	
	public DadosDashboardApresentacaoPainelQtdVotos qtdTotalVotosHoje(Long idApresentacao) {
		
		Apresentacao referenceById = apresentacaoRepository.getReferenceById(idApresentacao);
		long count = carnavalVotoRepository.totalVotosPorApresentacaoEDataRegistro(idApresentacao, LocalDate.now());
		
		DadosDashboardApresentacaoPainelQtdVotos dadosDashboardApresentacaoPainelQtdVotos 
			= new DadosDashboardApresentacaoPainelQtdVotos(referenceById, count, 0);
		
		return dadosDashboardApresentacaoPainelQtdVotos;
	}
	
	public DadosDashboardApresentacaoPainelQtdVotos qtdTotalVotosOntem(Long idApresentacao) {
		
		Apresentacao referenceById = apresentacaoRepository.getReferenceById(idApresentacao);
		long count = carnavalVotoRepository.totalVotosPorApresentacaoEDataRegistro(idApresentacao, LocalDate.now().plusDays(-1));
		
		DadosDashboardApresentacaoPainelQtdVotos dadosDashboardApresentacaoPainelQtdVotos 
		= new DadosDashboardApresentacaoPainelQtdVotos(referenceById, count, 0);
		
		return dadosDashboardApresentacaoPainelQtdVotos;
	}
	
	public DadosDashboardApresentacaoPainelQtdVotos qtdTotalVotosUltimos7Dias(Long idApresentacao) {
		
		Apresentacao referenceById = apresentacaoRepository.getReferenceById(idApresentacao);
		long count = carnavalVotoRepository.totalVotosPorApresentacaoEDataRegistro(idApresentacao, LocalDate.now().plusDays(-7));
		
		DadosDashboardApresentacaoPainelQtdVotos dadosDashboardApresentacaoPainelQtdVotos 
		= new DadosDashboardApresentacaoPainelQtdVotos(referenceById, count, 0);
		
		return dadosDashboardApresentacaoPainelQtdVotos;
	}
	
	
	public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia(Long idApresentacao) {
		
		List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = 
					carnavalVotoRepository.obterDadosGraficoBarraDia(idApresentacao);
		return obterDadosGraficoBarraDia;
	}
	
	public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraMes(Long idApresentacao) {
		
		List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = 
				carnavalVotoRepository.obterDadosGraficoBarraMes(idApresentacao);
		return obterDadosGraficoBarraDia;
	}
	
	public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraAno(Long idApresentacao) {
		
		List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = carnavalVotoRepository.obterDadosGraficoBarraAno(idApresentacao);
		return obterDadosGraficoBarraDia;
	}
	
	public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraHora24h(Long idApresentacao) {
		
		List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = 
				carnavalVotoRepository.obterDadosGraficoBarraHora24h(idApresentacao);
		return obterDadosGraficoBarraDia;
	}
	
	public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraHora24hData(Long idApresentacao, String dataSelecionada) {
		
        LocalDate date = LocalDate.parse(dataSelecionada, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
        List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = 
        		carnavalVotoRepository.obterDadosGraficoBarraHora24hData(idApresentacao, date);
		
		return obterDadosGraficoBarraDia;
	}

	public List<DadosGraficoRadar> obterDadosGraficoRadar(Long idApresentacao) {

		List<DadosGraficoRadar> dadosGraficoRadar = carnavalVotoRepository.findDadosGraficoRadar(idApresentacao);
		return dadosGraficoRadar;
	}

	public List<DadosDashboardApresentacaoGraficoQtdVotosRatingDto> obterGraficosBarraRating(Long idApresentacao) {
		
		HashMap<String, DadosDashboardApresentacaoGraficoQtdVotosRatingDto> mapQualificador =  new HashMap<String, DadosDashboardApresentacaoGraficoQtdVotosRatingDto>();
		mapQualificador.put("1", new DadosDashboardApresentacaoGraficoQtdVotosRatingDto("Ruim", 0l));
		mapQualificador.put("2", new DadosDashboardApresentacaoGraficoQtdVotosRatingDto("Melhorar", 0l));
		mapQualificador.put("3", new DadosDashboardApresentacaoGraficoQtdVotosRatingDto("Na media", 0l));
		mapQualificador.put("4", new DadosDashboardApresentacaoGraficoQtdVotosRatingDto("Bom", 0l));
		mapQualificador.put("5", new DadosDashboardApresentacaoGraficoQtdVotosRatingDto("Muito Bom", 0l));
		
		List<DadosDashboardApresentacaoGraficoQtdVotosRating> lista = carnavalVotoRepository.obterDadosGraficoRating(idApresentacao);
		
		for (DadosDashboardApresentacaoGraficoQtdVotosRating dadosDashboardApresentacaoGraficoQtdVotosRating : lista) {
			mapQualificador.get(dadosDashboardApresentacaoGraficoQtdVotosRating.getRating()).setQtdVotos(
					dadosDashboardApresentacaoGraficoQtdVotosRating.getQtdVotos());
		}
		
		return  mapQualificador.values().stream().collect(Collectors.toCollection(ArrayList::new));
	}

	public double obterMediaVotacao(Long idApresentacao) {
		
//		With 120 total responses, the calculation becomes:
//
//			Score total: 50(5) + 25(4) + 20(3) + 15(2) + 10(1) = 450
//
//			Response total: 50 + 25 + 20 + 15 + 10 = 120
//
//			5-star score: 450 / 120 = 3.75, which rounds to 3.8
		
		
		List<DadosDashboardApresentacaoGraficoQtdVotosRating> lista = carnavalVotoRepository.obterDadosGraficoRating(idApresentacao);	
		
		float scoreTotal = 0;
		float responseTotal = 0;
		for (DadosDashboardApresentacaoGraficoQtdVotosRating dadosDashboardApresentacaoGraficoQtdVotosRating : lista) {
			Integer rating = Integer.valueOf(dadosDashboardApresentacaoGraficoQtdVotosRating.getRating());
			Long votoSoma = Long.valueOf(dadosDashboardApresentacaoGraficoQtdVotosRating.getQtdVotos());
			scoreTotal += (rating * votoSoma);
			responseTotal += votoSoma;
		}
//		long responseTotal = carnavalVotoRepository.somaVotosByIdApresentacao(idApresentacao);
		
		double mediaFinal = 0.0;
		if (scoreTotal > 0 && responseTotal > 0) {
			float mediaVotos =  scoreTotal / responseTotal;
			BigDecimal bd = new BigDecimal(mediaVotos).setScale(1, RoundingMode.HALF_EVEN);
			mediaFinal = bd.doubleValue();
		} 
		
		return mediaFinal;
		
	}
	
}
