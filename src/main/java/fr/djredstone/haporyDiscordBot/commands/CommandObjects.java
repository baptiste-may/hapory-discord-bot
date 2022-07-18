package fr.djredstone.haporyDiscordBot.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import fr.djredstone.haporyDiscordBot.classes.CustomObject;
import fr.djredstone.haporyDiscordBot.classes.objects;
import org.jetbrains.annotations.NotNull;

public class CommandObjects extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("objects")) return;
        if (event.getOption("utilisateur") != null) {
            User target = Objects.requireNonNull(event.getOption("utilisateur")).getAsUser();
            try {
                final ArrayList<CustomObject> list = objects.get(target);
                final EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Garage de " + target.getAsTag());
                if (list.isEmpty())
                    embed.setDescription(target.getAsMention() + " n'avez aucun objets !");
                else {
                    for (CustomObject object : list)
                        embed.addField(object.getName(), object.getDescription(), true);
                }
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        } else {
            try {
                final ArrayList<CustomObject> list = objects.get(event.getUser());
                final EmbedBuilder embed = new EmbedBuilder()
                        .setTitle("Votre garage");
                if (list.isEmpty())
                    embed.setDescription("Vous n'avez aucun objets !");
                else {
                    for (CustomObject object : list)
                        embed.addField(object.getName(), object.getDescription(), true);
                }
                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        }
    }

}
