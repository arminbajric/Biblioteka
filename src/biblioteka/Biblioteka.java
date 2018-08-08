package biblioteka;

import java.sql.Connection;
import java.sql.SQLException;

public interface Biblioteka {
Connection OpenConnection() throws SQLException;

void executeSQL(String upit);

}
