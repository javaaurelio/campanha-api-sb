package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.voto.DadosCadastroDadosVoto;
import med.voll.api.domain.voto.DadosListagemVoto;
import med.voll.api.domain.voto.VotoRepository;
import med.voll.api.service.voto.VotoService;

@RestController
@RequestMapping("voto")
public class VotacaoController {

    @Autowired
    private VotoService votoService;
    
    @Autowired
    private VotoRepository votoRepository;

    @PostMapping
    @Transactional
    public void registrar(@RequestBody @Valid DadosCadastroDadosVoto dadosVotoParam, HttpServletRequest request) {
    	
    	StringBuffer sbHeaderNames = new StringBuffer();    	
    	request.getHeaderNames().asIterator().forEachRemaining(entry -> { sbHeaderNames.append(entry+":"+request.getHeader(entry)+"| "); });
    	votoService.registrarVoto(dadosVotoParam, sbHeaderNames.toString());
    }

    @GetMapping("/evento/{idEvento}")
    public List<DadosListagemVoto> listarVotos(@PathVariable Long idEvento) {
    	
    	return votoRepository.findAllByPesquisaEventoId(idEvento).stream().map(DadosListagemVoto::new).toList();
    }
    
    @GetMapping("/painelvotacao/evento/{hash}")
    public List<DadosListagemPesquisa> obterDadosPainelVotacao(@PathVariable String hash, HttpServletRequest request) {
    	
    	return votoService.obterDadosPainelVotacao(hash);
    }
}
