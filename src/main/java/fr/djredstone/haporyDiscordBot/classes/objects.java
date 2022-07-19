package fr.djredstone.haporyDiscordBot.classes;

import java.awt.*;
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

public class objects {

    private static final TextChannel logChannel = Main.getJda().getTextChannelById("997761427865612338");

    public static ArrayList<CustomObject> get(User user) throws SQLException {

        final PreparedStatement preparedStatement = Utils.createPreparedStatement("SELECT * FROM user_objects WHERE uuid = ?");
        preparedStatement.setString(1, user.getId());
        final ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<CustomObject> list = new ArrayList<>();
        while (resultSet.next())
            list.add(new CustomObject(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("img_URL"), resultSet.getInt("rarity"), resultSet.getInt("price")));

        return list;
    }

    public static CustomObject getById(String ID) throws SQLException {
        final PreparedStatement preparedStatement = Utils.createPreparedStatement("SELECT * FROM user_objects WHERE id = ?");
        preparedStatement.setString(1, ID);
        final ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            return new CustomObject(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getString("img_URL"), resultSet.getInt("rarity"), resultSet.getInt("price"));
        else
            return null;
    }

    public static void remove(User user, CustomObject object) throws SQLException {

        final PreparedStatement preparedStatement = Utils.createPreparedStatement("DELETE FROM user_objects WHERE (uuid = ? AND id = ? AND name = ? AND description = ? AND img_URL = ? AND rarity = ? AND price = ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, object.getID());
        preparedStatement.setString(3, object.getName());
        preparedStatement.setString(4, object.getDescription());
        preparedStatement.setString(5, object.getImgURL());
        preparedStatement.setInt(6, object.getRarity());
        preparedStatement.setInt(7, object.getPrice());
        preparedStatement.execute();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Objet retiré à " + user.getAsTag())
                .setColor(Color.RED)
                .addField(object.getName(), object.getDescription(), false)
                .setThumbnail(object.getImgURL())
                .setFooter("ID de l'objet : " + object.getID());

        Objects.requireNonNull(logChannel).sendMessageEmbeds(embed.build()).queue();

    }



}
