package entities;

import entities.Kategorija;
import entities.Prodaje;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, Kategorija> idKat;
    public static volatile ListAttribute<Artikal, Prodaje> prodajeList;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, Integer> idArtikal;
    public static volatile SingularAttribute<Artikal, String> opis;

}