package com.davidagood.cdkjava;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static java.util.Objects.isNull;

public class CdkJavaStack extends Stack {
    public CdkJavaStack(final Construct parent, final String id) {
        this(parent, id, null);
    }

    public CdkJavaStack(final Construct parent, final String id, final StackProps props) {
        super(parent, id, props);

        Table table = Table.Builder.create(this, "ExampleTable")
                .partitionKey(Attribute.builder().name("PK").type(AttributeType.STRING).build())
                .sortKey(Attribute.builder().name("SK").type(AttributeType.STRING).build())
                .build();

        URI uri;

        try {
            URL url = getClass().getResource("/liba");

            if (isNull(url)) {
                throw new RuntimeException("Executable JAR not found in classpath resources");
            }

            uri = url.toURI();

            System.out.println("Full URI for classpath resource \"/lib\": " + uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // We expect the URI to be in this format
        // jar:file:/home/dgood/IdeaProjects/cdk-java-starter/lambda/build_lambda/lambda.jar!/lib
        // Not sure of a better way to get the absolute path of the JAR
        String lambdaExecutableJarPath = uri.toString().split("jar:file:")[1].split("!")[0];
        System.out.println("Lambda executable JAR path: " + lambdaExecutableJarPath);

        Function function = Function.Builder.create(this, "ExampleJavaLambda")
                .code(Code.fromAsset(lambdaExecutableJarPath))
                .handler("com.davidagood.cdkjava.ExampleHandler::handleRequest")
                .runtime(Runtime.JAVA_11)
                .build();
    }

}
