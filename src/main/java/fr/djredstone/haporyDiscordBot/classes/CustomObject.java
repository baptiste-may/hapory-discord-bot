package fr.djredstone.haporyDiscordBot.classes;

public class CustomObject {

    String name, description;
    int rarity, price;

    public CustomObject(String name, String description, int rarity, int price) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.price = price;
    }

    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public int getRarity() { return rarity; }
    public int getPrice() { return price; }

}
