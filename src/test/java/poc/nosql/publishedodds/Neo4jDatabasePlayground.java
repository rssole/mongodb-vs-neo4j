package poc.nosql.publishedodds;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Map;

// Just playground :) don't expect tests to run and succeed just like that :)
// before running "test" which queries for or remove, you need to run one which inserts data :)
public class Neo4jDatabasePlayground {

    private static final int TWO = 2;
    private static final String MESSAGE_PROPERTY = "message";
    private GraphDatabaseService gds;
    private Node firstNode;
    private Node secondNode;
    private Relationship relationship;

    private static final String COLUMN_NAME = "theNode";

    @BeforeClass
    public void initialize() {
        // Starts the database server. Note that if the directory given for the database doesn't already exist, it will be created.
        gds = new GraphDatabaseFactory().newEmbeddedDatabase(Neo4jDatabaseRelatedConstants.NEO4J_DBPATH);
    }

    @AfterClass
    public void cleanUp() {
        gds.shutdown();
    }

    @Test
    public void shouldPutDataToNeo() {
        Transaction tx = gds.beginTx();
        try {
            firstNode = gds.createNode();
            firstNode.setProperty(MESSAGE_PROPERTY, "Hello, ");

            secondNode = gds.createNode();
            secondNode.setProperty(MESSAGE_PROPERTY, "World!");

            relationship = firstNode.createRelationshipTo(secondNode, RelTypes.HAS);
            relationship.setProperty(MESSAGE_PROPERTY, "brave Neo4j");

            tx.success();

            System.out.print(firstNode.getProperty(MESSAGE_PROPERTY));
            System.out.print(relationship.getProperty(MESSAGE_PROPERTY));
            System.out.print(secondNode.getProperty(MESSAGE_PROPERTY));

        } finally {
            tx.finish();
        }
    }

    @Test
    public void shouldRemoveDataFromNeo() {

        Node nodeTwo = null;

        ExecutionEngine ee = new ExecutionEngine(gds);
        String query = String.format("START %s = node(7,8) return %1$s", COLUMN_NAME);
        ExecutionResult result = ee.execute(query);

        Iterator<Map<String, Object>> iterator = result.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> next = iterator.next();
            Node n = (Node) next.get(COLUMN_NAME);

            if (n.getId() == TWO) {
                nodeTwo = n;
            }

            System.out.printf("Message from node %d: %s", n.getId(), n.getProperty(MESSAGE_PROPERTY));
            System.out.println();
        }

        if (nodeTwo != null) {
            Transaction transaction = gds.beginTx();
            ;
            try {
                nodeTwo.delete();
                transaction.success();
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                transaction.finish();
            }
        }
    }

    @Test
    public void shouldConnectNodeZeroToNodesOneAndTwo() {


        ExecutionEngine ee = new ExecutionEngine(gds);
        String query = String.format("START %s = node(0,1) return %1$s", COLUMN_NAME);
        ExecutionResult result = ee.execute(query);

        Transaction tx = gds.beginTx();
        try {
            Node nodeZero = gds.getNodeById(0);
            nodeZero.setProperty(MESSAGE_PROPERTY, "I AM THE ROOT!!!");

            Iterator<Map<String, Object>> iterator = result.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> next = iterator.next();
                Node n = (Node) next.get(COLUMN_NAME);

                nodeZero.createRelationshipTo(n, RelTypes.HAS);

                System.out.printf("Message from node %d: %s", n.getId(), n.getProperty(MESSAGE_PROPERTY));
                System.out.println();
            }
            tx.success();
        } finally {
            tx.finish();
        }
    }
}
