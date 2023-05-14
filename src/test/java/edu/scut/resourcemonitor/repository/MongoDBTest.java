package edu.scut.resourcemonitor.repository;

import edu.scut.resourcemonitor.ResourceMonitorApplication;
import edu.scut.resourcemonitor.entity.TestChild;
import edu.scut.resourcemonitor.entity.TestEntity;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ResourceMonitorApplication.class)
public class MongoDBTest {
    @Resource
    MongoTemplate mongoTemplate;
    @Test
    public void dbTest() {
        String COLLECTION_NAME = "testCollection";

//        mongoTemplate.createCollection(COLLECTION_NAME);
//
//        TestEntity entity = new TestEntity()
//                .setId("01000")
//                .setName("a package")
//                .setParent(new TestChild());
//
//        TestEntity testEntity = mongoTemplate.insert(entity, COLLECTION_NAME);
//
//        System.out.println(testEntity);
    }
}
