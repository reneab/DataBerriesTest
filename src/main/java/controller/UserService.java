package main.java.controller;

import java.util.LinkedHashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import main.java.bean.User;
import main.java.dao.UserDao;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/user")
public class UserService {

	@GET
	@Produces("application/json")
	public String getUser(@QueryParam("name") String userName)
			throws JSONException {

		UserDao dao = UserDao.getInstance();
				
		User user = dao.getUser(userName);
		
		JSONObject jsonObject = new JSONObject();
		if (user == null) {
			jsonObject.put("error", "User not found in database !");
		} else {
			jsonObject.put("name", user.getName());
			jsonObject.put("age", user.getAge());
		}

		return jsonObject.toString();
	}
}
