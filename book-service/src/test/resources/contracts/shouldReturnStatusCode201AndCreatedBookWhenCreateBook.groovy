package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/books'
        headers {
            contentType('application/json')
        }
        body([
            "isbn" : "9780132350884",
            "authors" : "Robert Cecil Martin",
            "title" : "Clean Code",
            "publisher" : "Prentice Hall"
        ])
    }
    response {
        status CREATED()
        body([
            "isbn" : "9780132350884",
            "authors" : "Robert Cecil Martin",
            "title" : "Clean Code",
            "publisher" : "Prentice Hall"
        ])
        headers {
            contentType('application/json')
        }
    }
}