package shop.mihalen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductEntity implements Serializable {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @NotNull
      private String name;
      @NotNull
      @DecimalMin("0.0")
      private Double price;
      private Boolean available;
      @NotNull
      private Boolean genderSpecific;
      private String description;

      @ManyToOne
      @JoinColumn(name = "category_id",referencedColumnName = "id")
      private CategoryEntity category;

      @JsonIgnore
      @OneToMany(mappedBy = "product")
      private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
      
      @ManyToMany(cascade = CascadeType.ALL)
      @JoinTable(name = "product_images", 
                  joinColumns = {
                        @JoinColumn(name = "product_id")
                  }, 
                  inverseJoinColumns = {
                        @JoinColumn(name = "image_id")
                  }
                  )
      private Set<ImageModel> images;

      @OneToOne(cascade = CascadeType.ALL)
      @JoinTable(name = "product_thumbnail", 
                  joinColumns = {
                        @JoinColumn(name = "product_id")
                  }, 
                  inverseJoinColumns = {
                        @JoinColumn(name = "image_id")
                  }
                  )
      private ImageModel thumbnail;

      @OneToOne(mappedBy = "product",fetch = FetchType.LAZY)
      @JoinColumn(name = "cartItem_id", referencedColumnName = "id")
      private CartItem cartItem;

      @CreationTimestamp
      @Column(name = "createDate", updatable = false)
      private Date createDate;

      @UpdateTimestamp
      @Column(name = "modifiDate")
      private Date modifiDate;

      public ProductEntity(Long id, String name, Double price, Boolean available,
                   Boolean genderSpecific, String description, CategoryEntity category, ImageModel thumbnail,
                  Date createDate, Date modifiDate) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.available = available;
            this.genderSpecific = genderSpecific;
            this.description = description;
            this.category = category;
            this.thumbnail = thumbnail;
            this.createDate = createDate;
            this.modifiDate = modifiDate;
      }

      public ProductEntity(Long id,String name, Double price,Boolean available,
      String description, CategoryEntity category,Date createDate, Date modifiDate, ImageModel thumbnail
      ) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.available = available;
            this.description = description;
            this.category = category;
            this.createDate = createDate;
            this.modifiDate = modifiDate;
            this.thumbnail = thumbnail;
         //   this.images = images;
      }

      
         
}