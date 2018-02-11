![alt text](https://travis-ci.org/cmccauley/iothub.svg?branch=master "Travis-CI")

# iothub
Rest-MQTT API Gateway for Internet of Things

# Getting started

```sh
mvn package
docker-compose up
```

If you have access to nix, you can open a nix-shell and run the previous commands in it, e.g.:
```sh
nix-shell --pure
mvn package
docker-compose up
```

Now the service should be reachable from `localhost:8080`.


