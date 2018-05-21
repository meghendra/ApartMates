package com.mycompany.entityclasses;

/**
 *
 * @author megh
 */
public class Ingredient {
    private String text;
    private Float weight;

    public Ingredient() {
    }

    public Ingredient(String text, Float weight) {
        this.text = text;
        this.weight = weight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Ingredient{" + "text=" + text + ", weight=" + weight + '}';
    }
    
    
}
