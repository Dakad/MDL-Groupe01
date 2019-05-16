package be.unamur.info.mdl.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
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
@ApiModel(value = "Article", description = "Model representing an Article")
public class ArticleDTO {

  @NotBlank(message = "The reference is required")
  private String reference;

  @NotBlank(message = "The title is required")
  private String title;

  @NotBlank(message = "The content (abstract) is required")
  @JsonProperty(value = "abstract")
  private String content;

  @PastOrPresent(message = "The publication date cannot be in the future")
  @JsonProperty(value = "publication_date")
  private LocalDate publicationDate;

//  @NotBlank(message = "The URL is required")
  private String url;

  @NotBlank(message = "The publication medium is required ")
  @JsonAlias({"booktitle", "series"})
  private String journal;

  private String volume;

  private String number;

  private String publisher;

  @Positive(message = "The year cannot null or negative")
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

  @Builder.Default
  private String category = "unknown";

  @JsonProperty(value = "created_at")
  private LocalDate createdAt;


  private UserDTO creator;

  private BibtexType type;

  @Builder.Default
  private List<@NotBlank(message = "The author(s) must be defined") String> authors = new LinkedList<>();

  // Receive a list of String representing the keywords
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.WRITE_ONLY)
  private Set<@NotBlank(message = "The keywords(s) must be defined") String> keywordList = new LinkedHashSet<>();


  // Send Keywords with it name && slug
  @Builder.Default
  @JsonProperty(value = "keywords", access = Access.READ_ONLY)
  private Set<TagDTO> keywords = new LinkedHashSet<>();

}
