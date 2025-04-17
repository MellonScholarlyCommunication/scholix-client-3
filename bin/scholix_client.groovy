#!/usr/bin/env groovy -cp ./lib

import groovy.cli.commons.CliBuilder
import scholix.ScholixClient
import groovy.json.*

def usage() {
    System.err.println("""
usage: scholix_client.groovy 

    links {targetPublisher|sourcePublisher} {name} [{harvestedAfter}]
""")
    System.exit(1)
}

def cli = new CliBuilder()

def options = cli.parse(args)

if (options.arguments().size() < 1) {
    usage()
}

def function = options.arguments()[0]

if (function == 'links' && options.arguments().size() > 1 ) {
    def filterName = options.arguments()[1]
    def filterValue = options.arguments()[2]
    def harvestedAfter = null

    if (options.arguments().size() > 3) {
        harvestedAfter = options.arguments()[3]
    }

    doLinks(filterName, filterValue, harvestedAfter)
}
else {
    usage()
}

def doLinks(filterName, filterValue, harvestedAfter) {
    def links = new ScholixClient().links(filterName, filterValue, harvestedAfter, {
        x -> println(JsonOutput.toJson(x))
    })
}