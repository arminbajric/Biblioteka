package biblioteka;

import java.sql.SQLException;

public class Naslov extends BibliotekaBaza {
	@Override
	public void addNewRow(String upit)
	{
	super.addNewRow(upit);	
	}
	@Override
	public void deleteRow(String upit)
	{
	super.addNewRow(upit);	
	}
	@Override
	public void updateRow(String upit)
	{
	super.updateRow(upit);	
	}
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
