package fr.djredstone.haporyDiscordBot.commands;

import java.sql.SQLException;
import java.util.Objects;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import fr.djredstone.haporyDiscordBot.classes.money;
import org.jetbrains.annotations.NotNull;

public class CommandMoney extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("money")) return;
        if (event.getOption("utilisateur") != null) {
            User target = Objects.requireNonNull(event.getOption("utilisateur")).getAsUser();
            try {
                event.reply(target.getAsMention() + " a actuellement **" + money.get(target) + "$**").setEphemeral(true).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        } else {
            try {
                event.reply("Tu as actuellement **" + money.get(event.getUser()) + "$**").setEphemeral(true).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        }
    }

}
