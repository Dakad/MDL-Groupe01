package be.unamur.info.mdl.dal.entity;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "bookmark")
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
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
  private ArticleEntity sota;
}
