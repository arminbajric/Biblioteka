package biblioteka;

import java.sql.SQLException;

public class Pozajmljivanje extends BibliotekaBaza {
	@Override
	public void addNewRow(String upit) {
		super.addNewRow(upit);
	}

	public boolean checkKnjiga(int ID,int korisnik) throws SQLException {
		if (super.checkKnjiga(ID)) {
			return true;

		} else {
			return false;
		}

	}

	@Override
	public void updateRow(String upit) {
		super.updateRow(upit);
	}
	public void pozajmiKnjigu(String upit,int knjiga,int ID)
	{
		super.pozajmiKnjigu(upit,knjiga,ID);
	}
	public boolean checkPozajmljivanje(int ID) throws SQLException
	{
		if(super.checkPozajmljivanje(ID))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
