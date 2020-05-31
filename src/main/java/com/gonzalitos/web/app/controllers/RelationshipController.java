package com.gonzalitos.web.app.controllers;

import static com.gonzalitos.web.app.utils.Texts.ACCION_LABEL;
import static com.gonzalitos.web.app.utils.Texts.AGGRESSOR_LABEL;
import static com.gonzalitos.web.app.utils.Texts.ERROR;
import static com.gonzalitos.web.app.utils.Texts.PAGE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.QUERY_LABEL;
import static com.gonzalitos.web.app.utils.Texts.SAVE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.UNEXPECTED_ERROR;
import static com.gonzalitos.web.app.utils.Texts.URL_LABEL;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.gonzalitos.web.app.entities.Relationship;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.models.RelationshipModel;
import com.gonzalitos.web.app.services.RelationshipService;

@Controller
@RequestMapping("/relationship")
public class RelationshipController extends OwnController{

	@Autowired
	private RelationshipService relationshipService;

	public RelationshipController() {
		super("relationship-list", "relationship-form");
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) RelationshipModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: relationship.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				relationshipService.save(m);
				return "redirect:/helprequest/list";
			}
		} catch (WebException e) {
			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el relationship. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el relationship.");
			log.error(UNEXPECTED_ERROR, e);
		}
		return formView;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(AGGRESSOR_LABEL) HelpRequestModel m, ModelMap model) {
		log.info("METODO: relationship.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			relationshipService.delete(m.getId());
			return "redirect:/relationship/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el relationship.");
			return formView;
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public ModelAndView list(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);
		Page<Relationship> page = null;
		if (q == null || q.isEmpty()) {
			page = relationshipService.toList(paginable);
		} else {
			page = relationshipService.toList(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: relationship.toList() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/helprequest/list");
		modelo.addObject(AGGRESSOR_LABEL, new HelpRequestModel());
		return modelo;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView("frontend/emergencyrequest-form");
		RelationshipModel relationship = new RelationshipModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			relationship = relationshipService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, relationship);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}
	
}
