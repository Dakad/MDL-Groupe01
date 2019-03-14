package be.unamur.info.mdl.dal.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity // This tells Hibernate to make a table out of this class
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @JoinColumn(name = "article_id")
  private List<ArticleEntity> articles = new ArrayList<>();
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @JoinColumn(name = "stateoftheart_id")
  private List<StateofTheArtEntity> stateofthearts = new ArrayList<>();
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @JoinColumn(name = "bookmark_id")
  private List<BookmarkEntity> bookmarks = new ArrayList<>();

    protected UserEntity() {}

    public UserEntity(String name, String email) {
    	this.name(name);
    	this.email(email);
	}



	public Long id() {
		return id;
	}

	public void id(Long id) {
		this.id = id;
	}

	public String name() {
		return name;
	}

	public void name(String name) {
		this.name = name;
	}

	public String email() {
		return email;
	}

	public void email(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return String.format("UserEntity[id=%d, name='%s', email='%s']", id, name, email);
	}
}
