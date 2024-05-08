package med.voll.api.domain.voto.metadado;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadadosVotoRepository extends JpaRepository<MetadadosVoto, Long> {
	
	List<MetadadosVoto> findAllByEventoIdAndDataRegistroVotoBetweenOrderByDataRegistroVotoDesc(Long idEvento, LocalDateTime dataInicio, LocalDateTime dataFim);
}
