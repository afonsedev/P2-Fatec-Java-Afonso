package com.example.demo.model;

import java.time.LocalDateTime;

public class Ninja {

    private Long id;
    private String nome;
    private int idade;
    private String rankNinja;
    private String afinidadeElemental;
    private String statusNinja;
    private LocalDateTime dataCadastro;
    private Long vilaId;
    private Long usuarioId;
    private String vilaNome;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getRankNinja() { return rankNinja; }
    public void setRankNinja(String rankNinja) { this.rankNinja = rankNinja; }

    public String getAfinidadeElemental() { return afinidadeElemental; }
    public void setAfinidadeElemental(String afinidadeElemental) { this.afinidadeElemental = afinidadeElemental; }

    public String getStatusNinja() { return statusNinja; }
    public void setStatusNinja(String statusNinja) { this.statusNinja = statusNinja; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public Long getVilaId() { return vilaId; }
    public void setVilaId(Long vilaId) { this.vilaId = vilaId; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getVilaNome() { return vilaNome; }
    public void setVilaNome(String vilaNome) { this.vilaNome = vilaNome; }
}
