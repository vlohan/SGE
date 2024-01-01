package com.ifrn.sge.controllers;

import com.ifrn.sge.models.Park;
import com.ifrn.sge.repositories.ParkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vaga")
public class ParkController {

    @Autowired
    ParkRepository pr;

    @RequestMapping("")
    public ModelAndView schedulePark() {
        return new ModelAndView("park/scheduleForm");
    }

    @RequestMapping("/listar")
    public ModelAndView listAllPark() {
        List<Park> opt = pr.findAll();
        return new ModelAndView("park/listAllPark").addObject("parks", opt);
    }
}
