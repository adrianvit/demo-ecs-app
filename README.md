##Demo ECS app
Simple spring boot app with the actuator endpoints enabled.
One test controller to easily force the application to throw 500 errors that will trigger some CloudWatch alarms which in turn will scale up the application.

##Code
Build with
 - mvn clean install
 
Run with 
 - mvn spring-boot:run

##Deploy
Prerequisites: AWS account + AWS CLI tools installed. This stack should fit within the free tier offered by AWS. Region is eu-east-1.
If you have multiple AWS accounts make sure you have profiles for those and use the correct one.

####Create an ECR repo by running the ecr.json cloudformation template. 
- aws cloudformation create-stack --stack-name demo-ecr --template-body file://ecr.json

####Build and push the docker image:
- aws ecr get-login --no-include-email --region us-east-1
- Run the command printed by the above command, this will log you in to the registry and allow image push
- docker build -t demo .
- docker tag demo:latest <accountNumber>.dkr.ecr.us-east-1.amazonaws.com/demo:latest
- docker push <accountNumber>.dkr.ecr.us-east-1.amazonaws.com/demo:latest

The commands above can be found in the AWS console, under the ECS repository resource.

####Create the ECS stack, which will have: 
- one EC2 autoscaling group (cluster in ECS terminology) which is just 1 t2.micro instance as default
- one ECS service which defines the demo app service
- one ECS task definition which wraps starting the docker container on AWS
- one ALB (application load balancer) which routes requests to our service and publicly exposes it
- one cloudwatch alarm that monitors the service for 500 responses and will scale it up by 1 when too many are thrown

You will need to provide values for the vpc and subnet groups.
To create:
-aws cloudformation create-stack --stack-name demo-ecs --template-body file://ecs.yaml --capabilities CAPABILITY_IAM

To update:
-aws cloudformation update-stack --stack-name demo-ecs --template-body file://ecs.yaml --capabilities CAPABILITY_IAM

You can play with the /500 endpoint to see the app scale up. There is no scale down at this point, you can try and implement that yourself :)