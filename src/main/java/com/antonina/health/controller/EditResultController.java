package com.antonina.health.controller;

import com.antonina.health.domain.Result;
import com.antonina.health.form.ResultForm;
import com.antonina.health.repository.ResultRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/editResult")
public class EditResultController {
    private final ResultRepository resultRepository;

    public EditResultController(ResultRepository repository) {
        this.resultRepository = repository;
    }

    @GetMapping
    public String edit(@RequestParam long id, Model model) {
        ResultForm resultForm = new ResultForm();

        Result result = resultRepository.findById(id).get();

        resultForm.setPressureDia(result.getPressureDia());
        resultForm.setPressureSys(result.getPressureSys());
        resultForm.setTemperature(result.getTemperature());
        resultForm.setMood(result.getMood());

        model.addAttribute("id", id);
        model.addAttribute("resultForm", resultForm);
        return "editResult";
    }

    @PostMapping
    public String doEdit(@Validated @ModelAttribute ResultForm resultForm,
                         BindingResult bindingResult,
                         @RequestParam long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "editResult";
        }

        Result result = resultRepository.findById(id).get();
        result.setPressureDia(resultForm.getPressureDia());
        result.setPressureSys(resultForm.getPressureSys());
        result.setTemperature(resultForm.getTemperature());
        result.setMood(resultForm.getMood());
        resultRepository.update(result);

        return "redirect:/history";
    }

}