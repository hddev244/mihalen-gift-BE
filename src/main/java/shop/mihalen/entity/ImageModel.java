package shop.mihalen.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image_model")
public class ImageModel implements Serializable {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;
      private String name;
      private String type;
      @Column(length = 52428800)
      private byte[] picByte;

      public ImageModel(String name, String type, byte[] picByte) {
            this.name = name;
            this.type = type;
            this.picByte = picByte;
      }
      public ImageModel(Long id,String name) {
            this.id = id;
            this.name = name;
      }
      public ImageModel(Long id) {
            this.id = id;
      }
}
