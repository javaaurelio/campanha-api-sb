package med.voll.api.domain.pesquisa;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {
    //Page<Evento> findAllByAtivoTrue(Pageable paginacao);
	
	Pesquisa findByEventoIdAndOrdem(Long idEvento, int ordem);
	
	List<Pesquisa> findAllByEventoIdOrderByOrdemAsc(Long idEvento);
	
}
