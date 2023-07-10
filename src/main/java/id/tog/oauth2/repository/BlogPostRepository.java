package id.tog.oauth2.repository;

import id.tog.oauth2.entity.BlogPost;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BlogPostRepository extends PagingAndSortingRepository<BlogPost, Long>, JpaSpecificationExecutor<BlogPost> {
}
