package HasteClient

import kotlin.Throws
import com.google.gson.JsonParser
import java.io.*
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

object HasteUploader {
    @Throws(IOException::class)
    fun UploadToHaste(content: String?, url: String?) {
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("User-Agent", "Mozilla/5.0")
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5")
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
        val json = JsonParser.parseString(response.toString())
        if (!json.isJsonObject) {
            throw IOException("Can't parse JSON")
        }
        val hasteCode = json.asJsonObject["key"].asString
    }
}