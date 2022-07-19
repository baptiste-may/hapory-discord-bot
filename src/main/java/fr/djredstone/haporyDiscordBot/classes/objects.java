package fr.djredstone.haporyDiscordBot.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import fr.djredstone.haporyDiscordBot.Main;
import fr.djredstone.haporyDiscordBot.Utils;
import org.javatuples.Quartet;

public class objects {

    private static final TextChannel logChannel = Main.getJda().getTextChannelById("997761427865612338");

    public static ArrayList<CustomObject> get(User user) throws SQLException {

        final PreparedStatement preparedStatement = Utils.createPreparedStatement("SELECT * FROM user_objects WHERE uuid = ?");
        preparedStatement.setString(1, user.getId());
        final ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<CustomObject> list = new ArrayList<>();
        while (resultSet.next())
            list.add(new CustomObject(resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("rarity"), resultSet.getInt("price")));

        return list;
    }

    public static void remove(User user, CustomObject object) throws SQLException {

        final PreparedStatement preparedStatement = Utils.createPreparedStatement("DELETE FROM user_objects WHERE (uuid = ? AND name = ? AND description = ? AND rarity = ? AND price = ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, object.getName());
        preparedStatement.setString(3, object.getDescription());
        preparedStatement.setInt(4, object.getRarity());
        preparedStatement.setInt(5, object.getPrice());
        final boolean resultSet = preparedStatement.execute();

        if (resultSet) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Objet retiré à " + user.getAsTag())
                    .addField(object.getName(), object.getDescription(), false);

            Objects.requireNonNull(logChannel).sendMessage("").queue();
        }

    }



}
