package med.voll.api.domain.voto.metadado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MetadadosVotoRepository extends JpaRepository<MetadadosVoto, Long> {
	
	@Query(value = "SELECT to_char(mtv.DATA_REGISTRO_VOTO,'dd-MM-yyyy') as dataVoto, count(mtv.ID) as qtdVotos FROM PUBLIC.METADADOSVOTO mtv "
			+ "INNER JOIN PUBLIC.EVENTO evento ON evento.ID = mtv.EVENTO_ID  "
			+ "WHERE mtv.DATA_REGISTRO_VOTO IS NOT NULL and evento.ID = :idEvento "
			+ "GROUP BY to_char(mtv.DATA_REGISTRO_VOTO,'dd-MM-yyyy') "
			+ " order by to_char(mtv.DATA_REGISTRO_VOTO,'dd-MM-yyyy') asc",
		    nativeQuery = true)
	List<DadosMetadadosVotoGraficoBarra> findDadosMetadadosVotoGraficoBarraPorDia(@Param("idEvento") Long idEvento);
	
	@Query(value = "SELECT to_char(mtv.DATA_REGISTRO_VOTO,'MM-yyyy') as dataVoto, count(mtv.ID) as qtdVotos FROM PUBLIC.METADADOSVOTO mtv "
			+ "INNER JOIN PUBLIC.EVENTO evento ON evento.ID = mtv.EVENTO_ID  "
			+ "WHERE mtv.DATA_REGISTRO_VOTO IS NOT NULL and evento.ID = :idEvento "
			+ "GROUP BY to_char(mtv.DATA_REGISTRO_VOTO,'MM-yyyy') "
			+ " order by to_char(mtv.DATA_REGISTRO_VOTO,'MM-yyyy') asc ",
			nativeQuery = true)
	List<DadosMetadadosVotoGraficoBarra> findDadosMetadadosVotoGraficoBarraPorMes(@Param("idEvento") Long idEvento);
	
	@Query(value = "SELECT to_char(mtv.DATA_REGISTRO_VOTO,'yyyy') as dataVoto, count(mtv.ID) as qtdVotos FROM PUBLIC.METADADOSVOTO mtv "
			+ "INNER JOIN PUBLIC.EVENTO evento ON evento.ID = mtv.EVENTO_ID  "
			+ "WHERE mtv.DATA_REGISTRO_VOTO IS NOT NULL and evento.ID = :idEvento "
			+ "GROUP BY to_char(mtv.DATA_REGISTRO_VOTO,'yyyy') "
			+ " order by to_char(mtv.DATA_REGISTRO_VOTO,'yyyy') asc ",
			nativeQuery = true)
	List<DadosMetadadosVotoGraficoBarra> findDadosMetadadosVotoGraficoBarraPorAno(@Param("idEvento") Long idEvento);
	
	@Query(value = "SELECT to_char(mtv.DATA_REGISTRO_VOTO,'HH24"+ "\"h\"" +"') as dataVoto, count(mtv.ID) as qtdVotos FROM PUBLIC.METADADOSVOTO mtv "
			+ "INNER JOIN PUBLIC.EVENTO evento ON evento.ID = mtv.EVENTO_ID  "
			+ "WHERE mtv.DATA_REGISTRO_VOTO IS NOT NULL and evento.ID = :idEvento "
			+ "GROUP BY to_char(mtv.DATA_REGISTRO_VOTO,'HH24"+ "\"h\"" +"') "
			+ " order by to_char(mtv.DATA_REGISTRO_VOTO,'HH24"+ "\"h\"" +"') asc ",
			nativeQuery = true)
	List<DadosMetadadosVotoGraficoBarra> findDadosMetadadosVotoGraficoBarraPorHora24(@Param("idEvento") Long idEvento);
	
	long countByEventoIdAndDataRegistroVotoBetweenOrderByDataRegistroVotoDesc(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	long countByEventoIdAndDataRegistroVotoIsNotNullOrderByDataRegistroVotoDesc(Long idEvento);
	
	List<MetadadosVoto> findAllByEventoIdAndDataRegistroVotoBetweenOrderByDataRegistroVotoDesc(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);

	@Query(value = "SELECT to_char(mtv.DATA_REGISTRO_VOTO,'HH24" + "\"h\"" + "') as dataVoto, count(mtv.ID) as qtdVotos FROM PUBLIC.METADADOSVOTO mtv "
			+ "INNER JOIN PUBLIC.EVENTO evento ON evento.ID = mtv.EVENTO_ID  "
			+ "WHERE mtv.DATA_REGISTRO_VOTO IS NOT NULL and evento.ID = :idEvento AND to_char(mtv.DATA_REGISTRO_VOTO,'yyyy-MM-dd') = :data "
			+ "GROUP BY to_char(mtv.DATA_REGISTRO_VOTO,'HH24"+ "\"h\"" + "') "
			+ " order by to_char(mtv.DATA_REGISTRO_VOTO,'HH24" + "\"h\"" + "') asc ",
			nativeQuery = true)
	List<DadosMetadadosVotoGraficoBarra> findDadosMetadadosVotoGraficoBarraPorHora24HoraDia(Long idEvento, LocalDate data);
}
