package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'DELETE'
        url '/api/books/123456789'
    }
    response {
        status OK()
    }
}