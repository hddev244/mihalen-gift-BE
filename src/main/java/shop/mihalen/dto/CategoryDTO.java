package shop.mihalen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private String id;
    private String name;
    private ImagesDTO thumbnail;

    public CategoryDTO(String id, String name) {
        this.id = id;
        this.name = name;
      
    } 
}
