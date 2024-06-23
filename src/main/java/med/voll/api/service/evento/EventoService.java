package med.voll.api.service.evento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import med.voll.api.domain.evento.DadosListagemEvento;
import med.voll.api.domain.evento.Evento;
import med.voll.api.domain.evento.EventoRepository;
import med.voll.api.domain.voto.metadado.MetadadosVotoRepository;

@Service
public class EventoService {
	
	@Autowired
	private EventoRepository eventoRepository;
	
	@Autowired
	private MetadadosVotoRepository metadadosVotoRepository;

	public Page<DadosListagemEvento> recuperarListaEventos(Pageable paginacao) {
		
		Page<Evento> all = eventoRepository.findAll(paginacao);
		Page<DadosListagemEvento> map = all.map(DadosListagemEvento::new);
		
//		
//		for (Evento evento : all) {
//			
//			LocalDateTime dataInicio = evento.getDataInicio().atTime(10, 30).with(LocalTime.MIN);
//			LocalDateTime dataFim = evento.getDataFim().atTime(10, 30).with(LocalTime.MAX);
//			long count = 
//					metadadosVotoRepository.countByEventoIdAndDataRegistroVotoBetweenOrderByDataRegistroVotoDesc(evento.getId(), dataInicio, dataFim);
//		}
		
		
		
		return map;
	}

}
