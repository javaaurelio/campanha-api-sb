package med.voll.api.service.dashboardevento;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.dashboardevento.DadosDashboardEventoGrafico;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoGraficoQtdVotos;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoGraficoVotoPorData;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelAtividadesOnlineVoto;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelQtdVotos;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.pesquisado.Pesquisado;
import med.voll.api.domain.voto.Voto;
import med.voll.api.domain.voto.VotoRepository;
import med.voll.api.domain.voto.metadado.MetadadosVoto;
import med.voll.api.domain.voto.metadado.MetadadosVotoRepository;

@Service
public class DashboardEventoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private MetadadosVotoRepository metadadosVotoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;

	public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnline(Long idEvento) {
		
		LocalDateTime data = LocalDateTime.now();
		LocalDateTime dataInicio = data.with(LocalTime.MIN);
		LocalDateTime dataFim = data.with(LocalTime.MAX);
		
		List<Voto> allByPesquisaEventoIdAndDataVoto = 
				votoRepository.findAllByPesquisaEventoIdAndDataVotoBetweenOrderByDataVotoDesc(idEvento, dataInicio, dataFim);
		
		List<DadosDashboardEventoPainelAtividadesOnlineVoto> listaAtividade =  new ArrayList<DadosDashboardEventoPainelAtividadesOnlineVoto>();
		
		for (Voto itemVoto : allByPesquisaEventoIdAndDataVoto) {
			itemVoto.getMetadadosVoto().getListaVotos().size();
			LocalDateTime dataAtual = LocalDateTime.now();
			LocalDateTime dataVoto = itemVoto.getDataVoto();
			Duration duracao = Duration.between(dataVoto, dataAtual);
			StringBuffer formatDuration = new StringBuffer();
			StringBuffer formatDurationEspecifico = new StringBuffer();
			
			formatDurationEspecifico.append(
					DurationFormatUtils.formatDuration(duracao.toMillis(), "H 'horas' m 'minutos' s 'segundos' S 'millisegundos'"));
			
			if (duracao.toHours()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " H 'horas'"));
			} else
			if (duracao.toMinutes()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " m 'minutos'"));
			} else
			if (duracao.toSeconds()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " s 'segundos'"));
			} else
			if (duracao.toMillis()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " S 'millisegundos'"));
			}
			
			
			if (itemVoto.getPesquisado() == null) {
				itemVoto.setPesquisado(new Pesquisado());
			}
			
			listaAtividade.add(
					new DadosDashboardEventoPainelAtividadesOnlineVoto(itemVoto.getId(), itemVoto.getVoto(), 
							itemVoto.getPesquisa().getPesquisa(), itemVoto.getPesquisado().getNome(), itemVoto.getPesquisado().getCidade(), itemVoto.getPesquisado().getSexo(), formatDuration.toString(), 
							formatDurationEspecifico.toString(), 0));
		}
		// TEM QUE FAZER QUERY NATIVA
		
		return listaAtividade.subList(0, (listaAtividade.size()>10?10:listaAtividade.size()));
	}
	
	public DadosDashboardEventoGrafico obterDadosGrafico(Long idEvento) {
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dataInicio = now.plusDays(-7l);
		dataInicio = dataInicio.with(LocalTime.MIN);
		
		LocalDateTime dataFim = now.with(LocalTime.MAX);;
		
		List<Voto> allByPesquisaEventoIdAndDataVoto = votoRepository.findAllByPesquisaEventoIdAndDataVotoBetween(
				idEvento, dataInicio, dataFim);
		
		Map<String, Map<String, Long>> seriesQualificadoComQtdVotos = new HashMap<String, Map<String,Long>>();
		
		List<String> categoriesListaDatas = new ArrayList<String>();
		for (Voto voto : allByPesquisaEventoIdAndDataVoto) {
			LocalDateTime date = voto.getDataVoto();
		    String data = date.format(DateTimeFormatter.ISO_DATE);
		    if (!categoriesListaDatas.contains(data)) {
		    	categoriesListaDatas.add(data);
		    }
		    
		    Map<String, Long> votosDoQualificados = seriesQualificadoComQtdVotos.get(""+voto.getVoto());
		    if (votosDoQualificados == null) {
		    	votosDoQualificados = new HashMap<String,Long>();
		    	votosDoQualificados.put(data, 1l);
		    	seriesQualificadoComQtdVotos.put(""+voto.getVoto(), votosDoQualificados);
		    } else {
		    	Long qtdVotos = votosDoQualificados.get(data);
		    	if (qtdVotos == null) {
		    		qtdVotos = 0l;
		    	}
		    	votosDoQualificados.put(data, qtdVotos+1);
		    	
		    }
		}
		
		HashMap<String, String> mapQualificador =  new HashMap<String, String>();
		mapQualificador.put("1", "Muito Ruim");
		mapQualificador.put("2", "Ruim");
		mapQualificador.put("3", "Medio");
		mapQualificador.put("4", "Bom");
		mapQualificador.put("5", "Otimo");
		
		List<DadosDashboardEventoGraficoVotoPorData> lista =  new ArrayList<DadosDashboardEventoGraficoVotoPorData>();
		for (String idQualificador : seriesQualificadoComQtdVotos.keySet()) {

			Map<String, Long> map = seriesQualificadoComQtdVotos.get(idQualificador);
			
			List<DadosDashboardEventoGraficoQtdVotos> listaVotos = new ArrayList<DadosDashboardEventoGraficoQtdVotos>();
			
			for (String itemData : categoriesListaDatas) {
				Long qtd = map.get(itemData);
				listaVotos.add(new DadosDashboardEventoGraficoQtdVotos(itemData , qtd));
			}
			
				
			lista.add(new DadosDashboardEventoGraficoVotoPorData(mapQualificador.get(idQualificador), listaVotos));
			
		}
		
		
		DadosDashboardEventoGrafico dadosDashboardEventoGrafico 
			= new DadosDashboardEventoGrafico(categoriesListaDatas, lista);
		
		// TEM QUE FAZER QUERY NATIVA
		return dadosDashboardEventoGrafico;
	}
	
	public DadosDashboardEventoPainelQtdVotos qtdTotalVotos(Long idEvento) {
		
		Evento evento = eventoRepository.getReferenceById(idEvento);
		List<Voto> allByPesquisaEventoIdAndDataVoto = votoRepository.findAllByPesquisaEventoId(idEvento);
		DadosDashboardEventoPainelQtdVotos dadosDashboardEventoPainelQtdVotos 
			= new DadosDashboardEventoPainelQtdVotos(evento, Long.valueOf(allByPesquisaEventoIdAndDataVoto.size()), 0);
		// TEM QUE FAZER QUERY NATIVA
		
		return dadosDashboardEventoPainelQtdVotos;
	}

	public DadosDashboardEventoPainelQtdVotos qtdVotosHoje(Long idEvento) {
		
		LocalDateTime data = LocalDateTime.now();
		LocalDateTime dataInicio = data.with(LocalTime.MIN);
		LocalDateTime dataFim = data.with(LocalTime.MAX);		
		return obterQuantidadeVotos(idEvento, dataInicio, dataFim);
	}
	
	public DadosDashboardEventoPainelQtdVotos qtdVotosOntem(Long idEvento) {
		
		LocalDateTime data = LocalDateTime.now().plusDays(-1l);
		LocalDateTime dataInicio = data.with(LocalTime.MIN);
		LocalDateTime dataFim = data.with(LocalTime.MAX);
		return obterQuantidadeVotos(idEvento, dataInicio, dataFim);
	}
	
	public DadosDashboardEventoPainelQtdVotos qtdVotosUltimos7Dias(Long idEvento) {
		
		LocalDateTime now = LocalDateTime.now();
//		TemporalField dayOfWeek = WeekFields.ISO.dayOfWeek();		
//		LocalDateTime dataInicio = now.with(dayOfWeek, dayOfWeek.range().getMinimum());
//		dataInicio = dataInicio.with(LocalTime.MIN);
//		
//		LocalDateTime dataFim = now.with(dayOfWeek, dayOfWeek.range().getMaximum());
//		dataFim = dataFim.with(LocalTime.MAX);

		LocalDateTime dataInicio = now.plusDays(-7l);
		dataInicio = dataInicio.with(LocalTime.MIN);
		LocalDateTime dataFim = now.with(LocalTime.MAX);;
		
		return obterQuantidadeVotos(idEvento, dataInicio, dataFim);
	}
	
	private DadosDashboardEventoPainelQtdVotos obterQuantidadeVotos(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim) {
		
		Evento evento = eventoRepository.getReferenceById(idEvento);
		List<Voto> allByPesquisaEventoIdAndDataVoto = votoRepository.findAllByPesquisaEventoIdAndDataVotoBetween(idEvento, dataInicio, dataFim);
		
		DadosDashboardEventoPainelQtdVotos dadosDashboardEventoPainelQtdVotos 
		    = new DadosDashboardEventoPainelQtdVotos(evento, Long.valueOf(allByPesquisaEventoIdAndDataVoto.size()), 0);
		// TEM QUE FAZER QUERY NATIVA
		return dadosDashboardEventoPainelQtdVotos;
	}
	
	public double calculatePercentage(double obtained, double total) {
        return obtained * 100 / total;
    }

	
	
	public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnlineMedia(Long idEvento) {
		LocalDateTime data = LocalDateTime.now();
		LocalDateTime dataInicio = data.with(LocalTime.MIN);
		LocalDateTime dataFim = data.with(LocalTime.MAX);
		
		List<MetadadosVoto> allByPesquisaEventoIdAndDataVoto = 
				metadadosVotoRepository.findAllByEventoIdAndDataRegistroVotoBetweenOrderByDataRegistroVotoDesc(idEvento, dataInicio, dataFim);
		
		List<DadosDashboardEventoPainelAtividadesOnlineVoto> listaAtividade =  new ArrayList<DadosDashboardEventoPainelAtividadesOnlineVoto>();

		for (MetadadosVoto itemMetadadosVoto : allByPesquisaEventoIdAndDataVoto) {
			int totalVotos = 0;
			int qtdPerguntas = 0;
					
			LocalDateTime dataAtual = LocalDateTime.now();
			LocalDateTime dataVoto = itemMetadadosVoto.getDataRegistroVoto();
			Duration duracao = Duration.between(dataVoto, dataAtual);
			
			StringBuffer formatDuration = new StringBuffer();
			StringBuffer formatDurationEspecifico = new StringBuffer();
			
			formatDurationEspecifico.append(
					DurationFormatUtils.formatDuration(duracao.toMillis(), "H 'horas' m 'minutos' s 'segundos' S 'millisegundos'"));
			
			if (duracao.toHours()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " H 'horas'"));
			} else
			if (duracao.toMinutes()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " m 'minutos'"));
			} else
			if (duracao.toSeconds()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " s 'segundos'"));
			} else
			if (duracao.toMillis()>0) {
				formatDuration.append(DurationFormatUtils.formatDuration(duracao.toMillis(), " S 'millisegundos'"));
			}
			
			Pesquisado pessoa = new Pesquisado();
			if (!itemMetadadosVoto.getListaVotos().isEmpty()) {
				pessoa = itemMetadadosVoto.getListaVotos().get(0).getPesquisado();
				if (pessoa == null) {
					pessoa = new Pesquisado();
				}
			}
			
			totalVotos = 0;
			qtdPerguntas =  itemMetadadosVoto.getListaVotos().size();
			for (Voto itemVoto : itemMetadadosVoto.getListaVotos()) {
				totalVotos += itemVoto.getVoto();			
			} 

			int MaximoVoto = 5;
			int mediaVotos =  totalVotos * MaximoVoto / (qtdPerguntas*MaximoVoto);
			
			listaAtividade.add(
					new DadosDashboardEventoPainelAtividadesOnlineVoto(null, null, 
							"", pessoa.getNome(), 
							pessoa.getCidade(), pessoa.getSexo(), formatDuration.toString(), formatDurationEspecifico.toString(), mediaVotos));
			
			
		}
		
		// TEM QUE FAZER QUERY NATIVA
		return listaAtividade.subList(0, (listaAtividade.size()>10?10:listaAtividade.size()));
	}

}
