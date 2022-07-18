package fr.djredstone.haporyDiscordBot.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import fr.djredstone.haporyDiscordBot.Main;
import fr.djredstone.haporyDiscordBot.Utils;

public class money {

    private static final TextChannel logChannel = Main.getJda().getTextChannelById("997761427865612338");
    private static final int originalMoney = 100;

    public static int get(User user) throws SQLException {

        final PreparedStatement preparedStatement = Utils.createPreparedStatement("SELECT uuid, money FROM user_money WHERE uuid = ?");
        preparedStatement.setString(1, user.getId());
        final ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {

            final PreparedStatement preparedStatement1 = Utils.createPreparedStatement("INSERT INTO user_money VALUES(?, ?, ?, ?)");
            preparedStatement1.setString(1, user.getId());
            preparedStatement1.setInt(2, originalMoney);
            final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            preparedStatement1.setTimestamp(3, timestamp);
            preparedStatement1.setTimestamp(4, timestamp);

            preparedStatement1.executeUpdate();

            return originalMoney;
        }

        return resultSet.getInt("money");
    }

    public static void add(User user, int money) throws SQLException {

        int userMoney = get(user);

        PreparedStatement preparedStatement = Utils.createPreparedStatement("UPDATE user_money SET money = ?, updated_at = ? WHERE uuid = ?");
        preparedStatement.setInt(1, userMoney + money);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(3, user.getId());

        preparedStatement.executeUpdate();

        Objects.requireNonNull(logChannel).sendMessage(String.format("Money [+] %1$s - `%2$s + %3$s = %4$s`", user.getAsTag(), userMoney, money, userMoney + money)).queue();

    }

    public static void remove(User user, int money) throws SQLException {

        int userMoney = get(user);

        PreparedStatement preparedStatement = Utils.createPreparedStatement("UPDATE user_money SET money = ?, updated_at = ? WHERE uuid = ?");
        preparedStatement.setInt(1, userMoney - money);
        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setString(3, user.getId());

        preparedStatement.executeUpdate();

        Objects.requireNonNull(logChannel).sendMessage(String.format("Money [-] %1$s - `%2$s - %3$s = %4$s`", user.getAsTag(), userMoney, money, userMoney - money)).queue();

    }

}
