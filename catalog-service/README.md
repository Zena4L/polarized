## Deployment

1. For building the docker image
> ./mvnw spring-boot:build-image
 
2. Managing with Kubernates
> kind create cluster

> kind load docker-image catalog-service:0.0.1-SNAPSHOT

> kubectl create deployment catalog-service --image=catalog-service:0.0.1-SNAPSHOT

You can verify the creation of the Deployment object as follows.
> kubectl get deployment

> kubectl get pod

> kubectl expose deployment catalog-service --name=catalog-service --port=8081

> kubectl port-forward service/catalog-service 8000:8080

