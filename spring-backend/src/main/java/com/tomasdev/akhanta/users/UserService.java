package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.UnionWithOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private MongoTemplate mongoTemplate;

    public User findUserByEmailAndRole(String email, String role) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("email").is(email).and("role").is(role));
        UnionWithOperation unionWithShops = UnionWithOperation.unionWith("shops");

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                unionWithShops,
                Aggregation.match(Criteria.where("email").is(email).and("role").is(role))
        );

        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "customers", User.class);
        if (results.getMappedResults().isEmpty()) throw new ResourceNotFoundException("Usuario");

        return results.getUniqueMappedResult();
    }

    public User findUserByIdAndRole(String id, String role) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(id).and("role").is(role));
        UnionWithOperation unionWithShops = UnionWithOperation.unionWith("shops");

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                unionWithShops,
                Aggregation.match(Criteria.where("_id").is(id).and("role").is(role))
        );

        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "customers", User.class);
        return results.getUniqueMappedResult();
    }
}
