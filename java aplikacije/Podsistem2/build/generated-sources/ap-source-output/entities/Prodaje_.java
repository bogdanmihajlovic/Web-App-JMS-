package entities;

import entities.Artikal;
import entities.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Prodaje.class)
public class Prodaje_ { 

    public static volatile SingularAttribute<Prodaje, Korisnik> idProdavac;
    public static volatile SingularAttribute<Prodaje, Integer> idProdaje;
    public static volatile SingularAttribute<Prodaje, Double> popust;
    public static volatile SingularAttribute<Prodaje, Double> cena;
    public static volatile SingularAttribute<Prodaje, Artikal> idArtikal;

}