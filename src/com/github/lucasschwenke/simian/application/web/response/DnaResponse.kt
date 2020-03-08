package com.github.lucasschwenke.simian.application.web.response

import com.fasterxml.jackson.annotation.JsonProperty

data class DnaResponse(@JsonProperty("simian") val simian: Boolean)
