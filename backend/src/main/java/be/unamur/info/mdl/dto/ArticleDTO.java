package be.unamur.info.mdl.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

  private long id;

  @NotBlank()
  private String reference;

  @NotBlank()
  private String title;

  @NotBlank()
  private String content;

  @NotBlank()
  private String url;

  @NotBlank()
  private String journal;

  private String volume;

  private String number;

  private String publisher;

  @Positive()
  private int year;

  private String month;

  @PositiveOrZero
  private float price;

  private String pages;

  @PositiveOrZero()
  private int nbCitations;

  @PositiveOrZero
  private int nbViews;

  @NotBlank()
  private String category;


  private List<@NotBlank(message="The author(s) must be defined")String> authors;

  private LocalDate createdAt;

  private UserDTO creator;

  private List<String> keywordList;

  private Set<TagDTO> keywords = Collections.emptySet();

}
