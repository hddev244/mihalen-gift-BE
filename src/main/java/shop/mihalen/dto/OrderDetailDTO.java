package shop.mihalen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long id;
    private Double price;
    private Integer quantity;
    private ProductDTO product;
    private OrderDTO order;
}
