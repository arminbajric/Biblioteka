package biblioteka;

import java.sql.SQLException;

public class Naslov extends BibliotekaBaza {
	@Override
	public void executeSQL(String upit)
	{
	super.executeSQL(upit);	
	}
	@Override
	
	public void pretraziNaslove(String naslov) throws SQLException
	{
		super.pretraziNaslove(naslov);
	}
	public void ispisiNaslove() throws SQLException
	{
		try {
			super.ispisiNaslove();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
