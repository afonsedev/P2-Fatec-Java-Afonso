package com.example.demo.service;

import com.example.demo.model.Missao;
import com.example.demo.model.Ninja;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MissaoService {

    private final DataSource dataSource;

    public MissaoService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Missao> listarTodas() {
        String sql = "SELECT m.id, m.ninja_id, m.descricao, m.rank_missao, m.data_inicio, "
                   + "m.data_previsao_fim, m.data_fim, m.status, n.nome AS ninja_nome, n.rank_ninja AS ninja_rank "
                   + "FROM missao m JOIN ninja n ON m.ninja_id = n.id ORDER BY m.data_inicio DESC";

        List<Missao> missoes = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                missoes.add(mapMissao(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return missoes;
    }

    public Missao buscarPorId(Long id) {
        String sql = "SELECT m.id, m.ninja_id, m.descricao, m.rank_missao, m.data_inicio, "
                   + "m.data_previsao_fim, m.data_fim, m.status, n.nome AS ninja_nome, n.rank_ninja AS ninja_rank "
                   + "FROM missao m JOIN ninja n ON m.ninja_id = n.id WHERE m.id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapMissao(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String criar(Missao missao) {
        // Regra de negócio: missão só pode ser feita por ninja de mesmo rank
        Ninja ninja = buscarNinjaPorId(missao.getNinjaId());
        if (ninja == null) {
            return "Ninja não encontrado.";
        }
        if (!ninja.getRankNinja().equalsIgnoreCase(missao.getRankMissao())) {
            return "O ninja deve ter o mesmo rank da missão. Ninja: " + ninja.getRankNinja() + ", Missão: " + missao.getRankMissao();
        }

        String sql = "INSERT INTO missao (ninja_id, descricao, rank_missao, data_inicio, data_previsao_fim, status) "
                   + "VALUES (?, ?, ?, ?, ?, 'EM_ANDAMENTO')";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, missao.getNinjaId());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getRankMissao());
            stmt.setTimestamp(4, Timestamp.valueOf(missao.getDataInicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(missao.getDataPrevisaoFim()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao criar missão.";
        }

        return null; // sem erro
    }

    public String atualizar(Missao missao) {
        // Regra de negócio: missão só pode ser feita por ninja de mesmo rank
        Ninja ninja = buscarNinjaPorId(missao.getNinjaId());
        if (ninja == null) {
            return "Ninja não encontrado.";
        }
        if (!ninja.getRankNinja().equalsIgnoreCase(missao.getRankMissao())) {
            return "O ninja deve ter o mesmo rank da missão. Ninja: " + ninja.getRankNinja() + ", Missão: " + missao.getRankMissao();
        }

        String sql = "UPDATE missao SET ninja_id = ?, descricao = ?, rank_missao = ?, data_inicio = ?, data_previsao_fim = ? WHERE id = ? AND status = 'EM_ANDAMENTO'";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, missao.getNinjaId());
            stmt.setString(2, missao.getDescricao());
            stmt.setString(3, missao.getRankMissao());
            stmt.setTimestamp(4, Timestamp.valueOf(missao.getDataInicio()));
            stmt.setTimestamp(5, Timestamp.valueOf(missao.getDataPrevisaoFim()));
            stmt.setLong(6, missao.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao atualizar missão.";
        }

        return null;
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM missao WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void finalizar(Long id, String resultado) {
        String sql = "UPDATE missao SET status = ?, data_fim = ? WHERE id = ? AND status = 'EM_ANDAMENTO'";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, resultado); // SUCESSO ou FRACASSO
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(3, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Ninja buscarNinjaPorId(Long id) {
        String sql = "SELECT id, nome, rank_ninja FROM ninja WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Ninja ninja = new Ninja();
                ninja.setId(rs.getLong("id"));
                ninja.setNome(rs.getString("nome"));
                ninja.setRankNinja(rs.getString("rank_ninja"));
                return ninja;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Missao mapMissao(ResultSet rs) throws SQLException {
        Missao missao = new Missao();
        missao.setId(rs.getLong("id"));
        missao.setNinjaId(rs.getLong("ninja_id"));
        missao.setDescricao(rs.getString("descricao"));
        missao.setRankMissao(rs.getString("rank_missao"));
        Timestamp tsInicio = rs.getTimestamp("data_inicio");
        missao.setDataInicio(tsInicio != null ? tsInicio.toLocalDateTime() : null);
        Timestamp tsPrevisao = rs.getTimestamp("data_previsao_fim");
        missao.setDataPrevisaoFim(tsPrevisao != null ? tsPrevisao.toLocalDateTime() : null);
        Timestamp tsFim = rs.getTimestamp("data_fim");
        missao.setDataFim(tsFim != null ? tsFim.toLocalDateTime() : null);
        missao.setStatus(rs.getString("status"));
        missao.setNinjaNome(rs.getString("ninja_nome"));
        missao.setNinjaRank(rs.getString("ninja_rank"));
        return missao;
    }
}
