package br.richbars.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RichbarsApiApplication

fun main(args: Array<String>) {
	runApplication<RichbarsApiApplication>(*args)
}
