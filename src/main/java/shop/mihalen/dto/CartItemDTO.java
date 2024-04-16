package shop.mihalen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private String username;
    private ProductDTO product;
    private Integer quantity;
    
    public CartItemDTO(Long id, String username, Integer quantity) {
        this.id = id;
        this.username = username;
        this.quantity = quantity;
    }
    
}
