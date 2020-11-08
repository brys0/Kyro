/**
 * Copyright (c) 2020
 *Creative Commons.
 *You must give attribution to the creator (Bryson T.) of this work.
 *Noncommercial
 *This can not be used for commercial use or used to make a profit.
 */

package src.Command.Help

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import src.Command.Play.play
import src.Command.Play.playHelp

class KyroHelp : Command() {
    override fun execute(event: CommandEvent?) {


        event?.reply("${playHelp()}")

    }
    init{
        name = "helpa"
    }
}