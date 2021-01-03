package brys.org.dev.Command.Music

import brys.org.dev.Authenicator.AUTH
import brys.org.dev.Command.Music.API.YouTube
import brys.org.dev.lib.Util.RequiredUtil.KyroCommand


import com.jagrosh.jdautilities.command.CommandEvent
import java.lang.StringBuilder


class Top: KyroCommand() {
    override fun execute(event: CommandEvent?) {
        val kek = AUTH["youtube_key"]?.let { YouTube.rawQuery("high hopes", 4, it) }
        val sb = StringBuilder()
        for (i in 0 until kek?.size!!) {
            sb.append("[${i+1}] ${kek[i].values} ")
        }
        event?.reply(sb.toString())
    }
init {
    name = "test"
    }
}