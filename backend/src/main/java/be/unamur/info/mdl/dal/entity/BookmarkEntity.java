package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "bookmark")
@AllArgsConstructor
@NoArgsConstructor
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
  @JoinColumn(name = "user_id", unique = true, nullable = false)
  private UserEntity creator;

  @ManyToOne(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  private ArticleEntity article;

  @ManyToOne(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE})
  private StateOfTheArtEntity sota;
}
