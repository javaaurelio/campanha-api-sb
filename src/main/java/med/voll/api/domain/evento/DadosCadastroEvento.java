package med.voll.api.domain.evento;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEvento(

		
		Long id,
		
		@NotBlank
        String campanha,
        
        String descricao,
        
        @NotBlank
        String dataInicio,
        
        @NotBlank
        String dataFim,

        @NotBlank
        @URL
        String imagemUrl
        
//        @NotBlank
//        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}")
//        String cpf,

        ) {
}
