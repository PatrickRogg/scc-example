package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'PUT'
        url '/books/0'
        headers {
            contentType('application/json')
        }
        body([
            "isbn" : "9780132350884",
            "author" : "Robert Cecil Martin",
            "title" : "Clean Code",
            "publisher" : "Prentice Hall",
            "pages" : 0
        ])
    }
    response {
        status 404
        body("Isbn not found")
        headers {
            contentType('text/plain')
        }
    }
}