package med.voll.api.domain.voto;

import java.util.List;

import med.voll.api.domain.agremiacao.Agremiacao;

public record DadosPainelVotacaoListaPergunta(
       
		List<DadosPainelVotacaoPergunta> listaPerguntas,
        String agremiacao,
		String agremiacaoDescricao,
		String bandeiraBase64Imagem        
		) {
		
	public DadosPainelVotacaoListaPergunta(List<DadosPainelVotacaoPergunta> listaPerguntas, Agremiacao agremiacao) {
		this(listaPerguntas, agremiacao.getAgremiacao(), agremiacao.getAgremiacaoDescriacao(), agremiacao.getBandeiraBase64Html());
	}
}
