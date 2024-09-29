package med.voll.api.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import med.voll.api.domain.agremiacao.Agremiacao;
import med.voll.api.domain.agremiacao.AgremiacaoRepository;
import med.voll.api.domain.agremiacao.DadosCadastroAgremiacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.apresentacao.DadosPainelVotacaoApresentacao;
import med.voll.api.domain.ensaio.DadosCadastroEnsaio;
import med.voll.api.domain.ensaio.EnsaioRepository;
import med.voll.api.domain.pergunta.DadosListagemPergunta;
import med.voll.api.domain.pergunta.Pergunta;
import med.voll.api.domain.pergunta.PerguntaRepository;
import med.voll.api.domain.pesquisa.DadosListagemPesquisa;
import med.voll.api.domain.voto.DadosCadastroDadosVoto;
import med.voll.api.domain.voto.DadosListagemVoto;
import med.voll.api.domain.voto.DadosPainelVotacaoListaPergunta;
import med.voll.api.domain.voto.DadosPainelVotacaoPergunta;
import med.voll.api.domain.voto.VotoRepository;
import med.voll.api.service.voto.VotoCarnavalService;
import med.voll.api.service.voto.VotoService;

@RestController
@RequestMapping("/voto")
public class VotacaoController {

    @Autowired
    private VotoService votoService;

    @Autowired
    private VotoCarnavalService votoCarnavalService;
    
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private ApresentacaoRepository apresentacaoRepository;
    
    @Autowired
    private EnsaioRepository ensaioRepository;
    
    @Autowired
    private PerguntaRepository perguntaRepository;
    
    @Autowired
    private AgremiacaoRepository agremiacaoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<String> registrar(@RequestBody @Valid DadosCadastroDadosVoto dadosVotoParam, HttpServletRequest request) {
    	
    	StringBuffer sbHeaderNames = new StringBuffer();    	
    	request.getHeaderNames().asIterator().forEachRemaining(entry -> { sbHeaderNames.append(entry+":"+request.getHeader(entry)+"| "); });
    	votoService.registrarVoto(dadosVotoParam, sbHeaderNames.toString());
    	return ResponseEntity.noContent().build();
    }


    @GetMapping("/evento/{idEvento}")
    public List<DadosListagemVoto> listarVotos(@PathVariable Long idEvento) {
    	
    	return votoRepository.findAllByPesquisaEventoId(idEvento).stream().map(DadosListagemVoto::new).toList();
    }
    
    @GetMapping("/painelvotacao/evento/{hash}")
    public List<DadosListagemPesquisa> obterDadosPainelVotacao(@PathVariable String hash, HttpServletRequest request) {
    	return votoService.obterDadosPainelVotacao(hash);
    }
    
    @GetMapping("/painelvotacao/perguntas/{idApresentacao}")
    public List<DadosListagemPergunta> listarPerguntasPelaApresentacao(@PathVariable Long idApresentacao) {

		List<Pergunta> allByEventoIdOrderByOrdemAsc = perguntaRepository.findAllByApresentacaoIdOrderByOrdemAsc(idApresentacao);
		List<DadosListagemPergunta> map = allByEventoIdOrderByOrdemAsc.stream().map(
				item -> new DadosListagemPergunta(item, null)).collect(Collectors.toList());
		return map;
	}
    
    
//    ##########################
    
    @PostMapping("/painelvotacao/carnaval")
    @Transactional
    public ResponseEntity<String> registrarVotos(@RequestBody @Valid DadosCadastroDadosVoto dadosVotoParam, HttpServletRequest request) {
    	
    	StringBuffer sbHeaderNames = new StringBuffer();
    	request.getHeaderNames().asIterator().forEachRemaining(entry -> { sbHeaderNames.append(entry+":"+request.getHeader(entry)+"| "); });
    	votoCarnavalService.registrarVotoCarnaval(dadosVotoParam, sbHeaderNames.toString());
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/painelvotacao/agremiacao/{id}")
    public DadosCadastroAgremiacao obter(@PathVariable Long id, HttpServletRequest request) {
    	
    	Agremiacao referenceById = agremiacaoRepository.getReferenceById(id);
    	return new DadosCadastroAgremiacao(referenceById);
    }
    
    
    @GetMapping("/painelvotacao/carnaval")
    public DadosPainelVotacaoApresentacao obterDadosPainelVotacaoCarnavalEstadosA(HttpServletRequest request) {
    	
    	List<DadosCadastroEnsaio>  ensaios = ensaioRepository.findAll().stream().map(item -> new DadosCadastroEnsaio(item)).collect(Collectors.toList());
    	
    	List<DadosCadastroAgremiacao> allGroupByAgremiacao = apresentacaoRepository.findAllGroupByAgremiacao().stream().map(item -> new DadosCadastroAgremiacao(item)).collect(Collectors.toList());
    	
    	
		DadosPainelVotacaoApresentacao dadosPainelVotacaoApresentacao = new DadosPainelVotacaoApresentacao(
    			apresentacaoRepository.findAllGroupByEstado(),
    			ensaios, allGroupByAgremiacao, apresentacaoRepository.findAllGroupByDataApresentacao());
    	
    	return dadosPainelVotacaoApresentacao;
    }
    
    @GetMapping("/painelvotacao/tela/1")
    public DadosPainelVotacaoApresentacao obterDadosPainelVotacaoCarnavalEstados(HttpServletRequest request) {
    	
    	List<DadosCadastroEnsaio>  ensaios = ensaioRepository.findAll().stream().map(item -> new DadosCadastroEnsaio(item)).collect(Collectors.toList());
    	
    	List<String> allGroupByAgremiacao = apresentacaoRepository.findAllGroupByEstado();
    	
    	DadosPainelVotacaoApresentacao dadosPainelVotacaoApresentacao = new DadosPainelVotacaoApresentacao(
    			allGroupByAgremiacao,
    			ensaios, null, null);
    	return dadosPainelVotacaoApresentacao;
    }
    
    @GetMapping("/painelvotacao/tela/2/{codEstado}/{codEnsaio}")
    public DadosPainelVotacaoApresentacao obterDadosPainelVotacaoCarnavalAgremiacao(
    		@PathVariable Long codEnsaio,
    		@PathVariable String codEstado,
    		HttpServletRequest request) {
    	
    	List<DadosCadastroAgremiacao> listaAgremiacao 
    	    = apresentacaoRepository.findAllByEstadoAndEnsaioId(codEstado, codEnsaio).stream().map(item -> new DadosCadastroAgremiacao(item.getAgremiacao())).collect(Collectors.toList());
    	
    	List<LocalDate>  listaDataApresentacao = apresentacaoRepository.findAllByEstadoAndEnsaioIdGroupByDataApresentacao(codEstado, codEnsaio);
    	
    	DadosPainelVotacaoApresentacao dadosPainelVotacaoApresentacao = new DadosPainelVotacaoApresentacao(
    			null,
    			null, 
    			listaAgremiacao, listaDataApresentacao);
    	return dadosPainelVotacaoApresentacao;
    }
    
    @GetMapping("/painelvotacao/tela/3/{codEstado}/{codEnsaio}/{codAgremiacao}/{dataApresentacao}")
    public DadosPainelVotacaoListaPergunta obterDadosPainelVotacaoCarnavalPerguntasDisponiveis(
    		@PathVariable Long codEnsaio,
    		@PathVariable String codEstado,
    		@PathVariable Long codAgremiacao,
    		@PathVariable String dataApresentacao,
    		HttpServletRequest request) {
    	
    	LocalDate localDate = LocalDate.parse(dataApresentacao, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    	List<Pergunta> allPerguntas = perguntaRepository.findAllPerguntas(codEstado, codEnsaio, codAgremiacao, localDate);
    	
    	Agremiacao referenceById = agremiacaoRepository.getReferenceById(codAgremiacao);
    	
    	List<DadosPainelVotacaoPergunta> listaAgremiacao 
    	     = allPerguntas.stream().map(item -> new DadosPainelVotacaoPergunta(item)).collect(Collectors.toList());
    	
    	DadosPainelVotacaoListaPergunta dadosPainelVotacaoListaPergunta = new DadosPainelVotacaoListaPergunta(listaAgremiacao, referenceById);
    	
    	return dadosPainelVotacaoListaPergunta;
    }
}
