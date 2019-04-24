package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SOTA", description = "Model representing a State Of The Art")
public class StateOfTheArtDTO {

  @JsonIgnoreProperties
  private Long id;

  @NotBlank(message = "The name is required")
  private String name;

  @NotBlank(message = "The reference is required")
  private String reference;

  @NotBlank(message = "The subject is required")
  private String subject;


  private LocalDate date;


  @JsonAlias("creatd_at")
  private LocalDate createdAt;


  private UserDTO creator;


  private List<TagDTO> tags;


}
