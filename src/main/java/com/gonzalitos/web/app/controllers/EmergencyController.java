package com.gonzalitos.web.app.controllers;

import static com.gonzalitos.web.app.utils.Texts.ACCION_LABEL;
import static com.gonzalitos.web.app.utils.Texts.AGGRESSOR_LABEL;
import static com.gonzalitos.web.app.utils.Texts.ERROR;
import static com.gonzalitos.web.app.utils.Texts.PAGE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.QUERY_LABEL;
import static com.gonzalitos.web.app.utils.Texts.SAVE_LABEL;
import static com.gonzalitos.web.app.utils.Texts.URL_LABEL;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gonzalitos.web.app.entities.Emergency;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.services.EmergencyService;

@Controller
@RequestMapping("/emergency")
public class EmergencyController extends OwnController {
	
	@Autowired
	private EmergencyService emergencyService;
	
	public EmergencyController() {
		super("emergency-list", "emergency-form");
	}


//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@PostMapping("/save")
//	public String save(HttpSession session, @Valid @ModelAttribute(AGGRESSOR_LABEL) EmergencyModel m, BindingResult result, ModelMap modelo) {
//		log.info("METODO: emergency.save() -- PARAMETROS: " + m);
//		try {
//			if (result.hasErrors()) {
//				error(modelo, result);
//			} else {
//				//GENERA OTRO SAVE 
////				emergencyService.save(m);
//				return "redirect:/emergency/list";
//			}
//		} catch (WebException e) {
//			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el Agresor. " + e.getMessage());
//		} catch (Exception e) {
//			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el Agresor.");
//			log.error(UNEXPECTED_ERROR, e);
//		}
//		return formView;
//	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/delete")
	public String delete(@ModelAttribute(AGGRESSOR_LABEL) EmergencyModel m, ModelMap model) {
		log.info("METODO: emergency.delete() -- PARAMETROS: " + m);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			emergencyService.delete(m.getId());
			return "redirect:/emergency/list";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el Agresor.");
			return formView;
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public ModelAndView form(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		EmergencyModel emergency = new EmergencyModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			emergency = emergencyService.search(id);
		}

		model.addObject(AGGRESSOR_LABEL, emergency);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public ModelAndView list(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);

		Page<Emergency> page = null;
		if (q == null || q.isEmpty()) {
			page = emergencyService.toList(paginable);
		} else {
			page = emergencyService.toList(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: emergency.toList() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/emergency/list");
		modelo.addObject(AGGRESSOR_LABEL, new EmergencyModel());
		return modelo;
	}
	
	@GetMapping("/formfront")
	public String formFront() {
		return "/frontend/emergencyrequest-form";
	}


}