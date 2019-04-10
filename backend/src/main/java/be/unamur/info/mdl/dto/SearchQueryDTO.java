package be.unamur.info.mdl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data@NoArgsConstructor@AllArgsConstructor
public class SearchQueryDTO {
  @NotBlank
  private String keyword;

  private String tag;

  private int page;
  private String order;
  private String sort;
}
