package fr.djredstone.haporyDiscordBot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import fr.djredstone.haporyDiscordBot.Main;
import org.jetbrains.annotations.NotNull;

public class CommandPing extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("ping")) return;
        event.reply("Pong ! (`" + Main.getJda().getGatewayPing() + " ms`)").setEphemeral(true).queue();
    }

}
