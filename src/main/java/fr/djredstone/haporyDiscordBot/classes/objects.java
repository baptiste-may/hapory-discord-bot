package fr.djredstone.haporyDiscordBot.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

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

    public static void add(User user, int money) throws SQLException {



        Objects.requireNonNull(logChannel).sendMessage("").queue();

    }

    public static void remove(User user, int money) throws SQLException {



        Objects.requireNonNull(logChannel).sendMessage("").queue();

    }

}
