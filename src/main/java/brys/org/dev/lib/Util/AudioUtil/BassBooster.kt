package brys.org.dev.lib.Util.AudioUtil

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer
import java.text.DecimalFormat

class BassBooster(private val player: AudioPlayer): EqualizerFactory() {
    var percentage: Float = 0.0f
        private set

    val pcString: String
        get() = dpFormatter.format(percentage)

    val isEnabled: Boolean
        get() = percentage != 0.0f

    fun boost(pc: Float) {
        val lastPc = percentage
        this.percentage = pc
   if (pc == 0.0f) {
            return player.setFilterFactory(null)
        }

        if (lastPc == 0.0f) {
            player.setFilterFactory(this)
        }

        val multiplier = pc / 100

        for ((band, gain) in freqGains) {
            this.setGain(band, gain * multiplier)
        }
    }

    companion object {
        private val dpFormatter = DecimalFormat("0.00")

        private val freqGains = mapOf(
                0 to -0.05f,  // 25 Hz
                1 to 0.07f,   // 40 Hz
                2 to 0.16f,   // 63 Hz
                3 to 0.03f,   // 100 Hz
                4 to -0.05f,  // 160 Hz
                5 to -0.11f   // 250 Hz
        )

//        OFF(0F, 0F, 0F),
//        WEAK(0.03F, 0.01F, 0.0F),
//        MEDIUM(0.1F, 0.08F, 0.04F),
//        STRONG(0.2F, 0.15F, 0.11F),
//        INSANE(0.4F, 0.26F, 0.18F),
//        WTF(1F, 0.8F, 0.6F);
    }

}

