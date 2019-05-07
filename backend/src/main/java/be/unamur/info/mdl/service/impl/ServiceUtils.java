package be.unamur.info.mdl.service.impl;

import be.unamur.info.mdl.dal.entity.TagEntity;
import be.unamur.info.mdl.dal.repository.TagRepository;
import com.github.slugify.Slugify;
import java.util.Optional;

/**
 * This class offers different methods common between the services.
 */
public abstract class ServiceUtils {
  private static final Slugify slugify = new Slugify();

  private ServiceUtils() {
    super();
  }

  /**
   * Get the matching tag from the repository or create a new one.
   * @param tagName  The tag name
   * @param tagRepository
   * @return the persisted or created Tag
   */
  public static TagEntity getOrCreateTag(String tagName,
    TagRepository tagRepository) {
    String slug = slugify.slugify(tagName);
    Optional<TagEntity> dbTag = tagRepository.findBySlug(slug);

    TagEntity tag;
    if (!dbTag.isPresent()) {
      tag = TagEntity.builder().name(tagName.trim()).slug(slug).build();
    } else {
      tag = dbTag.get();
    }
    return tag;
  }

}
