package brys.org.dev.Settings


import brys.org.dev.Mongo.ConnectionPoolListenerMongoDb
import brys.org.dev.Mongo.client
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
    fun addSetting(Name: String, Param: String, guild: String) {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        val rootLogger = loggerContext.getLogger("org.mongodb.driver")
        rootLogger.level = ch.qos.logback.classic.Level.OFF
        mongoCli = MongoClient("127.0.0.1", 27017, )
        val db = mongoCli!!.getDatabase("GuildSettings")
        val tbl = db.getCollection("Guild")
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
        mongoCli = MongoClient("127.0.0.1", 27017)
        val db = mongoCli!!.getDatabase("GuildSettings")
        val tbl = db.getCollection("Guild")
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
        mongoCli = MongoClient("127.0.0.1", 27017)
        val db = mongoCli!!.getDatabase("GuildSettings")
        val tbl = db.getCollection("Guild")
        val qry = BasicDBObject()
        qry["Guild"] = guildId
        tbl.deleteOne(qry)
    }
    fun guild(guild: String) {
        mongoCli = MongoClient("127.0.0.1", 27017)
        val db = mongoCli!!.getDatabase("GuildSettings")
        val tbl = db.getCollection("Guild")
        val doc = BasicDBObject()
        doc["Guild"] = guild
            tbl.insertOne(doc.json)
            return
        }
    }



