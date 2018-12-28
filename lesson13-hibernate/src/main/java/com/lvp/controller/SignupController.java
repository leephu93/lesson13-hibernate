package com.lvp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.lvp.model.NV_M;
import com.lvp.service.NV_SERVICE;

@Controller
@RequestMapping("signup/")
public class SignupController {

	@Autowired
	ServletContext context;

	@Autowired
	NV_SERVICE nvs;

	@GetMapping()
	public String GetSignup() {
		return "signup";
	}

	@PostMapping()
	public String PostSignup(ModelMap model, @RequestParam("EMAIL") String email,
			@RequestParam("PASSWORDS") String passwords, @RequestParam("RE_PASSWORDS") String re_passwords,
			@RequestParam("IMAGE") MultipartFile image) {

		
		String path = context.getRealPath("/static/img/" + image.getOriginalFilename());
		
//		model.addAttribute("url_image", context.getContextPath()+"/static/img/" + image.getOriginalFilename());	
//		try {
//			image.transferTo(new File(path));
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		if (email.trim().length() != 0 && passwords.trim().length() != 0 && re_passwords.trim().length() != 0) {
			if (passwords.equals(re_passwords)) {
				NV_M ck_nv = nvs.GETONE(email);
				if (ck_nv != null) {
					model.addAttribute("alert",
							"This account is existed in our system. Choose other account, please!...");
				} else {
					try {
						byte[] binary_img = image.getBytes();
						NV_M nv = new NV_M();
						nv.setEMAIL(email);
						nv.setPASSWORDS(passwords);
						nv.setIMAGE(binary_img);
						boolean rs = nvs.ADDONE(nv);
						if (rs == true) {
							return "redirect:/signin/";
						} else {
							model.addAttribute("alert", "Register unsuccessful...");
						}
					} catch (IOException e) {
						e.printStackTrace();
						model.addAttribute("alert", "Register unsuccessful...");
					}
				}
			} else {
				model.addAttribute("alert", "Passwords and re_passwords are not equally...");
			}
		} else if (email.trim().length() == 0 && passwords.trim().length() == 0 && re_passwords.trim().length() == 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_email", "Email not empty...");
			model.addAttribute("alert_passwords", "Passwords not empty...");
			model.addAttribute("alert_re_passwords", "Re_passwords not empty...");
		} else if (email.trim().length() != 0 && passwords.trim().length() == 0 && re_passwords.trim().length() == 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_passwords", "Passwords not empty...");
			model.addAttribute("alert_re_passwords", "Re_passwords not empty...");
		} else if (email.trim().length() == 0 && passwords.trim().length() != 0 && re_passwords.trim().length() == 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_email", "Email not empty...");
			model.addAttribute("alert_re_passwords", "Re_passwords not empty...");
		} else if (email.trim().length() == 0 && passwords.trim().length() == 0 && re_passwords.trim().length() != 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_email", "Email not empty...");
			model.addAttribute("alert_passwords", "Passwords not empty...");
		} else if (email.trim().length() != 0 && passwords.trim().length() != 0 && re_passwords.trim().length() == 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_re_passwords", "Re_passwords not empty...");
		} else if (email.trim().length() != 0 && passwords.trim().length() != 0 && re_passwords.trim().length() == 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_re_passwords", "Re_passwords not empty...");
		} else if (email.trim().length() == 0 && passwords.trim().length() != 0 && re_passwords.trim().length() != 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_email", "Email not empty...");
		} else if (email.trim().length() != 0 && passwords.trim().length() == 0 && re_passwords.trim().length() != 0) {
			model.addAttribute("alert", "Please! Enter full information...");
			model.addAttribute("alert_passwords", "Passwords not empty...");
		}
		return "signup";
	}

}
