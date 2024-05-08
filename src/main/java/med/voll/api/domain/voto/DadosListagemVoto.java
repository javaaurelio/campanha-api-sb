package med.voll.api.domain.voto;

public record DadosListagemVoto(int voto, String pesquisa, String nomeEvento) {

    public DadosListagemVoto(Voto voto) {
        this(voto.getVoto(), voto.getPesquisa().getPesquisa(), voto.getPesquisa().getEvento().getNome());
    }

}
