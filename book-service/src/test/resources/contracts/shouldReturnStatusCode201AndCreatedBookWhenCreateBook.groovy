package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/api/books'
        headers {
            contentType('application/json')
        }
        body([
            "isbn" : "9780132350884",
            "author" : "Robert Cecil Martin",
            "title" : "Clean Code",
            "publisher" : "Prentice Hall"
        ])
    }
    response {
        status CREATED()
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