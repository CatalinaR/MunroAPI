package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.MunroDto;
import com.example.servingwebcontent.process.LoadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunroService {

    private List<MunroDto> munroDtoList;

    @Autowired
    private MunroService(LoadData loadData) {
        munroDtoList = loadData.processCSVFile();
    }

    public List<MunroDto> getMunroDtoList(){
        return this.munroDtoList;
    }

    public List<MunroDto> getMunroDtoList(String name){
        return munroDtoList.stream().filter(a -> a.getHillCategory().equals(name)).collect(Collectors.toList());
    }

    public List<MunroDto> getMunroDtoListSortedByHeight(String name, String heightSort){
        if(heightSort.toLowerCase().equals("ascending")){
            return munroDtoList.stream()
                                .filter(a -> a.getHillCategory().equals(name))
                                .sorted(Comparator.comparing(MunroDto::getHeightMeters))
                                .collect(Collectors.toList());
        } else if(heightSort.toLowerCase().equals("descending")){
            return munroDtoList.stream()
                                .filter(a -> a.getHillCategory().equals(name))
                                .sorted(Comparator.comparing(MunroDto::getHeightMeters).reversed())
                                .collect(Collectors.toList());
        }
        return null;
    }

}
