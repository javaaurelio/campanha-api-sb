package med.voll.api.domain.voto.carnaval;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotos;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotosRating;
import med.voll.api.domain.voto.DadosGraficoRadar;

public interface VotoCarnavalRepository extends JpaRepository<VotoCarnaval, Long> {
	
	@Query(value = ""
			+ "SELECT car.* FROM PUBLIC.VOTO_CARNAVAL car "
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao "
			+ "",
			nativeQuery = true)
	List<VotoCarnaval> listaVotosByIdApresentacao(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = "SELECT\n"
			+ " sum(car.VOTO)\n"
			+ " FROM PUBLIC.VOTO_CARNAVAL car \n"
			+ " INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID  \n"
			+ " INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID  \n"
			+ " WHERE apre.ID = :idApresentacao",
			nativeQuery = true)
	Long somaVotosByIdApresentacao(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = ""
			+ "WITH dados AS ( SELECT count(*) FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao "
			+ "group by car.hash) "
			+ " SELECT count(*) FROM DADOS ",
			nativeQuery = true)
	long totalVotosPorApresentacao( @Param("idApresentacao") Long idApresentacao);
	
	@Query(value = ""
			+ "WITH dados AS ( SELECT count(*) FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE car.DATA_VOTO >= :dataVoto and apre.ID = :idApresentacao "
			+ " group by car.hash ) "
			+ "SELECT count(*) FROM DADOS ",
			nativeQuery = true)
	long totalVotosPorApresentacaoEDataRegistro( @Param("idApresentacao") Long idApresentacao, @Param("dataVoto") LocalDate dataVoto);

	
	@Query(value = ""
			+ "WITH DADOS AS (\n"
			+ " SELECT to_char(car.DATA_VOTO ,'dd-MM-yyyy') as dataVoto, car.HASH   \n"
			+ " FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ " INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ " INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ " WHERE apre.ID = :idApresentacao\n"
			+ " GROUP BY to_char(car.DATA_VOTO, 'dd-MM-yyyy'), car.HASH \n"
			+ " order by to_char(car.DATA_VOTO, 'dd-MM-yyyy') asc \n"
			+ ")\n"
			+ " SELECT dataVoto, count(*) as qtdVotos FROM  DADOS ",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = "WITH DADOS AS ( SELECT to_char(car.DATA_VOTO ,'MM-yyyy') as dataVoto, car.HASH   FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao "
			+ "GROUP BY to_char(car.DATA_VOTO, 'MM-yyyy'), car.HASH  \n"
			+ "order by to_char(car.DATA_VOTO, 'MM-yyyy') asc ) \n"
			+ "  SELECT dataVoto, count(*) as qtdVotos FROM  DADOS ",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraMes(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = "WITH DADOS AS ( SELECT to_char(car.DATA_VOTO ,'yyyy') as dataVoto, car.HASH  FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao "
			+ "GROUP BY to_char(car.DATA_VOTO, 'yyyy'), car.HASH \n "
			+ "order by to_char(car.DATA_VOTO, 'yyyy') asc ) "
			+ "SELECT dataVoto, count(*) as qtdVotos FROM  DADOS",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraAno(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = "WITH DADOS AS ( SELECT to_char(car.HORA_VOTO ,'HH24') as dataVoto, car.HASH  FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao "
			+ "GROUP BY to_char(car.HORA_VOTO, 'HH24'), car.HASH\n "
			+ "order by to_char(car.HORA_VOTO, 'HH24') asc )"
			+ " SELECT dataVoto, count(*) as qtdVotos FROM  DADOS",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraHora24h(@Param("idApresentacao") Long idApresentacao);
	
	@Query(value = " WITH DADOS AS ( SELECT to_char(car.HORA_VOTO ,'HH24') as dataVoto, car.HASH  FROM PUBLIC.VOTO_CARNAVAL car\n"
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID \n"
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID \n"
			+ "WHERE apre.ID = :idApresentacao AND to_char(car.DATA_VOTO,'yyyy-MM-dd') = :data "
			+ "GROUP BY to_char(car.HORA_VOTO, 'HH24'), car.HASH \n "
			+ "order by to_char(car.HORA_VOTO, 'HH24') asc ) "
			+ " SELECT dataVoto, count(*) as qtdVotos FROM  DADOS",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraHora24hData(
			@Param("idApresentacao") Long idApresentacao,
			@Param("data") LocalDate data );
	
	
	@Query(value = "SELECT  "
			+ "perg.ID , perg.PERGUNTA as pesquisa, "
			+ "sum(car.VOTO) as soma "
			+ "FROM PUBLIC.VOTO_CARNAVAL car "
			+ "INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID  "
			+ "INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID  "
			+ "WHERE apre.ID =  :idApresentacao "
			+ "GROUP BY perg.ID, perg.PERGUNTA",
		    nativeQuery = true)
	List<DadosGraficoRadar> findDadosGraficoRadar(@Param("idApresentacao") Long idApresentacao);
	
	
	@Query(value = "SELECT "
			+ " car.VOTO AS RATING, count(*) as qtdVotos"
			+ " FROM PUBLIC.VOTO_CARNAVAL car \n"
			+ " INNER JOIN PUBLIC.PERGUNTA perg ON PERG.ID  = car.PERGUNTA_ID  \n"
			+ " INNER JOIN PUBLIC.APRESENTACAO apre ON apre.ID  = PERG.APRESENTACAO_ID  \n"
			+ " WHERE apre.ID = :idApresentacao "
			+ " GROUP BY car.VOTO ORDER BY car.VOTO ASC",
			nativeQuery = true)
	List<DadosDashboardApresentacaoGraficoQtdVotosRating> obterDadosGraficoRating(@Param("idApresentacao") Long idApresentacao);
	
}
