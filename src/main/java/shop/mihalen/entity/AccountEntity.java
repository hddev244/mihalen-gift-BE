package shop.mihalen.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      @Column(nullable = false, unique = true,updatable = false)
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

      @OneToMany(mappedBy = "account",cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
      @OnDelete(action = OnDeleteAction.CASCADE)
      private List<CartItem> cartItems;
      

      @JsonManagedReference
      @OneToMany(mappedBy = "account",cascade = CascadeType.ALL, orphanRemoval = true)
      @OnDelete(action = OnDeleteAction.CASCADE)
      private List<RoleOfAccount> roleOfAccounts;

      @CreationTimestamp
      @Column(name = "createDate", updatable = false)
      private Date createDate;

      @UpdateTimestamp
      @Column(name = "modifiDate")
      private Date modifiDate;
      
}