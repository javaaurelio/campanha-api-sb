package med.voll.api.domain.usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record DadosCadastroUsuario(Long id, String nome, String email, String endereco, String cidade, String uf,
		String cep, LocalDate dataNascimento, String senha, boolean ativo, String usuario) {
	
	public String getDataNascimentoDDMMYYYY() {
		
		if (dataNascimento != null) {
			String format = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dataNascimento);
			return format;
		} else {
			
			return "";
		}
	}
	
	public DadosCadastroUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getEndereco()
        		, usuario.getCidade(), usuario.getUf(), usuario.getCep(), usuario.getDataNascimento(), "", usuario.isAtivo(), usuario.getUsuario());
    }
	
}
