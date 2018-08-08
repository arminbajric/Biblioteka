package biblioteka;

import java.sql.SQLException;

public class Pozajmljivanje extends BibliotekaBaza {
	@Override
	public void executeSQL(String upit) {
		super.executeSQL(upit);
	}

	public boolean checkKnjiga(int ID, int korisnik) throws SQLException {
		if (super.checkKnjiga(ID)) {
			return true;

		} else {
			return false;
		}

	}

	@Override

	public void pozajmiKnjigu(String upit, int knjiga, int ID) {
		super.pozajmiKnjigu(upit, knjiga, ID);
	}

	public boolean checkPozajmljivanje(int ID) throws SQLException {
		if (super.checkPozajmljivanje(ID)) {
			return true;
		} else {
			return false;
		}
	}
}
