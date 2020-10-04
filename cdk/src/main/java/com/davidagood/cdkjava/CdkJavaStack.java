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

import java.nio.file.Path;

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

        String lambdaExecutableJarPath = System.getProperty("lambdaJarAbsolutePath");

        if (!Path.of(lambdaExecutableJarPath).toFile().exists()) {
            throw new LambdaDeploymentPackageNotFoundException("Lambda deployment package not at path="
                    + lambdaExecutableJarPath);
        } else {
            System.out.println("Found lambda executable JAR at paht=" + lambdaExecutableJarPath);
        }

        Function function = Function.Builder.create(this, "ExampleJavaLambda")
                .code(Code.fromAsset(lambdaExecutableJarPath))
                .handler("com.davidagood.cdkjava.ExampleHandler::handleRequest")
                .runtime(Runtime.JAVA_11)
                .build();
    }

}
