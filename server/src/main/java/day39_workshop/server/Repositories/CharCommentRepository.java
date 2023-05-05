package day39_workshop.server.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import day39_workshop.server.Models.Comment;

@Repository
public class CharCommentRepository {
    
    @Autowired
    private MongoTemplate template;

    private static final String COMMENTS_COL = "comments";
    
    private Comment insertComment(Comment c){
        return template.insert(c, COMMENTS_COL);
    }

    public List<Comment> getAllComment(String charId){
        
        Pageable pageable = PageRequest.of(0,10);
        Query commentDynamicQUery = new Query()
            .addCriteria(Criteria.where("charId").is(charId)).with(pageable);


        // Not completed
        List<Comment> filteredComments = template.find(commentDynamicQUery, Comment.class);
        PageableExecutionUtils.getPage(filteredComments, pageable
        , () -> template.count(commentDynamicQUery, Comment.class));

        return filteredComments;
    }
}
