package fr.djredstone.coolyougamingDiscordBot.commands;

import java.util.Objects;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.SelectMenuInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import fr.djredstone.coolyougamingDiscordBot.Main;
import org.jetbrains.annotations.NotNull;

public class CommandRoles extends ListenerAdapter {

    private static final SelectMenu sexMenu = SelectMenu.create("menu.sex")
            .setPlaceholder("Genre")
            .setRequiredRange(0, 1)
            .addOption("Fille", "role.fille",
                    Emoji.fromMarkdown("\uD83D\uDC67"))
            .addOption("Garçon", "role.garçon",
                    Emoji.fromMarkdown("\uD83D\uDC66"))
            .addOption("Transgenre", "role.trans",
                    Emoji.fromMarkdown("⚧"))
            .build();

    private static final SelectMenu ageMenu = SelectMenu.create("menu.age")
            .setPlaceholder("Âge")
            .setRequiredRange(0, 1)
            .addOption("26 ans et +", "role.26+")
            .addOption("23 / 25 ans", "role.23-25")
            .addOption("21 / 23 ans", "role.21-23")
            .addOption("19 / 21 ans", "role.19-21")
            .addOption("17 / 19 ans", "role.17-19")
            .addOption("15 / 17 ans", "role.15-17")
            .addOption("13 / 15 ans", "role.13-15")
            .addOption("- de 13 ans", "role.-13")
            .build();

    private static final SelectMenu shipMenu = SelectMenu.create("menu.ship")
            .setPlaceholder("Relation")
            .setRequiredRange(0, 1)
            .addOption("Célibataire", "role.celiba",
                    Emoji.fromMarkdown("\uD83E\uDD0D"))
            .addOption("En couple", "role.couple",
                    Emoji.fromMarkdown("\uD83D\uDC9E"))
            .addOption("C'est complexe", "role.complexe",
                    Emoji.fromMarkdown("\uD83D\uDC94"))
            .build();

    private static final SelectMenu locMenu = SelectMenu.create("menu.loc")
            .setPlaceholder("Emplacement")
            .setRequiredRange(0, 1)
            .addOption("Occitanie", "role.occitanie")
            .addOption("Auvergne-Rhône-Alpes", "role.ARH")
            .addOption("Bourgogne-Franche-Comté", "role.BFC")
            .addOption("Bretagne", "role.bretagne")
            .addOption("Centre-Val de Loire", "role.CVL")
            .addOption("Corse", "role.corse")
            .addOption("Grand Est", "role.GE")
            .addOption("Hauts-de-France", "role.HDF")
            .addOption("Île-de-France", "role.IDF")
            .addOption("Normandie", "role.normandie")
            .addOption("Nouvelle-Aquitaine", "role.NA")
            .addOption("Pays de la Loire", "role.PL")
            .addOption("Provence-Alpes-Côte d'Azur", "role.PACA")
            .build();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equalsIgnoreCase("roles")) return;
        event.reply("Choisis les rôles qui t'intéresse")
                .addActionRow(sexMenu)
                .addActionRow(ageMenu)
                .addActionRow(shipMenu)
                .addActionRow(locMenu)
                .setEphemeral(true).queue();
    }

    @Override
    public void onSelectMenuInteraction(SelectMenuInteractionEvent event) {
        if (event.getComponentId().equalsIgnoreCase(sexMenu.getId())) {
            setRole("997435276811063388", "role.fille", event);
            setRole("997435371585552465", "role.garçon", event);
            setRole("997435608924426241", "role.trans", event);
        } else if (event.getComponentId().equalsIgnoreCase(ageMenu.getId())) {
            setRole("997450582447116340", "role.26+", event);
            setRole("997450063171289129", "role.23-25", event);
            setRole("997449713651548180", "role.21-23", event);
            setRole("997449569786920960", "role.19-21", event);
            setRole("997449426362712135", "role.17-19", event);
            setRole("997449208418287626", "role.15-17", event);
            setRole("997448946043588648", "role.13-15", event);
            setRole("997448781865951262", "role.-13", event);
        } else if (event.getComponentId().equalsIgnoreCase(shipMenu.getId())) {
            setRole("997479016137228368", "role.celiba", event);
            setRole("997479192453206017", "role.couple", event);
            setRole("997479359315181629", "role.complexe", event);
        } else if (event.getComponentId().equalsIgnoreCase(locMenu.getId())) {
            setRole("997435865947197550", "role.occitanie", event);
            setRole("997435950659534879", "role.ARH", event);
            setRole("997436426834681977", "role.BFC", event);
            setRole("997436501409411115", "role.bretagne", event);
            setRole("997436588533493892", "role.CVL", event);
            setRole("997436882864574494", "role.corse", event);
            setRole("997437136146010212", "role.GE", event);
            setRole("997437200276918302", "role.HDF", event);
            setRole("997437311346286612", "role.IDF", event);
            setRole("997437421895561217", "role.normandie", event);
            setRole("997437557749075989", "role.NA", event);
            setRole("997437689886412890", "role.PL", event);
            setRole("997437778457530479", "role.PACA", event);
        } else return;
        event.reply("Les rôles ont été mis à jour ✅")
                .setEphemeral(true).queue();
    }

    private static void setRole(String roleID, String roleValue, SelectMenuInteractionEvent event) {
        String userID = event.getUser().getId();
        UserSnowflake userSnowflake = UserSnowflake.fromId(userID);
        if (event.getValues().contains(roleValue))
            Main.getGuild().addRoleToMember(userSnowflake, Objects.requireNonNull(Main.getGuild().getRoleById(roleID))).queue();
        else
            Main.getGuild().removeRoleFromMember(userSnowflake, Objects.requireNonNull(Main.getGuild().getRoleById(roleID))).queue();
    }

}
