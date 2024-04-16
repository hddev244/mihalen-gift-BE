package shop.mihalen.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn; // Add this import statement

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class CategoryEntity  implements Serializable {
      @Id
      private String id;
      private String name;

      @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
      @JoinTable(name = "category_thumbnail", 
                  joinColumns = {
                        @JoinColumn(name = "category_id")
                  }, 
                  inverseJoinColumns = {
                        @JoinColumn(name = "image_id")
                  }
                  )
      private ImageModel thumbnail;

      @JsonIgnore
      @OneToMany(mappedBy = "category")
      private List<ProductEntity> products;

      public CategoryEntity(String id, String name) {
            this.id = id;
            this.name = name;
      }
}
