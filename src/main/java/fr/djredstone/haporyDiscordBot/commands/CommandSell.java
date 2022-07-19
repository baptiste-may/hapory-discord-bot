package fr.djredstone.haporyDiscordBot.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import fr.djredstone.haporyDiscordBot.classes.CustomObject;
import fr.djredstone.haporyDiscordBot.classes.money;
import fr.djredstone.haporyDiscordBot.classes.objects;
import org.jetbrains.annotations.NotNull;

public class CommandSell extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("sell")) return;
        try {
            final ArrayList<CustomObject> list = objects.get(event.getUser());
            if (list.isEmpty())
                event.reply("Tu n'as aucun objets dans ton inventaire").setEphemeral(true).queue();
            else {
                final SelectMenu.Builder menu = SelectMenu.create("menu.sell")
                        .setMinValues(1);
                for (CustomObject object : list)
                    menu.addOption(object.getName() + " (" + object.getPrice() + "$)", object.getID(), object.getDescription());
                event.reply("Choisis un objet à vendre dans la liste ci dessous :")
                        .addActionRow(menu.build()).setEphemeral(true).queue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Une erreur est survenue !").setEphemeral(true).queue();
        }
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        if (!event.getComponentId().equalsIgnoreCase("menu.sell")) return;
        for (String value : event.getValues()) {
            try {
                final CustomObject object = objects.getById(value);
                assert object != null;
                objects.remove(event.getUser(), object);
                money.add(event.getUser(), object.getPrice());
                event.reply("\"" + object.getName() + "\" a été vendu au prix de " + object.getPrice() + "$").setEphemeral(true).queue();
            } catch (SQLException e) {
                e.printStackTrace();
                event.reply("Une erreur est survenue !").setEphemeral(true).queue();
            }
        }
    }

}
