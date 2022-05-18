package validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/*
En règle générale , nous devrions préférer l'annotation @NotNull à l' annotation @Column(nullable = false).
De cette façon, nous nous assurons que la validation a lieu avant qu'Hibernate n'envoie toute requête SQL d'insertion ou de mise à jour à la base de données.

En outre, il est généralement préférable de s'appuyer sur les règles standard définies dans le Bean Validation,
plutôt que de laisser la base de données gérer la logique de validation.

Mais, même si nous laissons Hibernate générer le schéma de la base de données,
il traduira l'annotation @NotNull dans les contraintes de la base de données. Nous devons alors seulement nous assurer que la propriété hibernate.
validator.apply_to_ddl est définie sur true.
 */
@Entity
public class Item {
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal price;

    @NotBlank
    @Column(name = "item_name")
    private String itemName;

    public Item() {
    }

    public Item(BigDecimal price, String itemName) {
        this.price = price;
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
