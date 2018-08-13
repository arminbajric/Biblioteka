-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 13. Avg 2018. u 20:38
-- Verzija servera: 10.1.32-MariaDB
-- PHP Version: 5.6.36

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Struktura tabele `autor`
--

CREATE TABLE `autor` (
  `idAutor` int(11) NOT NULL,
  `Ime_autora` varchar(100) NOT NULL,
  `Prezime_autora` varchar(100) NOT NULL,
  `Biografija_autora` varchar(2000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabele `korisnik`
--

CREATE TABLE `korisnik` (
  `idKorisnik` int(11) NOT NULL,
  `Ime_korisnika` varchar(100) NOT NULL,
  `Prezime_korisnika` varchar(100) NOT NULL,
  `Datum_registracije` varchar(20) NOT NULL,
  `Broj_pozajmljenih_knjiga` int(11) NOT NULL,
  `Broj_vracenih_knjiga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabele `naslovi`
--

CREATE TABLE `naslovi` (
  `idNaslovi` int(11) NOT NULL,
  `Naziv_naslova` varchar(45) NOT NULL,
  `Datum_izdavanja` varchar(45) DEFAULT NULL,
  `Zanr_naslova` varchar(45) NOT NULL,
  `Autor_idAutor` int(11) NOT NULL,
  `Dostupno` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Struktura tabele `pozajmljivanje`
--

CREATE TABLE `pozajmljivanje` (
  `Datum_pozajmljivanja` varchar(20) DEFAULT NULL,
  `idpozajmljivanja` int(11) NOT NULL,
  `Naslovi_idNaslovi` int(11) NOT NULL,
  `Korisnik_idKorisnik` int(11) NOT NULL,
  `Vraceno_na_vrijeme` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `autor`
--
ALTER TABLE `autor`
  ADD PRIMARY KEY (`idAutor`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`idKorisnik`);

--
-- Indexes for table `naslovi`
--
ALTER TABLE `naslovi`
  ADD PRIMARY KEY (`idNaslovi`,`Autor_idAutor`),
  ADD KEY `fk_Naslovi_Autor1_idx` (`Autor_idAutor`);

--
-- Indexes for table `pozajmljivanje`
--
ALTER TABLE `pozajmljivanje`
  ADD PRIMARY KEY (`idpozajmljivanja`,`Naslovi_idNaslovi`,`Korisnik_idKorisnik`),
  ADD KEY `fk_Pozajmljivanje_Naslovi1_idx` (`Naslovi_idNaslovi`),
  ADD KEY `fk_Pozajmljivanje_Korisnik1_idx` (`Korisnik_idKorisnik`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `autor`
--
ALTER TABLE `autor`
  MODIFY `idAutor` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `idKorisnik` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `naslovi`
--
ALTER TABLE `naslovi`
  MODIFY `idNaslovi` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `pozajmljivanje`
--
ALTER TABLE `pozajmljivanje`
  MODIFY `idpozajmljivanja` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ograničenja za izvezene tabele
--

--
-- Ograničenja za tabele `naslovi`
--
ALTER TABLE `naslovi`
  ADD CONSTRAINT `fk_Naslovi_Autor1` FOREIGN KEY (`Autor_idAutor`) REFERENCES `autor` (`idAutor`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Ograničenja za tabele `pozajmljivanje`
--
ALTER TABLE `pozajmljivanje`
  ADD CONSTRAINT `fk_Pozajmljivanje_Korisnik1` FOREIGN KEY (`Korisnik_idKorisnik`) REFERENCES `korisnik` (`idKorisnik`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Pozajmljivanje_Naslovi1` FOREIGN KEY (`Naslovi_idNaslovi`) REFERENCES `naslovi` (`idNaslovi`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
