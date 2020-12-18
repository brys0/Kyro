/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package brys.org.dev.Command.Music.Play


import brys.org.dev.Command.Music.Play.Struct.PlayCommand_Struct
import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent


public fun playHelp(): String? {
    return "Play a song name or url from either YouTube\n" +
            "SoundCloud\n" +
            "Bandcamp\n" +
            "Vimeo\n" +
            "Twitch streams\n" +
            "Local files\n" +
            "HTTP URLs\n" +
            "Example: `kyro.play no friends - cadmium`"
}
class play : Command() {
    override fun execute(event: CommandEvent) {
        /** The checks compiled into a single struct.
         * @see PlayCommand_Struct */
        PlayCommand_Struct().play_struct(event)

    }

    init {
        name = "play"
        help = "Play a song name or url from either YouTube\n" +
                "SoundCloud\n" +
                "Bandcamp\n" +
                "Vimeo\n" +
                "Twitch streams\n" +
                "Local files\n" +
                "HTTP URLs\n" +
                "Example: `kyro.play no friends - cadmium`"
        guildOnly = true
        cooldown = 1
        cooldownScope = CooldownScope.GUILD
        arguments = getArguments()
        aliases = arrayOf("p","play","played")
        guildOnly = true

    }

    }

