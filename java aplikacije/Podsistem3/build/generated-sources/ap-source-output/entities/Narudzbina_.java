package entities;

import entities.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:53")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, Date> vreme;
    public static volatile SingularAttribute<Narudzbina, String> adresa;
    public static volatile SingularAttribute<Narudzbina, Integer> idNarudzbina;
    public static volatile SingularAttribute<Narudzbina, Double> cena;
    public static volatile SingularAttribute<Narudzbina, String> grad;
    public static volatile SingularAttribute<Narudzbina, Korisnik> idKorisnik;

}