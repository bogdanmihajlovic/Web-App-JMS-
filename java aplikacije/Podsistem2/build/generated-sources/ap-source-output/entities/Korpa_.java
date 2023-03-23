package entities;

import entities.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Korpa.class)
public class Korpa_ { 

    public static volatile SingularAttribute<Korpa, Integer> idKorpa;
    public static volatile SingularAttribute<Korpa, Double> ukupncaCena;
    public static volatile SingularAttribute<Korpa, Korisnik> idKorisnika;

}