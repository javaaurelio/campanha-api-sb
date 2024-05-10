package med.voll.api.domain.evento;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public record DadosListagemEvento(Long id, String campanha, String dataInicio, String dataFim, String imagemUrl, boolean publicado, int porcentagemDias) {

    public DadosListagemEvento(Evento evento) {
        this(evento.getId(), evento.getNome(),  DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataInicio()), 
        		DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataFim()), 
        		evento.getImagemUrl(), evento.isPublicado(),   
        		Math.abs(
        				Math.abs(
        						Period.between(evento.getDataInicio(), LocalDate.now()).getDays()) * 100 / 
        				Math.abs(
        						Period.between(evento.getDataInicio(),evento.getDataFim()).getDays()))
        		);
    }

}
