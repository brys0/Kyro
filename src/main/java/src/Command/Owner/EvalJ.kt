/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Owner

import com.github.ajalt.mordant.TermColors
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import src.Authenicator.AUTH
import java.awt.Color
import javax.script.ScriptEngineManager

class EvalJ : Command() {
   val manager = ScriptEngineManager()
  val  engine = manager.getEngineByName("nashorn")
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

        try {


            val script =  (event?.message?.contentRaw?.split("\\s+".toRegex(), 2)?.toTypedArray()?.get(1))
            val outS = engine.eval(script)

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
        name = "evaljs"


    }
}