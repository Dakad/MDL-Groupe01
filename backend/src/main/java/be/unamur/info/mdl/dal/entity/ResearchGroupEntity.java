package be.unamur.info.mdl.dal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class ResearchGroupEntity {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  @Column(name = "nombre")
  private int nbre;


  public ResearchGroupEntity(String name, int nbre) {
    this.name = name;
    this.nbre = nbre;
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

  public int getNbre() {
    return nbre;
  }

  public void setNbre(int nbre) {
    this.nbre = nbre;
  }

  @Override
  public String toString() {
    return "ResearchGroupEntity{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", nbre=" + nbre +
        '}';
  }
}
