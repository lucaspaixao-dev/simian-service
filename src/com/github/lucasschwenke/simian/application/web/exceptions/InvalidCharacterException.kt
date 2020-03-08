package com.github.lucasschwenke.simian.application.web.exceptions

import com.github.lucasschwenke.simian.common.exceptions.ApiError
import com.github.lucasschwenke.simian.common.exceptions.ApiException

class InvalidCharacterException(message: String) : ApiException(message = message) {

    override fun httpStatus() = ApiError.BAD_REQUEST.statusCode

    override fun apiError() = ApiError.BAD_REQUEST

    override fun userResponseMessage() = "$message"
}
