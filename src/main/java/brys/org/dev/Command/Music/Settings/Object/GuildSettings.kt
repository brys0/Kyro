package brys.org.dev.Command.Music.Settings.Object

import net.dv8tion.jda.api.entities.Role
import org.bson.Document
import com.mongodb.client.MongoClients
import com.mongodb.client.model.Filters.*
import net.dv8tion.jda.api.entities.Guild
import org.litote.kmongo.*


object GuildSettings {
fun addSettingGuild(Name: String, Param: String, guild: Guild) {
        class SettingsCol(val Name: String, val Param: String, val guild: Guild)
        val client = KMongo.createClient()
        val database = client.getDatabase("KyroGuildSettings")
        val col = database.getCollection<SettingsCol>()
    col.insertOne(SettingsCol(Name, Param, guild))
    }
    fun findSetting(guildId: Long) {
        class SettingsCol(val guildId: Long)
        val client = KMongo.createClient()
        val database = client.getDatabase("KyroGuildSettings")
        val col = database.getCollection<SettingsCol>()
        col.find().limit(1).json
    }
    fun addUserSongSetting(Songurl: Array<String>, userid: Long) {
        class UserData(val SongUrL: Array<String>,  val userid: Long)
        val client = KMongo.createClient()
        val database = client.getDatabase("UserData")
        val col = database.getCollection<UserData>()
        col.insertOne(UserData(Songurl, userid))
    }
    fun updateUserSongSetting(Songurl: Array<String>, userid: Long) {
        class UserData(val SongUrL: Array<String>,  val userid: Long)
        val client = KMongo.createClient()
        val database = client.getDatabase("UserData")
        val col = database.getCollection<UserData>()
        col.updateOne(UserData::userid eq "$userid", setValue(UserData::SongUrL, "$Songurl"))
    }
    fun addGuildDJ(guild: Guild, DjRole: Role) {
        addSettingGuild("DjRole", DjRole.toString(), guild)
    }
    fun addSongtoUserData(userid: Long, Songurl: Array<String>) {
        val mongoClient = MongoClients.create()
        val database = mongoClient.getDatabase("UserData")
        val collection = database.getCollection("userData")
       if  (collection.findOne(eq("userid", userid)) == null) {
           addUserSongSetting(Songurl, userid)
       }


    }

    fun findUserSongSetting(userid: Long): Document? {
        val mongoClient = MongoClients.create()
        val database = mongoClient.getDatabase("UserData")
        val collection = database.getCollection("userData")
        return collection.findOne(eq("userid", userid))

    }
    }
