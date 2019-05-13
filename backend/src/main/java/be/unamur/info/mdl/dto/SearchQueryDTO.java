package be.unamur.info.mdl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.LinkedList;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Model representing an user's profile basic information")
public class SearchQueryDTO {

  @ApiParam(value = "Search term", required = true)
  @NotBlank(message = "The search term is required")
  @Length(min = 2, message = "The search term must be at least 2 characters long")
  @Length(max = 255, message = "The search term is too long")
  private String term;

  @ApiParam(value = "Tags")
  private List<String> tag = new LinkedList<>();

  @ApiParam(value = "Pagination", defaultValue = "1")
  @Min(value = 1, message = "The min. page is 1, no less")
  private int page = 1;

  @ApiParam(value = "Order by", defaultValue = "ASC", allowableValues = "ASC,DESC")
  private String order = "ASC";

  @ApiParam(value = "Sort by", defaultValue = "DATE", allowableValues = "DATE, TITLE, NAME")
  private String sort = "DATE";

  @ApiParam(value = "Only search for ", defaultValue = "ALL", allowableValues = "ALL, ARTICLES,SOTAS,USERS")
  private String only = "ALL";


  public enum SearchFor {
    ALL,
    ARTICLES,
    AUTHORS,
    SOTAS,
    USERS;

    @Override
    public String toString() {
      return this.name();
    }
  }
}


