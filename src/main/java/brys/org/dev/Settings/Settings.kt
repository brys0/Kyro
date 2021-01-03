package brys.org.dev.Settings

import com.jagrosh.jdautilities.command.GuildSettingsProvider

class Settings(manager: SettingsManager, prefix: String?): GuildSettingsProvider {
    fun Settings(manager: SettingsManager, prefix: String) {
        fun getManager(): SettingsManager {
            return manager
        }
        fun getPrefix(): String {
            return prefix
        }
    }
}
