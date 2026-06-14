package com.example.demo.model;

import java.time.LocalDateTime;

public class Missao {

    private Long id;
    private Long ninjaId;
    private String descricao;
    private String rankMissao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataPrevisaoFim;
    private LocalDateTime dataFim;
    private String status; // EM_ANDAMENTO, SUCESSO, FRACASSO
    private String ninjaNome;
    private String ninjaRank;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getNinjaId() { return ninjaId; }
    public void setNinjaId(Long ninjaId) { this.ninjaId = ninjaId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getRankMissao() { return rankMissao; }
    public void setRankMissao(String rankMissao) { this.rankMissao = rankMissao; }

    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }

    public LocalDateTime getDataPrevisaoFim() { return dataPrevisaoFim; }
    public void setDataPrevisaoFim(LocalDateTime dataPrevisaoFim) { this.dataPrevisaoFim = dataPrevisaoFim; }

    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNinjaNome() { return ninjaNome; }
    public void setNinjaNome(String ninjaNome) { this.ninjaNome = ninjaNome; }

    public String getNinjaRank() { return ninjaRank; }
    public void setNinjaRank(String ninjaRank) { this.ninjaRank = ninjaRank; }
}
