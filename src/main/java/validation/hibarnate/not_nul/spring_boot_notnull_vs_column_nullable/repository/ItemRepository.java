package validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import validation.hibarnate.not_nul.spring_boot_notnull_vs_column_nullable.model.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
