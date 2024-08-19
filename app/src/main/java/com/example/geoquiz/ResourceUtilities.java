package com.example.geoquiz;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ResourceUtilities {

    public static final HashMap<String, Integer> countryLandmarkImageMap = new HashMap<>();

    static {

        countryLandmarkImageMap.put("R.drawable.eiffel_tower", R.drawable.eiffel_tower);
        countryLandmarkImageMap.put("R.drawable.pyramids", R.drawable.pyramids);
        countryLandmarkImageMap.put("R.drawable.the_great_wall", R.drawable.the_great_wall);
        countryLandmarkImageMap.put("R.drawable.colosseum", R.drawable.colosseum);
        countryLandmarkImageMap.put("R.drawable.statue_of_liberty", R.drawable.statue_of_liberty);
        countryLandmarkImageMap.put("R.drawable.stonehenge", R.drawable.stonehenge);
        countryLandmarkImageMap.put("R.drawable.mt_fujil", R.drawable.mt_fujil);
        countryLandmarkImageMap.put("R.drawable.great_barrier_reef", R.drawable.great_barrier_reef);
        countryLandmarkImageMap.put("R.drawable.cristo_redentor", R.drawable.cristo_redentor);
        countryLandmarkImageMap.put("R.drawable.burj_khalifa", R.drawable.burj_khalifa);
        countryLandmarkImageMap.put("R.drawable.taj_mahal", R.drawable.taj_mahal);
        countryLandmarkImageMap.put("R.drawable.machu_picchu", R.drawable.machu_picchu);
        countryLandmarkImageMap.put("R.drawable.leaning_tower_of_pisa", R.drawable.leaning_tower_of_pisa);
        countryLandmarkImageMap.put("R.drawable.grand_canyon", R.drawable.grand_canyon);
        countryLandmarkImageMap.put("R.drawable.buckingham_palace", R.drawable.buckingham_palace);
        countryLandmarkImageMap.put("R.drawable.petra", R.drawable.petra);
        countryLandmarkImageMap.put("R.drawable.loch_ness", R.drawable.loch_ness);
        countryLandmarkImageMap.put("R.drawable.uluru", R.drawable.uluru);
        countryLandmarkImageMap.put("R.drawable.ha_long_bay", R.drawable.ha_long_bay);
        countryLandmarkImageMap.put("R.drawable.mont_saint_michel", R.drawable.mont_saint_michel);
        countryLandmarkImageMap.put("R.drawable.mount_kilamanjaro", R.drawable.mount_kilamanjaro);
        countryLandmarkImageMap.put("R.drawable.galapagos_islands", R.drawable.galapagos_islands);
        countryLandmarkImageMap.put("R.drawable.milford_sound", R.drawable.milford_sound);
        countryLandmarkImageMap.put("R.drawable.hagia_sophia", R.drawable.hagia_sophia);
        countryLandmarkImageMap.put("R.drawable.angel_falls", R.drawable.angel_falls);
        countryLandmarkImageMap.put("R.drawable.st_basils_cathedral", R.drawable.st_basils_cathedral);
        countryLandmarkImageMap.put("R.drawable.angkor_wat", R.drawable.angkor_wat);
        countryLandmarkImageMap.put("R.drawable.chich_n_itza", R.drawable.chich_n_itza);
        countryLandmarkImageMap.put("R.drawable.sagrada_familia", R.drawable.sagrada_familia);
        countryLandmarkImageMap.put("R.drawable.forbidden_city", R.drawable.forbidden_city);
        countryLandmarkImageMap.put("R.drawable.krakatoa", R.drawable.krakatoa);
        countryLandmarkImageMap.put("R.drawable.cn_radio_tower", R.drawable.cn_radio_tower);
        countryLandmarkImageMap.put("R.drawable.brandenburg_gate", R.drawable.brandenburg_gate);
        countryLandmarkImageMap.put("R.drawable.red_fort", R.drawable.red_fort);
        countryLandmarkImageMap.put("R.drawable.petronas_towers", R.drawable.petronas_towers);
        countryLandmarkImageMap.put("R.drawable.yosemite_national_park", R.drawable.yosemite_national_park);
        countryLandmarkImageMap.put("R.drawable.table_mountain", R.drawable.table_mountain);
        countryLandmarkImageMap.put("R.drawable.neuschwanstein_castle", R.drawable.neuschwanstein_castle);
        countryLandmarkImageMap.put("R.drawable.casino_de_monte_carlo", R.drawable.casino_de_monte_carlo);
        countryLandmarkImageMap.put("R.drawable.parthenon", R.drawable.parthenon);
        countryLandmarkImageMap.put("R.drawable.okavango_delta", R.drawable.okavango_delta);
        countryLandmarkImageMap.put("R.drawable.uyuni_salt_flat", R.drawable.uyuni_salt_flat);
        countryLandmarkImageMap.put("R.drawable.cliffs_of_moher", R.drawable.cliffs_of_moher);
        countryLandmarkImageMap.put("R.drawable.moraine_lake", R.drawable.moraine_lake);
        countryLandmarkImageMap.put("R.drawable.tikal", R.drawable.tikal);
        countryLandmarkImageMap.put("R.drawable.lake_baikal", R.drawable.lake_baikal);
        countryLandmarkImageMap.put("R.drawable.borobudur", R.drawable.borobudur);
        countryLandmarkImageMap.put("R.drawable.sigiriya", R.drawable.sigiriya);
        countryLandmarkImageMap.put("R.drawable.mount_kosciuszko", R.drawable.mount_kosciuszko);
        countryLandmarkImageMap.put("R.drawable.torre_egger", R.drawable.torre_egger);
        countryLandmarkImageMap.put("R.drawable.jungfraujoch", R.drawable.jungfraujoch);
        countryLandmarkImageMap.put("R.drawable.the_rijksmuseum", R.drawable.the_rijksmuseum);
        countryLandmarkImageMap.put("R.drawable.wieliczka_salt_mine", R.drawable.wieliczka_salt_mine);
        countryLandmarkImageMap.put("R.drawable.danakil_depression", R.drawable.danakil_depression);
        countryLandmarkImageMap.put("R.drawable.zuma_rock", R.drawable.zuma_rock);
        countryLandmarkImageMap.put("R.drawable.mt_nyiragongo", R.drawable.mt_nyiragongo);
        countryLandmarkImageMap.put("R.drawable.fox_glacier", R.drawable.fox_glacier);
        countryLandmarkImageMap.put("R.drawable.st_stephens_cathedral", R.drawable.st_stephens_cathedral);
        countryLandmarkImageMap.put("R.drawable.belem_tower", R.drawable.belem_tower);
        countryLandmarkImageMap.put("R.drawable.chocolate_hills", R.drawable.chocolate_hills);
    }  // HashMap linking landmark database image paths to resource directory paths.

    public static int getResourceId (String key){

        Integer resourceId = countryLandmarkImageMap.get(key);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + key);
            return -1; // Or handle the missing resource appropriately
        }
    }
}

