package com.gonzalitos.web.app.controllers;

import static com.gonzalitos.web.app.utils.Texts.ACCION_LABEL;
import static com.gonzalitos.web.app.utils.Texts.AGGRESSOR_LABEL;
import static com.gonzalitos.web.app.utils.Texts.ERROR;
import static com.gonzalitos.web.app.utils.Texts.HELPREQUEST_LABEL;
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

import com.gonzalitos.web.app.entities.HelpRequest;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.HelpRequestModel;
import com.gonzalitos.web.app.services.HelpRequestService;

@Controller
@RequestMapping("/helprequest")
public class HelpRequestController extends OwnController {
	
	@Autowired
	private HelpRequestService helpRequestService;
	
	public HelpRequestController() {
		super("helprequest-list", "helprequest-form");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) HelpRequestModel m, BindingResult result, ModelMap modelo) {
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(AGGRESSOR_LABEL) HelpRequestModel m, ModelMap model) {
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView("frontend/emergencyrequest-form");
		HelpRequestModel helpRequest = new HelpRequestModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			helpRequest = helpRequestService.search(id);
		}

		model.addObject(HELPREQUEST_LABEL, helpRequest);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}
	
	@GetMapping("/formfront")
	public String formFront() {
		return "/frontend/helprequest-form";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public ModelAndView list(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);
		Page<HelpRequest> page = null;
		if (q == null || q.isEmpty()) {
			page = helpRequestService.toList(paginable);
		} else {
			page = helpRequestService.toList(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: helpRequest.toList() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/helprequest/list");
		modelo.addObject(HELPREQUEST_LABEL, new HelpRequestModel());
		return modelo;
	}
}
