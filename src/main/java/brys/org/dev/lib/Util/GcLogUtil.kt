package brys.org.dev.lib.Util

import com.github.ajalt.mordant.TermColors
import javax.management.NotificationListener
import com.sun.management.GarbageCollectionNotificationInfo
import javax.management.openmbean.CompositeData
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import java.lang.Exception
import java.lang.management.ManagementFactory
import java.lang.management.MemoryUsage
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GcLogUtil {
    val t = TermColors()
    val Gc =  t.rgb("#EF2929")
    fun startLoggingGc() {
        println("$Gc [GC] - Logger Activated.")
        for (gcMbean in ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ManagementFactory.getPlatformMBeanServer()
                    .addNotificationListener(gcMbean.objectName, listener, null, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private val listener = NotificationListener { notification, handback ->
        if (notification.type == GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION) {
            val cd = notification.userData as CompositeData
            val gcNotificationInfo = GarbageCollectionNotificationInfo.from(cd)
            val gcInfo = gcNotificationInfo.gcInfo
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = current.format(formatter)
            val gcCompile =  "$Gc────────────────GC─────────────────${t.reset}\n"+
                    "[GC] - " + gcNotificationInfo.gcName +
                    "\n[GC] - Duration: " + gcInfo.duration + "ms" +
                    "\n[GC] - Used: " + sumUsedMb(gcInfo.memoryUsageBeforeGc) + "MB" +
                    " -> " + sumUsedMb(gcInfo.memoryUsageAfterGc) + "MB" +
                             "\n$Gc────────────────GC────────────────${t.reset}"
            println(gcCompile)
        }
    }
    private fun sumUsedMb(memUsages: Map<String, MemoryUsage>): Long {
        var sum: Long = 0
        for (memoryUsage in memUsages.values) {
            sum += memoryUsage.used
        }
        return sum / (1024 * 1024)
    }
}