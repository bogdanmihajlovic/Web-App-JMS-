package entities;

import entities.Grad;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:49")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, String> password;
    public static volatile SingularAttribute<Korisnik, String> imePrezime;
    public static volatile SingularAttribute<Korisnik, Double> novac;
    public static volatile SingularAttribute<Korisnik, String> adresa;
    public static volatile SingularAttribute<Korisnik, Grad> idG;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnik;
    public static volatile SingularAttribute<Korisnik, String> username;

}