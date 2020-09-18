# Java CDK Starter

## How to Deploy

1. In `lambda` directory
1. Run `../gradlew build`
1. In `cdk` directory
1. Run `../gradlew build`
1. Run `cdk diff` then `cdk deploy`

## Todo

1. Find a way to share output sources from `lambda` module 
rather than using absolute path in `cdk` module' `CdkJavaStack`