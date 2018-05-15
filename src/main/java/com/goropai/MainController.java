package com.goropai;

import com.goropai.dao.UserDAO;
import com.goropai.model.User;
import com.goropai.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserValidator userValidator;

//    List<User> users = new ArrayList<>();

    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("msg", "test message!");
        return "index";
    }

    @GetMapping("/view2")
    public String view2(@RequestParam(value = "name", required = false, defaultValue = "Anonimous") String name, Model model) {
        model.addAttribute("msg", "Hello, " + name.toUpperCase());
        return "index";
    }

    @GetMapping("/view3/{name}")
    public String view3(@PathVariable("name") String name, Model model) {
        model.addAttribute("msg", "Hello, " + name);
        return "index";
    }

    @GetMapping("/raw")
    @ResponseBody
    public String view4() {
        return new Date().toString();
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("users", userDAO.getAll());

        return "/users";
    }

    @GetMapping("/users/new")
    public String getSignUp(Model model) {
        //for springform
        model.addAttribute("user", new User());
        return "/sign_up";
    }

    @PostMapping("users/new")
    public String signUp(
//            @RequestParam("name") String name,
//            @RequestParam("surname") String surname,
//            @RequestParam("email") String email
            @ModelAttribute @Valid User user, BindingResult result
    ) throws SQLException {
        userValidator.validate(user, result);
        if (result.hasErrors()) return "/sign_up";
        userDAO.add(user);
        return "redirect:/users";
    }
}
