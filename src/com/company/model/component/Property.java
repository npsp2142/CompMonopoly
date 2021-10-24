package com.company.model.component;

public class Property {
    private final String name;
    private final int price;
    private final int rent;
    private Player owner;

    public Property(String name,int price, int rent){
        this.name = name;
        this.price = price;
        this.rent = rent;
    }

    public void reload(){
        owner = null;
    }

    public int getRent() {
        return rent;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
