package HasteClient

import com.google.gson.JsonParser
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
class HasteBin {
    fun upload(content: String?, extension: String): String? {
        try {
            val obj = URL("http://haste.brys.tk")
            val con = obj.openConnection() as HttpURLConnection

            // Add a reuqest header
            con.requestMethod = "POST"
            con.setRequestProperty("User-Agent", "Mozilla/5.0")
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5")

            // Send the post request
            con.doOutput = true
            val wr = BufferedWriter(OutputStreamWriter(con.outputStream, StandardCharsets.UTF_8))
            wr.write(content)
            wr.flush()
            wr.close()
            val `in` = BufferedReader(InputStreamReader(con.inputStream))
            var inputLine: String?
            val response = StringBuilder()
            while (`in`.readLine().also { inputLine = it } != null) response.append(inputLine)
            `in`.close()

            // Get the id of the haste
            val json = JsonParser.parseString(response.toString())
            if (!json.isJsonObject) {
                throw IOException("Can't parse JSON")
            }
            val hasteCode = json.asJsonObject["key"].asString
                return hasteCode
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "unknown"
    }

    fun getFileUrl(hasteCode: String, extension: String): String {
        return "https://haste.brys.tk/$hasteCode.$extension"
    }

    interface HasteResult : ShareResult {
        fun onHaste(hasteUrl: String?)
    }

    interface ShareResult {
        fun onSuccess() {}
        fun onFailure(ex: Throwable?)
    }
}