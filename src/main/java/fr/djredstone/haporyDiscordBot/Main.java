package fr.djredstone.haporyDiscordBot;

import net.dv8tion.jda.api.JDA;

import fr.djredstone.haporyDiscordBot.classes.mysql.DatabaseManager;

public class Main {

    private static JDA jda;
    public static JDA getJda() { return jda; }
    public static void setJda(JDA jda) { Main.jda = jda; }

    private static DatabaseManager databaseManager;
    public static DatabaseManager getDatabaseManager() { return databaseManager; }
    public static void setDatabaseManager(DatabaseManager databaseManager) { Main.databaseManager = databaseManager; }

    public static void main(String[] args) {
        new Setup();
    }

}
