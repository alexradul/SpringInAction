package sia.tacocloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("orders")
public class OrderController {
	
	@GetMapping("/current")
	public String orderForm(Model model) {
		
		return "orderForm";
	}
}
