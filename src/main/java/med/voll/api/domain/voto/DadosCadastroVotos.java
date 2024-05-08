package med.voll.api.domain.voto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroVotos(
		@NotBlank
        Long codpesquisa,
        
        @NotNull
        int voto      
		) {
}
