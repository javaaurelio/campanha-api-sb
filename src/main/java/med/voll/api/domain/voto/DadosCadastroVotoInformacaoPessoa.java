package med.voll.api.domain.voto;

public record DadosCadastroVotoInformacaoPessoa(
		String nomePessoa,
		String sexo,
		String cidade,     
		Integer idade,    
		String uf
		) {
}
