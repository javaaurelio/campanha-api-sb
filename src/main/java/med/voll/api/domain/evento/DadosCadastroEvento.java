package med.voll.api.domain.evento;

import java.time.format.DateTimeFormatter;

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
        String imagemUrl,
        
        @NotBlank
        String layoutPainelVotacao,
        
        boolean publicado,
        
        String hashPublicacao
        
        ) {
	
	public DadosCadastroEvento(Evento evento) {
        this(
        		evento.getId(), evento.getNome(), evento.getDescricao(), 
        		DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataInicio()) ,
        		DateTimeFormatter.ofPattern("dd/MM/yyyy").format(evento.getDataFim()), 
        		evento.getImagemUrl(), evento.getLayoutPainelVotacao(), evento.isPublicado(), evento.getUrlPublicacao());
    }
}
