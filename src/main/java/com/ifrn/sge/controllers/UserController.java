package com.ifrn.sge.controllers;

import com.ifrn.sge.models.Role;
import com.ifrn.sge.models.User;
import com.ifrn.sge.repositories.RoleRepository;
import com.ifrn.sge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private RoleRepository rr;
    @Autowired
    private UserRepository ur;

    @GetMapping("")
    public ModelAndView listAll() {
        return new ModelAndView("user/listAll").addObject("users", ur.findAll());
    }

    @GetMapping("/adicionar")
    public ModelAndView addPage(User user) {
        return new ModelAndView("user/addPage").addObject("user", user);
    }

    @PostMapping("/adicionar")
    public ModelAndView addUser(@Valid @ModelAttribute("user") User user, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return addPage(user);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPassword(encoder.encode(user.getPassword()));
        Role role = rr.findByName("ROLE_CLIENT").get();
        ArrayList<Role> list = new ArrayList<Role>();
        list.add(role);
        newUser.setRoles(list);

        ur.save(newUser);
        ra.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
        return new ModelAndView("redirect:/usuarios");
    }

    @GetMapping("/deletar/{id}")
    public ModelAndView removeUser(@PathVariable Long id, RedirectAttributes ra){

        Optional<User> opt = ur.findById(id);

        if (opt.isEmpty()) {
            return new ModelAndView("redirect:/usuarios");
        }

        ur.delete(opt.get());
        ra.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
        return new ModelAndView("redirect:/usuarios");
    }

}
