package entities;

import entities.Artikal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-02-22T03:35:51")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile SingularAttribute<Kategorija, Integer> idNadKat;
    public static volatile SingularAttribute<Kategorija, Integer> idKat;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile ListAttribute<Kategorija, Artikal> artikalList;

}