package com.cotiviti.springapp.controller;

import com.cotiviti.springapp.dao.FlightDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class FlightController {

    @Autowired
    FlightDao flightDao;

    @GetMapping("/searchFlight")
    public ModelAndView searchFlight() {
        ModelAndView modelAndView = new ModelAndView("flightSearch");
        List<String> fromCities = flightDao.getFromCities();
        modelAndView.addObject("source",fromCities);
        return modelAndView;
    }

    @GetMapping("/myTickets")
    public String myTickets() {
        return "myTickets";
    }

    @GetMapping("/maindashboard")
    public String mainDashboard() {
        return "mainDashboard";
    }
}
