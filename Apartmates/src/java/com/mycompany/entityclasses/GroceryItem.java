package com.mycompany.entityclasses;

import java.util.Objects;

/**
 *
 * @author megh
 */
public class GroceryItem {

    private String name;
    private String image;
    private Float cost;
    private Float weight;

    public GroceryItem() {
    }

    public GroceryItem(String name, String image, Float cost, Float weight) {
        this.name = name;
        this.image = image;
        this.cost = cost;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "GroceryItem{" + "name=" + name + ", cost=" + cost + ", weight=" + weight + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroceryItem other = (GroceryItem) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
}
