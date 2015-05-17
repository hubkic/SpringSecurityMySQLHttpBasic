package demo;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class mainController {
	
	@RequestMapping("/hello")
	public @ResponseBody String hello(){
		
		return "jol";
	}
	
	@RequestMapping("/login")
	public @ResponseBody String login(){
		
		return "elo";
	}
	
	@RequestMapping("/admin/login")
	public @ResponseBody String adminLogin(){
		
		return "ADMIN elo";
	}
	
	@RequestMapping("/jakasStronka")
	public @ResponseBody String jakasStronka(){
		
		return "Stronka zabezpieczona musisz byc ROLE_USER";
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accesssDenied() {
 
	  ModelAndView model = new ModelAndView();
 
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
 
	  model.setViewName("403");
	  return model.toString();
 
	}
}
