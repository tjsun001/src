package com.byteslounge.spring.tx.dao.impl;

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: thurmansanders
 * Date: 10/3/13
 * Time: 11:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class deleteUser {
    protected static Keyspace keyspace;
    protected static Properties properties;
    protected static Cluster tutorialCluster;
    static StringSerializer stringSerializer = StringSerializer.get();
    static LongSerializer longSerializer = LongSerializer.get();
    protected String socialSecurityNumber;
    public deleteUser(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public QueryResult<?> execute() {
        properties = new Properties();
        try {
            properties.load(GetSliceForUser.class.getResourceAsStream("/tutorial.properties"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        tutorialCluster = HFactory.getOrCreateCluster(properties.getProperty("cluster.name", "TutorialCluster"),
        properties.getProperty("cluster.hosts", "127.0.0.1:9160"));
        ConfigurableConsistencyLevel ccl = new ConfigurableConsistencyLevel();
        ccl.setDefaultReadConsistencyLevel(HConsistencyLevel.ONE);
        keyspace = HFactory.createKeyspace(properties.getProperty("tutorial.keyspace", "Tutorial"), tutorialCluster, ccl);

        Mutator<String> mutator = HFactory.createMutator(keyspace, stringSerializer);


        mutator.addDeletion(socialSecurityNumber, "User", null, stringSerializer);
        // adding a non-existent key like the following will cause the insertion of a tombstone
        // mutator.addDeletion("652", "AreaCode", null, stringSerializer);
        MutationResult mr = mutator.execute();
        return null;

    }

}
