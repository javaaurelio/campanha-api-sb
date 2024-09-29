package med.voll.api.domain.agremiacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;
import med.voll.api.domain.apresentacao.Apresentacao;

public record DadosCadastroAgremiacao(Long id,

		@NotBlank String agremiacao,
		
		String agremiacaoDescricao,

		String dataHorasCadastro,

		String dataHorasAtualizacao,

		@NotBlank String bandeiraBase64Imagem,
		
		String dadosTabelaAgremiacao,

		boolean ativo,
		
		String dataHorasApresentacao,
		
		String ensaio,
		
		String estado

) {

	public DadosCadastroAgremiacao(Agremiacao ensaio, String dadosTabelaAgremiacao) {
		this(ensaio.getId(), ensaio.getAgremiacao(), ensaio.getAgremiacaoDescriacao(),
				(ensaio.getDataHorasCadastro() != null
						? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasCadastro())
						: ""),
				(ensaio.getDataHorasAtualizacao() != null
						? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasAtualizacao())
						: ""),
				ensaio.getBandeiraBase64Html(), dadosTabelaAgremiacao, ensaio.isAtivo(), null, null, null);
	}
	
	public DadosCadastroAgremiacao(Agremiacao ensaio) {
		this(ensaio.getId(), ensaio.getAgremiacao(), ensaio.getAgremiacaoDescriacao(),
				(ensaio.getDataHorasCadastro() != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasCadastro())
						: ""),
				(ensaio.getDataHorasAtualizacao() != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasAtualizacao())
						: ""),
				ensaio.getBandeiraBase64Html(), "", ensaio.isAtivo(), null, null, null);
	}
	
	public DadosCadastroAgremiacao(Agremiacao ensaio, Apresentacao apresentacao) {
		this(ensaio.getId(), ensaio.getAgremiacao(), ensaio.getAgremiacaoDescriacao(),
				(ensaio.getDataHorasCadastro() != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasCadastro())
						: ""),
				(ensaio.getDataHorasAtualizacao() != null
				? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(ensaio.getDataHorasAtualizacao())
						: ""),
				ensaio.getBandeiraBase64Html(), "", 
				ensaio.isAtivo(), DateTimeFormatter.ofPattern("dd/MM/yyyy").format(apresentacao.getDataApresentacao()) , 
				apresentacao.getEnsaio().getNome(), apresentacao.getEstado());
	}
}
