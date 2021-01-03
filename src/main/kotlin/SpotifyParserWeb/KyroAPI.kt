package brys.org.dev.Kyro

import brys.org.dev.Command.Music.API.Spotify
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.StringBuilder
import java.util.*
import javax.servlet.http.HttpServletRequest
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
import org.springframework.boot.web.servlet.error.ErrorController
import com.adamratzman.spotify.models.Track

@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class, ErrorMvcAutoConfiguration::class])
class Application()
fun main(args: Array<String>) {
    runApplication<Application>()
}
@RestController
     class errorController: ErrorController {
    @GetMapping("/error")
    fun handleError(request: HttpServletRequest): String {
        val errorPage = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"ISO-8859-1\">\n" +
                "    <title>Error</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h3>Internal Error occured (Hint: Check your required info!)</h3>\n" +
                "<style>\n" +
                "    body {\n" +
                "        background: #23272A;\n" +
                "    }\n" +
                "</style>\n" +
                "</body>\n" +
                "</html>" // default
        return errorPage
    }
    override fun getErrorPath(): String {
        return "/error"
    }
}


@RestController
class DefaultController {
    @GetMapping(value = ["/"])
    fun default(): String {
        return "<div class=\"mainTxt\">" +
                "Welcome to Spotify Parser</div>" +
                "<style>body {" +
                "background: #23272A; }" +
                ".mainTxt {" +
                "background: #b19cd9" +
                "}" +
                "</style>"
    }
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
 * Get and returns [Track] information as easy to read **JSON** format
 * * How do I use this ***[Endpoint](https://stackoverflow.com/questions/2122604/what-is-an-endpoint)***
 * 1. Head to **your_spotify_parser_server_ip**:**your_port**\track?id=**your_track_id**
 * 2. See what data is returned commonly it will look like Track {}
 * 3. Parse the data out from the **JSON**
 * ***[NodeJS](https://shouts.dev/parse-json-in-node-js-from-external-url)***,
 * ***[Python](https://pythonbasics.org/json/)***,
 * ***[Kotlin](https://stackoverflow.com/questions/44883593/how-to-read-json-from-url-using-kotlin-android)***,
 * ***[Java](https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java)***,
 * ***[C# (CSharp)](https://stackoverflow.com/questions/22550738/how-to-get-json-string-from-the-url/22550937)***,
 * ***[C++ (CPlusplus)](https://gist.github.com/connormanning/41efa6075515019e499c)***,
 * ***[Ruby](https://stackoverflow.com/questions/18581792/ruby-on-rails-and-json-parser-from-url)***,
 * ***[Batch](https://stackoverflow.com/questions/50811698/how-to-get-json-from-curl-as-array-in-a-batch-file)***
 * * If response data is entirely ***[null]*** it failed finding your search id check if the search id matches what was put in the header **id=**
 */
@RestController
class TrackController {
    @GetMapping(value = ["/trackName"])
    fun trackName(@RequestParam(value = "trackid") id: String): String {
        return "{\"name\": \"${Spotify.getTrack(id)}\"}"
    }
    @GetMapping(value = ["/track"])
    fun track(@RequestParam(value = "id") id: String): StringBuilder {
        val songobj =
                StringBuilder()
                        .append("Track {")
                        .append("\"name\": \"${Spotify.getRawTrack(id)?.name}\",")
                        .append("\n\"artwork\": \"${Spotify.getRawTrack(id)?.album?.images?.get(0)?.url}\",")
                        .append("\n\"artist\": \"${Spotify.getRawTrack(id)?.artists?.get(0)?.name}\",")
                        .append("\n\"popularity\": ${Spotify.getRawTrack(id)?.popularity},")
                        .append("\n\"explicit\": ${Spotify.getRawTrack(id)?.explicit},")
                        .append("\n\"duration\": ${Spotify.getRawTrack(id)?.durationMs},")
                        .append("\n\"track_num\": ${Spotify.getRawTrack(id)?.trackNumber}")
                        .append("\n\"converted_trk\": \"${Spotify.getRawTrack(id)?.name} - ${Spotify.getRawTrack(id)?.artists?.get(0)?.name}")
                    .append("}")
        return songobj
    }

}





