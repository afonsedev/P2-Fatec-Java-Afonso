package com.example.demo.controller;

import com.example.demo.model.Ninja;
import com.example.demo.service.NinjaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/ninjas")
    public String listar(Model model) {
        model.addAttribute("ninjas", ninjaService.listarTodos());
        return "ninjas";
    }

    @GetMapping("/ninjas/novo")
    public String formulario(Model model) {
        model.addAttribute("vilas", ninjaService.listarVilas());
        return "ninja-form";
    }

    @PostMapping("/ninjas/novo")
    public String criar(@RequestParam String nome,
                        @RequestParam int idade,
                        @RequestParam String rankNinja,
                        @RequestParam String afinidadeElemental,
                        @RequestParam Long vilaId,
                        @RequestParam Long usuarioId) {

        Ninja ninja = new Ninja();
        ninja.setNome(nome);
        ninja.setIdade(idade);
        ninja.setRankNinja(rankNinja);
        ninja.setAfinidadeElemental(afinidadeElemental);
        ninja.setStatusNinja("ATIVO");
        ninja.setVilaId(vilaId);
        ninja.setUsuarioId(usuarioId);

        ninjaService.criar(ninja);
        return "redirect:/ninjas";
    }

    @GetMapping("/ninjas/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Ninja ninja = ninjaService.buscarPorId(id);
        if (ninja == null) {
            return "redirect:/ninjas";
        }
        model.addAttribute("ninja", ninja);
        model.addAttribute("vilas", ninjaService.listarVilas());
        return "ninja-edit";
    }

    @PostMapping("/ninjas/editar/{id}")
    public String editar(@PathVariable Long id,
                         @RequestParam String nome,
                         @RequestParam int idade,
                         @RequestParam String rankNinja,
                         @RequestParam String afinidadeElemental,
                         @RequestParam String statusNinja,
                         @RequestParam Long vilaId) {

        Ninja ninja = new Ninja();
        ninja.setId(id);
        ninja.setNome(nome);
        ninja.setIdade(idade);
        ninja.setRankNinja(rankNinja);
        ninja.setAfinidadeElemental(afinidadeElemental);
        ninja.setStatusNinja(statusNinja);
        ninja.setVilaId(vilaId);

        ninjaService.atualizar(ninja);
        return "redirect:/ninjas";
    }

    @PostMapping("/ninjas/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        ninjaService.deletar(id);
        return "redirect:/ninjas";
    }
}
