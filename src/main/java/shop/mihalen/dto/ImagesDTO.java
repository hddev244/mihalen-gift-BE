package shop.mihalen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagesDTO {
   private Long id;
   private String name;

   public ImagesDTO(Long id) {
        this.id = id;
    }
   
}
