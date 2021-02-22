package com.example.servingwebcontent.client;

import com.example.servingwebcontent.service.MunroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MunroClient {

    @Autowired
    private MunroService munroService;

    @GetMapping("/munroAll")
    public String getHillType(@RequestParam(name = "type", required=false, defaultValue = "MUN")String name,
                              Model model){
        model.addAttribute("munro", munroService.getMunroDtoList(name));
        return "jsonTemplate";
    }


    @GetMapping("/munro")
    public String getHillTypeByHeight(@RequestParam(name = "type", required = false, defaultValue = "MUN") String name,
                                      @RequestParam(name = "height", required = false, defaultValue ="any") String heightSort,
                                      @RequestParam(name = "nameOrder", required = false, defaultValue ="any") String orderNameMunro,
                                      @RequestParam(name = "limit", required = false, defaultValue = "0") String limit,
                                      @RequestParam(name = "minHeight", required = false, defaultValue = "0") String minHeight,
                                      @RequestParam(name = "maxHeight", required = false, defaultValue = "0") String maxHeight,
                                      Model model){
        model.addAttribute("munro", munroService.getMunroDtoListSortedByFilters(name, heightSort, limit, minHeight, maxHeight, orderNameMunro));
        return "jsonTemplate";
    }

}
