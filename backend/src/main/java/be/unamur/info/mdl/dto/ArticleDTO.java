package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

  @NotBlank()
  private String reference;

  @NotBlank()
  private String title;

  @NotBlank()
  private String content;

  @NotBlank()
  private String url;

  @NotBlank()
  @JsonAlias({"booktitle"})
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
  @JsonProperty(value= "nb_citations")
  private int nbCitations;

  @PositiveOrZero
  @JsonProperty(value= "nb_views")
  private int nbViews;

  @NotBlank()
  private String category = "unknown";

  @JsonProperty(value= "created_at")
  private LocalDate createdAt;

  private UserDTO creator;

  private List<@NotBlank(message="The author(s) must be defined")String> authors = Collections.emptyList();

  // Receive a list of String representing the keywords
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.WRITE_ONLY)
  private Set<String> keywordList = new LinkedHashSet<>();


  // Send Keywords with it name && slug
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.READ_ONLY)
  private Set<TagDTO> keywords = new LinkedHashSet<>();

}
