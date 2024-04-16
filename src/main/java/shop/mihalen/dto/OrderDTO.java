package shop.mihalen.dto;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Set<CartItemDTO> cartItems;
    private Double totalPrice;
    private Date orderDate;

    public OrderDTO(Long id, String name, String address, String phoneNumber, Double totalPrice, Date orderDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }
}
