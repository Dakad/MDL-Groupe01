package be.unamur.info.mdl.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class TagDTO {

  @NotBlank
  private String name;

  private String slug;
}
