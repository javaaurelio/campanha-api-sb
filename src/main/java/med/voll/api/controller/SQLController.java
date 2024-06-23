package med.voll.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@RestController
@RequestMapping("sql")
public class SQLController {
	
	@PersistenceContext
	private EntityManager em;

	
	@GetMapping()
	public String campanha(ModelMap model, @RequestParam String sql){
		 Query query = em.createNativeQuery(sql);
		 List list = query.getResultList();
		 return "<html>SQL: " + sql +" <br> -> " + Arrays.deepToString(list.toArray()).replaceAll("\\], \\[", "]<br> -> <br>[ ") + "</html>";
	}
}
