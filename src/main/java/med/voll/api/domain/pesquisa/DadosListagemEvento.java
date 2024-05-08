package med.voll.api.domain.pesquisa;

import med.voll.api.domain.evento.Evento;

public record DadosListagemEvento(Long id, String nome, String descricao, String imagemUrl) {

    public DadosListagemEvento(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getDescricao(), evento.getImagemUrl());
    }
}

