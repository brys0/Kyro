package brys.org.dev.Settings



import ch.qos.logback.classic.LoggerContext

import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document
import com.mongodb.client.model.Filters.*
import org.litote.kmongo.*
import com.mongodb.client.model.Updates.*
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import org.slf4j.LoggerFactory


object GuildSettings {
    private var mongoCli: MongoClient? = null
    val tbl =  MongoData.getGuildDB()
    fun addSetting(Name: String, Param: String, guild: String) {
        val doc = BasicDBObject()
        doc["Guild"] = guild
        doc[Name] = Param
        if (findSetting(guild)?.get("Guild") == null) {
            tbl.insertOne(doc.json)
            return
        } else {
            val filter = eq("Guild", findSetting(guild)?.get("Guild"))
            val update = set(Name, Param)
            tbl.updateOne(filter, update)
        }
    }
    fun findSetting(guildId: String): Document? {
        val qry = BasicDBObject()
        qry["Guild"] = guildId
        return tbl.findOne(qry)
    }
    fun getRawDB(): MongoCollection<Document> {
        mongoCli = MongoClient("127.0.0.1", 27017)
        val db = mongoCli!!.getDatabase("GuildSettings")
        return db.getCollection("Guild")
    }
    fun wipe(guildId: String) {
        val qry = BasicDBObject()
        qry["Guild"] = guildId
        tbl.deleteOne(qry)
    }
    fun guild(guild: String) {
        val doc = BasicDBObject()
        doc["Guild"] = guild
            tbl.insertOne(doc.json)
            return
        }
    fun unset(guildId: String, unset: String, existing: String) {
        findSetting(guildId)?.remove(unset, existing)
    }
    }



