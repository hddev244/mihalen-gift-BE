package shop.mihalen.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Accounts")
public class AccountEntity implements Serializable {
      @Id
      @GeneratedValue(strategy = GenerationType.AUTO)
      private Long id;
      @Column(nullable = false, unique = true)
      private String username;
      @Nonnull
      private String password;
      private String fullname;
      @Column(nullable = false, unique = true)
      private String email;
      private String photo;
      private String address;
      private String phoneNumber;
      private boolean activated = true;
      private boolean locked = false;

      @JsonManagedReference
      @OneToMany(mappedBy = "account")
      private List<Order> orders;

      @JsonManagedReference
      @OneToMany(mappedBy = "account")
      private List<CartItem> cartItems;

      @JsonManagedReference
      @OneToMany(mappedBy = "account")
      private List<RoleOfAccount> roleOfAccounts;
}