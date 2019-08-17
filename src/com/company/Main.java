package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static Integer iVal;

        public static final String DRIVER = "org.sqlite.JDBC";
        public static final String DB_URL = "jdbc:sqlite:mydb.db";

        private static Connection conn;
        private static Statement stmt;

    //METODA TWORZĄCA POŁĄCZENIE Z BAZĄ DANYCH.
    public static void zdobadzPolonczenie() {

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem połączenia");
            e.printStackTrace();
        }
    }

    //METODA TWORZĄCA TABELE I REKORDY.
    public static void dodajRekordyTabele(String stworzRekordTabele) {
        try {
            stmt.execute(stworzRekordTabele);

        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu rekordu lub tabeli");
            e.printStackTrace();
        }
    }

    //METODA WYSWIETLAJACA TABELE.
    public static void pokazTabele(String pokazTabele) {

        try {
            ResultSet rs = stmt.executeQuery(pokazTabele);
            if (iVal == 1) {
                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    Integer numer = rs.getInt("numer");

                    System.out.println("ID = " + id);
                    System.out.println("Imie = " + imie);
                    System.out.println("Nazwisko = " + nazwisko);
                    System.out.println("Numer = " + numer);
                }
            } else if (iVal == 2) {
                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String nazwa = rs.getString("nazwa");
                    String data = rs.getString("data");
                    Integer cena = rs.getInt("cena");
                    Integer lekarz_id = rs.getInt("lekarz_id");

                    System.out.println("ID = " + id);
                    System.out.println("Nazwa = " + nazwa);
                    System.out.println("Data = " + data);
                    System.out.println("Cena = " + cena);
                    System.out.println("Lekarz_id = " + lekarz_id);
                }
            } else if (iVal == 3) {
                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    String imie = rs.getString("imie");
                    String nazwisko = rs.getString("nazwisko");
                    String PESEL = rs.getString("PESEL");

                    System.out.println("ID = " + id);
                    System.out.println("Imie = " + imie);
                    System.out.println("Nazwisko = " + nazwisko);
                    System.out.println("Pesel = " + PESEL);
                }
            } else if (iVal == 4) {
                while (rs.next()) {
                    Integer id = rs.getInt("ID");
                    Integer pacjent_id = rs.getInt("pacjent_id");
                    Integer zabieg_id = rs.getInt("zabieg_id");

                    System.out.println("ID = " + id);
                    System.out.println("Pacjent_id = " + pacjent_id);
                    System.out.println("Zabieg_id = " + zabieg_id);
                }
            }

        } catch (SQLException e) {
            System.err.println("Blad przy wyświetleniu rekordów tabeli.");
            e.printStackTrace();
        }
    }

    //METODA USUWAJĄCA REKORD Z TABELI.
    public static void usunRekord(String usunRekord) {
        try {
            stmt.execute(usunRekord);

        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu rekordu z tabeli.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String wybor;
        String lekarz;
        Scanner scan = new Scanner(System.in);

        Main metody = new Main();
        metody.zdobadzPolonczenie();

        metody.dodajRekordyTabele("CREATE TABLE IF NOT EXISTS lekarz" +
                "(id INTEGER PRIMARY KEY, imie VARCHAR(20) NOT NULL, nazwisko " +
                "VARCHAR(20) NOT NULL, numer INTEGER NOT NULL UNIQUE);");
        metody.dodajRekordyTabele("CREATE TABLE IF NOT EXISTS zabieg" +
                "(id INTEGER PRIMARY KEY, nazwa VARCHAR(20) NOT NULL, data TEXT UNIQUE NOT NULL, " +
                "cena INTEGER NOT NULL, lekarz_id INTEGER REFERENCES lekarz(id) NOT NULL);");
        metody.dodajRekordyTabele("CREATE TABLE IF NOT EXISTS pacjent" +
                "(id INTEGER PRIMARY KEY, imie VARCHAR(20) NOT NULL, " +
                "nazwisko VARCHAR(20) NOT NULL, PESEL VARCHAR(9) NOT NULL UNIQUE);");
        metody.dodajRekordyTabele("CREATE TABLE IF NOT EXISTS pacjent_has_zabieg" +
                "(id INTEGER PRIMARY KEY, pacjent_id INTEGER REFERENCES pacjent(id), " +
                "zabieg_id INTEGER REFERENCES zabieg(id));");

        do {

            System.out.println("Wybierz opcje:\n1 - Dodaj rekord,\n2 - Modyfikuj rekord,\n" +
                    "3 - Usuń rekord,\n4 - Wyświetl pojedzyńczy rekord,\n5 - Wyświetl wszystkie rekordy.\n0 - " +
                    "Zakończ program.");
            wybor = scan.nextLine();

            if (wybor.equals("1")) {
                System.out.println("Wybrałeś opcje: 1 - Dodaj rekord.\nWybierz do jakiej tabeli chcesz dodać rekord:\n" +
                        "1 - Tabela lekarz,\n2 - Tabela zabieg,\n3 - Tabela pacjent,\n4 - Tabela pacjent_has_zabieg.");
                String wyborDodaj = scan.nextLine();
                if (wyborDodaj.equals("1")) {
                    System.out.println("Imie i nazwisko musi posiadać max. 20 znaków, numer musi być unikalny, " +
                            "a wszystkie dane muszę zostać wprowadzone.");
                    System.out.println("Wpisz imie lekarza:");
                    String imieLekarza = scan.nextLine();
                    System.out.println("Wpisz nazwisko lekarza:");
                    String nazwiskoLekarza = scan.nextLine();
                    System.out.println("Wpisz numer lekarza:");
                    String numerLekarza = scan.nextLine();
                        metody.dodajRekordyTabele("INSERT INTO LEKARZ (imie, nazwisko, numer) VALUES ('" + imieLekarza +
                                "', '" + nazwiskoLekarza + "', '" + numerLekarza + "');");
                } else if (wyborDodaj.equals("2")) {
                    System.out.println("Nazwa musi posiadać max. 20 znaków, wszystkie dane muszą zostać wprowadzone.");
                    System.out.println("Wpisz nazwe zabiegu:");
                    String nazwaZabiegu = scan.nextLine();
                    System.out.println("Wpisz date zabiegu (format YYYY-MM-DD):");
                    String dataZabiegu = scan.nextLine();
                    System.out.println("Wpisz cene zabiegu:");
                    String cenaZabiegu = scan.nextLine();
                    System.out.println("Wpisz numer (id) lekarza wykonującego zabieg:");
                    String lekarzZabieg = scan.nextLine();
                    metody.dodajRekordyTabele("INSERT INTO ZABIEG (nazwa, data, cena, lekarz_id) VALUES ('"
                            + nazwaZabiegu + "', '" + dataZabiegu + "', '" + cenaZabiegu + "', '" + lekarzZabieg + "');");
                } else if (wyborDodaj.equals("3")) {
                    System.out.println("Imie i nazwisko musi posiadać max. 20 znaków. Pesel musi być unikalny, wszystkie " +
                            "danę muszą zostać wprowadzone.");
                    System.out.println("Wpisz imie pacjenta:");
                    String imiePacjenta = scan.nextLine();
                    System.out.println("Wpisz nazwisko pacjenta:");
                    String nazwiskoPacjenta = scan.nextLine();
                    System.out.println("Wpisz pesel pacjenta:");
                    String peselPacjenta = scan.nextLine();
                    metody.dodajRekordyTabele("INSERT INTO PACJENT (imie, nazwisko, pesel) VALUES ('"
                            + imiePacjenta + "', '" + nazwiskoPacjenta + "', '" + peselPacjenta + "');");
                } else if (wyborDodaj.equals("4")) {
                    System.out.println("Wpisz numer pacjent_id:");
                    String numerPacjent = scan.nextLine();
                    System.out.println("Wpisz numer zabieg_id:");
                    String numerZabieg = scan.nextLine();
                    metody.dodajRekordyTabele("INSERT INTO PACJENT_HAS_ZABIEG (pacjent_id, zabieg_id) " +
                            "VALUES ('"+numerPacjent+"', '"+numerZabieg+"');");

                } else System.out.println("Wybrałeś złą liczbę!");
            } else if (wybor.equals("2")) {
                System.out.println("Wybrałeś opcje modyfikacji rekordu. \nWybierz dla jakiej tabeli chcesz modyfikować " +
                        "rekord:\n1 - Tabela lekarz,\n2 - Tabela zabieg,\n3 - Tabela pacjent,\n4 - Tabela pacjent_has_" +
                        "zabieg.");
                String wyborModyfikuj = scan.nextLine();
                if (wyborModyfikuj.equals("1")) {
                    iVal = 1;
                    metody.pokazTabele("SELECT * FROM LEKARZ;");
                    System.out.println("Wpisz nr ID rekordu który chcesz modyfikować.");
                    String idRekordu = scan.nextLine();
                    System.out.println("Wybierz co chcesz modyfikowac:\n1 - ID,\n2 - Imie,\n3 - Nazwisko,\n4 - Numer.");
                    String wyborMod = scan.nextLine();
                    if (wyborMod.equals("1")) {
                        System.out.println("Podaj nowe ID:");
                        String nevWybor = scan.nextLine();
                        wyborMod = "ID";
                        metody.dodajRekordyTabele("UPDATE LEKARZ SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("2")) {
                        wyborMod = "IMIE";
                        System.out.println("Podaj nowe imie:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE LEKARZ SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("3")) {
                        wyborMod = "NAZWISKO";
                        System.out.println("Podaj nowe nazwisko:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE LEKARZ SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("4")) {
                        wyborMod = "NUMER";
                        System.out.println("Podaj nowy numer:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE LEKARZ SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else System.out.println("Wybrałeś złą opcje!");

                } else if (wyborModyfikuj.equals("2")) {
                    iVal = 2;
                    metody.pokazTabele("SELECT * FROM ZABIEG;");
                    System.out.println("Wpisz nr ID rekordu który chcesz modyfikować.");
                    String idRekordu = scan.nextLine();
                    System.out.println("Wybierz co chcesz modyfikowac:\n1 - ID,\n2 - Nazwa,\n3 - Data,\n4 - Cena," +
                            "\n5 - lekarz_id");
                    String wyborMod = scan.nextLine();
                    if (wyborMod.equals("1")) {
                        wyborMod = "ID";
                        System.out.println("Podaj nowe ID:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("2")) {
                        wyborMod = "NAZWA";
                        System.out.println("Podaj nową nazwę:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("3")) {
                        wyborMod = "DATA";
                        System.out.println("Podaj nową datę:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("4")) {
                        wyborMod = "CENA";
                        System.out.println("Podaj nową cene:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("5")) {
                        wyborMod = "LEKARZ_ID";
                        System.out.println("Podaj nowy numer dla lekarz_id:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else System.out.println("Wybrałeś złą opcję!");
                } else if (wyborModyfikuj.equals("3")) {
                    iVal = 3;
                    metody.pokazTabele("SELECT * FROM PACJENT;");
                    System.out.println("Wpisz nr ID rekordu który chcesz modyfikować.");
                    String idRekordu = scan.nextLine();
                    System.out.println("Wybierz co chcesz modyfikowac:\n1 - ID,\n2 - Imie,\n3 - Nazwisko,\n4 - PESEL.");
                    String wyborMod = scan.nextLine();
                    if (wyborMod.equals("1")) {
                        System.out.println("Podaj nowe ID:");
                        String nevWybor = scan.nextLine();
                        wyborMod = "ID";
                        metody.dodajRekordyTabele("UPDATE PACJENT SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("2")) {
                        wyborMod = "IMIE";
                        System.out.println("Podaj nowe imie:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE PACJENT SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("3")) {
                        wyborMod = "NAZWISKO";
                        System.out.println("Podaj nowe nazwisko:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE PACJENT SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("4")) {
                        wyborMod = "PESEL";
                        System.out.println("Podaj nowy pesel:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE PACJENT SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else System.out.println("Wybrałeś złą opcje!");
                } else if (wyborModyfikuj.equals("4")) {
                    iVal = 4;
                    metody.pokazTabele("SELECT * FROM PACJENT_HAS_ZABIEG;");
                    System.out.println("Wpisz nr ID rekordu który chcesz modyfikować.");
                    String idRekordu = scan.nextLine();
                    System.out.println("Wybierz co chcesz modyfikowac:\n1 - ID,\n2 - pacjent_id,\n3 - zabieg_id.");
                    String wyborMod = scan.nextLine();
                    if (wyborMod.equals("1")) {
                        System.out.println("Podaj nowe ID:");
                        String nevWybor = scan.nextLine();
                        wyborMod = "ID";
                        metody.dodajRekordyTabele("UPDATE PACJENT_HAS_ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("2")) {
                        wyborMod = "PACJENT_ID";
                        System.out.println("Podaj nowy numer dla pacjent_id:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE PACJENT_HAS_ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else if (wyborMod.equals("3")) {
                        wyborMod = "ZABIEG_ID";
                        System.out.println("Podaj nowy numer dla zabieg_id:");
                        String nevWybor = scan.nextLine();
                        metody.dodajRekordyTabele("UPDATE PACJENT_HAS_ZABIEG SET " + wyborMod + " = '" + nevWybor + "' " +
                                "WHERE ID = " + idRekordu);
                    } else System.out.println("Wybrałeś złą opcję!");
                }


            } else if (wybor.equals("3")) {
                System.out.println("Wybrałeś opcje usunięcia rekordu.\nWybierz dla jakiej tabeli chcesz usunąć rekord:\n" +
                        "1 - Tabela lekarz,\n2 - Tabela zabieg,\n3 - Tabela pacjent,\n4 - Tabela pacjent_has_zabieg.");
                String wyborUsun = scan.nextLine();
                if (wyborUsun.equals("1")) {
                    iVal = 1;
                    metody.pokazTabele("SELECT * FROM LEKARZ;");
                    System.out.println("Wpisz numer ID rekordu który chcesz usunąć:");
                    String wyborID = scan.nextLine();
                    metody.usunRekord("DELETE FROM LEKARZ WHERE ID = " + wyborID);
                } else if (wyborUsun.equals("2")) {
                    iVal = 2;
                    metody.pokazTabele("SELECT * FROM ZABIEG");
                    System.out.println("Wpisz numer ID rekordu który chcesz usunąć:");
                    String wyborID = scan.nextLine();
                    metody.usunRekord("DELETE FROM ZABIEG WHERE ID = " + wyborID);
                } else if (wyborUsun.equals("3")) {
                    iVal = 3;
                    metody.pokazTabele("SELECT * FROM PACJENT");
                    System.out.println("Wpisz numer ID rekordu który chcesz usunąć:");
                    String wyborID = scan.nextLine();
                    metody.usunRekord("DELETE FROM PACJENT WHERE ID = " + wyborID);
                } else if (wyborUsun.equals("4")) {
                    iVal = 4;
                    metody.pokazTabele("SELECT * FROM PACJENT_HAS_ZABIEG");
                    System.out.println("Wpisz numer ID rekordu który chcesz usunąć:");
                    String wyborID = scan.nextLine();
                    metody.usunRekord("DELETE FROM PACJENT_HAS_ZABIEG WHERE ID = " + wyborID);
                } else System.out.println("Wybrałeś złą opcję!");


            } else if (wybor.equals("4")) {
                System.out.println("Wybrałeś opcje: 4 - Wyświetl pojedyńczy rekord.\nWybierz do jakiej tabeli chcesz " +
                        "wyświetlić pojedynczy rekord:\n" +
                        "1 - Tabela lekarz,\n2 - Tabela zabieg,\n3 - Tabela pacjent,\n4 - Tabela pacjent_has_zabieg.");
                String wyborPojedynczy = scan.nextLine();
                if (wyborPojedynczy.equals("1")) {
                    iVal = 1;
                    System.out.println("Wpisz numer ID rekordu który chcesz wyświetlić z tabeli lekarz:");
                    String wyborID = scan.nextLine();
                    metody.pokazTabele("SELECT * FROM LEKARZ WHERE ID = " + wyborID);
                } else if (wyborPojedynczy.equals("2")) {
                    iVal = 2;
                    System.out.println("Wpisz numer ID rekordu który chcesz wyświetlić z tabeli zabieg:");
                    String wyborID = scan.nextLine();
                    metody.pokazTabele("SELECT * FROM ZABIEG WHERE ID = " + wyborID);
                } else if (wyborPojedynczy.equals("3")) {
                    iVal = 3;
                    System.out.println("Wpisz numer ID rekordu który chcesz wyświetlić z tabeli pacjent:");
                    String wyborID = scan.nextLine();
                    metody.pokazTabele("SELECT * FROM PACJENT WHERE ID = " + wyborID);
                } else if (wyborPojedynczy.equals("4")) {
                    iVal = 4;
                    System.out.println("Wpisz numer ID rekordu który chcesz wyświetlić z tabeli pacjent_has_zabieg:");
                    String wyborID = scan.nextLine();
                    metody.pokazTabele("SELECT * FROM PACJENT_HAS_ZABIEG WHERE ID = " + wyborID);
                } else System.out.println("Wybrałeś złą opcję!");

            } else if (wybor.equals("5")) {
                System.out.println("Wybrałeś opcje: 5 - Wyświetl wszystkie rekordy.\nWybierz do jakiej tabeli chcesz " +
                        "wyświetlić pojedynczy rekord:\n" +
                        "1 - Tabela lekarz,\n2 - Tabela zabieg,\n3 - Tabela pacjent,\n4 - Tabela pacjent_has_zabieg.");
                String wyborPojedynczy = scan.nextLine();
                if (wyborPojedynczy.equals("1")) {
                    iVal = 1;
                    metody.pokazTabele("SELECT * FROM LEKARZ;");
                } else if (wyborPojedynczy.equals("2")) {
                    iVal = 2;
                    metody.pokazTabele("SELECT * FROM ZABIEG;");
                } else if (wyborPojedynczy.equals("3")) {
                    iVal = 3;
                    metody.pokazTabele("SELECT * FROM PACJENT;");
                } else if (wyborPojedynczy.equals("4")) {
                    iVal = 4;
                    metody.pokazTabele("SELECT * FROM PACJENT_HAS_ZABIEG;");
                }

            }
        }while (!wybor.equals("0"));
    }
    }
