package entities;

import entities.Korpa;
import entities.Prodaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Korisnik.class)
public class Korisnik_ { 

    public static volatile ListAttribute<Korisnik, Prodaje> prodajeList;
    public static volatile SingularAttribute<Korisnik, Korpa> korpa;
    public static volatile SingularAttribute<Korisnik, Integer> idKorisnik;
    public static volatile SingularAttribute<Korisnik, String> username;

}