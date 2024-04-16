package shop.mihalen.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="cartItems")
@Entity
public class CartItem {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
      @JoinColumn(name = "account_id", referencedColumnName = "id")
      private AccountEntity account;
      
      @ManyToOne(fetch = FetchType.EAGER)
      @JoinColumn(name = "product_id", referencedColumnName = "id")
      private ProductEntity product;

      @DecimalMin(value="1")
      private Integer quantity = 1;
      
}
