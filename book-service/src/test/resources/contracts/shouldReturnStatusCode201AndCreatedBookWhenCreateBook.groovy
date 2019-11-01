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
                "author" : "Robert Cecil Martin",
                "title" : "Clean Code",
                "publisher" : "Prentice Hall",
                "pages" : 0
        ])
    }
    response {
        status CREATED()
        body([
                "isbn" : "9780132350884",
                "author" : "Robert Cecil Martin",
                "title" : "Clean Code",
                "publisher" : "Prentice Hall",
                "pages" : 0
        ])
        headers {
            contentType('application/json')
        }
    }
}