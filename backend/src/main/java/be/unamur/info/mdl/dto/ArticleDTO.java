package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
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

  @NotBlank(message = "The reference is required")
  private String reference;

  @NotBlank(message = "The title is required")
  private String title;

  @NotBlank(message = "The content(abstract) is required")
  private String content;

  @PastOrPresent(message = "publicationDate cannot be in the futur")
  private LocalDate publicationDate;

  @NotBlank("The URL is required")
  private String url;

  @NotBlank()
  @JsonAlias({"booktitle"})
  private String journal;

  private String volume;

  private String number;

  private String publisher;

  @Positive("The year cannot null or negative")
  private int year;

  private String month;

  @PositiveOrZero(message = "The price cannont connot be negative")
  private float price;

  @PositiveOrZero(message = "The number of Page cannot be negative")
  private int nbrePage;
  private String pages;

  @PositiveOrZero(message = "The number of citation cannot be negative")
  @JsonProperty(value = "nb_citations")
  private int nbCitations;

  @PositiveOrZero
  @JsonProperty(value = "nb_views")
  private int nbViews;

  private String category = "unknown";

  @JsonProperty(value = "created_at")
  private LocalDate createdAt;


  private UserDTO creator;

  private List<@NotBlank(message = "The author(s) must be defined") String> authors = Collections
    .emptyList();

  // Receive a list of String representing the keywords
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.WRITE_ONLY)
  private Set<@NotBlank(message = "The keywords(s) must be defined")String> keywordList = new LinkedHashSet<>();


  // Send Keywords with it name && slug
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.READ_ONLY)
  private Set<TagDTO> keywords = new LinkedHashSet<>();

}
