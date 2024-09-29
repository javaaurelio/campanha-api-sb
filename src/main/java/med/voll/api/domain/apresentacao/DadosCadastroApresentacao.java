package med.voll.api.domain.apresentacao;

import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroApresentacao(Long id,
		String dataApresentacao,
		
		@NotNull 
		String estado,

		@NotNull 
		long codEnsaio,
		
		String ensaio,

		@NotNull
		long codAgremiacao,
		
		String agremiacao
) {

	public DadosCadastroApresentacao(Apresentacao apresentacao) {
		this(apresentacao.getId(),
				(apresentacao.getDataApresentacao() != null
						? DateTimeFormatter.ofPattern("yyyy-MM-dd").format(apresentacao.getDataApresentacao())
						: "")
				, apresentacao.getEstado(), 
				apresentacao.getEnsaio().getId(), 
				apresentacao.getEnsaio().getNome(), 
				apresentacao.getAgremiacao().getId(),
				apresentacao.getAgremiacao().getAgremiacao());
	}
}
