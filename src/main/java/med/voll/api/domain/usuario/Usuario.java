package med.voll.api.domain.usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Table(name = "usuario")
@Entity(name = "usuario")
public class Usuario implements UserDetails {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String senha;
        
    private String nome;
    private String email;
    private String usuario;
    
    private String endereco;
    private String cidade;
    private String uf;
    private String cep;
    private LocalDate dataNascimento;
    private boolean ativo;
    private LocalDateTime dataHoraPreRegistro;
    private LocalDateTime dataHoraRegistro;
    private LocalDateTime dataHoraAtualizacao;
    private LocalDateTime dataHoraPrimeiroAcesso;
    private LocalDateTime dataHoraUltimoAcesso;
    
    @Lob
    private byte[] fotoPerfil;
    
    
    public Usuario() {
   	}
    
    public Usuario(DadosCadastroUsuario cadastroUsuario) {
    	super();
   		this.nome = cadastroUsuario.nome();
   		this.cep = cadastroUsuario.cep();
   		this.cidade= cadastroUsuario.cidade();
   		this.email= cadastroUsuario.email();
   		this.endereco=cadastroUsuario.endereco();
   		this.uf=cadastroUsuario.uf();
   		this.dataNascimento= cadastroUsuario.dataNascimento();
   		this.ativo= cadastroUsuario.ativo();
   		this.usuario = cadastroUsuario.usuario();
   		this.login = cadastroUsuario.login();
   		this.senha = cadastroUsuario.senha();
    }

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getCidade() {
		if (cidade == null) {
			cidade="";
		}
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getUf() {
		return uf;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}


	public LocalDateTime getDataHoraRegistro() {
		return dataHoraRegistro;
	}


	public void setDataHoraRegistro(LocalDateTime dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataHoraAtualizacao() {
		return dataHoraAtualizacao;
	}

	public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) {
		this.dataHoraAtualizacao = dataHoraAtualizacao;
	}

	public String getFotoPerfilStringHtml() {
		
		if (fotoPerfil != null) {
			return new String(fotoPerfil);
		}
		return "";
	}
	
	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataHoraPreRegistro() {
		return dataHoraPreRegistro;
	}

	public void setDataHoraPreRegistro(LocalDateTime dataHoraPreRegistro) {
		this.dataHoraPreRegistro = dataHoraPreRegistro;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public LocalDateTime getDataHoraPrimeiroAcesso() {
		return dataHoraPrimeiroAcesso;
	}

	public LocalDateTime getDataHoraUltimoAcesso() {
		return dataHoraUltimoAcesso;
	}

	public void setDataHoraPrimeiroAcesso(LocalDateTime dataHoraPrimeiroAcesso) {
		this.dataHoraPrimeiroAcesso = dataHoraPrimeiroAcesso;
	}

	public void setDataHoraUltimoAcesso(LocalDateTime dataHoraUltimoAcesso) {
		this.dataHoraUltimoAcesso = dataHoraUltimoAcesso;
	}

}
