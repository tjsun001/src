package com.byteslounge.spring.tx.dao.impl;

/**
 * Created with IntelliJ IDEA.
 * User: thurmansanders
 * Date: 10/2/13
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */

import me.prettyprint.cassandra.model.ConfigurableConsistencyLevel;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.HConsistencyLevel;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: thurmansanders
 * Date: 10/2/13
 * Time: 3:01 PM
 * To change this template use File | Settings | File Templates.
 */

/*
* To run this example from maven:
 * mvn -e exec:java -Dexec.args="get_slice_user" -Dexec.mainClass="com.datastax.tutorial.TutorialRunner"
 */
//public class GetSliceForUser extends TutorialCommand {
//    public GetSliceForUser(Keyspace keyspace) {
//        super(keyspace);
//    }

public class GetSliceForUser{
    protected static Cluster tutorialCluster;
    protected static Keyspace keyspace;
    protected static Properties properties;
    protected String socialSecurityNumber;

    protected Logger log = LoggerFactory.getLogger(GetSliceForUser.class);
    static StringSerializer stringSerializer = StringSerializer.get();
    static LongSerializer longSerializer = LongSerializer.get();
    public GetSliceForUser(String socialSecurityNumber){
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public QueryResult<ColumnSlice<String,String>> execute() {
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
        
        SliceQuery<String, String, String> sliceQuery =
                HFactory.createSliceQuery(keyspace, stringSerializer, stringSerializer, stringSerializer);
        sliceQuery.setColumnFamily("User");
        sliceQuery.setKey(socialSecurityNumber);
        // We only ever have these four columns on Npanxx
        sliceQuery.setColumnNames("firstName","lastName","socialSecurityNumber","dateOfBirth","countryOfBirth");
        // The following would do the exact same as the above
        // accept here we say get the first 4 columns according to comparator order
        // sliceQuery.setRange("", "", false, 4);

        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute();
        return result;
    }
}

