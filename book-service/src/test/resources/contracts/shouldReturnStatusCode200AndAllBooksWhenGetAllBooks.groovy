package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/books'
    }
    response {
        status OK()
        body([
            [
                 "isbn" : "9780132350884",
                 "authors" : "Robert Cecil Martin",
                 "title" : "Clean Code",
                 "publisher" : "Prentice Hall"
            ]
        ])
        headers {
            contentType('application/json')
        }
    }
}