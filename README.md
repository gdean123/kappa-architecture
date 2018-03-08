# kappa-architecture
An experiment using immutable event logs, stream processors, and materialized views to achieve distributed functional reactive programming

## Installation

Download confluent platform from `https://www.confluent.io/download` and move it into this directory:

```
tar -xvf ~/Downloads/confluent-oss-4.0.0-2.11.tar.gz
mv confluent-4.0.0 ~/workspace/kappa-architecture/runtime
```

Use the workstation setup script to install the required dependencies (including the kappa command line interface):

```
./setup/install
```

Create a postgres database into which we can materialize a view:

```
createdb consumer_development
```

Install the private key used to decrypt secrets:

```
sudo mkdir -p /opt/ejson/keys
sudo vim /opt/ejson/keys/3563cb1ccbbc1c5adc6f81684e7b85d9d40b0e8cfece2320e04e31af641b624c
```

## Development

Use the command line interface to operate the application. For example, to run all tests:

```
kappa test
```

## Startup

First:

`kappa runtime start` to start zookeeper, kafka, schema-registry, and connect

`kappa topics create` to create the required topics in the local Kafka

`kappa bindings generate` to generate serializers and deserializers from schema files

Then, in separate terminals:

`kappa start producer` to launch the producer application

`kappa start stream-processor` to launch the stream-processor application

`kappa connectors load` to load the jdbc-sink Kafka Connect connector

## Usage

Notice that the word_counts table doesn't even exist:

```
$ psql consumer_development

consumer_development=# SELECT * FROM word_counts;
ERROR:  relation "word_counts" does not exist
```

Use the producer to write a few sentences to the sentence_created kafka topic:

```
curl -X POST localhost:8080/sentences -H "Content-Type: application/json" --data '{"words": "alpine rainbows"}'
curl -X POST localhost:8080/sentences -H "Content-Type: application/json" --data '{"words": "rainbows and sheep"}'
```

The stream-processor is listening to that topic and will write an updated set of word counts to the word_counts topic.

The jdbc-sink connector is listening to word_counts and will materialize that into a table in the consumer_development database.

```
$ psql consumer_development

consumer_development=# SELECT * FROM word_counts;
 count |  word
-------+---------
     1 | alpine
     2 | rainbows
     1 | and
     1 | sheep
(4 rows)
```


