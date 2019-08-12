package com.javaniu.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {

	@RequestMapping(value = "/*/**", method = RequestMethod.GET)
	public void page(ModelMap model) throws Exception {
	}
}
