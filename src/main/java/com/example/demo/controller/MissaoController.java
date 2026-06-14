package com.example.demo.controller;

import com.example.demo.model.Missao;
import com.example.demo.service.MissaoService;
import com.example.demo.service.NinjaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class MissaoController {

    private final MissaoService missaoService;
    private final NinjaService ninjaService;

    public MissaoController(MissaoService missaoService, NinjaService ninjaService) {
        this.missaoService = missaoService;
        this.ninjaService = ninjaService;
    }

    @GetMapping("/missoes")
    public String listar(Model model) {
        model.addAttribute("missoes", missaoService.listarTodas());
        return "missoes";
    }

    @GetMapping("/missoes/nova")
    public String formulario(Model model) {
        model.addAttribute("ninjas", ninjaService.listarTodos());
        return "missao-form";
    }

    @PostMapping("/missoes/nova")
    public String criar(@RequestParam Long ninjaId,
                        @RequestParam String descricao,
                        @RequestParam String rankMissao,
                        @RequestParam String dataInicio,
                        @RequestParam String dataPrevisaoFim,
                        Model model) {

        Missao missao = new Missao();
        missao.setNinjaId(ninjaId);
        missao.setDescricao(descricao);
        missao.setRankMissao(rankMissao);
        missao.setDataInicio(LocalDateTime.parse(dataInicio));
        missao.setDataPrevisaoFim(LocalDateTime.parse(dataPrevisaoFim));

        String erro = missaoService.criar(missao);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("ninjas", ninjaService.listarTodos());
            return "missao-form";
        }

        return "redirect:/missoes";
    }

    @GetMapping("/missoes/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Missao missao = missaoService.buscarPorId(id);
        if (missao == null || !"EM_ANDAMENTO".equals(missao.getStatus())) {
            return "redirect:/missoes";
        }
        model.addAttribute("missao", missao);
        model.addAttribute("ninjas", ninjaService.listarTodos());
        return "missao-edit";
    }

    @PostMapping("/missoes/editar/{id}")
    public String editar(@PathVariable Long id,
                         @RequestParam Long ninjaId,
                         @RequestParam String descricao,
                         @RequestParam String rankMissao,
                         @RequestParam String dataInicio,
                         @RequestParam String dataPrevisaoFim,
                         Model model) {

        Missao missao = new Missao();
        missao.setId(id);
        missao.setNinjaId(ninjaId);
        missao.setDescricao(descricao);
        missao.setRankMissao(rankMissao);
        missao.setDataInicio(LocalDateTime.parse(dataInicio));
        missao.setDataPrevisaoFim(LocalDateTime.parse(dataPrevisaoFim));

        String erro = missaoService.atualizar(missao);
        if (erro != null) {
            model.addAttribute("erro", erro);
            model.addAttribute("missao", missao);
            model.addAttribute("ninjas", ninjaService.listarTodos());
            return "missao-edit";
        }

        return "redirect:/missoes";
    }

    @PostMapping("/missoes/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        missaoService.deletar(id);
        return "redirect:/missoes";
    }

    @PostMapping("/missoes/finalizar/{id}")
    public String finalizar(@PathVariable Long id, @RequestParam String resultado) {
        missaoService.finalizar(id, resultado);
        return "redirect:/missoes";
    }
}
