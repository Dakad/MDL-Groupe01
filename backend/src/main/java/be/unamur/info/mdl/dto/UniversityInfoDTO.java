package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UniversityInfo", description = "Model representing an information about an university")
public class UniversityInfoDTO {

  private String abbreviation;
  private String name;
  private String website;
}
