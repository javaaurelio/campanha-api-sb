package med.voll.api.infra;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.parametro.ParametroRepository;

@Component
public class ParametrosUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(ParametrosUtil.class);
	
	@Autowired
	private static ParametroRepository parametroRepository;
	
	private static Map<String, String> cache = new HashMap<String, String>();
	
	public static String get(String param) {
		
		String valor = cache.get(param);
		if (valor == null) {
			LOG.info("Carregando Cache param " + param);
			valor = parametroRepository.findParametroByNome(param).getValor();
			cache.put(param, valor);
			LOG.info("Carregando Cache Parametro: " + param);
		} else {
			LOG.info("Recuperado do Cache parametro: " + param);
		}
		return cache.get(param);
	}
	
	public static void reset() {
		
		cache.clear();
	}

}
