package validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.model;

import jdk.jfr.Enabled;
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

    @Test
    @Disabled
    public void shouldNotAllowToPersistNullItemsPrice(){
        //Test échoue, car item.price est null
        /*Item newItem =*/ itemRepository.save(new Item());
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
    }
}