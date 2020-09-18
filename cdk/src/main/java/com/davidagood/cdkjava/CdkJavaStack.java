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

        Code codeFromLocalPath = Code.fromAsset("/Users/davidgood/dev/cdk-java-gradle-multi-module/lambda/build/libs/lambda-uber.jar");

        Function function = Function.Builder.create(this, "test-func-cdk")
                .code(codeFromLocalPath)
                .handler("com.davidagood.cdkjava.ExampleHandler::handleRequest")
                .runtime(Runtime.JAVA_8)
                .build();
    }
}
