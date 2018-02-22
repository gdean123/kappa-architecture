# kappa-architecture
An experiment using immutable event logs, stream processors, and materialized views to achieve distributed functional reactive programming

## Installation

Download confluent platform from `https://www.confluent.io/download` and move it into this directory:

```
tar -xvf ~/Downloads/confluent-oss-4.0.0-2.11.tar.gz
mv confluent-4.0.0 ~/workspace/kappa-architecture/runtime
```

Use the workstation setup scripts to install the required dependencies (including the kappa command line interface):

```
./setup/kappa_cli
./setup/kafka
./setup/confluent_platform
...
```

## Development

Use the command line interface to operate the application. For example, to launch the development server:

```
kappa launch
```

## Usage

Write a value to a kafka topic (values-topic):

```
curl -X POST localhost:8080
```

The ValueListener is listening to that topic and will save the new value into the values table.

Read all values in the values table:

```
curl localhost:8080
```


