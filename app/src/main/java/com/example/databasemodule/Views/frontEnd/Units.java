package com.example.databasemodule.Views.frontEnd;

import java.util.HashMap;
import java.util.Map;

public class Units {
    static public final Map<String, String> unitsMap = new HashMap<String, String>() {
        {
            put("TEMP", "\u2103");
            put("PRESS", "hPa");
            put("HUM", "%");
            put("BAT_V", "V");
            put("BAT_I", "mA");
            put("SOLAR_V", "V");
            put("SOLAR_I", "mA");
            put("NODE_U", "V");
            put("TEMP_F", "s");
            put("PRESS_F", "s");
            put("HUM_F", "s");
            put("ENERGY_F", "s");
        }
    };

    static public final Map<String, String> nameMap = new HashMap<String, String>(){
        {
            put("TEMP", "Temperatura");
            put("PRESS", "Ciśnienie");
            put("HUM", "Wilgotność");
            put("BAT_V", "Napięcie na baterii");
            put("BAT_I", "Prąd pobierany z baterii");
            put("SOLAR_V", "Napięcie na panelu słonecznego");
            put("SOLAR_I", "Prąd pobierany z panelu słonecznego");
            put("NODE_U", "Napięcie zasilające na węźle");
            put("NODE_I", "Prąd pobierany z węzła");
            put("TEMP_F", "Pomiar temperatury");
            put("PRESS_F", "Pomiar ciśnienia");
            put("HUM_F", "Pomiar wilgotności");
            put("ENERGY_F", "Pomiar danych energetycznych");
        }
    };

    static public final Map<String, String> shortcutMap = new HashMap<String, String>(){
        {
            put("Temperatura", "TEMP");
            put("Ciśnienie", "PRESS");
            put("Wilgotność", "HUM");
            put("Napięcie na baterii", "BAT_V");
            put("Prąd pobierany z baterii", "BAT_I");
            put("Napięcie na panelu słonecznego", "SOLAR_V");
            put("Prąd pobierany z panelu słonecznego", "SOLAR_I");
            put("Napięcie zasilające na węźle", "NODE_U");
            put("Prąd pobierany z węzła", "NODE_I");
            put("Pomiar temperatury", "TEMP_F");
            put("Pomiar ciśnienia", "PRESS_F");
            put("Pomiar wilgotności", "HUM_F");
            put("Pomiar danych energetycznych", "ENERGY_F");
        }
    };
}
