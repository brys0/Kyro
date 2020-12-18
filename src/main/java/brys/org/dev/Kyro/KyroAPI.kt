package brys.org.dev.Kyro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.text.DecimalFormat
import java.util.*


@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class Application()
fun main(args: Array<String>) {
    runApplication<Application>()
}

@RestController
class HelloController {
    @GetMapping(value = ["/hello"])
    fun hello(@RequestParam("name", defaultValue = "unknown") name: String): String {
        val arr = arrayOfNulls<Int>(1000)
        for (i in arr.indices) {
            arr[i] = i
        }
        Collections.shuffle(Arrays.asList(arr))
return "{\"greeting\": \"Hello $name\" }"
    }
}

/**
 * Gets and returns the cpu usage [KyroUtils.procUsage]
 */
@RestController
class CPUController {

    @GetMapping(value = ["/cpusage"])
    fun usage(): String {

        return "{\"usage\": \"${DecimalFormat("#.##").format(KyroUtils.procUsage())}\"}"
    }
}


