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
import com.gonzalitos.web.app.models.AggressionTypeModel;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/aggressiontype")
public class AggressionTypeController extends OwnController {
	
	@Autowired
	private AggressionTypeService aggressiontypeService;
	
	public AggressionTypeController() {
		super("aggressiontype-list", "aggressiontype-form");
	}

	@PostMapping("/save")
	public String guardar(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) AggressionTypeModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: aggressionType.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				aggressiontypeService.save(m);
				return "redirect:/aggressiontype/list";
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
	public String eliminar(@ModelAttribute(AGGRESSOR_LABEL) AggressionTypeModel m, ModelMap model) {
		log.info("METODO: aggressionType.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			aggressiontypeService.delete(m.getId());
			return "redirect:/aggressiontype/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el Agresor.");
			return formView;
		}
	}

	@GetMapping("/form")
	public ModelAndView formulario(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		AggressionTypeModel aggressiontype = new AggressionTypeModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			aggressiontype = aggressiontypeService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, aggressiontype);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}




}
