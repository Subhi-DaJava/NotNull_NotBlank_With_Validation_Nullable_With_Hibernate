package validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.repository.ItemRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class ItemIntegrationTest {
    @Autowired
    private ItemRepository itemRepository;

    /* Première étape : With null, price= null
     * Comme nous pouvons le voir, dans ce cas, notre système a lancé javax.validation.ConstraintViolationException .
     *
     * Il est important de noter qu'Hibernate n'a pas déclenché l'instruction d'insertion SQL. Par conséquent, les données invalides n'étaient pas enregistrées dans la base de données.
     *
     * En effet, l'événement de cycle de vie de l'entité de pré-persistance a déclenché la validation du bean juste avant l'envoi de la requête à la base de données.
     */

    /*Deuxième étape : Hibernate
     *Dans la section précédente, nous avons présenté le fonctionnement de la validation @NotNull.
     *Découvrons maintenant ce qui se passe si nous laissons Hibernate générer le schéma de la base de données pour nous.
     * Pour cette raison, nous allons définir quelques propriétés dans notre fichier application.properties :
     * spring.jpa.hibernate.ddl-auto=create-drop
     * spring.jpa.show-sql=true
     * Si nous démarrons maintenant notre application, nous verrons l'instruction DDL :
     * create table item (
     * id bigint not null,
     * price decimal(19,2) not null,
     * primary key (id))
     * Étonnamment, Hibernate ajoute automatiquement la contrainte non nulle à la définition de la colonne de prix.
     * Comment est-ce possible ?
     * Il s'avère que, prêt à l'emploi, Hibernate traduit les annotations de validation du bean appliquées aux entités dans les métadonnées du schéma DDL.
     * C'est assez pratique et fait beaucoup de sens. Si nous appliquons @NotNull à l'entité, nous souhaitons très probablement que
     * la colonne de base de données correspondante ne soit pas nulle également.
     * Note :
     * Cependant, si, pour une raison quelconque, nous voulons désactiver cette fonctionnalité Hibernate, tout ce que nous devons faire est de définir
     * la propriété hibernate.validator.apply_to_ddl sur false.
     */
    /*
     * Troisième étape :
     * @Column(nullable = false)
     * private BigDecimal price;
     *
     */

  /*  @Test
    @Disabled
    public void shouldNotAllowToPersistNullItemsPrice(){
        //Test échoue, car item.price est null
        *//*Item newItem =*//* itemRepository.save(new Item());
       // assertThat(newItem).isNull();
    }

    @Test
    public void shouldNotAllowToPersistNullItemsPriceWithAGivenPriceAndItemName(){
        Item newItem = new Item();
        newItem.setPrice(BigDecimal.valueOf(200.00));
        newItem.setItemName("New Item");
        Item savedItem = itemRepository.save(newItem);
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isEqualTo(1);
    }
    @Test
    public void shouldNotAllowToPersistNullItemsPriceWithGivenEmptyPrice(){
        Item newItem = new Item();
        newItem.setPrice(BigDecimal.valueOf(200.00));
        newItem.setItemName("Cou cou");
        Item savedItem = itemRepository.save(newItem);
        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isEqualTo(1);

        Tout d'abord, nous pouvons remarquer qu'Hibernate a généré la colonne de prix avec la contrainte non nulle comme nous l'avions prévu .
        De plus, il a pu créer la requête d'insertion SQL et la transmettre. Par conséquent, c'est la base de données sous-jacente qui a déclenché l'erreur.

        Note :
        Presque toutes les sources soulignent que @Column(nullable = false) n'est utilisé que pour la génération de schéma DDL.
        Hibernate, cependant, est capable d'effectuer la validation de l'entité par rapport aux éventuelles valeurs nulles,
        même si le champ correspondant est annoté uniquement avec @Column(nullable = false).

        important:
        Afin d'activer cette fonctionnalité Hibernate, nous devons définir explicitement la propriété hibernate.check_nullability sur true :
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.check_nullability=true

        Cette fois, notre cas de test a lancé l' exception org.hibernate.PropertyValueException .
        Il est crucial de noter que, dans ce cas, Hibernate n'a pas envoyé la requête SQL d'insertion à la base de données .

    }*/

    //Pour la troisième étape
    @Test
    public void shouldNotAllowToPersistNullItemsPrice() {
        Item newItem = new Item();
        newItem.setItemName("new item");
        itemRepository.save(newItem);
        assertThat(newItem).isNull();
    }


}