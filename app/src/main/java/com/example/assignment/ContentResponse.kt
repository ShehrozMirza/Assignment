package com.example.assignment

import com.example.assignment.AppConstants.DEFAULT_INTEGER
import com.example.assignment.AppConstants.DEFAULT_STRING

data class ContentResponse(
    var httpStatusCode: Int = DEFAULT_INTEGER,
    var httpStatusMessage: String = DEFAULT_STRING,
    var wholeText: String = DEFAULT_STRING,
    var request1Content: String = DEFAULT_STRING,
    var request2Content: String = DEFAULT_STRING,
    var request3Content: String = DEFAULT_STRING)