package med.voll.api.domain.evento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public record DadosListagemEvento(Long id, String campanha, String dataInicio, String dataFim, String imagemUrl, 
		boolean publicado, long porcentagemDias, String urlPublicacao, int qtdPerguntas, String layoutPainelVotacao, String descricao) {

    public DadosListagemEvento(Evento evento) {
        this(evento.getId(), evento.getNome(),  DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataInicio()), 
        		DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataFim()), 
        		evento.getImagemUrl(), evento.isPublicado(),   
        		Math.abs(
        				Math.abs(
        						ChronoUnit.DAYS.between(evento.getDataInicio(), LocalDate.now())) * 100 / 
        				Math.abs(
        						ChronoUnit.DAYS.between(evento.getDataInicio(), evento.getDataFim()))), 
        		formatarUrlPublicacao(evento), evento.getListaPesquisa().size(), evento.getLayoutPainelVotacao(), evento.getDescricao());
    }

	private static String formatarUrlPublicacao(Evento evento) {
		return evento.getUrlPublicacao()+"&l="+evento.getLayoutPainelVotacao();
	}

}
