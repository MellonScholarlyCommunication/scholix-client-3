# Scholix Client

Groovy tools to generate LDN Notifications following our [Mellon Spec](https://mellonscholarlycommunication.github.io/spec-notifications/#the-artifact-context) from Scholix data as provided by [ScholeXplorer](https://scholexplorer.openaire.eu/#/).

This is an updated version of https://github.com/MellonScholarlyCommunication/scholix-client.

# Dependency

- Groovy (use [SDKMAN!](https://sdkman.io))
- Anystyle (use `gem install anystyle-cli`)
- Jena riot (JSON-LD processing is required `brew install jena`)
- gtar `brew install gtar`
- Node.js 22.0 or higher
  
# Usage

List all sources/targets:

```
./bin/list_providers.sh
```

Fetch JSON records for one source/target:

```
./bin/scholix_client.groovy links targetPublisher "Ghent University" > data/ghent.jsonl
./bin/scholix_client.groovy links sourcePublisher "Ghent University" >> data/ghent.jsonl
```

Convert JSON to events:

```
./bin/scholix2events.groovy data/ghent.jsonl > data/ghent.events.jsonl
```

Create inbox folder for the events:

```
./bin/mkinbox.sh data/ghent.events.jsonl out/test/scholix
```

Clean any data from previous tests in Solid:

```
./bin/clean_solid.sh
```

Start a Solid server on http://localhost:3000/

```
./bin/start_solid.sh
```

Post the events to the inboxes:

```
./bin/events2inbox.groovy data/ghent.events.jsonl
```

Create a Gephi graph for a repository:

```
bin/events2graph.groovy  out/test/scholix/biblio.ugent.be > data/biblio.gexf
```
