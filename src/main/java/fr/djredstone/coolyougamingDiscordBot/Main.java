package fr.djredstone.coolyougamingDiscordBot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;

public class Main {

    private static JDA jda;
    public static JDA getJda() { return jda; }
    public static void setJda(JDA jda) { Main.jda = jda; }

    private static Guild guild;
    public static Guild getGuild() { return guild; }
    public static void setGuild(Guild guild) { Main.guild = guild; }

    public static void main(String[] args) {
        new Setup();
    }

}
