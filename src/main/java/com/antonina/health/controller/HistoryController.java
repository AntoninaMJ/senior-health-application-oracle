package com.antonina.health.controller;

import com.antonina.health.repository.ResultRepository;
import com.antonina.health.repository.paging.PageRequest;
import com.antonina.health.repository.paging.Sort;
import com.antonina.health.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final ResultRepository resultRepository;
    private final UserService userService;

    public HistoryController(ResultRepository resultRepository, UserService userService) {
        this.resultRepository = resultRepository;
        this.userService = userService;
    }

    @GetMapping
    public String history(@RequestParam(required = false, defaultValue = "0") int page,
                          @RequestParam(required = false, defaultValue = "date_time") String sort,
                          @RequestParam(required = false, defaultValue = "desc") String dir,
                          Model model) {

        Sort.Direction direction = Sort.Direction.fromString(dir);
        Sort sortBy = Sort.by(direction, sort);
        PageRequest pageRequest = PageRequest.of(page, 8, sortBy);

        Long userId = userService.getLoggedUser().getId();
        model.addAttribute("results", resultRepository.findByUserId(userId, pageRequest));
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "history";
    }
}