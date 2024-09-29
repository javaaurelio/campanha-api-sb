package med.voll.api.domain.apresentacao;

import java.time.LocalDate;
import java.util.List;

import med.voll.api.domain.agremiacao.DadosCadastroAgremiacao;
import med.voll.api.domain.ensaio.DadosCadastroEnsaio;

public record DadosPainelVotacaoApresentacao(
		List<String> estados,
		List<DadosCadastroEnsaio> listaCadastroEnsaios,
		List<DadosCadastroAgremiacao> listaAgremiacao,
		List<LocalDate> listaDataApresentacao
		
) {

//	public DadosPainelVotacaoApresentacao(List<String> estados, List<DadosCadastroEnsaio> listaCadastroEnsaios) {
//		this(estados, listaCadastroEnsaios);
//	}
}
