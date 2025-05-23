package scholix

import groovy.json.*
import groovy.time.*
import java.text.SimpleDateFormat

class ScholixClient {
    def BASE_URL = 'http://api.scholexplorer.openaire.eu/v3'

    def time() {
        return new Date()
    }

    def duration(str,start,end) {
        def TimeDuration duration = TimeCategory.minus(end,start)
        def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        def endTime = dateFormatter.format(end)
        System.err.println("${endTime} ${str} : ${duration}")
    }

    def verbose(str) {
        def end = new Date()
        def dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        def endTime = dateFormatter.format(end)
        System.err.println("${endTime} ${str}")
    }

    def cache_loop(file,closure) {
        def jsonSlurper = new JsonSlurper()

        new File(file).withReader('UTF-8') {
            reader -> {
                def line
                while( (line = reader.readLine()) != null) {
                    if (closure) 
                        closure.call(jsonSlurper.parseText(line))
                }
            }
        }
    }
    
    def links(filterName,filterValue, harvestedAfter = "", closure) {
        def jsonSlurper = new JsonSlurper()

        def filterNameEsc  = java.net.URLEncoder.encode(filterName, "UTF-8")
        def filterValueEsc = java.net.URLEncoder.encode(filterValue, "UTF-8")
        def totalPages = 0
        def currentPage = 0

        do {
            def url = "${BASE_URL}/Links?${filterNameEsc}=${filterValueEsc}&harvestedAfter=${harvestedAfter}&page=${currentPage}"
            
            System.err.println("${url} + ${totalPages}")

            def connection = new URL(url).openConnection()
        
            connection.setRequestProperty('Accept','application/json')

            def response = connection.inputStream.text

            def json = jsonSlurper.parseText(response)

            for (res in json.result) {
                closure.call(res)
            }

            totalPages = json.totalPages
            currentPage += 1
        } while (currentPage <= totalPages)
    }
}