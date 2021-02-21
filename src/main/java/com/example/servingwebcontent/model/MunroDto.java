package com.example.servingwebcontent.model;

public class MunroDto {
    private String name;
    private float heightMeters;
    private String gridRef;
    private String hillCategory;

    public MunroDto(String name, float heightMeters, String gridRef, String post1997){
        this.name = name;
        this.heightMeters = heightMeters;
        this.gridRef = gridRef;
        this.hillCategory = post1997;
    }

    public String getName() {
        return name;
    }

    public float getHeightMeters() {
        return heightMeters;
    }

    public String getGridRef() {
        return gridRef;
    }

    public String getHillCategory() {
        return hillCategory;
    }

    @Override
    public String toString(){
        return "Munro Dto: Name: "+ getName()+ " Height: " + getHeightMeters() +
                " Grid Ref: " + getGridRef() +" HillCategory: " + getHillCategory();
    }
}
