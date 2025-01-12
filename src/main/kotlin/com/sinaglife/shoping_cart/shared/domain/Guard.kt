package com.sinaglife.shoping_cart.shared.domain

data class GuardResult(val succeeded: Boolean, val message: String? = null)

object Guard {

    fun combine(guardResults: List<GuardResult>): GuardResult {
        for (result in guardResults) {
            if (!result.succeeded) return result
        }
        return GuardResult(succeeded = true)
    }

    fun againstNullOrUndefined(
        args: Map<String, Any?>,
        requiredArgs: List<String>
    ): GuardResult {
        if (args.isEmpty()) throw IllegalArgumentException("There are no arguments to check")

        for (key in requiredArgs) {
            if (args[key] == null) {
                return GuardResult(succeeded = false, message = "$key is null or undefined")
            }
        }

        return GuardResult(succeeded = true)
    }

    fun againstNullOrUndefinedBulk(
        args: List<Map<String, Any?>>,
        requiredArgs: List<List<String>>
    ): GuardResult {
        args.forEachIndexed { index, arg ->
            val result = againstNullOrUndefined(arg, requiredArgs[index])
            if (!result.succeeded) return result
        }

        return GuardResult(succeeded = true)
    }

    fun <T> validateAgainstNullOrUndefined(props: Map<String, T>, validKeys: List<String> = props.keys.toList()) {
        val guardResult = againstNullOrUndefined(props, validKeys)
        if (!guardResult.succeeded) throw IllegalArgumentException(guardResult.message)
    }

    fun isOneOf(value: Any?, validValues: List<Any>, argumentName: String): GuardResult {
        return if (validValues.contains(value)) {
            GuardResult(succeeded = true)
        } else {
            GuardResult(
                succeeded = false,
                message = "$argumentName isn't one of the valid values in $validValues. Got \"$value\"."
            )
        }
    }

    fun inRange(num: Int, min: Int, max: Int, argumentName: String): GuardResult {
        return if (num in min..max) {
            GuardResult(succeeded = true)
        } else {
            GuardResult(
                succeeded = false,
                message = "$argumentName is not within range $min to $max."
            )
        }
    }

    fun allInRange(numbers: List<Int>, min: Int, max: Int, argumentName: String): GuardResult {
        for (num in numbers) {
            val result = inRange(num, min, max, argumentName)
            if (!result.succeeded) {
                return GuardResult(
                    succeeded = false,
                    message = "$argumentName is not within the range."
                )
            }
        }

        return GuardResult(succeeded = true)
    }
}
