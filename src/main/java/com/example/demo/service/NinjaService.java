package com.example.demo.service;

import com.example.demo.model.Ninja;
import com.example.demo.model.Vila;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class NinjaService {

    private final DataSource dataSource;

    public NinjaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Ninja> listarTodos() {
        String sql = "SELECT n.id, n.nome, n.idade, n.rank_ninja, n.afinidade_elemental, "
                   + "n.status_ninja, n.data_cadastro, n.vila_id, n.usuario_id, v.nome AS vila_nome "
                   + "FROM ninja n JOIN vila v ON n.vila_id = v.id ORDER BY n.nome";

        List<Ninja> ninjas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ninja ninja = new Ninja();
                ninja.setId(rs.getLong("id"));
                ninja.setNome(rs.getString("nome"));
                ninja.setIdade(rs.getInt("idade"));
                ninja.setRankNinja(rs.getString("rank_ninja"));
                ninja.setAfinidadeElemental(rs.getString("afinidade_elemental"));
                ninja.setStatusNinja(rs.getString("status_ninja"));
                ninja.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                ninja.setVilaId(rs.getLong("vila_id"));
                ninja.setUsuarioId(rs.getLong("usuario_id"));
                ninja.setVilaNome(rs.getString("vila_nome"));
                ninjas.add(ninja);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ninjas;
    }

    public void criar(Ninja ninja) {
        String sql = "INSERT INTO ninja (nome, idade, rank_ninja, afinidade_elemental, status_ninja, vila_id, usuario_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ninja.getNome());
            stmt.setInt(2, ninja.getIdade());
            stmt.setString(3, ninja.getRankNinja());
            stmt.setString(4, ninja.getAfinidadeElemental());
            stmt.setString(5, ninja.getStatusNinja());
            stmt.setLong(6, ninja.getVilaId());
            stmt.setLong(7, ninja.getUsuarioId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Vila> listarVilas() {
        String sql = "SELECT id, nome, pais FROM vila ORDER BY nome";
        List<Vila> vilas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vila vila = new Vila();
                vila.setId(rs.getLong("id"));
                vila.setNome(rs.getString("nome"));
                vila.setPais(rs.getString("pais"));
                vilas.add(vila);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vilas;
    }
}
