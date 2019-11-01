package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/api/books/9780132350884'
    }
    response {
        status OK()
        body([
            "isbn" : "9780132350884",
            "author" : "Robert Cecil Martin",
            "title" : "Clean Code",
            "publisher" : "Prentice Hall"
        ])
        headers {
            contentType('application/json')
        }
    }
}