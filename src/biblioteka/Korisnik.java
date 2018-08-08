package biblioteka;

import java.sql.SQLException;

public class Korisnik extends BibliotekaBaza{
@Override
public void executeSQL(String upit)
{
super.executeSQL(upit);	
}
public void checkUserId(String ime,String prezime)
{
	super.checkUserId(ime, prezime);
	}
@Override


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
