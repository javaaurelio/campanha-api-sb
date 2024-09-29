package med.voll.api.service.voto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import med.voll.api.domain.dashboardevento.DadosDashboardEventoGrafico;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoGraficoQtdVotos;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoGraficoVotoPorData;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelAtividadesOnlineVoto;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelQtdVotos;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.pesquisa.Pesquisa;
import med.voll.api.domain.pesquisa.PesquisaRepository;
import med.voll.api.domain.pesquisado.Pesquisado;
import med.voll.api.domain.voto.DadosCadastroDadosVoto;
import med.voll.api.domain.voto.DadosCadastroVotos;
import med.voll.api.domain.voto.Voto;
import med.voll.api.domain.voto.VotoRepository;
import med.voll.api.domain.voto.metadado.MetadadosVoto;
import med.voll.api.domain.voto.metadado.MetadadosVotoRepository;

@Service
public class VotoService {

	@Autowired
	private VotoRepository votoRepository;
	
	@Autowired
	private MetadadosVotoRepository metadadosVotoRepository;
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private PesquisaRepository pesquisaRepository;

	public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnline(Long idEvento) {
		
		LocalDateTime data = LocalDateTime.now();
		LocalDateTime dataInicio = data.with(LocalTime.MIN);
		LocalDateTime dataFim = data.with(LocalTime.MAX);
		
		List<Voto> allByPesquisaEventoIdAndDataVoto = 
				votoRepository.findAllByPesquisaEventoIdAndDataVotoBetweenOrderByDataVotoDesc(idEvento, dataInicio, dataFim);
		
		List<DadosDashboardEventoPainelAtividadesOnlineVoto> listaAtividade =  new ArrayList<DadosDashboardEventoPainelAtividadesOnlineVoto>();

		for (Voto itemVoto : allByPesquisaEventoIdAndDataVoto) {
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
							itemVoto.getPesquisa().getPesquisa(), itemVoto.getPesquisado().getNome(), 
							itemVoto.getPesquisado().getCidade(), "", 
							formatDuration.toString(), 
							formatDurationEspecifico.toString(), 0, itemVoto.getMetadadosVoto().getLatitude(), 
							itemVoto.getMetadadosVoto().getLongitude()));
		}
		// TEM QUE FAZER QUERY NATIVA
		
		return listaAtividade.subList(0, (listaAtividade.size()>10?10:listaAtividade.size()));
	}
	
	public DadosDashboardEventoGrafico obterDadosGrafico(Long idEvento) {
		
		Evento evento = eventoRepository.getReferenceById(idEvento);
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

	public void registrarVoto(DadosCadastroDadosVoto dadosVoto, String headerNames) {
		
		List<Voto> listaVotos = new ArrayList<Voto>();
		Pesquisado pesquisado = new Pesquisado();

		pesquisado = new Pesquisado(dadosVoto.pessoa());
		
		MetadadosVoto referenceById = metadadosVotoRepository.getReferenceById(dadosVoto.idMetadadoVoto());
		referenceById.setDataRegistroVoto(LocalDateTime.now());
		referenceById.setLatitude(dadosVoto.coordenadas().latitude());
		referenceById.setLongitude(dadosVoto.coordenadas().longitude());
		referenceById.setDataAberturaTela(dadosVoto.dataTela());
		referenceById.setHeaderHttp(headerNames);
		
		for (DadosCadastroVotos itemTela : dadosVoto.listaVoto()) {
			
			Voto e = new Voto(itemTela);
			e.setMetadadosVoto(referenceById);
			e.setPesquisado(pesquisado);
			listaVotos.add(e);
		}
		try {
			votoRepository.saveAll(listaVotos);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voto nao registrado!");
		}
	}

	public List<DadosListagemPesquisa> obterDadosPainelVotacao(String hash) {
		
		try {
			UUID.fromString(hash);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametro invalido!");
		}
		
		Evento eventoByHash = eventoRepository.findByHash(hash);
		
		if (!eventoByHash.isPublicado()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Evento nao aberto para voto!");
		} 
		
		
		MetadadosVoto metadadosVoto = new MetadadosVoto();
		metadadosVoto.setDataCriacao(LocalDateTime.now());
		metadadosVoto.setEvento(eventoByHash);
		metadadosVotoRepository.save(metadadosVoto);
		
		List<DadosListagemPesquisa> listaDadosPainelVotacao = new ArrayList<DadosListagemPesquisa>();
		List<Pesquisa> allByEventoIdOrderByOrdemAsc = pesquisaRepository.findAllByEventoIdOrderByOrdemAsc(eventoByHash.getId());
		for (Pesquisa pesquisa : allByEventoIdOrderByOrdemAsc) {
			listaDadosPainelVotacao.add(new DadosListagemPesquisa(pesquisa, metadadosVoto.getId()));
		}
		return listaDadosPainelVotacao;
	}

}
