package com.github.lucasschwenke.simian.domain.exceptions

import com.github.lucasschwenke.simian.common.exceptions.ApiError
import com.github.lucasschwenke.simian.common.exceptions.ApiException

class InvalidDnaLengthException(message: String) : ApiException(message = message) {

    override fun httpStatus() = ApiError.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.BAD_REQUEST

    override fun userResponseMessage() = "$message"

}