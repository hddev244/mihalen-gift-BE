package shop.mihalen.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Category  implements Serializable {
      @Id
      private String id;
      private String name;

      @OneToMany(mappedBy = "category")
      private List<Product> products;

      @Override
      public String toString() {
            return this.name;
      }
}
