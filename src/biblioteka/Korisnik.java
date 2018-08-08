package biblioteka;

import java.sql.SQLException;

public class Korisnik extends BibliotekaBaza{
@Override
public void addNewRow(String upit)
{
super.addNewRow(upit);	
}
public void checkUserId(String ime,String prezime)
{
	super.checkUserId(ime, prezime);
	}
@Override
public void deleteRow(String upit)
{
super.addNewRow(upit);	
}
@Override
public void updateRow(String upit)
{
super.addNewRow(upit);	
}
public void addNewUser(String upit)
{
	super.addNewRow(upit);
}
public void deleteUser(String upit)
{
	
}
public void updateUser(String upit)
{
	
}
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
