package brys.org.dev.Settings

import com.jagrosh.jdautilities.command.GuildSettingsManager
import com.mongodb.BasicDBObject
import net.dv8tion.jda.api.entities.Guild
import java.util.function.Function

class SettingsManager : GuildSettingsManager<Settings> {
    var settings: HashMap<Long, Settings>? = null
    private val manager: SettingsManager? = null
    private var prefix: String? = null
    fun SettingsManager() {
        settings = HashMap()
        val qry = BasicDBObject()
        qry["Guild"]
        GuildSettings.getRawDB().find(qry)
    }
    override fun getSettings(guild: Guild?): Settings {
       return getSettings(guild?.idLong!!)
    }
    fun getSettings(guildId: Long): Settings {
        return settings!!.computeIfAbsent(guildId) { id: Any? -> createDefaultSettings() }
    }
    fun getPrefix(): String? {
        return prefix
    }
    fun setPrefix(prefix: String) {
        this.prefix = prefix
    }

    private fun createDefaultSettings(): Settings {
        return Settings(this, null)
    }
    }

