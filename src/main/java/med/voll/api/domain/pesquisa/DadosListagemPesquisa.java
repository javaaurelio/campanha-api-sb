package med.voll.api.domain.pesquisa;

public record DadosListagemPesquisa(Long id, String pesquisa, DadosListagemEvento evento, int ordem, Long idMetadadosVoto) {

    public DadosListagemPesquisa(Pesquisa pesquisa, Long idMetadadosVoto) {
        this(pesquisa.getId(), pesquisa.getPesquisa(), new DadosListagemEvento(pesquisa.getEvento()), pesquisa.getOrdem(), idMetadadosVoto);
    }
    
    
}
