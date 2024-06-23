package med.voll.api.domain.usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DadosListagemUsuario(Long id, String nome, String email, String endereco, String cidade, String uf,
		String cep, 
		String dataNascimentoFormatado, 
		boolean ativo, 
		String dataRegistroFormatado, 
		String dataHoraPreRegistroFormatado, 
		String dataHoraPrimeiroAcesso, 
		String dataHoraUltimoAcesso, 
		String senha) {
	
	 public DadosListagemUsuario(Usuario usuario) {
	        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getEndereco(), 
	        		usuario.getCidade(), usuario.getUf(), usuario.getCep(), 
	        		(usuario.getDataNascimento()!= null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(usuario.getDataNascimento()): ""), 
	        		usuario.isAtivo(), 
	        		(usuario.getDataHoraRegistro()!= null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(usuario.getDataHoraRegistro()): ""), 
	        		(usuario.getDataHoraPreRegistro()!= null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(usuario.getDataHoraPreRegistro()): ""),
	        		DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now().plusDays(10)),
	        		LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")),
	        		usuario.getSenha());
	    }

}
