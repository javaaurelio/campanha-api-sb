package med.voll.api.domain.pergunta;

public record DadosListagemPergunta(Long id, String pesquisa, DadosListagemApresentacao evento, int ordem, Long idMetadadosVoto) {

    public DadosListagemPergunta(Pergunta pergunta, Long idMetadadosVoto) {
        this(pergunta.getId(), pergunta.getPergunta(), new DadosListagemApresentacao(pergunta.getApresentacao()), pergunta.getOrdem(), idMetadadosVoto);
    }
    
    
}
