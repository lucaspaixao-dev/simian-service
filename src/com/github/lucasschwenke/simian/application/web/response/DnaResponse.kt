package com.github.lucasschwenke.simian.application.web.response

import com.fasterxml.jackson.annotation.JsonProperty

data class DnaResponse(@get:JsonProperty("is_simian") val isSimian: Boolean)
