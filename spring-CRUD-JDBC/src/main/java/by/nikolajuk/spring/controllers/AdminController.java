package by.nikolajuk.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import by.nikolajuk.spring.dao.PersonDAO;
import by.nikolajuk.spring.models.Person;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final PersonDAO personDAO;

	@Autowired
	public AdminController(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@GetMapping()
	public String adminPage(Model model, @ModelAttribute("person") Person person) {
		model.addAttribute("people", personDAO.index());

		return "adminPage";
	}

	@PatchMapping("/add")
	public String makeAdmin(@ModelAttribute("person") Person person) {
		System.out.println(person.getId());

		return "redirect:/people";
	}

}
