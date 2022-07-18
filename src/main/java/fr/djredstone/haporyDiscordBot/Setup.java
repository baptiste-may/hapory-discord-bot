package fr.djredstone.haporyDiscordBot;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.Map;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import fr.djredstone.haporyDiscordBot.classes.mysql.DatabaseManager;
import fr.djredstone.haporyDiscordBot.commands.CommandMoney;
import fr.djredstone.haporyDiscordBot.commands.CommandObjects;
import fr.djredstone.haporyDiscordBot.commands.CommandPing;
import org.jetbrains.annotations.NotNull;

public class Setup implements EventListener {

    private static final Map<String, String> env = System.getenv();

    public Setup() {

        DBConnect();

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES
        );
        JDABuilder builder = JDABuilder.createDefault(env.get("TOKEN"), intents);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOTE);
        builder.enableCache(CacheFlag.VOICE_STATE);
        builder.setActivity(Activity.playing("chercher des pépites"));
        try {
            Main.setJda(builder.build());
        } catch (LoginException e) {
            e.printStackTrace();
        }

        Main.getJda().addEventListener(this);

        setupCommands(Main.getJda());

        final OptionData optionUser = new OptionData(OptionType.USER, "utilisateur", "Un membre");

        Main.getJda().updateCommands().addCommands(
                Commands.slash("ping", "Pong"),
                Commands.slash("money", "Affiche l'argent").addOptions(optionUser),
                Commands.slash("objects", "Affiche les objets en ta posséssion ou d'un autre memebre").addOptions(optionUser)
        ).queue();

    }

    private static void setupCommands(JDA jda) {
        jda.addEventListener(new CommandMoney());
        jda.addEventListener(new CommandObjects());
        jda.addEventListener(new CommandPing());
    }

    public static void DBConnect() {
        if (Main.getDatabaseManager() != null) Main.getDatabaseManager().close();
        Main.setDatabaseManager(new DatabaseManager(env.get("DB-HOST"), env.get("DB-USER"), env.get("DB-PASSWORD"), env.get("DB-NAME")));
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ReadyEvent) {
            System.out.println("Discord bot ready !");
        }
    }

}
