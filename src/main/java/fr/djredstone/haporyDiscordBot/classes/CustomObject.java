package fr.djredstone.haporyDiscordBot.classes;

public class CustomObject {

    String id, name, description, imgURL;
    int rarity, price;

    public CustomObject(String id, String name, String description, String imgURL, int rarity, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imgURL = imgURL;
        this.rarity = rarity;
        this.price = price;
    }

    public String getID() { return this.id; }
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public String getImgURL() { return this.imgURL; }
    public int getRarity() { return this.rarity; }
    public int getPrice() { return this.price; }

}
