package main.java.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import main.java.bean.User;
import main.java.dao.UserDao;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/user")
public class UserService {

	@Path("{name}")
	@GET
	@Produces("application/json")
	public String getUser(@PathParam("name") String userName)
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
