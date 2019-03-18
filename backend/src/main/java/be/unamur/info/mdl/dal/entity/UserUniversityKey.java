package be.unamur.info.mdl.dal.entity;

import java.io.Serializable;
import javax.persistence.Column;

public class UserUniversityKey implements Serializable {

    @Column(name ="user_id")
    Long userId;
    @Column(name="university_id")
    Long universityId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getUniversityId() {
    return universityId;
  }

  public void setUniversityId(Long universityId) {
    this.universityId = universityId;
  }


}
