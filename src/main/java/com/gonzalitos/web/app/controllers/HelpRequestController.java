package com.gonzalitos.web.app.controllers;

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
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.services.HelpRequestService;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/helprequest")
public class HelpRequestController extends OwnController {
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	public HelpRequestController() {
		super("helprequest-list", "helprequest-form");
	}

	@PostMapping("/save")
	public String guardar(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) HelpRequestModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: helpRequest.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				helpRequestService.save(m);
				return "redirect:/helprequest/list";
			}
		} catch (WebException e) {
			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el helpRequest. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el helpRequest.");
			log.error(UNEXPECTED_ERROR, e);
		}
		return formView;
	}

	@PostMapping("/delete")
	public String eliminar(@ModelAttribute(AGGRESSOR_LABEL) HelpRequestModel m, ModelMap model) {
		log.info("METODO: helpRequest.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			helpRequestService.delete(m.getId());
			return "redirect:/helpRequest/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el helpRequest.");
			return formView;
		}
	}

	@GetMapping("/form")
	public ModelAndView formulario(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		HelpRequestModel helpRequest = new HelpRequestModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			helpRequest = helpRequestService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, helpRequest);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}

}
