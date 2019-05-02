package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import be.unamur.info.mdl.dto.BookmarkDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bookmark")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookmarkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;

  @Column
  private String note;


  @Column(name = "created_at")
  private LocalDate createdAt = LocalDate.now();

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity creator;

  @ManyToOne(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  private ArticleEntity article;

  @ManyToOne(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  private StateOfTheArtEntity sota;

  public BookmarkDTO toDTO(){
    if(article != null) return  new BookmarkDTO(note,article.getTitle(),article.getReference());
    if(sota != null) return new BookmarkDTO(note, sota.getTitle(),sota.getReference());
    return null;
  }
}
