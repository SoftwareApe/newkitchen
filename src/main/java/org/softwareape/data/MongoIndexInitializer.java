package org.softwareape.data;

import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Indexes;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;

@Singleton
public class MongoIndexInitializer {
    private static final Logger log = Logger.getLogger(MongoIndexInitializer.class.getName());

    // Check if index already exists
    void onStart(@Observes StartupEvent event) {
        log.info("Initializing MongoDB unique index on email for the member collection.");

        // Get the Mongo collection for the "Member" entity
        MongoCollection<PanacheMongoEntityBase> collection = MemberDTO.mongoCollection();

        // Create a unique index on "email" if it doesn't exist already
        if (!indexExists(collection, "email_1")) {
            log.info("Creating unique index for email field in member.");
            collection.createIndex(Indexes.ascending("email"), new com.mongodb.client.model.IndexOptions().unique(true));
        }
        else {
            log.info("Index email_1 already exists.");
        }
    }

    // Check if index already exists
    private boolean indexExists(MongoCollection<PanacheMongoEntityBase> collection, String indexName) {
        try (MongoCursor<Document> cursor = collection.listIndexes().iterator()) {
            while (cursor.hasNext()) {
                Document index = cursor.next();
                if (indexName.equals(index.getString("name"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
