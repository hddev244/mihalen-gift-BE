package shop.mihalen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String name;
      private String address;
      private String phoneNumber;
      private Boolean deliverySatus = false;

      @CreationTimestamp
      @Temporal(TemporalType.TIMESTAMP)
      @Column(name = "createDate", updatable = false)
      private Date createDate;

      @JsonBackReference
      @ManyToOne
      @JoinColumn(name = "accountId", referencedColumnName = "username")
      private AccountEntity account;

      @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
      private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
}
