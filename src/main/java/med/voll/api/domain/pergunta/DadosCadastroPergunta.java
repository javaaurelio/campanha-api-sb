package med.voll.api.domain.pergunta;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPergunta(
        @NotBlank
        String pergunta,
        
        @NotNull
        Long idApresentacao,
        
        @Min(value = 1)
        int ordem,
        
        Long id
		) {
		
	public DadosCadastroPergunta(Pergunta pergunta) {
		this(pergunta.getPergunta(), pergunta.getApresentacao().getId(), pergunta.getOrdem(), pergunta.getId());
	}
}
