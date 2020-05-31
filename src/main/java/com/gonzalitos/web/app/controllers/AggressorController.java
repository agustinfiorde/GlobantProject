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

import com.gonzalitos.web.app.entities.Aggressor;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.AggressorModel;
import com.gonzalitos.web.app.services.AggressorService;

@Controller
@RequestMapping("/aggressor")
public class AggressorController extends OwnController {
	
	@Autowired
	private AggressorService aggressorService;
	
	public AggressorController() {
		super("aggressor-list", "aggressor-form");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String save(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) AggressorModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: aggressor.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				aggressorService.save(m);
				return "redirect:/aggressor/list";
			}
		} catch (WebException e) {
			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el Agresor. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el Agresor.");
			log.error(UNEXPECTED_ERROR, e);
		}
		return formView;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(AGGRESSOR_LABEL) AggressorModel m, ModelMap model) {
		log.info("METODO: aggressor.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "delete");
		try {
			aggressorService.delete(m.getId());
			return "redirect:/aggressor/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el Agresor.");
			return formView;
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		AggressorModel aggressor = new AggressorModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			aggressor = aggressorService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, aggressor);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public ModelAndView list(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);

		Page<Aggressor> page = null;
		if (q == null || q.isEmpty()) {
			page = aggressorService.toList(paginable);
		} else {
			page = aggressorService.toList(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: aggressor.toList() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/aggressor/list");
		modelo.addObject(AGGRESSOR_LABEL, new AggressorModel());
		return modelo;
	}
}
