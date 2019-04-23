package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SOTA", description = "Model representing a State Of The Art")
public class StateOfTheArtDTO {

  @JsonIgnoreProperties
  private Long id;

  private String name;

  private String subject;

  private LocalDate date;

  private UserDTO creator;

  private Set<TagDTO> tags;

}
