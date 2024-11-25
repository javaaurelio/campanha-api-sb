package med.voll.api.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Page<Evento> findAllByAtivoTrue(Pageable paginacao);
	
	 Usuario findUsuarioByHash(String hash);
	
	 Page<Usuario> findAll(Pageable paginacao);

	 Long countByDataHoraPreRegistroIsNotNullAndDataHoraRegistroIsNull();

	 UserDetails findByLogin(String username);
}
