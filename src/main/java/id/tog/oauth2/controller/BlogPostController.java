package id.tog.oauth2.controller;


import id.tog.oauth2.entity.BlogPost;
import id.tog.oauth2.repository.BlogPostRepository;
import id.tog.oauth2.responseException.BadRequest;
import id.tog.oauth2.util.PageableSpec;
import id.tog.oauth2.util.SpecificationUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/blog-post")
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;

    public BlogPostController(BlogPostRepository businessRepository){
        this.blogPostRepository=businessRepository;
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @GetMapping({"","/"})
    public Page<BlogPost> getAllBlogPost(@RequestParam Map<String, String> params){
        PageableSpec<BlogPost> pageableSpec = SpecificationUtils.of(params);
        return blogPostRepository.findAll(pageableSpec.getSpecification(), pageableSpec.getPageable());
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @GetMapping({"/{blogPostId:[\\d]+}", "/{blogPostId:[\\d]+}"})
    public BlogPost getByIdBlogPost(@PathVariable Long blogPostId){
        return blogPostRepository.findById(blogPostId).orElseThrow(() -> new BadRequest("Blog post id not found "));
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @PostMapping({"","/"})
    public BlogPost postBlogPost(@RequestBody BlogPost newBlogPost){

        if (newBlogPost.getTitle().isEmpty()) throw new BadRequest("Blog post tittle required");
        if (newBlogPost.getBody().isEmpty()) throw new BadRequest("Blog post body required");
        if (newBlogPost.getAuthor().isEmpty()) throw new BadRequest("Blog post author required");

        return blogPostRepository.save(newBlogPost);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @PutMapping({"/{blogPostId:[\\d]+}", "/{blogPostId:[\\d]+}"})
    public BlogPost putBlogPost(@PathVariable Long blogPostId, @RequestBody BlogPost newBlogPost){
        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(() -> new BadRequest("Blog post not found"));
        if (newBlogPost.getTitle() != null) blogPost.setTitle(newBlogPost.getTitle());
        if (newBlogPost.getBody()!= null) blogPost.setBody(newBlogPost.getBody());
        if (newBlogPost.getAuthor()!= null) blogPost.setAuthor(newBlogPost.getAuthor());


        return blogPostRepository.save(blogPost);
    }

    @Secured({"ROLE_SUPERADMIN", "ROLE_ADMIN"})
    @DeleteMapping({"/{blogPostId:[\\d]+}", "/{blogPostId:[\\d]+}"})
    public String deleteBlogPost(@PathVariable Long blogPostId){
        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(() -> new BadRequest("Blog post not found"));
        blogPost.setDeleted(new Date());
        blogPostRepository.save(blogPost);
        return "{\"success\":true}";
    }

}
