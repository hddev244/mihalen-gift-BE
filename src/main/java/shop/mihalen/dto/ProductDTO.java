package shop.mihalen.dto;


import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Boolean available;
    private Boolean genderSpecific = false;
    private String description;
    private CategoryDTO category;
    private ImagesDTO thumbnail;
    private Set<ImagesDTO> images;
    private Date createDate;
    private Date modifiDate;

    public ProductDTO(
    Long id
    ,String name
    ,Double price
    ,String description
    ,CategoryDTO category
     ,ImagesDTO thumbnail
     ,Date createDate
      ,Date modifiDate
) {
    this.id = id;
    this.name = name;
    this.price = price;
     this.description = description;
    this.category = category;
     this.thumbnail = thumbnail;

     this.createDate = createDate;
     this.modifiDate = modifiDate;
}

}
