package com.gonzalitos.web.app.controllers;

import static com.gonzalitos.web.app.utils.Texts.ACCION_LABEL;
import static com.gonzalitos.web.app.utils.Texts.AGGRESSOR_LABEL;
import static com.gonzalitos.web.app.utils.Texts.ERROR;
import static com.gonzalitos.web.app.utils.Texts.PAGE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.QUERY_LABEL;
import static com.gonzalitos.web.app.utils.Texts.SAVE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.UNEXPECTED_ERROR;
import static com.gonzalitos.web.app.utils.Texts.URL_LABEL;
import static com.gonzalitos.web.app.utils.Texts.VICTIM_LABEL;

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

import com.gonzalitos.web.app.entities.Victim;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.VictimModel;
import com.gonzalitos.web.app.services.VictimService;

@Controller
@RequestMapping("/victim")
public class VictimController extends OwnController {
	
	@Autowired
	private VictimService victimService;
	
	public VictimController() {
		super("victim-list", "victim-form");
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String guardar(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) VictimModel m, BindingResult result, ModelMap modelo) {
		log.info("METODO: victim.save() -- PARAMETROS: " + m);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				victimService.save(m);
				return "redirect:/victim/list";
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
	public String delete(@ModelAttribute(AGGRESSOR_LABEL) VictimModel m, ModelMap model) {
		log.info("METODO: victim.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			victimService.delete(m.getId());
			return "redirect:/victim/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el Agresor.");
			return formView;
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		VictimModel victim = new VictimModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			victim = victimService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, victim);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public ModelAndView list(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);

		Page<Victim> page = null;
		if (q == null || q.isEmpty()) {
			page = victimService.toList(paginable);
		} else {
			page = victimService.toList(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: victim.toList() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/victim/list");
		modelo.addObject(VICTIM_LABEL, new VictimModel());
		return modelo;
	}


}
