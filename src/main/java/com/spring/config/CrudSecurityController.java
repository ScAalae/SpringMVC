package com.spring.config;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.spring.config.dao.UserDao;

@Controller
@RequestMapping(value = "/login")
public class CrudSecurityController {

	private static Logger logger = LogManager.getLogger(CrudSecurityController.class);

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/readuser")
	public ModelAndView readStudent(ModelAndView model) throws IOException {

		List<com.spring.config.pojo.User> listUser = userDao.read();
		model.addObject("listUser", listUser);
		model.setViewName("readuser");

		logger.debug("-------------Liste Angezeigt----------");

		return model;
	}

	@GetMapping(value = "/createuser")
	public ModelAndView createUser(ModelAndView mv) {

		mv.setViewName("createuser");

		return mv;
	}

	@ModelAttribute("user")
	public User getUser() {
		return new User();
	}

	@PostMapping(value = "/createuser")
	public String createUser(@Valid @ModelAttribute("user") User user, BindingResult binRes,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("roles") String[] roles) {
		if (binRes.hasErrors()) {

			return "createuser";

		} else {
			User u = new User();
			u.setUserame(username);
			u.setPassword(password);
			u.setRoles(roles);

			userDao.create(user);
			logger.debug("-------------Student hinzugefügt----------");

			return "redirect:/login/readuser/";
		}
	}

	//////////////////////// UPDATE///////////////////////////////////////////////////////////////
	@GetMapping(value = "/update/{userId}")
	public ModelAndView findUserById(ModelAndView model, @PathVariable("userId") int userId) throws IOException {

		List<User> listUser = userDao.findUserById(userId);
		model.addObject("user", listUser.get(0));
		model.setViewName("update");

		return model;
	}

	@PostMapping(value = "/update")
	public String updateUser(@Valid @ModelAttribute("user") User user, BindingResult binRes, @RequestParam("id") int id,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("roles") String[] roles, ModelAndView mv) {

		if (binRes.hasErrors()) {

			return "update";

		} else {

			User u = new User();
			u.setUserame(username);
			u.setPassword(password);
			u.setRoles(roles);

			userDao.update(u);
			logger.debug("-------------Update gemacht----------");
			return "redirect:/login/readuser/";
		}

	}

}
