package shop.mihalen.dto;

import lombok.Data;

@Data
public class CategoryDTO {
    private String id;
    private String name;
    private ImagesDTO thumbnail;

    public CategoryDTO() {
    }
    public CategoryDTO(String id, String name) {
        this.id = id;
        this.name = name;
      
    } 
    public CategoryDTO(String id, String name, ImagesDTO thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    } 
}
