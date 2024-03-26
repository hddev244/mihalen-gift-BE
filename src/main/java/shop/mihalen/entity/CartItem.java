package shop.mihalen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

      @JsonBackReference
      @ManyToOne
      @JoinColumn(name = "accountId", referencedColumnName = "username")
      private AccountEntity account;

      @JsonIgnore
      @ManyToOne
      @JoinColumn(name = "productId", referencedColumnName = "id")
      private Product product;

      @DecimalMin(value="1")
      private Integer quantity = 1;
}
