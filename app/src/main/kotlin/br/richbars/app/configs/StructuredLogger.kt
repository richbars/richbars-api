package br.richbars.app.configs

import net.logstash.logback.argument.StructuredArguments.kv
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class StructuredLogger(private val logger: Logger) {

    companion object {
        @JvmStatic
        inline fun <reified T> of(): StructuredLogger =
            StructuredLogger(LoggerFactory.getLogger(T::class.java))

        @JvmStatic
        fun get(name: String): StructuredLogger =
            StructuredLogger(LoggerFactory.getLogger(name))
    }

    private fun enrich(args: Array<out Pair<String, Any?>>): Array<Any> {
        val traceId = MDC.get("traceId") ?: "no-trace"
        val userId = MDC.get("userId") ?: "anon"

        val contextData = listOf(
            kv("traceId", traceId),
            kv("userId", userId)
        )

        val structuredArgs = args.map { kv(it.first, it.second ?: "null") }

        return (contextData + structuredArgs).toTypedArray()
    }

    private fun log(level: LogLevel, msg: String, ex: Throwable? = null, vararg args: Pair<String, Any?>) {
        val enrichedArgs = enrich(args)
        when (level) {
            LogLevel.INFO -> logger.info(msg, *enrichedArgs)
            LogLevel.WARN -> logger.warn(msg, *enrichedArgs)
            LogLevel.DEBUG -> logger.debug(msg, *enrichedArgs)
            LogLevel.TRACE -> logger.trace(msg, *enrichedArgs)
            LogLevel.ERROR -> {
                if (ex != null) logger.error(msg, *enrichedArgs, ex)
                else logger.error(msg, *enrichedArgs)
            }
        }
    }

    fun info(msg: String, vararg args: Pair<String, Any?>) = log(LogLevel.INFO, msg, null, *args)
    fun warn(msg: String, vararg args: Pair<String, Any?>) = log(LogLevel.WARN, msg, null, *args)
    fun debug(msg: String, vararg args: Pair<String, Any?>) = log(LogLevel.DEBUG, msg, null, *args)
    fun trace(msg: String, vararg args: Pair<String, Any?>) = log(LogLevel.TRACE, msg, null, *args)
    fun error(msg: String, ex: Throwable? = null, vararg args: Pair<String, Any?>) = log(LogLevel.ERROR, msg, ex, *args)

    private enum class LogLevel { INFO, WARN, DEBUG, TRACE, ERROR }
}
