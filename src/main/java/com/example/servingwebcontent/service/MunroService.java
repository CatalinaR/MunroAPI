package com.example.servingwebcontent.service;

import com.example.servingwebcontent.model.MunroDto;
import com.example.servingwebcontent.process.LoadData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<MunroDto> getMunroDtoListSortedByFilters(String name, String heightSort, String limit, String minHeight, String maxHeight, String orderNameMunro){
        List<MunroDto> result = munroDtoList.stream().filter(a -> a.getHillCategory().equals(name)).collect(Collectors.toList());

        if(!heightSort.toLowerCase().equals("any")) {
            if (heightSort.toLowerCase().equals("ascending")) {
                result = result.stream()
                        .sorted(Comparator.comparing(MunroDto::getHeightMeters))
                        .collect(Collectors.toList());
            } else if (heightSort.toLowerCase().equals("descending")) {
                result = result.stream()
                        .sorted(Comparator.comparing(MunroDto::getHeightMeters).reversed())
                        .collect(Collectors.toList());
            }
        } else if(!orderNameMunro.toLowerCase().equals("any")){
            if (orderNameMunro.toLowerCase().equals("ascending")) {
                result = result.stream()
                        .sorted(Comparator.comparing(MunroDto::getName))
                        .collect(Collectors.toList());
            } else if (orderNameMunro.toLowerCase().equals("descending")) {
                result = result.stream()
                        .sorted(Comparator.comparing(MunroDto::getName).reversed())
                        .collect(Collectors.toList());
            }
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
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty() && minHeight.equals("0") && maxHeight.equals("0")){
            return result.stream().limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }
        else if(Integer.parseInt(limit) > 0 && Integer.parseInt(limit) < munroDtoList.size() && !result.isEmpty() && !minHeight.equals("0") && !maxHeight.equals("0")){
            return result.stream().filter(a -> a.getHeightMeters() >= Integer.parseInt(minHeight) && a.getHeightMeters() <= Integer.parseInt(maxHeight)).limit(Integer.parseInt(limit)).collect(Collectors.toList());
        }

        return null;
    }
}
