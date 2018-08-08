package biblioteka;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BibliotekaTest {
	public static Scanner unos() {
		Scanner unos = new Scanner(System.in);
		return unos;
	}

	public static int getInputInt() {

		int a = 0;
		try {
			System.out.println("Unesite vrijednost:");
			a = unos().nextInt();
		} catch (InputMismatchException e) {
			getInputInt();
		}
		return a;
	}

	public static double getInputDbl() {

		double a = 0;
		try {
			System.out.println("Unesite vrijednost:");
			a = unos().nextDouble();
		} catch (InputMismatchException e) {
			getInputInt();
		}
		return a;
	}

	// void metode za ispis menija
	public static void ispisiMeni() {
		System.out.println("1.Login");
		System.out.println("2.Register");
		System.out.println("3.Izmjeni bazu biblioteke");
		System.out.println("0.Izlaz");
	}

	public static void bazaMeni() {
		System.out.println("1.Dodaj naslov");
		System.out.println("2.Dodaj autora");
		System.out.println("3.Ispisi naslove");
		System.out.println("0.Nazad");
	}

	public static void ispisiLoginMeni() {
		System.out.println("1.Pozajmi knjigu");
		System.out.println("2.Vrati knjigu");
		System.out.println("3.Pretraži naslove");
		System.out.println("4.Ispisi vasa pozajmljivanja");
		System.out.println("5.Ispisi informacije o racunu");
		System.out.println("0.LogOut");

	}

	public static void main(String[] args) throws SQLException {
		// deklarisanje super klasa

		DateFormat datum = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		String danasnjiDatum = datum.format(cal.getTime());

		BibliotekaBaza test;
		Korisnik korisnik;
		int korisnikLoggedIn;

		// definisanje okidaèa za switch selekcije
		int trigger = -1;
		int trigger2 = -1;
		// while petlja vrti poèetni meni sve dok se ne unese 0
		while (trigger != 0) {
			// ispis poèetnog menija
			ispisiMeni();
			// unos za odabir opcije menija
			trigger = getInputInt();
			// selekcija koja se izvršava na osnovu prethodnog unosa
			switch (trigger) {
			// login korisnika
			case 1:
				String upit = null;
				// definisanje instance super klase
				korisnik = new KorisnikLogIN();
				System.out.println("Unesite vas ID:");
				int ID = getInputInt();
				// formiranje sql querya zaprovjeru logina
				upit = "SELECT `idKorisnik` FROM `korisnik` WHERE `idKorisnik`='" + ID + "'";
				// provjera da li postoji registrovani korisnik na osnovu prethodnog unosa
				if (korisnik.checkUser(upit) == true) {
					korisnikLoggedIn = ID;
					// ako postoji korisnik ispisuje mu se korisnièki meni sa opcijama
					int okidac = -1;
					while (okidac != 0) {
						ispisiLoginMeni();
						okidac = getInputInt();
						// selekcija izvršava korisnièke zahtjeve na osnovu unosa opcije
						switch (okidac) {
						case 1:
							// korisniku se otvara sucelje za iznajmljivanje knjige
							System.out.println();
							System.out.println("Unesite ID knjige za iznajmljivanje");
							int idKnjige = getInputInt();
							test = new Pozajmljivanje();
							if (test.checkKnjiga(idKnjige)) {
								upit = "INSERT INTO `Pozajmljivanje` (`Naslovi_idNaslovi`, `Korisnik_idKorisnik`, `Datum_pozajmljivanja`) VALUES ('"
										+ String.valueOf(idKnjige) + "', '" + String.valueOf(ID) + "', '"
										+ danasnjiDatum + "');";
								;
								test.pozajmiKnjigu(upit, idKnjige, ID);
							} else {
								System.out.println(
										"Greška!Moguæi razlozi:knjiga ne postoji,knjiga nije dostupna ili imate veæ posuðene tri knjige.");
							}
							test = new Naslov();

							break;
						case 2:
							System.out.println();
							test = new Pozajmljivanje();
							System.out.println("Unesite ID pozajmljivanja");
							int idP = getInputInt();
							if (test.checkPozajmljivanje(idP)) {
								upit = "UPDATE `pozajmljivanje` SET `Vraceno_na_vrijeme` = '1' WHERE `pozajmljivanje`.`idpozajmljivanja` ="
										+ idP + "  AND `pozajmljivanje`.`Korisnik_idKorisnik` = " + ID + "";
								test.executeSQL(upit);
								test.vratiKnjigu(ID, idP);

							}

							break;
						case 3:
							test = new Naslov();
							System.out.println();
							System.out.println("Unesite kljucnu rijec za pretrazivanje");
							String pretrazi = unos().nextLine();
							test.pretraziNaslove(pretrazi);
							break;
						case 4:
							test = new Pozajmljivanje();
							test.ispisiPozajmljivanja(ID);
							break;
						case 5:
							test = new Pozajmljivanje();
							test.ispisiInforacuna(ID);
							break;
						}
					}
				} else {
					System.out.println("Pogrešan ID.Pokušajte ponovo.");
				}

				break;
			case 2:

				System.out.println();

				korisnik = new KorisnikRegister();
				System.out.println("Unesite vase Ime:");
				String ime = unos().nextLine();

				System.out.println("Unesite vase prezime:");
				String prezime = unos().nextLine();
				String query = "INSERT INTO `Korisnik` (`idKorisnik`, `Ime_korisnika`, `Prezime_korisnika`, `Datum_registracije`) VALUES (NULL, '"
						+ ime + "', '" + prezime + "', '" + danasnjiDatum + "');";
				korisnik.executeSQL(query);
				korisnik.checkUserId(ime, prezime);
				break;
			case 3:
				while (trigger2 != 0) {
					bazaMeni();
					trigger2 = getInputInt();
					switch (trigger2) {
					case 1:
						test = new Naslov();
						System.out.println();
						System.out.println("Unesite naziv novog naslova");
						String nazivNaslova = unos().nextLine();
						System.out.println("Unesite godinu izdavanja naslova");
						String godinaIzdavanja = unos().nextLine();
						System.out.println("Unesite zanr naslova");
						String zanr = unos().nextLine();
						System.out.println("Unesite ID autora naslova");
						int idA = unos().nextInt();
						upit = "INSERT INTO `naslovi` (`idNaslovi`, `Naziv_naslova`, `Datum_izdavanja`, `Zanr_naslova`,`Autor_idAutor`,`Dostupno`) VALUES (NULL, '"
								+ nazivNaslova + "', '" + godinaIzdavanja + "', '" + zanr + "'," + idA + ",1);";
						test.executeSQL(upit);
						break;
					case 2:
						test = new Naslov();
						System.out.println();
						System.out.println("Unesite ime autora");
						String imeA = unos().nextLine();
						System.out.println("Unesite prezime autora");
						String prezimeA = unos().nextLine();
						System.out.println("Unesite kratku biografiju autora(nije obavezno)");
						String bio = unos().nextLine();
						upit = "INSERT INTO `autor` (`idAutor`, `Ime_autora`, `Prezime_autora`, `Biografija_autora`) VALUES (NULL, '"
								+ imeA + "', '" + prezimeA + "', '" + bio + "');";
						test.executeSQL(upit);
						break;
					case 3:
						test = new Naslov();
						test.ispisiNaslove();
						break;
					}
				}
				break;
			}

		}

	}

}
