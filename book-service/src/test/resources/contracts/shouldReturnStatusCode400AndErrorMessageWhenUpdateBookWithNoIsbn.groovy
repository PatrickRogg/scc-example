package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'PUT'
        url '/books/123456789'
        headers {
            contentType('application/json')
        }
        body([
            "isbn" : null,
            "author" : ["Robert Cecil Martin"],
            "title" : "Clean Code",
            "publisher" : "Prentice Hall"
        ])
    }
    response {
        status 400
        body("isbn must not be null")
        headers {
            contentType('text/plain')
        }
    }
}