package med.voll.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.agremiacao.DadosCadastroAgremiacao;
import med.voll.api.domain.apresentacao.Apresentacao;
import med.voll.api.domain.apresentacao.ApresentacaoRepository;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotos;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotosRating;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoGraficoQtdVotosRatingDto;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoListaCard;
import med.voll.api.domain.dashboarapresentacao.DadosDashboardApresentacaoPainelQtdVotos;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelAtividadesOnlineVoto;
import med.voll.api.domain.voto.DadosGraficoPiaEstado;
import med.voll.api.domain.voto.DadosGraficoRadar;
import med.voll.api.service.dashboardapresentacao.DashboardApresentacaoService;
import med.voll.api.service.dashboardevento.DashboardEventoService;

@RestController
@RequestMapping("dashboardapresentacao")
public class DashboardApresentacaoController {

    @Autowired
    private DashboardEventoService dashboardEventoService;
    
    @Autowired
    private ApresentacaoRepository apresentacaoRepository;
    
    @Autowired
    private DashboardApresentacaoService apresentacaoService;

    @GetMapping("/rating/{idApresentacao}")
    public List<DadosDashboardApresentacaoGraficoQtdVotosRatingDto> obterGraficosBarraRating(@PathVariable Long idApresentacao) {
    	return apresentacaoService.obterGraficosBarraRating(idApresentacao);
    }
    
    @GetMapping("/rating/media/{idApresentacao}")
    public String obterMediaVotacao(@PathVariable Long idApresentacao) {
    	return Double.valueOf(apresentacaoService.obterMediaVotacao(idApresentacao)).toString();
    }
    
    @GetMapping("/agremiacao/{idApresentacao}")
    public DadosCadastroAgremiacao obterAgremiacaoPelaApresentacao(@PathVariable Long idApresentacao) {
    	
    	Apresentacao referenceById = apresentacaoRepository.getReferenceById(idApresentacao);
    	return new DadosCadastroAgremiacao(referenceById.getAgremiacao(), referenceById);
    }
    
    @GetMapping("/{idApresentacao}")
    public DadosDashboardApresentacaoPainelQtdVotos obterTotalVotos(@PathVariable Long idApresentacao) {
    	
    	return apresentacaoService.qtdTotalVotos(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/hoje")
    public DadosDashboardApresentacaoPainelQtdVotos obterQdVotosHoje(@PathVariable Long idApresentacao) {
    	
    	return apresentacaoService.qtdTotalVotosHoje(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/ontem")
    public DadosDashboardApresentacaoPainelQtdVotos obterQdVotosOntem(@PathVariable Long idApresentacao) {
    	
    	return apresentacaoService.qtdTotalVotosOntem(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/ultimo7dias")
    public DadosDashboardApresentacaoPainelQtdVotos obterQdVotosUltimo7dias(@PathVariable Long idApresentacao) {
    	
    	return apresentacaoService.qtdTotalVotosUltimos7Dias(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/grafico/barra/dia")
    public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia(@PathVariable Long idApresentacao) {
    	List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraDia = apresentacaoService.obterDadosGraficoBarraDia(idApresentacao);
    	return obterDadosGraficoBarraDia;
    }
    
    @GetMapping("/{idApresentacao}/grafico/barra/mes")
    public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraMes(@PathVariable Long idApresentacao) {
    	return apresentacaoService.obterDadosGraficoBarraMes(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/grafico/barra/ano")
    public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraAno(@PathVariable Long idApresentacao) {
    	return apresentacaoService.obterDadosGraficoBarraAno(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/grafico/barra/hora24")
    public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraAnoHora24h(@PathVariable Long idApresentacao) {
    	return apresentacaoService.obterDadosGraficoBarraHora24h(idApresentacao);
    }
    
    @GetMapping("/{idApresentacao}/grafico/barra/hora24/{data}")
    public List<DadosDashboardApresentacaoGraficoQtdVotos> obterDadosGraficoBarraAnoHora24hData(@PathVariable Long idApresentacao, @PathVariable String data) {
    	return apresentacaoService.obterDadosGraficoBarraHora24hData(idApresentacao, data);
    }
    
    @GetMapping("/{idApresentacao}/grafico/radar")
    public List<DadosGraficoRadar> obterDadosGraficoRadar(@PathVariable Long idApresentacao) {
    	
    	return apresentacaoService.obterDadosGraficoRadar(idApresentacao);
    }
    
    
    
    @GetMapping("/{idEvento}/atividades/online")
    public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnline(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterAtividadesOnline(idEvento);
    }
    @GetMapping("/{idEvento}/atividades/online/media")
    public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnlineMedia(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterAtividadesOnlineMedia(idEvento);
    }
    
//    @GetMapping("/{idEvento}/grafico")
//    public DadosDashboardEventoGrafico obterDadosGrafico(@PathVariable Long idEvento) {
//    	
//    	return dashboardEventoService.obterDadosGrafico(idEvento);
//    }
    
//    @GetMapping("/{idEvento}/grafico/barra/mes")
//    public List<DadosMetadadosVotoGraficoBarra> obterDadosGraficoBarraMes(@PathVariable Long idEvento) {
//    	
//    	return dashboardEventoService.obterDadosGraficoBarraMes(idEvento);
//    }
//    @GetMapping("/{idEvento}/grafico/barra/ano")
//    public List<DadosMetadadosVotoGraficoBarra> obterDadosGraficoBarraAno(@PathVariable Long idEvento) {
//    	
//    	return dashboardEventoService.obterDadosGraficoBarraAno(idEvento);
//    }
//    @GetMapping("/{idEvento}/grafico/barra/hora24")
//    public List<DadosMetadadosVotoGraficoBarra> obterDadosGraficoBarraHora24(@PathVariable Long idEvento) {
//    	
//    	return dashboardEventoService.obterDadosGraficoBarraPorHora24(idEvento);
//    }
//    @GetMapping("/{idEvento}/grafico/barra/hora24/{data}")
//    public List<DadosMetadadosVotoGraficoBarra> obterDadosGraficoBarraHoraDia(@PathVariable Long idEvento, @PathVariable String data) {
//    	
//    	return dashboardEventoService.obterDadosGraficoBarraPorHora24HoraDia(idEvento, data);
//    }
    
    @GetMapping("/geral")
    public DadosDashboardApresentacaoListaCard obterDadosGraficoGeral() {
    	
    	DadosDashboardApresentacaoListaCard apresentacaoListaCard = apresentacaoService.obterApresentacaoListaCards();
		return apresentacaoListaCard;
    }
    
//    @GetMapping("/{idEvento}/grafico/radar")
//    public List<DadosGraficoRadar> obterDadosGraficoRadar(@PathVariable Long idEvento) {
//    	
//    	return dashboardEventoService.obterDadosGraficoRadar(idEvento);
//    }
    
    @GetMapping("/{idEvento}/grafico/pie")
    public List<DadosGraficoPiaEstado> obterDadosGraficoPie(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterDadosGraficoPie(idEvento);
    }
}
