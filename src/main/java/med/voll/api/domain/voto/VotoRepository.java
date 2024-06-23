package med.voll.api.domain.voto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    
	List<Voto> findAllByPesquisaEventoId(Long idEvento);
	
	List<Voto> findAllByPesquisaEventoIdAndDataVotoBetweenOrderByDataVotoDesc(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	List<Voto> findAllByPesquisaEventoIdAndDataVotoBeforeOrderByDataVotoDesc(Long idEvento, LocalDateTime data);
	
	List<Voto> findAllByPesquisaEventoIdAndDataVotoBetween(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	@Query(value = "SELECT pes.ID, pes.PESQUISA , sum(vt.VOTO) AS soma "
			+ "FROM PUBLIC.VOTO vt "
			+ "INNER JOIN PUBLIC.PESQUISA pes ON pes.ID = vt.PESQUISA_ID "
			+ "INNER JOIN PUBLIC.EVENTO ev ON pes.EVENTO_ID  = ev.ID "
			+ "WHERE ev.ID = :idEvento "
			+ "GROUP BY pes.ID, pes.PESQUISA ",
		    nativeQuery = true)
	List<DadosGraficoRadar> findDadosGraficoRadar(@Param("idEvento") Long idEvento);

	@Query(value = "SELECT ev.ID, pesqd.UF as estado, count(vt.VOTO) AS soma "
			+ "FROM PUBLIC.VOTO vt "
			+ "INNER JOIN PUBLIC.PESQUISA pes ON PES.ID = vt.PESQUISA_ID  "
			+ "INNER JOIN PUBLIC.PESQUISADO pesqd ON PESQd.ID = vt.PESQUISADO_ID "
			+ "INNER JOIN PUBLIC.EVENTO ev ON pes.EVENTO_ID  = ev.ID "
			+ "WHERE ev.ID = :idEvento "
			+ "GROUP BY ev.ID, PESQd.UF",
		    nativeQuery = true)
	List<DadosGraficoPiaEstado> findDadosGraficoPorEstado(Long idEvento);
	//List<Voto> findAllByPesquisaEventoIdGroupByDataVotoAndDataVotoBetweenDataVoto(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
}
