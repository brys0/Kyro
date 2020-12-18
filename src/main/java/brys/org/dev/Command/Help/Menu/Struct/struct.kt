package brys.org.dev.Command.Help.Menu.Struct

import net.dv8tion.jda.api.EmbedBuilder

object struct {
    val menu = EmbedBuilder()
            .setTitle("Help")
            .setDescription("Kyro's help page to see the different commands. Use the reactions at the bottom of this screen.")
            .addField("Main Developer", "[Brys](https://github.com/brys0)", true)
            .addField("Contributors", "[Br4d](https://github.com/Iskawo)", true)
            .addField("Reactions","> ```asciidoc\n" + "> [◀] [▶]\n" + "> ```",false)
    val play = EmbedBuilder()
            .setTitle("Play", "https://github.com/brys0/Kyro/tree/v1.0.1/src/main/java/src/Command/Play")
            .setDescription("Need help adding your track? Watch the gif down below!")
            .addField("Permissions", "`VOICE_CONNECT` `EMBED_LINKS` `VOICE_SPEAK` `SEND_MESSAGES`", false)
            .addField("Exceptions", "`IOException` `IllegalArgumentException` `UnsupportedOperationException` `InsufficientPermissionException`", false)
            .setImage("https://i.imgur.com/EZpYro0.gif")
            .addField("Optional Arguments","`--force` - Forces the track to play instantly\n `--repeat` - Repeats the song\n **These Optional Arguments may be stacked!** ",false)
            .setFooter("Want to check out the code? Click the link in the title of this embed!")
    val queue = EmbedBuilder()
        .setTitle("Queue.kt", "https://github.com/brys0/Kyro/blob/v1.0.1/src/main/java/src/Command/Queue/Queue.kt")
        .setDescription("Need help finding out queue? Watch the gif down below!a")
        .addField("Permissions", "`EMBED_LINKS` `SEND_MESSAGES`", false)
        .setImage("https://i.imgur.com/URVfoDo.gif")
        .addField("Exceptions", "`IOException` `IllegalArgumentException` `UnsupportedOperationException` `InsufficientPermissionException` `NullPointerException`", false)
        .setFooter("Want to check out the code? Click the link in the title of this embed!")
    val skip = EmbedBuilder()
            .setTitle("Skip", "https://github.com/brys0/Kyro/blob/v1.0.1/src/main/java/src/Command/Skip/Skip.kt")
            .setDescription("Need help finding out queue? Watch the gif down below!")
            .addField("Permissions", "`SEND_MESSAGES`", false)
            .setImage("https://i.imgur.com/zDP1fvw.gif")
            .addField("Exceptions", "`NullPointerException` `UnsupportedOperationException` `InsufficientPermissionException`", false)
    val volume = EmbedBuilder()
            .setTitle("Volume", "https://github.com/brys0/Kyro/blob/v1.0.1/src/main/java/src/Command/Volume/Volume.kt")
            .setDescription("Need help finding out queue? Watch the gif down below!")
            .addField("Permissions", "`SEND_MESSAGES`", false)
            .setImage("https://i.imgur.com/br1l6ed.gif")
            .addField("Exceptions", "`IOException` `IllegalArgumentException` `UnsupportedOperationException` `InsufficientPermissionException` `NullPointerException`", false)
    val seek = EmbedBuilder()
        .setTitle("Seek")
        .setDescription("Need help seeking through your track? Watch the gif down below!")
        .addField("Permissions","`SEND_MESSAGES`",false)
        .setImage("https://i.imgur.com/dicHSsU.gif")
        .addField("Exceptions", "`IllegalArgumentException` `UnsupportedOperationException` `InsufficientPermissionException` `NullPointerException` `IndexOutOfBoundsException`", false)
    val skipTo = EmbedBuilder()
}