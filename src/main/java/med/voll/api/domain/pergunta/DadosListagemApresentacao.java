package med.voll.api.domain.pergunta;

import med.voll.api.domain.apresentacao.Apresentacao;

public record DadosListagemApresentacao(Long id, String nome, String descricao, String imagemUrl) {

    public DadosListagemApresentacao(Apresentacao apresentacao) {
        this(apresentacao.getId(), apresentacao.getAgremiacao().getAgremiacao(), "", "");
    }
}

