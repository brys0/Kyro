## Kyro 
The simple music bot written in Kotlin

> Note: This is entirely open source and is in **no case** under any sort of warranty and special service 

Adding Your OWN Commands

### Whats the structure?

```java
class <Your class here> : Command() 
{
override fun execute(event: CommandEvent)  
{
// Example defines
  val channel = event.channel
  val message = event.message

// Your other code here

}
init {
name = "<Command Name>" // String
aliases = arrayOf("<alias>" ,"<alias>") // Array String
help = "<Help Message>" // String
guildOnly = <Boolean> // true (Command can only be used in guild) / false (Command can be used in private messaging and guild)
arguments = getArguments()
cooldown = <int seconds> // 1 2 3
requiredRole = "<Role Name>" // String
	}
}
```
### Example

```java
class Skip : Command() {  
  
  override fun execute(event: CommandEvent) {  
  val channel = event.textChannel  
  val playerManager = instance  
  val GMM = playerManager!!.getGuildMusicManager(event.guild)  
  val s = GMM.scheduler  
  val player = GMM.audioPlayer  
  
s.isRepeating = true  
if (event.guild.roles.stream().noneMatch{r: Role -> r.name.equals("DJ", ignoreCase = true)}) {  
  event.reply("Seems like a DJ role didn't exist so i've created one for you.")  
  event.guild.createRole().setName("DJ").setColor(Color.CYAN).queue()  
}  
  if (event.member.roles.stream().noneMatch { r: Role -> r.name.equals("DJ", ignoreCase = true) or !event.isOwner or !event.member.hasPermission(Permission.ADMINISTRATOR) }) {  
  event.textChannel.sendMessage("You need the role `DJ` for that!").queue()  
  return  
  
  }  
  if (player.playingTrack == null) {  
  channel.sendMessage("Can't skip when no track is playing!").queue()  
  return  
  }  
  s.nextTrack()  
  channel.sendMessage("Skipped.").queue()  
  
 }  init {  
  name = "skip"  
  aliases = arrayOf("s")  
  help = "Skips the currently playing track"  
  }  
}
```

# Q & A

### Q:  Who's the command handler your using made by?
Great question! The command handler is made by [@jagrosh](https://github.com/jagrosh) and the package is [JDA-Utilities](https://github.com/JDA-Applications/JDA-Utilities)

### Q: What are the rules for submitting pull requests?
The rules for submitting a pull request are the following:

> You must have at least 50 lines changed

> You must have changed something other than grammatical errors (i.e Fixing documentation)

That's it for now :3
