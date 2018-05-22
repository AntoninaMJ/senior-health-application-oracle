package com.antonina.health.controller;

import com.antonina.health.repository.ResultRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/deleteResult")
public class DeleteResultContoller {
    private final ResultRepository resultRepository;

    public DeleteResultContoller(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    @GetMapping
    public String delete(@RequestParam Long id) {
        resultRepository.deleteById(id);
        return "redirect:/history";
    }
}