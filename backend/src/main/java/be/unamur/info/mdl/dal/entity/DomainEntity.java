package be.unamur.info.mdl.dal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class DomainEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;

  public DomainEntity(String name) {
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

  @Override
  public String toString() {
    return "DomainEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
