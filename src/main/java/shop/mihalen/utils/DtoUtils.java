package shop.mihalen.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mihalen.dto.CartItemDTO;
import shop.mihalen.dto.CategoryDTO;
import shop.mihalen.dto.ImagesDTO;
import shop.mihalen.dto.ProductDTO;
import shop.mihalen.entity.CartItem;
import shop.mihalen.entity.ProductEntity;
import shop.mihalen.repository.ProductRepository;

@Service
public class DtoUtils {
    @Autowired
    private ProductRepository productRepository;

    public ProductDTO copyProductToProductDTO(ProductEntity product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(new CategoryDTO(product.getCategory().getId(), product.getCategory().getName()));
        productDTO.setCreateDate(product.getCreateDate());
        productDTO.setModifiDate(product.getModifiDate());

        Set<ImagesDTO> imagesDTO = productRepository.findImagesDTOByProductID(product.getId());
        productDTO.setImages(imagesDTO);
        ImagesDTO thumbnail = imagesDTO.iterator().next();
        productDTO.setThumbnail(thumbnail);

        return productDTO;
    }

    public CartItemDTO copyToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setUsername(cartItem.getAccount().getUsername());
        cartItemDTO.setProduct(copyProductToProductDTO(cartItem.getProduct()));
        return cartItemDTO;
    }
}
