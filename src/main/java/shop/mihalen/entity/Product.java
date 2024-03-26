package shop.mihalen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Product implements Serializable {
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

      @JsonBackReference
      @ManyToOne
      @JoinColumn(name = "category_id",referencedColumnName = "id")
      private Category category;

      @JsonManagedReference
      @OneToMany(mappedBy = "product")
      private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
      
      @JsonManagedReference
      @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
      private List<ProductImage> images = new ArrayList<ProductImage>();

      @JsonManagedReference
      @OneToMany(mappedBy = "product")
      private List<CartItem> cartItems = new ArrayList<CartItem>();

      @CreationTimestamp
      @Column(name = "createDate", updatable = false)
      private Date createDate;

      @UpdateTimestamp
      @Column(name = "modifiDate")
      private Date modifiDate;
}