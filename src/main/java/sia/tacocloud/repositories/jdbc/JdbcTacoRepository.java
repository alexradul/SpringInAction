package sia.tacocloud.repositories.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import sia.tacocloud.concepts.Ingredient;
import sia.tacocloud.concepts.Taco;
import sia.tacocloud.repositories.TacoRepository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Taco save(Taco design) {
        long tacoId = persistTaco(design);
        design.getIngredients().forEach(ingredient -> persistTacoIngredient(ingredient, tacoId));

        return null;
    }

    private long persistTaco(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreator preparedStatementCreator =
                new PreparedStatementCreatorFactory(
                        "insert into Taco(name, createdAt) values (?,  ?)", Types.VARCHAR, Types.TIMESTAMP)
                            .newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void persistTacoIngredient(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update("insert into Taco_Ingredients(taco, ingredient) values (?, ?)",
            tacoId, ingredient.getId());
    }
}
