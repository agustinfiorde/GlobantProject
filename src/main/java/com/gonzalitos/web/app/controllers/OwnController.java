package com.gonzalitos.web.app.controllers;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.gonzalitos.web.app.errors.WebException;
import com.gonzalitos.web.app.utils.Texts;

public abstract class OwnController {
	
	protected String listView;
	protected String formView;

	protected Log log;

	public OwnController(String list, String form) {
		this.listView = list;
		this.formView = form;
		this.log = LogFactory.getLog(getClass());
	}

	public String getUsuario() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@SuppressWarnings("rawtypes")
	public String getRol() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Iterator it = auth.getAuthorities().iterator();
		while (it.hasNext()) {
			return it.next().toString();
		}
		return "";
	}

	public boolean isAdministrador() {
		return getRol().equals("ROLE_ADMINISTRADOR");
	}

	public boolean isAdministrativo() {
		return getRol().equals("ROLE_ADMINISTRATIVO");
	}

	public void error(ModelAndView model, Exception e) {
		model.addObject(Texts.ERROR, "Ocurrio un error inesperado mientras se ejecutaba la acción.");
		log.error("Error inesperado", e);
	}

	public void error(ModelAndView model, WebException e) {
		model.addObject(Texts.ERROR, e.getMessage());
	}

	public void error(ModelMap modelo, BindingResult resultado) {
		StringBuilder msg = new StringBuilder();
		for (ObjectError o : resultado.getAllErrors()) {
			msg.append(o.getDefaultMessage() + System.getProperty("line.separator"));
		}
		log.info("Error: " + msg.toString());
		modelo.addAttribute(Texts.ERROR, msg.toString());
	}

	public void error(ModelAndView model, String e) {
		model.addObject(Texts.ERROR, e);
//		model.setViewName(vistaFormulario);
	}

	public void error(Model model, String e) {
		model.addAttribute(Texts.ERROR, e);
	}
}
