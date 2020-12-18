package brys.org.dev.Command.Music

import brys.org.dev.lib.Util.RequiredUtil.KyroCommand


import com.jagrosh.jdautilities.command.CommandEvent


class Top: KyroCommand() {
    override fun execute(event: CommandEvent?) {
        event?.reply("Executed.")
    }
init {
    name = "test"
    memberConnectedReq = true
    playingReq = true
    }
}