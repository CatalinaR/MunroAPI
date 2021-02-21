package com.example.servingwebcontent.process;

import com.example.servingwebcontent.model.MunroDto;
import com.google.common.base.Splitter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessData {

    public ProcessData(){
        
    }

    public List<MunroDto> processCSVFile() throws IOException {
        List<String> inputList = readFromFile();
        List<MunroDto> munroDtos = new ArrayList<>();
        String[] properties = inputList.get(0).split(",");
        int nameIndex = getIndexProperty(properties, "Name") +1;
        int heightMetersIndex = getIndexProperty(properties, "Height (m)") + 1;
        int gridRefIndex = getIndexProperty(properties, "Grid Ref") + 1;
        int post1997Index = getIndexProperty(properties, "Post 1997") + 1;


        for(int i = 1; i <inputList.size(); i++){
            String line = inputList.get(i);
            List<String> parsed = Splitter.on(',').splitToList(line);
            if(parsed.size() > 28) {
                if(!parsed.get(heightMetersIndex).equals("")) {
                    MunroDto munroDto = new MunroDto(parsed.get(nameIndex), Float.parseFloat(parsed.get(heightMetersIndex)),
                            parsed.get(gridRefIndex), parsed.get(post1997Index));
                    munroDtos.add(munroDto);
                }
            }
        }
        return munroDtos;
    }

    private List<String> readFromFile() throws IOException {
        List<String> inputList = new ArrayList<>();
        String line ="";
        try{
            String folder = System.getProperty("user.dir") + "/src/main/java/com/example/servingwebcontent/data/munrotab_v6.2.csv";
            BufferedReader br = new BufferedReader(new FileReader(folder));
            while((line=br.readLine()) != null){
                inputList.add(line);
            }

        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return inputList;
    }

    private Integer getIndexProperty(String[] inputString, String property){
        for(int i = 0 ; i<inputString.length; i++){
            if(inputString[i].equals(property)){
                return i;
            }
        }
        return null;
    }
}
