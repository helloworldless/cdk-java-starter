# Java CDK Starter

## How to Deploy

1. In `lambda` directory
1. Run `../gradlew build`
1. In `cdk` directory
1. Run `../gradlew build`
1. Run `cdk diff` then `cdk deploy`

## Todo

1. Current way of getting the lambda output jar is hacky. Investigate if we can get the path 
 of output artifact from the lambda and pass it as an environment variable which is read in the CDK
 code
