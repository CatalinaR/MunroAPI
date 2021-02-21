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

    @GetMapping("/hillCategory")
    public String getHillType(@RequestParam(name = "type", required=false, defaultValue = "MUN")String name,
                              Model model){
        model.addAttribute("munro", munroService.getMunroDtoList(name));
        return "jsonTemplate";
    }


    @GetMapping("/munroSortedByHeight")
    public String getHillTypeByHeight(@RequestParam(name = "type", required = false, defaultValue = "MUN") String name,
                                      @RequestParam(name = "height", required = false, defaultValue ="ascending") String heightSort,
                                      Model model){
        model.addAttribute("munroByHeight", munroService.getMunroDtoListSortedByHeight(name, heightSort));
        return "jsonTemplate";
    }

}
