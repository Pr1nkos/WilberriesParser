package wbparser.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule


object ConfigLoader {
    fun load(): AppConfig {
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        return mapper.readValue(
            this::class.java.getResourceAsStream("/config.yaml"),
            AppConfig::class.java
        )
    }
}