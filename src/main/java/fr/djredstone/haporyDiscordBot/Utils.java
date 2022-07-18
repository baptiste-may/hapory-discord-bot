package fr.djredstone.haporyDiscordBot;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Utils {

    public static PreparedStatement createPreparedStatement(String cmd) throws SQLException {
        return Main.getDatabaseManager().getDbConnection().getConnection().prepareStatement(cmd);
    }

}
