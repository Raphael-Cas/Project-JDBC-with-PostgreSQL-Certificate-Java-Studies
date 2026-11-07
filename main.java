
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Dados de conexão (altere usuário e senha conforme o seu PostgreSQL)
    static final String URL = "jdbc:postgresql://localhost:5432/biblioteca";
    static final String USUARIO = "yours";
    static final String SENHA = "yours";

    public static void main(String[] args) {

        String sql = "SELECT id, titulo, autor, ano_publicacao, disponivel FROM livro ORDER BY id";

        List<Livro> livros = new ArrayList<>();

        // try-with-resources fecha Connection, Statement e ResultSet automaticamente
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement statement = conexao.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            System.out.println("Conexão aberta com sucesso!\n");

            // Percorre cada linha do resultado
            while (rs.next()) {
                // Criação manual do objeto, preenchendo campo por campo
                Livro livro = new Livro();
                livro.setId(rs.getInt("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setAutor(rs.getString("autor"));
                livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
                livro.setDisponivel(rs.getBoolean("disponivel"));

                livros.add(livro);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
            return;
        }

        // Impressão dos objetos encontrados
        System.out.println("Livros encontrados: " + livros.size());
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }
}
