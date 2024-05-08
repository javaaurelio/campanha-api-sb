package med.voll.api.domain.voto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    
	List<Voto> findAllByPesquisaEventoId(Long idEvento);
	
	List<Voto> findAllByPesquisaEventoIdAndDataVotoBetweenOrderByDataVotoDesc(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	List<Voto> findAllByPesquisaEventoIdAndDataVotoBetween(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
	
	//List<Voto> findAllByPesquisaEventoIdGroupByDataVotoAndDataVotoBetweenDataVoto(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
}
