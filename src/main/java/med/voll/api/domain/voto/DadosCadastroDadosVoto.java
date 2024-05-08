package med.voll.api.domain.voto;

import java.util.List;

public record DadosCadastroDadosVoto(
		
		Long idMetadadoVoto,
		
		String dataTela,
		
		List<DadosCadastroVotos> listaVoto,
        
        DadosCadastroVotoCoordenadas coordenadas,
        
        DadosCadastroVotoInformacaoPessoa pessoa

        
		) {
}
