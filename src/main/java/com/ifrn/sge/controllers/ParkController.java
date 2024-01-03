package com.ifrn.sge.controllers;

import com.ifrn.sge.models.Park;
import com.ifrn.sge.models.User;
import com.ifrn.sge.repositories.ParkRepository;
import com.ifrn.sge.repositories.UserRepository;
import com.mysql.cj.xdevapi.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/vaga")
public class ParkController {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ParkRepository pr;

    @GetMapping("")
    @PreAuthorize("hasRole('CLIENT')")
    public ModelAndView schedulePage(Park park) {
        List<Park> parks = pr.findAll();
        return new ModelAndView("park/scheduleForm").addObject("parks", parks).addObject("park", park);
    }

    @RequestMapping("/listar")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ModelAndView listAllPark() {
        List<Park> opt = pr.findAll();
        return new ModelAndView("park/listAllPark").addObject("parks", opt);
    }

    @GetMapping("/adicionar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView addPage(Park park) {
        return new ModelAndView("park/addPage.html").addObject("park", park);
    }

    @PostMapping("/adicionar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView addPark(@Valid @ModelAttribute("park") Park park, BindingResult br, RedirectAttributes ra) {

        if(br.hasErrors()) {
            return addPage(park);
        }

        pr.save(park);
        ra.addFlashAttribute("mensagem", "Vaga salva com sucesso");
        return new ModelAndView("redirect:/vaga/listar");

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPERVISOR')")
    public ModelAndView emptyPark(@PathVariable String id) {
        Optional<Park> opt = pr.findById(id);

        if (opt.isEmpty()) {
            return new ModelAndView("redirect:/vaga/listar");
        }

        Park park = new Park();
        park.setId(id);

        pr.save(park);

        return new ModelAndView("redirect:/vaga/listar");

    }

    @GetMapping("/deletar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ModelAndView deletePark(@PathVariable String id, RedirectAttributes ra) {
        Optional<Park> opt = pr.findById(id);

        if (opt.isEmpty()) {
            return new ModelAndView("redirect:/vaga/listar");
        }

        pr.delete(opt.get());
        ra.addFlashAttribute("mensagem", "Vaga deletada com sucesso!");
        return new ModelAndView("redirect:/vaga/listar");
    }

    @PostMapping("/agendar")
    @PreAuthorize("hasRole('CLIENT')")
    public ModelAndView schedulePark(@Valid @ModelAttribute("park") Park park, BindingResult br, RedirectAttributes ra) {
        if(br.hasErrors()) {
            return addPage(park);
        }
        if (!(Objects.equals(pr.findById(park.getId()).get().getClient().getName(), ""))) {
            ra.addFlashAttribute("mensagem", "Vaga já ocupada!");
            return new ModelAndView("redirect:/vaga");
        }

        User user = getUser();
        park.setClient(user);

        pr.save(park);
        ra.addFlashAttribute("mensagem", "Vaga agendada com sucesso");
        return new ModelAndView("redirect:/");
    }

   public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return ur.findByUsername(user.getUsername()).get();
    }
}
