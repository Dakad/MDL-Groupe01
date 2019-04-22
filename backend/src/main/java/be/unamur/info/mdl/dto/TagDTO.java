package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "Tag", description = "Model representing an Tag or Category")
public class TagDTO {

  @NotBlank(message = "The tag name is required")
  private String name;

  private String slug;
}
