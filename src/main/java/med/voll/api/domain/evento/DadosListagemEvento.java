package med.voll.api.domain.evento;

public record DadosListagemEvento(Long id, String campanha, String dataInicio, String dataFim, String imagemUrl) {

    public DadosListagemEvento(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getDataInicio(), evento.getDataFim(), evento.getImagemUrl());
    }

}
