# kappa-architecture
An experiment using immutable event logs, stream processors, and materialized views to achieve distributed functional reactive programming

## Development

Use the workstation setup scripts to install the required dependencies (including the kappa command line interface):

```
./setup/kappa_cli
./setup/kafka
./setup/postgres
...
```

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