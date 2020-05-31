package com.gonzalitos.web.app.converters;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gonzalitos.web.app.entities.User;
import com.gonzalitos.web.app.models.UserModel;
import com.gonzalitos.web.app.repositories.UserRepository;

@Component("UserConverter")
public class UserConverter  extends OwnConverter<UserModel, User>{

	@Autowired
	private UserRepository userRepository;
	
	public UserModel entityToModel(User entity){
		UserModel model = new UserModel();
		try {
			BeanUtils.copyProperties(entity, model);
		} catch (Exception e) {
			log.error("Error al convertir la entity en el modelo del Usuario", e);
		}
		
		return model;
	}
	
	public User modelToEntity(UserModel model){
		User user = new User();
		if(model.getId() != null && !model.getId().isEmpty()){
			user = userRepository.getOne(model.getId());
		}
		
		try {
			BeanUtils.copyProperties(model, user);
		} catch (Exception e) {
			log.error("Error al convertir el modelo del Usuario en entity", e);
		}
		
		return user;
	}
	
	public List<UserModel> entitiesToModels(List<User> entities ){
		List<UserModel> model = new ArrayList<>();
		for(User a : entities){
			model.add(entityToModel(a));
		}
		return model;
	}

	@Override
	public List<User> modelsToEntities(List<UserModel> m) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONArray entitiesTOJSON(List<User> users) {
		JSONArray result = new JSONArray();
		try {
			for (User a : users) {
				result.put(entityTOJSON(a));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	public JSONObject entityTOJSON(User user) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", user.getId());
			object.put("name", user.getName());
			object.put("lastName", user.getLastName());
			object.put("dni", user.getDni());
			object.put("email", user.getEmail());
			object.put("password", user.getPassword());
			object.put("role", user.getRole());
			return object;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
