package biblioteka;

import java.sql.SQLException;

public class KorisnikLogIN extends Korisnik {
	public boolean checkUser(String upit) throws SQLException
	{
		if(super.checkUser(upit)==true)
		{
		return true;
		}
		else
		{
			return false;
		}
	}
}
