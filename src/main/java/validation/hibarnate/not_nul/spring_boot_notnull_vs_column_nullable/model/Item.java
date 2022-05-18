package validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
