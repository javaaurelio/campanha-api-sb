package med.voll.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.domain.dashboardevento.DadosDashboardEventoGrafico;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelAtividadesOnlineVoto;
import med.voll.api.domain.dashboardevento.DadosDashboardEventoPainelQtdVotos;
import med.voll.api.service.dashboardevento.DashboardEventoService;

@RestController
@RequestMapping("dashboardevento")
public class DashboardEventoController {

    @Autowired
    private DashboardEventoService dashboardEventoService;

    @GetMapping("/{idEvento}")
    public DadosDashboardEventoPainelQtdVotos obterTotalVotos(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.qtdTotalVotos(idEvento);
    }
    
    @GetMapping("/{idEvento}/hoje")
    public DadosDashboardEventoPainelQtdVotos obterQdVotosHoje(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.qtdVotosHoje(idEvento);
    }
    
    @GetMapping("/{idEvento}/ontem")
    public DadosDashboardEventoPainelQtdVotos obterQdVotosOntem(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.qtdVotosOntem(idEvento);
    }
    
    @GetMapping("/{idEvento}/ultimo7dias")
    public DadosDashboardEventoPainelQtdVotos obterQdVotosUltimo7dias(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.qtdVotosUltimos7Dias(idEvento);
    }
    
    @GetMapping("/{idEvento}/atividades/online")
    public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnline(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterAtividadesOnline(idEvento);
    }
    @GetMapping("/{idEvento}/atividades/online/media")
    public List<DadosDashboardEventoPainelAtividadesOnlineVoto> obterAtividadesOnlineMedia(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterAtividadesOnlineMedia(idEvento);
    }
    
    @GetMapping("/{idEvento}/grafico")
    public DadosDashboardEventoGrafico obterDadosGrafico(@PathVariable Long idEvento) {
    	
    	return dashboardEventoService.obterDadosGrafico(idEvento);
    }
}
