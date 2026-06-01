package com.example.demo.controller;

import com.example.demo.model.Usuario;
import com.example.demo.service.NinjaService;
import com.example.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;
    private final NinjaService ninjaService;

    public LoginController(UsuarioService usuarioService, NinjaService ninjaService) {
        this.usuarioService = usuarioService;
        this.ninjaService = ninjaService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String login,
                             @RequestParam String senha,
                             Model model) {
        Usuario usuario = usuarioService.autenticar(login, senha);

        if (usuario != null) {
            model.addAttribute("nome", usuario.getNome());
            return "redirect:/home?nome=" + usuario.getNome();
        }

        model.addAttribute("erro", "Login ou senha inválidos");
        return "login";
    }

    @GetMapping("/home")
    public String homePage(@RequestParam String nome, Model model) {
        model.addAttribute("nome", nome);
        model.addAttribute("ninjas", ninjaService.listarTodos());
        return "home";
    }
}
