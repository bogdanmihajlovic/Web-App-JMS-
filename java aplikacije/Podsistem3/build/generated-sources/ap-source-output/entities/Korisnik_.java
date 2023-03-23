package entities;

import entities.Narudzbina;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:53")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile SingularAttribute<Korisnik, Double> novac;
    public static volatile ListAttribute<Korisnik, Narudzbina> narudzbinaList;
    public static volatile SingularAttribute<Korisnik, String> adresa;
    public static volatile SingularAttribute<Korisnik, String> grad;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnik;
    public static volatile SingularAttribute<Korisnik, String> username;

}