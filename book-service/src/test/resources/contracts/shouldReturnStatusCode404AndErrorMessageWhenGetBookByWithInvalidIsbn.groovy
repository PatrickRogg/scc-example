package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url '/books/0'
    }
    response {
        status 404
        body("Isbn not found")
        headers {
            contentType('text/plain')
        }
    }
}