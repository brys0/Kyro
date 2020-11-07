/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Play


import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import src.Command.Play.Struct.PlayCommand_Struct

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

        arguments = getArguments()

    }
}