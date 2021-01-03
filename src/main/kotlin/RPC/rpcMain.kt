package RPC

import be.bluexin.drpc4k.jna.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.litote.kmongo.json
import java.time.Instant
import kotlin.random.Random

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
fun main(args: Array<String>) = runBlocking{
    val rpcOutput = Channel<RPCOutputMessage>(capacity = Channel.UNLIMITED)
    val rpcInput = rpcActor(rpcOutput)
        launch {
            for (msg in rpcOutput) with(msg) {
                when (this) {
                    is RPCOutputMessage.Ready -> with(user) { println("Logged in as $username#$discriminator") }
                    is RPCOutputMessage.Disconnected -> println("Disconnected: #$errorCode (${message.takeIf { message.isNotEmpty() }
                        ?: "No message provided"})")
                    is RPCOutputMessage.Errored -> println("Error: #$errorCode (${message.takeIf { message.isNotEmpty() }
                        ?: "No message provided"})")
                }
            }
        }
        rpcInput.send(RPCInputMessage.Connect("714647862335635546"))
        rpcInput.send(RPCInputMessage.UpdatePresence(DiscordRichPresence {
            partyId = "MTI4NzM0OjFpMmhuZToxMjMxMjM=w"
            state = "2 days till the very special day of the year"
            details = "<(^.^)>"
            largeImageKey = "christmas"
            setDuration(28800000L)
            instance = 1
            joinSecret = "uwuwuwuwuwuu"
           spectateSecret = "kek"
        }))
  rpcInput.send(RPCInputMessage.Connect("714647862335635546", true))

    }
