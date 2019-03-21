package be.unamur.info.mdl.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchQueryDTO {
  @NotBlank
  private String[] keyword;

  private String[] tag;

  private int page;
  private String order;
  private String sort;
}
