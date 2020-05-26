package de.maik.storeservice.repository.search;

import de.maik.storeservice.domain.Photo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Photo} entity.
 */
public interface PhotoSearchRepository extends ElasticsearchRepository<Photo, Long> {
}
