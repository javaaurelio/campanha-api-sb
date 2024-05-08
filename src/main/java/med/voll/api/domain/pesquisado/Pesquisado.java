package med.voll.api.domain.pesquisado;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.voto.DadosCadastroVotoInformacaoPessoa;

@Table(name = "pesquisado")
@Entity(name = "pesquisado")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pesquisado {

    public Pesquisado(Long id, String nome, Integer idade, String uF, String cidade, String telefone, String sexo) {
		super();
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		UF = uF;
		this.cidade = cidade;
		this.telefone = telefone;
		this.sexo = sexo;
	}
    
	public Pesquisado() {
	}
	
	public Pesquisado(DadosCadastroVotoInformacaoPessoa pessoa) {
		super();
   		this.nome = pessoa.nomePessoa();
		this.idade = pessoa.idade();
		this.UF = pessoa.uf();
		this.cidade = pessoa.cidade();
		this.sexo = pessoa.sexo();
	}

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;  
    private Integer idade;    
    private String UF;
    private String cidade;
    private String telefone;
    private String sexo;
    
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
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public String getUF() {
		return UF;
	}
	public void setUF(String uF) {
		UF = uF;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
    
    

}
