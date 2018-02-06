package sia.tacocloud.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sia.tacocloud.concepts.Ingredient;
import sia.tacocloud.repositories.IngredientRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbcTemplate.queryForObject("select id, name, type from Ingredient where id = ?",
                JdbcIngredientRepository::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcTemplate.update("INSERT INTO Ingredient (id, name, type) VALUES (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType());
        return ingredient;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcTemplate.query("select id, name, type from Ingredient",
                JdbcIngredientRepository::mapRowToIngredient);
    }

    static Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(
                rs.getString("id"),
                rs.getString("name"),
                Ingredient.Type.valueOf(rs.getString("type"))
        );
    }
}
