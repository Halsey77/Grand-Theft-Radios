package com.halsey.grandtheftradios.radio_objects

import com.halsey.grandtheftradios.R

class RadiosMap private constructor() {
    init {
        addGTA3Radios()
        addGTAVCRadios()
        addGTASARadios()
        addGTA4Radios()
        addGTA5Radios()
    }

    private fun addGTA5Radios() {
        val game = GTA_GAMES[4]
        val radios = arrayOfNulls<Radio>(19)
        radios[0] = Radio(
            game,
            "Radio Los Santos",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRadio%20Los%20Santos.mp3",
            R.drawable.gta5_radiolossantos_icon
        )
        radios[1] = Radio(
            game,
            "Space 103.2",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FSpace%20103.2.mp3",
            R.drawable.gta5_space1032_icon
        )
        radios[2] = Radio(
            game,
            "West Coast Classics",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FWest%20Coast%20Classics.mp3",
            R.drawable.gta5_westcoastclassics_icon
        )
        radios[3] = Radio(
            game,
            "Rebel Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRebel%20Radio.mp3",
            R.drawable.gta5_rebelradio_icon
        )
        radios[4] = Radio(
            game,
            "Los Santos Rock Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FLos%20Santos%20Rock%20Radio.mp3",
            R.drawable.gta5_lossantosrockradio_icon
        )
        radios[5] = Radio(
            game,
            "The Lowdown 91.1",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FThe%20Lowdown%2091.1.mp3",
            R.drawable.gta5_thelowdown911_icon
        )
        radios[6] = Radio(
            game,
            "The Blue Ark",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlue%20Ark.mp3",
            R.drawable.gta5_theblueark_icon
        )
        radios[7] = Radio(
            game,
            "Non-Stop Pop FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FNon-Stop-Pop%20FM.mp3",
            R.drawable.gta5_nonstoppopfm_icon
        )
        radios[8] = Radio(
            game,
            "East Los FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FEast%20Los%20FM.mp3",
            R.drawable.gta5_eastlosfm_icon
        )
        radios[9] = Radio(
            game,
            "WorldWide FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FWorldWide%20FM.mp3",
            R.drawable.gta5_worldwidefm_icon
        )
        radios[10] = Radio(
            game,
            "Channel X",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FChannel%20X.mp3",
            R.drawable.gta5_channelx_icon
        )
        radios[11] = Radio(
            game,
            "Radio Mirror Park",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRadio%20Mirror%20Park.mp3",
            R.drawable.gta5_radiomirrorpark_icon
        )
        radios[12] = Radio(
            game,
            "Vinewood Boulevard Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FVinewood%20Boulevard%20Radio.mp3",
            R.drawable.gta5_vinewoodboulevardradio_icon
        )
        radios[13] = Radio(
            game,
            "Soulwax FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FSoulwax%20FM.mp3",
            R.drawable.gta5_soulwaxfm_icon
        )
        radios[14] = Radio(
            game,
            "FlyLo FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FFlyLo%20FM.mp3",
            R.drawable.gta5_flylofm_icon
        )
        radios[15] = Radio(
            game,
            "Blaine County Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlaine%20County%20Radio.mp3",
            R.drawable.gta5_blainecountytalkradio_icon
        )
        radios[16] = Radio(
            game,
            "The Lab",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FThe%20Lab.mp3",
            R.drawable.gta5_thelab_icon
        )
        radios[17] = Radio(
            game,
            "blonded Los Santos 97.8 FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlonded%20Radio.mp3",
            R.drawable.gta5_blondedlossantos978fm_icon
        )
        radios[18] = Radio(
            game,
            "Los Santos Underground Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FLos%20Santos%20Underground%20Radio.mp3",
            R.drawable.gta5_lsur_icon
        )
        putRadiosIntoMap(game, radios)
    }

    private fun addGTA4Radios() {
        val game = GTA_GAMES[3]
        val radios = arrayOfNulls<Radio>(20)
        radios[0] = Radio(
            game,
            "Vladivostok FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FVladivostok%20FM.mp3",
            R.drawable.gta4_vladivostokfm_icon
        )
        radios[1] = Radio(
            game,
            "Liberty Rock Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FLiberty%20Rock%20Radio.mp3",
            R.drawable.gta4_liberty_rock_radio_icon
        )
        radios[2] = Radio(
            game,
            "Liberty City Hardcore",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FLiberty%20City%20Hardcore.mp3",
            R.drawable.gta4_lchc_icon
        )
        radios[3] = Radio(
            game,
            "The Journey",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Journey.mp3",
            R.drawable.gta4_thejourney_icon
        )
        radios[4] = Radio(
            game,
            "The Vibe 98.8",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Vibe%2098.8.mp3",
            R.drawable.gta4_thevibe988_icon
        )
        radios[5] = Radio(
            game,
            "San Juan Sounds",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FSan%20Juan%20Sounds.mp3",
            R.drawable.gta4_san_juan_sounds_icon
        )
        radios[6] = Radio(
            game,
            "Electro-Choc",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FElectro-Choc.mp3",
            R.drawable.gta4_electrochock_icon
        )
        radios[7] = Radio(
            game,
            "The Beat 102.7",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Beat%20102.7.mp3",
            R.drawable.gta4_thebeat_icon
        )
        radios[8] = Radio(
            game,
            "Jazz Nation Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FJazz%20Nation%20Radio%20108.5.mp3",
            R.drawable.gta4_jazznationradio_icon
        )
        radios[9] = Radio(
            game,
            "The Classics 104.1",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Classics%20104.1.mp3",
            R.drawable.gta4_theclassics_icon
        )
        radios[10] = Radio(
            game,
            "K109 The Studio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FK109%20The%20Studio.mp3",
            R.drawable.gta4_k109_icon
        )
        radios[11] = Radio(
            game,
            "WKTT",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FWKTT%20Radio.mp3",
            R.drawable.gta4_wktttalkradio_icon
        )
        radios[12] = Radio(
            game,
            "Integrity 2.0",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FIntegrity%202.0.mp3",
            R.drawable.gta4_integrity_icon
        )
        radios[13] = Radio(
            game,
            "Massive B Soundsystem",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FMassive%20B%20Soundsystem%2096.9.mp3",
            R.drawable.gta4_massivebsoundsystem_icon
        )
        radios[14] = Radio(
            game,
            "Public Liberty Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FPublic%20Liberty%20Radio.mp3",
            R.drawable.gta4_plr_icon
        )
        radios[15] = Radio(
            game,
            "Radio Broker",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FRadio%20Broker.mp3",
            R.drawable.gta4_radio_broker_icon
        )
        radios[16] = Radio(
            game,
            "RamJam FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FRamJam%20FM.mp3",
            R.drawable.gta4_ramjam_fm_icon
        )
        radios[17] = Radio(
            game,
            "Self-Actualization FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FSelf-Actualization%20FM.mp3",
            R.drawable.gta4_selfactualization_fm_icon
        )
        radios[18] = Radio(
            game,
            "Tuff Gong Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FTuff%20Gong%20Radio.mp3",
            R.drawable.gta4_tuff_gong_icon
        )
        radios[19] = Radio(
            game,
            "Vice City FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FVice%20City%20FM.mp3",
            R.drawable.gta4_vicecityfm_icon
        )
        putRadiosIntoMap(game, radios)
    }

    private fun addGTASARadios() {
        val game = GTA_GAMES[2]
        val radios = arrayOfNulls<Radio>(11)
        radios[0] = Radio(
            game,
            "Playback FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FPlayback%20FM.mp3",
            R.drawable.gtasa_playbackfm_icon
        )
        radios[1] = Radio(
            game,
            "K-Rose",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-Rose.mp3",
            R.drawable.gtasa_krose_icon
        )
        radios[2] = Radio(
            game,
            "K-DST",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-DST.mp3",
            R.drawable.gtasa_kdst_icon
        )
        radios[3] = Radio(
            game,
            "Bounce FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FBounce%20FM.mp3",
            R.drawable.gtasa_bouncefm_icon
        )
        radios[4] = Radio(
            game,
            "SF-UR",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FSF-UR.mp3",
            R.drawable.gtasa_sfur_icon
        )
        radios[5] = Radio(
            game,
            "Radio Los Santos",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FRadio%20Los%20Santos.mp3",
            R.drawable.gtasa_radiolossantos_icon
        )
        radios[6] = Radio(
            game,
            "Radio X",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FRadio%20X.mp3",
            R.drawable.gtasa_radiox_icon
        )
        radios[7] = Radio(
            game,
            "CSR 103.9",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FCSR%20103.9.mp3",
            R.drawable.gtasa_csr1039_icon
        )
        radios[8] = Radio(
            game,
            "K-Jah West",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-JAH%20West.mp3",
            R.drawable.gtasa_kjahwest_icon
        )
        radios[9] = Radio(
            game,
            "Master Sounds 98.3",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FMaster%20Sounds%2098.3.mp3",
            R.drawable.gtasa_mastersounds983_icon
        )
        radios[10] = Radio(
            game,
            "WCTR",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FWest%20Coast%20Talk%20Radio.mp3",
            R.drawable.gtasa_wctr_icon
        )
        putRadiosIntoMap(game, radios)
    }

    private fun addGTAVCRadios() {
        val game = GTA_GAMES[1]
        val radios = arrayOfNulls<Radio>(9)
        radios[0] = Radio(
            game,
            "Wildstyle",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FWILD.mp3",
            R.drawable.gtavc_wildstyle_icon
        )
        radios[1] = Radio(
            game,
            "Flash FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FFLASH.mp3",
            R.drawable.gtavc_flashfm_icon
        )
        radios[2] = Radio(
            game,
            "K-Chat",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FKCHAT.mp3",
            R.drawable.gtavc_kchat_icon
        )
        radios[3] = Radio(
            game,
            "Fever 105",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FFEVER.mp3",
            R.drawable.gtavc_fever_105_icon
        )
        radios[4] = Radio(
            game,
            "V-Rock",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FVROCK.mp3",
            R.drawable.gtavc_vrock_icon
        )
        radios[5] = Radio(
            game,
            "VCPR",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FVCPR.mp3",
            R.drawable.gtavc_vcpr_icon
        )
        radios[6] = Radio(
            game,
            "Espantoso",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FESPANT.mp3",
            R.drawable.gtavc_espantoso_icon
        )
        radios[7] = Radio(
            game,
            "Emotion 98.3",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FEMOTION.mp3",
            R.drawable.gtavc_emotion983_icon
        )
        radios[8] = Radio(
            game,
            "Wave 103",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FWAVE.mp3",
            R.drawable.gtavc_wave103_icon
        )
        putRadiosIntoMap(game, radios)
    }

    private fun addGTA3Radios() {
        val game = GTA_GAMES[0]
        val radios = arrayOfNulls<Radio>(9)
        radios[0] = Radio(
            game,
            "Head Radio",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FHEAD.mp3",
            R.drawable.gta3_headradio_icon
        )
        radios[1] = Radio(
            game,
            "Double Clef FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FCLASS.mp3",
            R.drawable.gta3_doublecleffm_icon
        )
        radios[2] =
            Radio(
                game,
                "K-Jah",
                "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FKJAH.mp3",
                R.drawable.gta3_kjahradio_icon
            )
        radios[3] =
            Radio(
                game,
                "Rise FM",
                "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FRISE.mp3",
                R.drawable.gta3_rise_fm_icon
            )
        radios[4] =
            Radio(
                game,
                "Lips 106",
                "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FLIPS.mp3",
                R.drawable.gta3_lips106_icon
            )
        radios[5] = Radio(
            game,
            "Game Radio FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FGAME.mp3",
            R.drawable.gta3_gameradio_icon
        )
        radios[6] =
            Radio(
                game,
                "MSX FM",
                "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FMSX.mp3",
                R.drawable.gta3_msxfm_icon
            )
        radios[7] = Radio(
            game,
            "Flashback FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FFLASH.mp3",
            R.drawable.gta3_flashbackfm_icon
        )
        radios[8] = Radio(
            game,
            "Chatterbox FM",
            "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FCHAT.mp3",
            R.drawable.gta3_chatterboxfm_icon
        )
        putRadiosIntoMap(game, radios)
    }

    private fun putRadiosIntoMap(game: String, radios: Array<Radio?>) {
        val names = arrayOfNulls<String>(radios.size)
        for (i in radios.indices) {
            radiosMap[game + "-" + radios[i]!!.name] = radios[i]
            names[i] = radios[i]!!.name
            urlToStationNameMap[radios[i]!!.url] = radios[i]!!.name
        }
        gameToStationNamesMap[game] = names
    }

    fun getRadioNamesOfGame(game: String): Array<String?> {
        return gameToStationNamesMap[game]!!
    }

    fun getRadio(game: String, radioName: String): Radio? {
        return radiosMap["$game-$radioName"]
    }

    fun getStationNameFromUrl(url: String?): String? {
        return if (urlToStationNameMap.containsKey(url)) {
            urlToStationNameMap[url]
        } else null
    }

    companion object {
        val GTA_GAMES = arrayOf("GTA 3", "GTA Vice City", "GTA San Andreas", "GTA 4", "GTA 5")
        val GTA_GAMES_DRAWABLES = arrayListOf<Int>(
            R.drawable.gta_3_icon,
            R.drawable.gta_vc_icon,
            R.drawable.gta_sa_icon,
            R.drawable.gta_4_icon,
            R.drawable.gta_5_icon
        )
        var instance: RadiosMap? = null
            get() {
                if (field == null) {
                    field = RadiosMap()
                }
                return field
            }
            private set
        private val radiosMap: MutableMap<String, Radio?> = HashMap()
        private val gameToStationNamesMap: MutableMap<String, Array<String?>> = HashMap()
        private val urlToStationNameMap: MutableMap<String, String> = HashMap()
    }
}
