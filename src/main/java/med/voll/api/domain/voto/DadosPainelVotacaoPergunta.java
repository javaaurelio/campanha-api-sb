package med.voll.api.domain.voto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.pergunta.Pergunta;

public record DadosPainelVotacaoPergunta(
        @NotBlank
        String pergunta,
        
        @NotNull
        Long idApresentacao,
        
        @Min(value = 1)
        int ordem,
        
        Long id
        
		) {
		
	public DadosPainelVotacaoPergunta(Pergunta pergunta) {
		this(pergunta.getPergunta(), pergunta.getApresentacao().getId(), pergunta.getOrdem(), 
				pergunta.getId());
	}
}
