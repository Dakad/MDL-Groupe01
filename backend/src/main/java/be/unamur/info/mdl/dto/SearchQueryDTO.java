package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SearchQuery", description = "Model representing an user's profile basic information")
public class SearchQueryDTO {

  @NotBlank(message = "The search term is required")
  @Min(value = 2, message = "The search term must be at least 3 characters long")
  private String searchTerm;

  private String tag;

  private int page;
  private String order;
  private String sort;
}
