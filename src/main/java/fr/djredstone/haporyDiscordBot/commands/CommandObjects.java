package fr.djredstone.haporyDiscordBot.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

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
                if (list.isEmpty())
                    event.reply("Votre garage est vide").setEphemeral(true).queue();
                else {
                    ReplyCallbackAction message = event.reply("Votre garage :").setEphemeral(true);
                    for (CustomObject object : list)
                        message.addEmbeds(new EmbedBuilder().setTitle(object.getName()).setDescription(object.getDescription()).setThumbnail(object.getImgURL()).build());
                    message.queue();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        }
    }

}
