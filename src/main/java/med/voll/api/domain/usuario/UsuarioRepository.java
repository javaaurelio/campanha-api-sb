package med.voll.api.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Page<Evento> findAllByAtivoTrue(Pageable paginacao);
	
	 Page<Usuario> findAll(Pageable paginacao);

	 Long countByDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull();
}
