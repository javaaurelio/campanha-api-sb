package med.voll.api.domain.pesquisa;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroPesquisa(
        @NotBlank
        String pesquisa,
        
        @NotNull
        Long idEvento,
        
        @Min(value = 1)
        int ordem,
        
        Long id
		) {
}
