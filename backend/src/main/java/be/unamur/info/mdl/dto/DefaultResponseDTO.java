package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DefaultResponse", description = "Model representing a default response to a request")
public class DefaultResponseDTO {

  private boolean done;

  private String message;

}
