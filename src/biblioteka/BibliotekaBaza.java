package biblioteka;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BibliotekaBaza  {
	final private String conString = "jdbc:mysql://localhost:3306/mydb?useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String userName = "root";
	private String password;

	BibliotekaBaza() {

	}

	public Connection OpenConnection() throws SQLException {
		Connection con;
		con = DriverManager.getConnection(conString, userName, password);
		return con;
	}

	public void executeSQL(String upit) {

		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			izjava.executeUpdate(upit);
			System.out.println("Uspješno izvršeno!");
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void checkUserId(String ime, String prezime) {
		ResultSet rs = null;
		String query = "SELECT * FROM korisnik ";

		Statement izjava;
		try {
			Connection con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(query);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			while (rs.isAfterLast() == false) {
				rs.next();
				if (rs.isLast()) {
					try {
						System.out.println("Vas ID je " + rs.getInt("idKorisnik") + ".");
					} catch (SQLException e) {

						e.printStackTrace();
					}
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public boolean checkUser(String upit) throws SQLException {
		ResultSet rs = null;

		Statement izjava;

		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs.first()) {

			return true;
		} else {

			return false;
		}

	}

	public boolean checkKnjiga(String upit, int korisnik) throws SQLException {
		ResultSet rs = null;

		Statement izjava;

		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs.first()) {

			return true;
		} else {

			return false;
		}

	}

	public boolean checkKnjiga(int ID) throws SQLException {
		ResultSet rs = null;
		String upit = "SELECT * FROM naslovi WHERE idNaslovi=" + ID + "";

		Statement izjava;

		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs.first() && rs.getInt("Dostupno") == 1 && this.provjeriPozajmljivanja(ID)) {

			return true;

		} else {

			return false;
		}

	}

	private boolean provjeriPozajmljivanja(int ID) throws SQLException {
		ResultSet rs = null;
		String upit = "SELECT * FROM korisnik WHERE idKorisnik=" + ID + "";

		Statement izjava;

		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs.next();
		if ((rs.getInt("Broj_pozajmljenih_knjiga") - rs.getInt("Broj_vracenih_knjiga")) < 4) {

			return true;

		} else {

			return false;
		}
	}

	{
	}

	public boolean checkPozajmljivanje(int ID) throws SQLException {
		ResultSet rs = null;
		String upit = "SELECT * FROM pozajmljivanje WHERE idpozajmljivanja=" + ID + "";

		Statement izjava;

		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rs.first()) {

			return true;
		} else {

			return false;
		}

	}

	public void pozajmiKnjigu(String upit, int idknjige, int ID) {

		ResultSet rs = null;
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			rs = izjava.executeQuery("SELECT * FROM korisnik WHERE idKorisnik=" + ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			izjava.executeUpdate(upit);
			System.out.println("Knjiga pozajmljena!");
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			izjava.executeUpdate("UPDATE `naslovi` SET `Dostupno` = 0 WHERE `naslovi`.`idNaslovi` = " + idknjige + ";");
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			rs.next();
			int temp = rs.getInt("Broj_pozajmljenih_knjiga") + 1;
			izjava.executeUpdate("UPDATE `korisnik` SET `Broj_pozajmljenih_knjiga` = " + temp
					+ " WHERE `korisnik`.`idKorisnik` = " + ID);
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void vratiKnjigu(int ID, int idP) throws SQLException {
		ResultSet rs = null;
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			rs = izjava.executeQuery("SELECT * FROM korisnik");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			rs.next();
			int temp = rs.getInt("Broj_vracenih_knjiga") + 1;
			izjava.executeUpdate("UPDATE `korisnik` SET `Broj_vracenih_knjiga` = " + temp
					+ " WHERE `korisnik`.`idKorisnik` = " + ID);

		} catch (SQLException e) {

			e.printStackTrace();
		}
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			rs = izjava.executeQuery("SELECT Naslovi_idNaslovi FROM pozajmljivanje WHERE idpozajmljivanja=" + idP);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs.next();
		int temp = rs.getInt("Naslovi_idNaslovi");
		try {
			Connection con = this.OpenConnection();
			Statement izjava = con.createStatement();
			izjava.executeUpdate("UPDATE `naslovi` SET `Dostupno` = 1 WHERE `naslovi`.`idNaslovi` = " + temp);
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public void pretraziNaslove(String Naslov) throws SQLException {
		String upit = "SELECT * FROM `naslovi` WHERE `Naziv_naslova` LIKE '%" + Naslov + "%'";
		ResultSet rs = null;
		Statement izjava;
		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rs.first()) {
			while (rs.isAfterLast() == false) {

				System.out.println("Naziv naslova:" + rs.getString("Naziv_naslova"));
				System.out.println("Autor:" + this.getAutor(rs.getInt("Autor_idAutor")));
				System.out.println("Godina izdavanja:" + rs.getString("Datum_izdavanja"));
				System.out.println("Zanr:" + rs.getString("Zanr_naslova"));
				if (rs.getInt("Dostupno") == 1) {
					System.out.println("Dostupno za izdavanje.");
					System.out.println("");
				} else {
					System.out.println("Nije dostupno za izdavanje.");
					System.out.println("");
				}
				rs.next();
			}
		}
	}

	public String getAutor(int idAutor) throws SQLException {
		String upit = "SELECT * FROM `autor` WHERE `idAutor` = " + idAutor + "";
		ResultSet rs = null;
		Statement izjava;
		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.next();
		return (rs.getString("Ime_autora") + " " + rs.getString("Prezime_autora"));
	}

	public void ispisiNaslove() throws SQLException {
		String upit = "SELECT * FROM `naslovi` ";
		ResultSet rs = null;
		Statement izjava;
		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rs.first()) {
			while (rs.isAfterLast() == false) {

				System.out.println("Naziv naslova:" + rs.getString("Naziv_naslova"));
				System.out.println("Autor:" + this.getAutor(rs.getInt("Autor_idAutor")));
				System.out.println("Godina izdavanja:" + rs.getString("Datum_izdavanja"));
				System.out.println("Zanr:" + rs.getString("Zanr_naslova"));
				if (rs.getInt("Dostupno") == 1) {
					System.out.println("Dostupno za izdavanje.");
					System.out.println("");
				} else {
					System.out.println("Nije dostupno za izdavanje.");
					System.out.println("");
				}
				rs.next();
			}
		}
	}

	public void ispisiPozajmljivanja(int ID) throws SQLException {
		String upit = "SELECT * FROM `pozajmljivanje` WHERE `korisnik_idKorisnik` = " + ID + "";
		ResultSet rs = null;
		Statement izjava;
		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.next();
		if (rs.first()) {
			while (rs.isAfterLast() == false) {

				System.out.println("ID pozajmljivanja:" + rs.getInt("idPozajmljivanja"));
				System.out.println("Id naslova:" + rs.getInt("Naslovi_idNaslovi"));
				System.out.println("Datum pozajmljivanja:" + rs.getString("Datum_pozajmljivanja"));

				if (rs.getInt("Vraceno_na_vrijeme") == 1) {
					System.out.println("Status:vraceno");
					System.out.println("");
				} else {
					System.out.println("Status:nije vraceno");
					System.out.println("");
				}
				rs.next();
			}
		}
	}

	public void ispisiInforacuna(int ID) throws SQLException {
		String upit = "SELECT * FROM `korisnik` WHERE `idKorisnik` = " + ID + "";
		ResultSet rs = null;
		Statement izjava;
		Connection con;
		try {
			con = this.OpenConnection();
			izjava = con.createStatement();
			rs = izjava.executeQuery(upit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		rs.next();
		System.out.println("ID:" + rs.getInt("idKorisnik"));
		System.out.println("Ime :" + rs.getString("Ime_korisnika"));
		System.out.println("Prezime:" + rs.getString("Prezime_korisnika"));
		System.out.println("Datum registracije:" + rs.getString("Datum_registracije"));
		System.out.println("Broj uzetih knjiga:" + rs.getInt("Broj_pozajmljenih_knjiga"));
		System.out.println("Broj vracenih knjiga:" + rs.getInt("Broj_vracenih_knjiga"));
		System.out.println("");
	}
}
