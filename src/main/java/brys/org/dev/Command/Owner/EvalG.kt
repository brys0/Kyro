

package brys.org.dev.Command.Owner

import brys.org.dev.Authenicator.AUTH
import brys.org.dev.Command.Music.API.Spotify
import brys.org.dev.Music_Handles.Manager.PlayerManager
import brys.org.dev.src.Command.Music.Lyric.API.KSoft
import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import groovy.lang.GroovyShell
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color


class EvalG : Command() {
    private val engine: GroovyShell
    private val imports: String
    var t = TermColors()

    /**
     * Importing groovy shell
     * @see groovy.lang.GroovyShell
     */


    /**
     * Importing java assets & JDA
     * @see java
     * @see net.dv8tion.jda
     */

    override fun execute(event: CommandEvent?) {
        val manager = PlayerManager.instance
        val musicm = event?.guild?.let { manager?.getGuildMusicManager(it, event.textChannel, event.author) }
        val queue = musicm?.scheduler?.queue
        val player = musicm?.audioPlayer
        val s = musicm?.scheduler
        val args = listOf(event?.args)
        if (args.isEmpty()) {
            event?.reactError()
            event?.reply("Failed to evaluate string: `[PLACE_HOLDER]` because it was empty.")
            return
        }
        try {


            engine.setProperty("args", args)
            engine.setProperty("event", event)
            engine.setProperty("message", event?.message)
            engine.setProperty("channel", event?.channel)
            engine.setProperty("jda", event?.jda)
            engine.setProperty("guild", event?.guild)
            engine.setProperty("member", event?.member)
            engine.setProperty("AUTH", brys.org.dev.Authenicator.AUTH)
            engine.setProperty("kyro", event?.isOwner)
            engine.setProperty("queue", queue)
            engine.setProperty("player", player)
            engine.setProperty("s", s)
            engine.setProperty("SpotifyParse", Spotify)
            engine.setProperty("Ksoft", KSoft)
            val script = imports + event?.args
            val outS = engine.evaluate(script)
            val convertedPackage = if (outS == null) "Void" else outS.javaClass.packageName
            val convertedType = if (outS == null) "Void" else outS.javaClass.typeName
            val out = if(outS == null) "Void" else brys.org.dev.Authenicator.AUTH.get("token")?.let { outS.toString().replace(it, "Tok3n", true) }
            val SuccessEmbed = EmbedBuilder()
                .setTitle("Success Evaluation:")
                .addField("Input:", "```yaml\n" + event?.message?.contentRaw?.split("""(?U)\s+""".toRegex()) + "\n```", true)
                .addField("Type of:", "```asciidoc\n[$convertedType]\n```", true)
                .addField("Package:", "```TOML\n[$convertedPackage]\n```", true)
                .setDescription(out)
                .setColor(Color.decode("#42f593"))

            event?.reply(SuccessEmbed.build())
        } catch (e: Exception) {
            if (e.message == null) {
                return
            }
            event?.reactError()
            val FailureEmbed = EmbedBuilder().setTitle("Evaluation Error").setDescription("```yaml\n ${e.message!!.replace("Index 1 out of bounds for length 1","Script contained no arguments.")} \n```").setColor(Color.RED)
            event?.reply(FailureEmbed.build())
            return

        }
    }

    init {
        guildOnly = false
        ownerCommand = true
        name = "eval"
        imports = """
            import java.io.*
            import java.lang.*
            import java.util.*
            import java.util.concurrent.*
            import net.dv8tion.jda.api.*
            import net.dv8tion.jda.api.entities.*
            import net.dv8tion.jda.api.entities.impl.*
            import net.dv8tion.jda.api.managers.*
            import net.dv8tion.jda.api.managers.impl.*
            import net.dv8tion.jda.api.utils.*
            import com.sedmelluq.discord.lavaplayer.track.AudioTrack
            
            """.trimIndent()
        engine = GroovyShell()
    }
}
