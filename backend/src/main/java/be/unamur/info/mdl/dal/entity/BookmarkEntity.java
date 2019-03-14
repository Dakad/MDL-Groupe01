package be.unamur.info.mdl.dal.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class BookmarkEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column
  private String name;
  @OneToOne(cascade= CascadeType.ALL)
  @JoinColumn(name="user_ID", unique= true, nullable=true, insertable=true, updatable=true)
  private UserEntity user;
  public BookmarkEntity(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
