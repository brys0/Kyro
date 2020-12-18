package brys.org.dev.Command.Owner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.natanbc.reliqua.request.RequestException
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.duncte123.botcommons.web.ContentType
import me.duncte123.botcommons.web.WebParserUtils
import me.duncte123.botcommons.web.WebUtils
import net.dv8tion.jda.api.EmbedBuilder
import okhttp3.RequestBody
import okhttp3.Response
import java.time.Instant
import java.util.function.Consumer


class HasteBin: Command() {
    override fun execute(event: CommandEvent?) {
        if (event?.args?.isBlank() == true) {
            event.reply("nu")
            return
        }
        val invoke = getName()
        val contentRaw = event?.message?.contentRaw
        val index = contentRaw?.indexOf(invoke)?.plus(invoke.length)
        val body = index?.let { contentRaw.substring(it).trim { it <= ' ' } }?.replace("```","")
        if (body != null) {
            createPaste("$body\n\n Debug:\n " +
                    "Status ${event.jda.status}\n " +
                    "Guilds ${event.jda.guilds}\n " +
                    "Made by ${event.author.asTag}\n" +
                    "Total Users ${event.jda.users}") { text: String? ->
                val ValidPaste = EmbedBuilder().setTitle("Hastebin", "$text.coffeescript").setDescription("```coffeescript\n${event.args}\n```").addField("Hash",
                    text?.replace("https://haste.brys.tk/",""),false).setFooter(event.author.asTag).setTimestamp(Instant.now()).build()
                event.channel?.sendMessage(ValidPaste)?.queue()
            }
        }
    }
    companion object {
        private fun createPaste(text: String, callback: Consumer<String>) {
            val request = WebUtils.defaultRequest()
                .post(RequestBody.create(null, text.toByteArray()))
                .addHeader("Content-Type", ContentType.TEXT_PLAIN.type)
                .url("https://haste.brys.tk/" + "documents")
                .build()
            WebUtils.ins.prepareRaw(request) { r: Response? -> WebParserUtils.toJSONObject(r, ObjectMapper()) }
                .async(
                    { json: ObjectNode ->
                        val key = json["key"].asText()
                        callback.accept("https://haste.brys.tk/$key")
                    }
                ) { e: RequestException -> callback.accept("Error: " + e.message) }
        }
    }
    init {
        name = "haste"
        ownerCommand = true
    }
}