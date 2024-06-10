package com.tomasdev.akhanta.users;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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

    public User findUserByEmailAndRol(String email, String rol) {
        MatchOperation matchStage = Aggregation.match(Criteria.where("email").is(email).and("role").is(rol));
        UnionWithOperation unionWithShops = UnionWithOperation.unionWith("shops");

        Aggregation aggregation = Aggregation.newAggregation(
                matchStage,
                unionWithShops,
                Aggregation.match(Criteria.where("email").is(email).and("role").is(rol))
        );

        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "customers", User.class);
        return results.getUniqueMappedResult();
    }
}
