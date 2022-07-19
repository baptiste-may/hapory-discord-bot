package fr.djredstone.haporyDiscordBot.commands;

import java.sql.SQLException;
import java.util.ArrayList;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import static java.lang.Integer.parseInt;
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
                    menu.addOption(object.getName() + " (" + object.getPrice() + "$)", customObjectToID(object), object.getDescription());
                event.reply("Choisis un objet à vendre dans la liste ci dessous :")
                        .addActionRow(menu.build()).setEphemeral(true).queue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            event.reply("Une erreur est survenue !").setEphemeral(true).queue();
        }
    }

    private static String customObjectToID(CustomObject object) {
        return (object.getName().replace(" ", "-") + "§" + object.getDescription().replace(" ", "-") + "§" + object.getRarity() + "§" + object.getPrice());
    }

    private static CustomObject IDtoCustomObject(String string) {
        String[] elements = string.split("§");
        return new CustomObject(elements[0].replace("-", " "), elements[1].replace("-", " "), parseInt(elements[2]), parseInt(elements[3]));
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        if (!event.getComponentId().equalsIgnoreCase("menu.sell")) return;
        for (String value : event.getValues()) {
            final CustomObject object = IDtoCustomObject(value);
            try {
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
