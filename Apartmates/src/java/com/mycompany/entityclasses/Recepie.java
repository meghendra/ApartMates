package com.mycompany.entityclasses;

import java.util.List;

/**
 *
 * @author megh
 */
public class Recepie {
    
private int id;
private String label;
private String image;
private String source;
private String url;
private String shareAs;
private String yield;
private String dietLabels;
private String healthLabels;
private List <Ingredient> ingredients;
private Double calories;

    public Recepie() {
    }

    public Recepie(int id, String label, String image, String source, String url, String shareAs, String yield, String dietLabels, String healthLabels, List<Ingredient> ingredients, Double calories) {
        this.id = id;
        this.label = label;
        this.image = image;
        this.source = source;
        this.url = url;
        this.shareAs = shareAs;
        this.yield = yield;
        this.dietLabels = dietLabels;
        this.healthLabels = healthLabels;
        this.ingredients = ingredients;
        this.calories = calories;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(String dietLabels) {
        this.dietLabels = dietLabels;
    }

    public String getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String healthLabels) {
        this.healthLabels = healthLabels;
    }

    public List <Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List <Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "Recepie{" +"id="+id+ ", label=" + label + ", image=" + image + ", source=" + source + ", url=" + url + ", shareAs=" + shareAs + ", yield=" + yield + ", dietLabels=" + dietLabels + ", healthLabels=" + healthLabels + ", ingredients=" + ingredients + ", calories=" + calories + '}';
    }

}
