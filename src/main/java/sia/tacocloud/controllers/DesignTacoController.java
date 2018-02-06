package sia.tacocloud.controllers;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import sia.tacocloud.concepts.Ingredient;
import sia.tacocloud.concepts.Ingredient.Type;
import sia.tacocloud.concepts.Taco;
import sia.tacocloud.repositories.IngredientRepository;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(ingredients::add);
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), 
				filterByType(ingredients, type));
		}
		
		model.addAttribute("taco", new Taco());
		return "design";
	}
	
	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors) {
		if (errors.hasErrors()) {
			return "design";
		}
		
		log.info("Processing design: "+ taco);
		return "redirect:/orders/current";
	}

	private static List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter( (ingredient) -> ingredient.getType() == type)
				.collect( toList());
	}
}
