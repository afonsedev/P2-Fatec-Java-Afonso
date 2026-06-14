package com.example.demo.controller;

import com.example.demo.model.Ninja;
import com.example.demo.service.NinjaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}
