package med.voll.api.domain.apresentacao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import med.voll.api.domain.agremiacao.Agremiacao;

public interface ApresentacaoRepository extends JpaRepository<Apresentacao, Long> {
	
	@Query("SELECT c.estado FROM apresentacao AS c GROUP BY c.estado ORDER BY c.estado")
	List<String> findAllGroupByEstado();
	
	@Query("SELECT c.agremiacao FROM apresentacao AS c GROUP BY c.agremiacao.id ORDER BY c.agremiacao.agremiacao ")
	List<Agremiacao> findAllGroupByAgremiacao();
	
//	@Query("SELECT c.agremiacao FROM apresentacao AS c GROUP BY c.agremiacao.id ORDER BY c.agremiacao.agremiacao ")
	Page<Apresentacao> findAllByAgremiacaoId(Long codAgremiacao, Pageable pageable);
	
	List<Apresentacao> findAllByEstadoAndEnsaioId(String estado, Long id);
	
	@Query("SELECT c FROM apresentacao AS c where c.estado = :estado and c.ensaio.id = :id GROUP BY c.agremiacao.id ORDER BY c.agremiacao.agremiacao ")
	List<Apresentacao> findAllByEstadoAndEnsaioIdGroupByAgremiacaoId(String estado, Long id);

	@Query("SELECT c.dataApresentacao FROM apresentacao AS c where c.estado = :estado and c.ensaio.id = :id GROUP BY c.dataApresentacao ORDER BY c.agremiacao.agremiacao ")
	List<LocalDate> findAllByEstadoAndEnsaioIdGroupByDataApresentacao(String estado, Long id);
	
	@Query("SELECT c.dataApresentacao FROM apresentacao AS c GROUP BY c.dataApresentacao ORDER BY c.dataApresentacao")
	List<LocalDate> findAllGroupByDataApresentacao();
	
	
	@Query(value = " SELECT estado, ensaio.NOME as ensaio, DATA_APRESENTACAO as dataApresentacao, count(*) AS qtdPerguntas  FROM PUBLIC.APRESENTACAO apr\n"
			+ " INNER JOIN PUBLIC.ENSAIO ensaio ON ENSAIO.ID =apr.ENSAIO_ID\n"
			+ " INNER JOIN PUBLIC.PERGUNTA per  ON per.APRESENTACAO_ID = apr.ID  \n"
			+ " WHERE AGREMIACAO_ID = :idAgremiacao \n"
			+ " group BY  ESTADO , DATA_APRESENTACAO, ensaio.NOME \n"
			+ " ORDER BY DATA_APRESENTACAO ", 	nativeQuery = true)
	List<DadosDescricaoTabelaAgremiacao> dadosGeraisAgremiacaoTabela(@Param("idAgremiacao") Long idAgremiacao);

}
