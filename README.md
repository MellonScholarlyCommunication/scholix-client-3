# Scholix Client

Groovy tools to generate LDN Notifications following our [Mellon Spec](https://mellonscholarlycommunication.github.io/spec-notifications/#the-artifact-context) from Scholix data as provided by [ScholeXplorer](https://scholexplorer.openaire.eu/#/).

This is an updated version of https://github.com/MellonScholarlyCommunication/scholix-client.

## Related

If you use this code in your research, please cite:

```bibtex
@inproceedings{hochstenbach_event_2022,
	location = {Cham},
	title = {Event Notifications in Value-Adding Networks},
	isbn = {978-3-031-16802-4},
	doi = {10.1007/978-3-031-16802-4_11},
	series = {Lecture Notes in Computer Science},
	abstract = {Linkages between research outputs are crucial in the scholarly knowledge graph. They include online citations, but also links between versions that differ according to various dimensions and links to resources that were used to arrive at research results. In current scholarly communication systems this information is only made available post factum and is obtained via elaborate batch processing. In this paper we report on work aimed at making linkages available in real-time, in which an alternative, decentralised scholarly communication network is considered that consists of interacting data nodes that host artifacts and service nodes that add value to artifacts. The first result of this work, the “Event Notifications in Value-Adding Networks” specification, details interoperability requirements for the exchange real-time life-cycle information pertaining to artifacts using Linked Data Notifications. In an experiment, we applied our specification to one particular use-case: distributing Scholix data-literature links to a network of Belgian institutional repositories by a national service node. The results of our experiment confirm the potential of our approach and provide a framework to create a network of interacting nodes implementing the core scholarly functions (registration, certification, awareness and archiving) in a decentralized and decoupled way.},
	pages = {133--146},
	booktitle = {Linking Theory and Practice of Digital Libraries},
	publisher = {Springer International Publishing},
	author = {Hochstenbach, Patrick and Van de Sompel, Herbert and Vander Sande, Miel and Dedecker, Ruben and Verborgh, Ruben},
	editor = {Silvello, Gianmaria and Corcho, Oscar and Manghi, Paolo and Di Nunzio, Giorgio Maria and Golub, Koraljka and Ferro, Nicola and Poggi, Antonella},
	date = {2022},
	langid = {english},
}
```

## Dependencies

- Groovy (use [SDKMAN!](https://sdkman.io))
- Anystyle (use `gem install anystyle-cli`)
- Jena riot (JSON-LD processing is required `brew install jena`)
- gtar `brew install gtar`
- Node.js 22.0 or higher
  
## Usage

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
