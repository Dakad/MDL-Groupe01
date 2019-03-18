package be.unamur.info.mdl.dal.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserUniversityKey implements Serializable {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "university_id")
  private Long universityId;


}
