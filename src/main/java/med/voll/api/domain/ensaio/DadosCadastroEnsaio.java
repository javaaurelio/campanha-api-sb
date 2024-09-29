package med.voll.api.domain.ensaio;

import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEnsaio(Long id,

		@NotBlank String nome,

		String dataHorasCadastro,

		String dataHorasAtualizacao,

		@NotBlank String cor,

		boolean ativo

) {

	public DadosCadastroEnsaio(Ensaio ensaio) {
		this(ensaio.getId(), ensaio.getNome(),
				(ensaio.getDataHorasCadastro() != null
						? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasCadastro())
						: ""),
				(ensaio.getDataHorasAtualizacao() != null
						? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasAtualizacao())
						: ""),
				ensaio.getCor(), ensaio.isAtivo());
	}
}
