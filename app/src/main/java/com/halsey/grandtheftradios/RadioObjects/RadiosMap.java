package com.halsey.grandtheftradios.RadioObjects;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class RadiosMap {
    public final static String[] GTA_GAMES = {"GTA 3", "GTA Vice City", "GTA San Andreas", "GTA 4", "GTA 5"};

    private static RadiosMap instance = null;
    private static final Map<String, Radio> radiosMap = new HashMap<>();
    private static final Map<String, String[]> radioNamesMap = new HashMap<>();

    private RadiosMap() {
        addGTA3Radios();
        addGTAVCRadios();
        addGTASARadios();
        addGTA4Radios();
        addGTA5Radios();
    }

    private void addGTA5Radios() {
        String game = GTA_GAMES[4];
        Radio[] radios = new Radio[19];
        radios[0] = new Radio(game,"Radio Los Santos", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRadio%20Los%20Santos.mp3");
        radios[1] = new Radio(game,"Space 103.2", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FSpace%20103.2.mp3");
        radios[2] = new Radio(game,"West Coast Classics", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FWest%20Coast%20Classics.mp3");
        radios[3] = new Radio(game,"Rebel Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRebel%20Radio.mp3");
        radios[4] = new Radio(game,"Los Santos Rock Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FLos%20Santos%20Rock%20Radio.mp3");
        radios[5] = new Radio(game,"The Lowdown 91.1", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FThe%20Lowdown%2091.1.mp3");
        radios[6] = new Radio(game,"The Blue Ark", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlue%20Ark.mp3");
        radios[7] = new Radio(game,"Non-Stop Pop FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FNon-Stop-Pop%20FM.mp3");
        radios[8] = new Radio(game,"East Los FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FEast%20Los%20FM.mp3");
        radios[9] = new Radio(game,"WorldWide FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FWorldWide%20FM.mp3");
        radios[10] = new Radio(game,"Channel X", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FChannel%20X.mp3");
        radios[11] = new Radio(game,"Radio Mirror Park", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FRadio%20Mirror%20Park.mp3");
        radios[12] = new Radio(game,"Vinewood Boulevard Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FVinewood%20Boulevard%20Radio.mp3");
        radios[13] = new Radio(game,"Soulwax FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FSoulwax%20FM.mp3");
        radios[14] = new Radio(game,"FlyLo FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FFlyLo%20FM.mp3");
        radios[15] = new Radio(game,"Blaine County Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlaine%20County%20Radio.mp3");
        radios[16] = new Radio(game,"The Lab", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FThe%20Lab.mp3");
        radios[17] = new Radio(game,"blonded Los Santos 97.8 FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FBlonded%20Radio.mp3");
        radios[18] = new Radio(game,"Los Santos Underground Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-V%2FLos%20Santos%20Underground%20Radio.mp3");

        putRadiosIntoMap(game, radios);
    }

    private void addGTA4Radios() {
        String game = GTA_GAMES[3];
        Radio[] radios = new Radio[20];
        radios[0] = new Radio(game,"Vladivostok FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FVladivostok%20FM.mp3");
        radios[1] = new Radio(game,"Liberty Rock Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FLiberty%20Rock%20Radio.mp3");
        radios[2] = new Radio(game,"Liberty City Hardcore", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FLiberty%20City%20Hardcore.mp3");
        radios[3] = new Radio(game,"The Journey", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Journey.mp3");
        radios[4] = new Radio(game,"The Vibe 98.8", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Vibe%2098.8.mp3");
        radios[5] = new Radio(game,"San Juan Sounds", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FSan%20Juan%20Sounds.mp3");
        radios[6] = new Radio(game,"Electro-Choc", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FElectro-Choc.mp3");
        radios[7] = new Radio(game,"The Beat 102.7", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Beat%20102.7.mp3");
        radios[8] = new Radio(game,"Jazz Nation Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FJazz%20Nation%20Radio%20108.5.mp3");
        radios[9] = new Radio(game,"The Classics 104.1", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FThe%20Classics%20104.1.mp3");
        radios[10] = new Radio(game,"K109 The Studio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FK109%20The%20Studio.mp3");
        radios[11] = new Radio(game,"WKTT", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FWKTT%20Radio.mp3");
        radios[12] = new Radio(game,"Integrity 2.0", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FIntegrity%202.0.mp3");
        radios[13] = new Radio(game,"Massive B Soundsystem", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FMassive%20B%20Soundsystem%2096.9.mp3");
        radios[14] = new Radio(game,"Public Liberty Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FPublic%20Liberty%20Radio.mp3");
        radios[15] = new Radio(game,"Radio Broker", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FRadio%20Broker.mp3");
        radios[16] = new Radio(game,"RamJam FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FRamJam%20FM.mp3");
        radios[17] = new Radio(game,"Self-Actualization FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FSelf-Actualization%20FM.mp3");
        radios[18] = new Radio(game,"Tuff Gong Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FTuff%20Gong%20Radio.mp3");
        radios[19] = new Radio(game,"Vice City FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-IV%2FVice%20City%20FM.mp3");

        putRadiosIntoMap(game, radios);
    }

    private void addGTASARadios() {
        String game = GTA_GAMES[2];
        Radio[] radios = new Radio[11];
        radios[0] = new Radio(game,"Playback FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FPlayback%20FM.mp3");
        radios[1] = new Radio(game,"K-Rose", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-Rose.mp3");
        radios[2] = new Radio(game,"K-DST", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-DST.mp3");
        radios[3] = new Radio(game,"Bounce FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FBounce%20FM.mp3");
        radios[4] = new Radio(game,"SF-UR", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FSF-UR.mp3");
        radios[5] = new Radio(game,"Radio Los Santos", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FRadio%20Los%20Santos.mp3");
        radios[6] = new Radio(game,"Radio X", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FRadio%20X.mp3");
        radios[7] = new Radio(game,"CSR 103.9", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FCSR%20103.9.mp3");
        radios[8] = new Radio(game,"K-Jah West", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FK-JAH%20West.mp3");
        radios[9] = new Radio(game,"Master Sounds 98.3", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FMaster%20Sounds%2098.3.mp3");
        radios[10] = new Radio(game,"WCTR", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-San-Andreas%2FWest%20Coast%20Talk%20Radio.mp3");

        putRadiosIntoMap(game, radios);
    }

    private void addGTAVCRadios() {
        String game = GTA_GAMES[1];
        Radio[] radios = new Radio[9];
        radios[0] = new Radio(game,"Wildstyle", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FWILD.mp3");
        radios[1] = new Radio(game,"Flash FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FFLASH.mp3");
        radios[2] = new Radio(game,"K-Chat", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FKCHAT.mp3");
        radios[3] = new Radio(game,"Fever 105", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FFEVER.mp3");
        radios[4] = new Radio(game,"V-Rock", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FVROCK.mp3");
        radios[5] = new Radio(game,"VCPR", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FVCPR.mp3");
        radios[6] = new Radio(game,"Espantoso", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FESPANT.mp3");
        radios[7] = new Radio(game,"Emotion 98.3", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FEMOTION.mp3");
        radios[8] = new Radio(game,"Wave 103", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-Vice-City%2FWAVE.mp3");

        putRadiosIntoMap(game, radios);
    }

    private void addGTA3Radios() {
        String game = GTA_GAMES[0];
        Radio[] radios = new Radio[10];
        radios[0] = new Radio(game,"Head Radio", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FHEAD.mp3");
        radios[1] = new Radio(game,"Double Clef FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FCLASS.mp3");
        radios[2] = new Radio(game,"K-Jah", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FKJAH.mp3");
        radios[3] = new Radio(game,"Rise FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FRISE.mp3");
        radios[4] = new Radio(game,"Lips 106", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FLIPS.mp3");
        radios[5] = new Radio(game,"Game Radio FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FGAME.mp3");
        radios[6] = new Radio(game,"MSX FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FMSX.mp3");
        radios[7] = new Radio(game,"Flashback FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FFLASH.mp3");
        radios[8] = new Radio(game,"Chatterbox FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FCHAT.mp3");
        radios[9] = new Radio(game,"Rise FM", "https://two66.com/mirrors/index.php?file=GTA-Radio-Stations%2FGTA-III%2FRISE.mp3");


        putRadiosIntoMap(game, radios);
    }

    private void putRadiosIntoMap(String game, Radio[] radios) {
        String[] names = new String[radios.length];
        for (int i = 0; i < radios.length; i++) {
            radiosMap.put(game + "-" + radios[i].getName(), radios[i]);
            names[i] = radios[i].getName();
        }
        radioNamesMap.put(game, names);
    }

    public static RadiosMap getInstance() {
        if(instance == null) {
            instance = new RadiosMap();
        }

        return instance;
    }

    public String[] getRadiosOfGame(String game) {
        return radioNamesMap.get(game);
    }

    public Radio getRadio(String game, String radioName) {
        return radiosMap.get(game + "-" + radioName);
    }

    public String getStationNameFromUrl(String url) {
        return null;
    }
}
