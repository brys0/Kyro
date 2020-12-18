package brys.org.dev.Command.Info

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * ### Your standard Ping Command
 */
class Ping: Command() {
    override fun execute(event: CommandEvent?) {

        /**
         * Takes the [Long] of the ping time and returns a [Color] Based on that
         * * [Color.red] - if the ping is greater than 200
         * * [Color.YELLOW] - if the ping is in between 150 and 200
         * * [Color.green] - if the ping is less than 150
         */
        fun colorStatus(ping: Long): Color {
            if (ping <= 150) {
                return Color.green
            } else if (ping <= 200) {
                return Color.YELLOW
            } else {
                return Color.red
            }
        }

        val gateway = event?.jda?.gatewayPing
        event?.channel?.sendMessage("Hang on pinging...")?.queue { m: Message ->
            val ping = event.message.timeCreated.until(m.timeCreated, ChronoUnit.MILLIS)
            val post = EmbedBuilder()
                    .setTitle("Ping & Status")
                    .setDescription(":stopwatch: **$ping ms**\n"
                                  + ":gear: **$gateway ms**\n")
                    .setFooter("Requested by ${event.author.asTag}")
                    .setTimestamp(Instant.now())
                    .setColor(colorStatus(ping))
                    .build()
            m.editMessage(post).queue()
        }
    }
    init {
        name = "ping"
        aliases = arrayOf("status")
        cooldown = 2
        guildOnly = false
    }
}
