package com.example.demo.service;

import com.example.demo.model.Usuario;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UsuarioService {

    private final DataSource dataSource;

    public UsuarioService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Usuario autenticar(String login, String senha) {
        String sql = "SELECT nome, login, senha FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                return usuario;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
