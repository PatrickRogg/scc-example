package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        urlPath('/books/search')  {
            queryParameters {
                parameter 'bookTitle': "Clean Code"
            }
        }
    }
    response {
        status OK()
        body([
            [
                "isbn" : "9780132350884",
                "author" : "Robert Cecil Martin",
                "title" : "Clean Code",
                "publisher" : "Prentice Hall",
                "pages" : 0
            ]
        ])
        headers {
            contentType('application/json')
        }
    }
}