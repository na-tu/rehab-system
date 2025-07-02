package rehab_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String showHome(Model model){
    model.addAttribute("message","患者・リハビリ管理システム");
    return "home";
  }
}
