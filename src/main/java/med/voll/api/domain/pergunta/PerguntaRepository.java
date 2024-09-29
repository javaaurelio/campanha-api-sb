package med.voll.api.domain.pergunta;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

	Pergunta findByApresentacaoIdAndOrdem(Long idApresentacao, int ordem);
	
	List<Pergunta> findAllByApresentacaoIdAndPergunta(Long idApresentacao, String pergunta);

	List<Pergunta> findAllByApresentacaoIdOrderByOrdemAsc(Long idApresentacao);
	
	@Query("SELECT p FROM pergunta AS p where p.apresentacao.estado = :estado and p.apresentacao.ensaio.id = :codEnsaio and  p.apresentacao.agremiacao.id = :codAgremiacao and p.apresentacao.dataApresentacao = :dataApresentacao")
	List<Pergunta> findAllPerguntas(String estado, Long codEnsaio, 
			Long codAgremiacao,	LocalDate dataApresentacao);
	
}
