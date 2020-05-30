package com.gonzalitos.web.app.controllers;


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

import com.gonzalitos.web.app.entities.Emergency;
import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.models.EmergencyModel;
import com.gonzalitos.web.app.services.EmergencyService;
import static com.gonzalitos.web.app.utils.Texts.*;



@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/emergency")
public class EmergencyController extends OwnController {
	
	@Autowired
	private EmergencyService emergencyService;
	
	public EmergencyController(String list, String form) {
		super(list, form);
	}
	
	

	@PostMapping("/guardar")
	public String guardar(HttpSession session, @Valid @ModelAttribute(EMERGENCY_LIST_LABEL) EmergencyModel emergency, BindingResult result, ModelMap modelo) {
		log.info("METODO: financiamiento.guardar -- PARAMETROS: " + emergency);
		try {
			if (result.hasErrors()) {
				error(modelo, result);
			} else {
				emergencyService.guardar(emergency);
				return "redirect:/financiamiento/listado";
			}
		} catch (WebException e) {
			modelo.addAttribute(ERROR, "Ocurrió un error al intentar modificar el financiamiento. " + e.getMessage());
		} catch (Exception e) {
			modelo.addAttribute(ERROR, "Ocurrió un error inesperado al intentar modificar el financiamiento.");
			log.error(UNEXPECTED_ERROR, e);
		}
		return formView;
	}

	@PostMapping("/eliminar")
	public String eliminar(@ModelAttribute(EMERGENCY_LABEL) EmergencyModel emergencyM, ModelMap model) {
		log.info("METODO: emergencyM.eliminar() -- PARAMETROS: " + emergencyM);
		model.addAttribute(ACCION_LABEL, "eliminar");
		try {
			emergencyService.eliminar(emergencyM.getId());
			return "redirect:/financiamiento/listado";
		} catch (Exception e) {
			model.addAttribute(ERROR, "Ocurrió un error inesperado al intentar eliminar el financiamiento.");
			return formView;
		}
	}

	@GetMapping("/formulario")
	public ModelAndView formulario(@RequestParam(required = false) String id, @RequestParam(required = false) String accion) {
		ModelAndView model = new ModelAndView(formView);
		EmergencyModel emergency = new EmergencyModel();
		if (accion == null || accion.isEmpty()) {
			accion = SAVE_LABEL;
		}

		if (id != null) {
			emergency = emergencyService.buscar(id);
		}

		model.addObject(EMERGENCY_LABEL, emergency);
		model.addObject(ACCION_LABEL, accion);
		return model;
	}

	@GetMapping("/listado")
	public ModelAndView listar(HttpSession session, Pageable paginable, @RequestParam(required = false) String q) {
		ModelAndView modelo = new ModelAndView(listView);

		Page<Emergency> page = null;
		if (q == null || q.isEmpty()) {
			page = emergencyService.listarActivos(paginable);
		} else {
			page = emergencyService.listarActivos(paginable, q);
			modelo.addObject(QUERY_LABEL, q);
		}
		modelo.addObject(PAGE_LABEL, page);

		log.info("METODO: financiamiento.listar() -- PARAMETROS: " + paginable);

		modelo.addObject(URL_LABEL, "/emergency/listado");
		modelo.addObject(EMERGENCY_LABEL, new EmergencyModel());
		return modelo;
	}


}
