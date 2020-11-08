package src

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import java.util.concurrent.TimeUnit

class HelloCommand(private val waiter: EventWaiter) : Command() {
    override fun execute(event: CommandEvent) {
        // ask what the user's name is
        event.reply("Hello. What is your name?")

        // wait for a response
        waiter.waitForEvent(MessageReceivedEvent::class.java,  // make sure it's by the same user, and in the same channel, and for safety, a different message
                { e: MessageReceivedEvent -> e.author == event.author && e.channel == event.channel && e.message != event.message },  // respond, inserting the name they listed into the response
                { e: MessageReceivedEvent -> event.reply("Hello, `" + e.message.contentRaw + "`! I'm `" + e.jda.selfUser.name + "`!") },  // if the user takes more than a minute, time out
                1, TimeUnit.MINUTES) { event.reply("Sorry, you took too long.") }
    }

    init {
        name = "hello"
        aliases = arrayOf("hi")
        help = "says hello and waits for a response"
    }
}