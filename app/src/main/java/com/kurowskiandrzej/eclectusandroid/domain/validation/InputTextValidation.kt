package com.kurowskiandrzej.eclectusandroid.domain.validation

class InputTextValidation private constructor(
    var allowWhiteSpaces: Boolean = true,
    var allowWhiteSpacesAtStart: Boolean = true,
    var whiteSpacesAtStartMessage: Int? = null,
    var allowWhiteSpacesAtEnd: Boolean = true,
    var whiteSpacesAtEndMessage: Int? = null,
    var minimumLength: Int = 0,
    var minimumLengthFailMessageResource: Int? = null,
    var maximumLength: Int? = null,
    var maximumLengthFailMessageResource: Int? = null,
    var regexPattern: Regex? = null,
    var regexPatternFailMessageResource: Int? = null
) {
    class Builder {
        private var allowWhiteSpaces: Boolean = true
        private var allowWhiteSpacesAtStart: Boolean = true
        private var whiteSpacesAtStartMessage: Int? = null
        private var allowWhiteSpacesAtEnd: Boolean = true
        private var whiteSpacesAtEndMessage: Int? = null
        private var minimumLength: Int = 0
        private var minimumLengthFailMessageResource: Int? = null
        private var maximumLength: Int? = null
        private var maximumLengthFailMessageResource: Int? = null
        private var regexPattern: Regex? = null
        private var regexPatternFailMessageResource: Int? = null

        fun setMinimumLength(length: Int) = apply {
            this.minimumLength = length
        }

        fun setMinimumLengthFailMessageResource(resource: Int) = apply {
            this.minimumLengthFailMessageResource = resource
        }

        fun setMaximumLength(length: Int) = apply {
            this.maximumLength = length
        }

        fun setMaximumLengthFailMessageResource(resource: Int) = apply {
            this.maximumLengthFailMessageResource = resource
        }

        fun setRegexPattern(pattern: Regex) = apply {
            this.regexPattern = pattern
        }

        fun setRegexPatternFailMessageResource(resource: Int) = apply {
            this.regexPatternFailMessageResource = resource
        }

        fun build() = InputTextValidation(
            allowWhiteSpaces,
            allowWhiteSpacesAtStart,
            whiteSpacesAtStartMessage,
            allowWhiteSpacesAtEnd,
            whiteSpacesAtEndMessage,
            minimumLength,
            minimumLengthFailMessageResource,
            maximumLength,
            maximumLengthFailMessageResource,
            regexPattern,
            regexPatternFailMessageResource
        )
    }

    /**
     * Checks if provided string variable [text] passes validation requirements
     * @return Pair of boolean which states if checks are successful and optional/nullable resource id
     * to localized message when validation fails
     * */
    fun validate(text: String): Pair<Boolean, Int?> {

        // White spaces

        if (!validateForWhiteSpacesAtStart(text)) return Pair(false, whiteSpacesAtStartMessage)
        if (!validateForWhiteSpacesAtEnd(text)) return Pair(false, whiteSpacesAtStartMessage)

        // Length

        if (text.length < minimumLength) return Pair(false, minimumLengthFailMessageResource)

        maximumLength?.let { maximumLength ->
            if (text.length > maximumLength) return Pair(false, maximumLengthFailMessageResource)
        }

        // Regex

        regexPattern?.let { pattern ->
            if (!pattern.matches(text)) return Pair(false, regexPatternFailMessageResource)
        }

        return Pair(true, null)
    }

    private fun validateForWhiteSpaces(text: String): Boolean {
        return if (!allowWhiteSpaces) {
            val pattern = Regex("""\s""")
            !pattern.matches(text)
        } else true
    }

    private fun validateForWhiteSpacesAtStart(text: String): Boolean {
        return if (!allowWhiteSpacesAtStart) {
            (text.length == text.trimStart().length)
        }
        else true
    }

    private fun validateForWhiteSpacesAtEnd(text: String): Boolean {
        return if (!allowWhiteSpacesAtEnd) {
            (text.length == text.trimEnd().length)
        }
        else true
    }
}