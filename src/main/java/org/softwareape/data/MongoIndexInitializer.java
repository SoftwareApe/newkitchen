package org.softwareape.data;

import java.util.logging.Logger;

import org.bson.Document;
import org.softwareape.util.TrimInterceptor;

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

    void onStart(@Observes StartupEvent event) {
        log.info("Initializing MongoDB unique index on email for the member collection.");

        // Get the Mongo collection for the "Member" entity
        MongoCollection<PanacheMongoEntityBase> collection = MemberDTO.mongoCollection();

        // Create a unique index on "email" if it doesn't exist already
        if (!indexExists(collection, "email_1")) {
            collection.createIndex(Indexes.ascending("email"), new com.mongodb.client.model.IndexOptions().unique(true));
        }
    }

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
