package brys.org.dev.Command.Help

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import net.dv8tion.jda.api.EmbedBuilder


class Help_Play : Command()  {
    override fun execute(event: CommandEvent?) {
        val playGifEmbedBuilder = EmbedBuilder()
                .setTitle("Play Command Help", "https://github.com/brys0/Kyro/tree/v1.0.1/src/main/java/src/Command/Play")
                .setDescription("Need help adding your track? Watch the gif down below!")
                .addField("Permissions", "`VOICE_CONNECT` `EMBED_LINKS` `VOICE_SPEAK` `SEND_MESSAGES`", false)
                .addField("Exceptions", "`IOException` `IllegalArgumentException` `UnsupportedOperationException` `InsufficientPermissionException`", false)
                .setImage("https://i.imgur.com/pL0amcq.gif")
                .setFooter("Want to check out the code? Click the link in the title of this embed!")
        event?.reply(playGifEmbedBuilder.build())
    }
init {
name = "play-h"
    aliases = arrayOf("playh","help-play","play-help")
cooldown = 2
    cooldownScope = CooldownScope.GUILD

}
}


