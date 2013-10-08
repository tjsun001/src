package com.byteslounge.spring.tx.dao.impl;

/**
 * Created with IntelliJ IDEA.
 * User: thurmansanders
 * Date: 10/3/13
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */

import com.byteslounge.spring.tx.model.User;
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
 * Time: 7:45 AM
 * To change this template use File | Settings | File Templates.
 */


/**
 * Uses batch_mutate to insert several rows into mutliple column families in
 * one execution.
 *
 * To run this example from maven:
 * mvn -e exec:java -Dexec.args="insertUser" -Dexec.mainClass="com.datastax.tutorial.TutorialRunner"
 *
 * @author zznate
 */
public class insertUser  {
    protected User user;
    protected static Keyspace keyspace;
    protected static Properties properties;
    protected static Cluster tutorialCluster;
    static StringSerializer stringSerializer = StringSerializer.get();
    static LongSerializer longSerializer = LongSerializer.get();

    public insertUser(User user){
        this.user = user;

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

//        mutator.addInsertion("555-55-5555", "User", HFactory.createStringColumn("firstName", "Bugs"));
//        mutator.addInsertion("555-55-5555", "User", HFactory.createStringColumn("lastNamme", "Bunny"));
//        mutator.addInsertion("555-55-5555", "User", HFactory.createStringColumn("dateOfBirth", "05-25-2000"));
//        mutator.addInsertion("555-55-5555", "User", HFactory.createStringColumn("socialSecurityNumber", "555-55-5555"));
//        mutator.addInsertion("555-55-5555", "User", HFactory.createStringColumn("countryOfBirth", "Spain"));

        mutator.addInsertion(user.getSocialSecurityNumber(), "User", HFactory.createStringColumn("socialSecurityNumber", user.getSocialSecurityNumber()));
        mutator.addInsertion(user.getSocialSecurityNumber(), "User", HFactory.createStringColumn("firstName", user.getFirstName()));
        mutator.addInsertion(user.getSocialSecurityNumber(), "User", HFactory.createStringColumn("lastName", user.getLastName()));
        mutator.addInsertion(user.getSocialSecurityNumber(), "User", HFactory.createStringColumn("dateOfBirth", user.getDateOfBirth()));
        mutator.addInsertion(user.getSocialSecurityNumber(), "User", HFactory.createStringColumn("countryOfBirth", user.getCountryOfBirth()));


        MutationResult mr = mutator.execute();
        return null;
    }

}


