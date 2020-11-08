

package src.Command.Owner

import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import groovy.lang.GroovyShell
import net.dv8tion.jda.api.EmbedBuilder
import src.Authenicator.AUTH
import java.awt.Color
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

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
        val args = listOf(event?.args)
        if (args.isEmpty()) {
            event?.reactError()
            event?.reply("Failed to evaluate string: `[PLACE_HOLDER]` because it was empty.")
            return
        }
        try {

            var fileName = "/Users/BigPEEN/facts.txt"
            var path = Paths.get(fileName)
            var allLines: List<String> = Files.readAllLines(path, StandardCharsets.UTF_8)

            engine.setProperty("args", args)
            engine.setProperty("event", event)
            engine.setProperty("message", event?.message)
            engine.setProperty("channel", event?.channel)
            engine.setProperty("jda", event?.jda)
            engine.setProperty("guild", event?.guild)
            engine.setProperty("member", event?.member)
            engine.setProperty("AUTH", AUTH)
            engine.setProperty("kyro", event?.isOwner)
            engine.setProperty("eval", src.Command.Owner.EvalG())
            engine.setProperty("facts", allLines)
            val script = imports + (event?.message?.contentRaw?.split("\\s+".toRegex(), 2)?.toTypedArray()?.get(1))
            val outS = engine.evaluate(script)

            val SuccessEmbed = EmbedBuilder()
                    .setTitle("Success Evaluation:")
                    .addField("Input:", "```yaml\n" + event?.message?.contentRaw?.split("""(?U)\s+""".toRegex()) + "\n```", true)
                    .addField("Type of:", "```asciidoc\n" + "[" + outS.javaClass.simpleName + "]" + "\n```", true)
                    .addField("Package:", "```TOML\n" + "[" + outS.javaClass.getPackage().name + "]" + "\n```", true)
                    .setDescription(AUTH.get("token")?.let { outS.toString().replace(it, "Tok3n", true) })
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
        name = "evalg"
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
            
            """.trimIndent()
        engine = GroovyShell()
    }
}
