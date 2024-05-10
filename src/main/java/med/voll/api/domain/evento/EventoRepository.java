package med.voll.api.domain.evento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    //Page<Evento> findAllByAtivoTrue(Pageable paginacao);
	Evento findByHash(String hash);
}
