package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.MunroDto;
import com.example.servingwebcontent.process.LoadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
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

    public List<MunroDto> getMunroDtoListSortedByHeight(String name, String heightSort, String limit, String minHeight, String maxHeight){
        List<MunroDto> result = new ArrayList<>();
        if(heightSort.toLowerCase().equals("ascending")){
            result = munroDtoList.stream()
                                .filter(a -> a.getHillCategory().equals(name))
                                .sorted(Comparator.comparing(MunroDto::getHeightMeters))
                                .collect(Collectors.toList());
        } else if(heightSort.toLowerCase().equals("descending")){
            result = munroDtoList.stream()
                                .filter(a -> a.getHillCategory().equals(name))
                                .sorted(Comparator.comparing(MunroDto::getHeightMeters).reversed())
                                .collect(Collectors.toList());
        }

        if(Integer.parseInt(limit) == 0 && minHeight.equals("0") && maxHeight.equals("0")){
            return result;
        } else if(Integer.parseInt(limit) == 0 && minHeight.equals("0") && !maxHeight.equals("0")){
                return result.stream().filter(a -> a.getHeightMeters() <= Integer.parseInt(maxHeight)).collect(Collectors.toList());
        }else if(Integer.parseInt(limit) == 0 && maxHeight.equals("0") && !minHeight.equals("0")){
                return result.stream().filter(a -> a.getHeightMeters() >= Integer.parseInt(minHeight)).collect(Collectors.toList());
        }
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty() && !minHeight.equals("0") && maxHeight.equals("0")){
            return result.stream().filter(a -> a.getHeightMeters() >= Integer.parseInt(minHeight)).limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty() && minHeight.equals("0") && !maxHeight.equals("0")){
            return result.stream().filter(a -> a.getHeightMeters() <= Integer.parseInt(maxHeight)).limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty() && !minHeight.equals("0") && !maxHeight.equals("0")){
            return result.stream().filter(a -> a.getHeightMeters() >= Integer.parseInt(minHeight) && a.getHeightMeters() <= Integer.parseInt(maxHeight)).limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }

        return null;
    }

    public List<MunroDto> getMunroDtoListSortedByName(String name, String nameOrder, String limit){
        List<MunroDto> result = new ArrayList<>();
        if(nameOrder.toLowerCase().equals("ascending")){
            result = munroDtoList.stream()
                    .filter(a -> a.getHillCategory().equals(name))
                    .sorted(Comparator.comparing(MunroDto::getName))
                    .collect(Collectors.toList());
        } else if(nameOrder.toLowerCase().equals("descending")){
            result =  munroDtoList.stream()
                    .filter(a -> a.getHillCategory().equals(name))
                    .sorted(Comparator.comparing(MunroDto::getName).reversed())
                    .collect(Collectors.toList());
        }

        if(Integer.parseInt(limit) == 0){
            return result;
        }
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty()){
            return result.stream().limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }

        return null;
    }

}
