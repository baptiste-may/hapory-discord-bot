package fr.djredstone.coolyougamingDiscordBot;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import fr.djredstone.coolyougamingDiscordBot.commands.CommandPing;
import fr.djredstone.coolyougamingDiscordBot.commands.CommandRoles;
import org.jetbrains.annotations.NotNull;

public class Setup implements EventListener {

    public Setup() {

        EnumSet<GatewayIntent> intents = EnumSet.of(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES
        );
        JDABuilder builder = JDABuilder.createDefault(System.getenv("TOKEN"), intents);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.EMOTE);
        builder.enableCache(CacheFlag.VOICE_STATE);
        try {
            Main.setJda(builder.build());
        } catch (LoginException e) {
            e.printStackTrace();
        }

        Main.getJda().addEventListener(this);

        setupCommands(Main.getJda());

        Main.getJda().updateCommands().addCommands(
                Commands.slash("ping", "Ping"),
                Commands.slash("roles", "Roles")
        ).queue();

    }

    private static void setupCommands(JDA jda) {
        jda.addEventListener(new CommandPing());
        jda.addEventListener(new CommandRoles());
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        if(event instanceof ReadyEvent) {
            System.out.println("Discord bot ready !");
            Main.setGuild(Main.getJda().getGuildById("997413706608685066"));
        }
    }

}
