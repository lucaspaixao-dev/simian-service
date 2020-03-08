package com.github.lucasschwenke.simian.common.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class ApplicationLogger {
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
}
