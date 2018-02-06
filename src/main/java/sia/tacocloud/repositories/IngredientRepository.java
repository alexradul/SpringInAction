package sia.tacocloud.repositories;

import sia.tacocloud.concepts.Ingredient;

public interface IngredientRepository {
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
    Iterable<Ingredient> findAll();
}
