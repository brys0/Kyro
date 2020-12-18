package brys.org.dev.Command.Owner

import me.duncte123.botcommons.web.WebUtils
import me.duncte123.botcommons.web.WebParserUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.natanbc.reliqua.request.RequestException
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.duncte123.botcommons.web.ContentType
import okhttp3.RequestBody
import okhttp3.Response
import java.util.function.Consumer

class Haste : Command() {
    override fun execute(event: CommandEvent) {
        if (event.args.isEmpty()) {
            event.channel.sendMessage("Missing arguments").queue()
            return
        }
        val invoke = getName()
        val contentRaw = event.message.contentRaw
        val index = contentRaw.indexOf(invoke) + invoke.length
        val body = contentRaw.substring(index).trim { it <= ' ' }
        createPaste(body) { text: String? ->
            event.channel.sendMessage(
                text!!
            ).queue()
        }
    }

    companion object {
        private fun createPaste(text: String, callback: Consumer<String>) {
            val request = WebUtils.defaultRequest()
                .post(RequestBody.create(null, text.toByteArray()))
                .addHeader("Content-Type", ContentType.TEXT_PLAIN.type)
                .url("https://hasteb.in/" + "documents")
                .build()
            WebUtils.ins.prepareRaw(request) { r: Response? -> WebParserUtils.toJSONObject(r, ObjectMapper()) }
                .async(
                    { json: ObjectNode ->
                        val key = json["key"].asText()
                        callback.accept("https://hasteb.in/$key")
                    }
                ) { e: RequestException -> callback.accept("Error: " + e.message) }
        }
    }

    init {
        name = "jaste"
    }
}