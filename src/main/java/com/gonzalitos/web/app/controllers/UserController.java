package com.gonzalitos.web.app.controllers;

import static com.gonzalitos.web.app.utils.Texts.ACCION_LABEL;
import static com.gonzalitos.web.app.utils.Texts.ERROR;
import static com.gonzalitos.web.app.utils.Texts.SAVE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.UNEXPECTED_ERROR;
import static com.gonzalitos.web.app.utils.Texts.USER_LABEL;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.UserModel;
import com.gonzalitos.web.app.services.UserService;


@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/user")
public class UserController extends OwnController {
	
	@Autowired
	private UserService userService;
	
	public UserController() {
		super("user-list", "user-form");
	}

	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(USER_LABEL) UserModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: user.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				userService.save(m);
				return "redirect:/user/list";
			}
		} catch (WebException e) {
			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el Agresor. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el Agresor.");
			log.error(UNEXPECTED_ERROR, e);
		}
		return formView;
	}

	@PostMapping("/delete")
	public String delete(@ModelAttribute(USER_LABEL) UserModel m, ModelMap model) {
		log.info("METODO: user.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			userService.delete(m.getId());
			return "redirect:/user/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el Agresor.");
			return formView;
		}
	}

	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		UserModel user = new UserModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			user = userService.search(id);
		}
		model.addObject(USER_LABEL, user);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}


}