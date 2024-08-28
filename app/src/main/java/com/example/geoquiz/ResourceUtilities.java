package com.example.geoquiz;

import android.util.Log;

import java.util.HashMap;

public class ResourceUtilities {

    public static final HashMap<String, Integer> countryLandmarkImageMap = new HashMap<>();
    public static final HashMap<String, Integer> countryFlagImageMap = new HashMap<>();

    //TODO Get images for sports team, foods + corporations (haven't decided on 6th type yet).

        static {

            countryFlagImageMap.put("R.drawable.afghanistan_flag", R.drawable.afghanistan_flag);
            countryFlagImageMap.put("R.drawable.albania_flag", R.drawable.albania_flag);
            countryFlagImageMap.put("R.drawable.algeria_flag", R.drawable.algeria_flag);
            countryFlagImageMap.put("R.drawable.angola_flag", R.drawable.angola_flag);
            countryFlagImageMap.put("R.drawable.argentina_flag", R.drawable.argentina_flag);
            countryFlagImageMap.put("R.drawable.australia_flag", R.drawable.australia_flag);
            countryFlagImageMap.put("R.drawable.austria_flag", R.drawable.austria_flag);
            countryFlagImageMap.put("R.drawable.azerbaijan_flag", R.drawable.azerbaijan_flag);
            countryFlagImageMap.put("R.drawable.bahrain_flag", R.drawable.bahrain_flag);
            countryFlagImageMap.put("R.drawable.bangladesh_flag", R.drawable.bangladesh_flag);
            countryFlagImageMap.put("R.drawable.barbados_flag", R.drawable.barbados_flag);
            countryFlagImageMap.put("R.drawable.belarus_flag", R.drawable.belarus_flag);
            countryFlagImageMap.put("R.drawable.belgium_flag", R.drawable.belgium_flag);
            countryFlagImageMap.put("R.drawable.belize_flag", R.drawable.belize_flag);
            countryFlagImageMap.put("R.drawable.bhutan_flag", R.drawable.bhutan_flag);
            countryFlagImageMap.put("R.drawable.bolivia_flag", R.drawable.bolivia_flag);
            countryFlagImageMap.put("R.drawable.botswana_flag", R.drawable.botswana_flag);
            countryFlagImageMap.put("R.drawable.brazil_flag", R.drawable.brazil_flag);
            countryFlagImageMap.put("R.drawable.bulgaria_flag", R.drawable.bulgaria_flag);
            countryFlagImageMap.put("R.drawable.burkina_faso_flag", R.drawable.burkina_faso_flag);
            countryFlagImageMap.put("R.drawable.burundi_flag", R.drawable.burundi_flag);
            countryFlagImageMap.put("R.drawable.cape_verde_flag", R.drawable.cape_verde_flag);
            countryFlagImageMap.put("R.drawable.cambodia_flag", R.drawable.cambodia_flag);
            countryFlagImageMap.put("R.drawable.cameroon_flag", R.drawable.cameroon_flag);
            countryFlagImageMap.put("R.drawable.canada_flag", R.drawable.canada_flag);
            countryFlagImageMap.put("R.drawable.chile_flag", R.drawable.chile_flag);
            countryFlagImageMap.put("R.drawable.china_flag", R.drawable.china_flag);
            countryFlagImageMap.put("R.drawable.colombia_flag", R.drawable.colombia_flag);
            countryFlagImageMap.put("R.drawable.comoros_flag", R.drawable.comoros_flag);
            countryFlagImageMap.put("R.drawable.dr_congo_flag", R.drawable.dr_congo_flag);
            countryFlagImageMap.put("R.drawable.congo_flag", R.drawable.congo_flag);
            countryFlagImageMap.put("R.drawable.costa_rica_flag", R.drawable.costa_rica_flag);
            countryFlagImageMap.put("R.drawable.ivory_coast_flag", R.drawable.ivory_coast_flag);
            countryFlagImageMap.put("R.drawable.croatia_flag", R.drawable.croatia_flag);
            countryFlagImageMap.put("R.drawable.cuba_flag", R.drawable.cuba_flag);
            countryFlagImageMap.put("R.drawable.cyprus_flag", R.drawable.cyprus_flag);
            countryFlagImageMap.put("R.drawable.czechia_flag", R.drawable.czechia_flag);
            countryFlagImageMap.put("R.drawable.denmark_flag", R.drawable.denmark_flag);
            countryFlagImageMap.put("R.drawable.djibouti_flag", R.drawable.djibouti_flag);
            countryFlagImageMap.put("R.drawable.dominica_flag", R.drawable.dominica_flag);
            countryFlagImageMap.put("R.drawable.ecuador_flag", R.drawable.ecuador_flag);
            countryFlagImageMap.put("R.drawable.egypt_flag", R.drawable.egypt_flag);
            countryFlagImageMap.put("R.drawable.england_flag", R.drawable.england_flag);
            countryFlagImageMap.put("R.drawable.eritrea_flag", R.drawable.eritrea_flag);
            countryFlagImageMap.put("R.drawable.estonia_flag", R.drawable.estonia_flag);
            countryFlagImageMap.put("R.drawable.ethiopia_flag", R.drawable.ethiopia_flag);
            countryFlagImageMap.put("R.drawable.fiji_flag", R.drawable.fiji_flag);
            countryFlagImageMap.put("R.drawable.finland_flag", R.drawable.finland_flag);
            countryFlagImageMap.put("R.drawable.france_flag", R.drawable.france_flag);
            countryFlagImageMap.put("R.drawable.gambia_flag", R.drawable.gambia_flag);
            countryFlagImageMap.put("R.drawable.georgia_flag", R.drawable.georgia_flag);
            countryFlagImageMap.put("R.drawable.germany_flag", R.drawable.germany_flag);
            countryFlagImageMap.put("R.drawable.ghana_flag", R.drawable.ghana_flag);
            countryFlagImageMap.put("R.drawable.greece_flag", R.drawable.greece_flag);
            countryFlagImageMap.put("R.drawable.guatemala_flag", R.drawable.guatemala_flag);
            countryFlagImageMap.put("R.drawable.guinea_flag", R.drawable.guinea_flag);
            countryFlagImageMap.put("R.drawable.guinea_bissau_flag", R.drawable.guinea_bissau_flag);
            countryFlagImageMap.put("R.drawable.guyana_flag", R.drawable.guyana_flag);
            countryFlagImageMap.put("R.drawable.haiti_flag", R.drawable.haiti_flag);
            countryFlagImageMap.put("R.drawable.honduras_flag", R.drawable.honduras_flag);
            countryFlagImageMap.put("R.drawable.hungary_flag", R.drawable.hungary_flag);
            countryFlagImageMap.put("R.drawable.iceland_flag", R.drawable.iceland_flag);
            countryFlagImageMap.put("R.drawable.india_flag", R.drawable.india_flag);
            countryFlagImageMap.put("R.drawable.indonesia_flag", R.drawable.indonesia_flag);
            countryFlagImageMap.put("R.drawable.iran_flag", R.drawable.iran_flag);
            countryFlagImageMap.put("R.drawable.iraq_flag", R.drawable.iraq_flag);
            countryFlagImageMap.put("R.drawable.ireland_flag", R.drawable.ireland_flag);
            countryFlagImageMap.put("R.drawable.israel_flag", R.drawable.israel_flag);
            countryFlagImageMap.put("R.drawable.italy_flag", R.drawable.italy_flag);
            countryFlagImageMap.put("R.drawable.jamaica_flag", R.drawable.jamaica_flag);
            countryFlagImageMap.put("R.drawable.japan_flag", R.drawable.japan_flag);
            countryFlagImageMap.put("R.drawable.jordan_flag", R.drawable.jordan_flag);
            countryFlagImageMap.put("R.drawable.kazakhstan_flag", R.drawable.kazakhstan_flag);
            countryFlagImageMap.put("R.drawable.kenya_flag", R.drawable.kenya_flag);
            countryFlagImageMap.put("R.drawable.north_korea_flag", R.drawable.north_korea_flag);
            countryFlagImageMap.put("R.drawable.south_korea_flag", R.drawable.south_korea_flag);
            countryFlagImageMap.put("R.drawable.kosovo_flag", R.drawable.kosovo_flag);
            countryFlagImageMap.put("R.drawable.kyrgyzstan_flag", R.drawable.kyrgyzstan_flag);
            countryFlagImageMap.put("R.drawable.laos_flag", R.drawable.laos_flag);
            countryFlagImageMap.put("R.drawable.latvia_flag", R.drawable.latvia_flag);
            countryFlagImageMap.put("R.drawable.lebanon_flag", R.drawable.lebanon_flag);
            countryFlagImageMap.put("R.drawable.lesotho_flag", R.drawable.lesotho_flag);
            countryFlagImageMap.put("R.drawable.liberia_flag", R.drawable.liberia_flag);
            countryFlagImageMap.put("R.drawable.liechtenstein_flag", R.drawable.liechtenstein_flag);
            countryFlagImageMap.put("R.drawable.lithuania_flag", R.drawable.lithuania_flag);
            countryFlagImageMap.put("R.drawable.luxembourg_flag", R.drawable.luxembourg_flag);
            countryFlagImageMap.put("R.drawable.madagascar_flag", R.drawable.madagascar_flag);
            countryFlagImageMap.put("R.drawable.malaysia_flag", R.drawable.malaysia_flag);
            countryFlagImageMap.put("R.drawable.maldives_flag", R.drawable.maldives_flag);
            countryFlagImageMap.put("R.drawable.mali_flag", R.drawable.mali_flag);
            countryFlagImageMap.put("R.drawable.malta_flag", R.drawable.malta_flag);
            countryFlagImageMap.put("R.drawable.mauritius_flag", R.drawable.mauritius_flag);
            countryFlagImageMap.put("R.drawable.mexico_flag", R.drawable.mexico_flag);
            countryFlagImageMap.put("R.drawable.moldova_flag", R.drawable.moldova_flag);
            countryFlagImageMap.put("R.drawable.monaco_flag", R.drawable.monaco_flag);
            countryFlagImageMap.put("R.drawable.mongolia_flag", R.drawable.mongolia_flag);
            countryFlagImageMap.put("R.drawable.montenegro_flag", R.drawable.montenegro_flag);
            countryFlagImageMap.put("R.drawable.morocco_flag", R.drawable.morocco_flag);
            countryFlagImageMap.put("R.drawable.mozambique_flag", R.drawable.mozambique_flag);
            countryFlagImageMap.put("R.drawable.myanmar_flag", R.drawable.myanmar_flag);
            countryFlagImageMap.put("R.drawable.namibia_flag", R.drawable.namibia_flag);
            countryFlagImageMap.put("R.drawable.nepal_flag", R.drawable.nepal_flag);
            countryFlagImageMap.put("R.drawable.netherlands_flag", R.drawable.netherlands_flag);
            countryFlagImageMap.put("R.drawable.new_zealand_flag", R.drawable.new_zealand_flag);
            countryFlagImageMap.put("R.drawable.nicaragua_flag", R.drawable.nicaragua_flag);
            countryFlagImageMap.put("R.drawable.niger_flag", R.drawable.niger_flag);
            countryFlagImageMap.put("R.drawable.nigeria_flag", R.drawable.nigeria_flag);
            countryFlagImageMap.put("R.drawable.north_macedonia_flag", R.drawable.north_macedonia_flag);
            countryFlagImageMap.put("R.drawable.northern_ireland_flag", R.drawable.northern_ireland_flag);
            countryFlagImageMap.put("R.drawable.norway_flag", R.drawable.norway_flag);
            countryFlagImageMap.put("R.drawable.oman_flag", R.drawable.oman_flag);
            countryFlagImageMap.put("R.drawable.pakistan_flag", R.drawable.pakistan_flag);
            countryFlagImageMap.put("R.drawable.palau_flag", R.drawable.palau_flag);
            countryFlagImageMap.put("R.drawable.panama_flag", R.drawable.panama_flag);
            countryFlagImageMap.put("R.drawable.paraguay_flag", R.drawable.paraguay_flag);
            countryFlagImageMap.put("R.drawable.peru_flag", R.drawable.peru_flag);
            countryFlagImageMap.put("R.drawable.philippines_flag", R.drawable.philippines_flag);
            countryFlagImageMap.put("R.drawable.poland_flag", R.drawable.poland_flag);
            countryFlagImageMap.put("R.drawable.portugal_flag", R.drawable.portugal_flag);
            countryFlagImageMap.put("R.drawable.qatar_flag", R.drawable.qatar_flag);
            countryFlagImageMap.put("R.drawable.romania_flag", R.drawable.romania_flag);
            countryFlagImageMap.put("R.drawable.russia_flag", R.drawable.russia_flag);
            countryFlagImageMap.put("R.drawable.rwanda_flag", R.drawable.rwanda_flag);
            countryFlagImageMap.put("R.drawable.samoa_flag", R.drawable.samoa_flag);
            countryFlagImageMap.put("R.drawable.san_marino_flag", R.drawable.san_marino_flag);
            countryFlagImageMap.put("R.drawable.saudi_arabia_flag", R.drawable.saudi_arabia_flag);
            countryFlagImageMap.put("R.drawable.scotland_flag", R.drawable.scotland_flag);
            countryFlagImageMap.put("R.drawable.senegal_flag", R.drawable.senegal_flag);
            countryFlagImageMap.put("R.drawable.serbia_flag", R.drawable.serbia_flag);
            countryFlagImageMap.put("R.drawable.seychelles_flag", R.drawable.seychelles_flag);
            countryFlagImageMap.put("R.drawable.singapore_flag", R.drawable.singapore_flag);
            countryFlagImageMap.put("R.drawable.slovakia_flag", R.drawable.slovakia_flag);
            countryFlagImageMap.put("R.drawable.slovenia_flag", R.drawable.slovenia_flag);
            countryFlagImageMap.put("R.drawable.somalia_flag", R.drawable.somalia_flag);
            countryFlagImageMap.put("R.drawable.south_africa_flag", R.drawable.south_africa_flag);
            countryFlagImageMap.put("R.drawable.spain_flag", R.drawable.spain_flag);
            countryFlagImageMap.put("R.drawable.sri_lanka_flag", R.drawable.sri_lanka_flag);
            countryFlagImageMap.put("R.drawable.sudan_flag", R.drawable.sudan_flag);
            countryFlagImageMap.put("R.drawable.suriname_flag", R.drawable.suriname_flag);
            countryFlagImageMap.put("R.drawable.sweden_flag", R.drawable.sweden_flag);
            countryFlagImageMap.put("R.drawable.switzerland_flag", R.drawable.swiss_flag);
            countryFlagImageMap.put("R.drawable.syria_flag", R.drawable.syria_flag);
            countryFlagImageMap.put("R.drawable.taiwan_flag", R.drawable.taiwan_flag);
            countryFlagImageMap.put("R.drawable.tajikistan_flag", R.drawable.tajikistan_flag);
            countryFlagImageMap.put("R.drawable.tanzania_flag", R.drawable.tanzania_flag);
            countryFlagImageMap.put("R.drawable.thailand_flag", R.drawable.thailand_flag);
            countryFlagImageMap.put("R.drawable.togo_flag", R.drawable.togo_flag);
            countryFlagImageMap.put("R.drawable.tonga_flag", R.drawable.tonga_flag);
            countryFlagImageMap.put("R.drawable.trinidad_and_tobago_flag", R.drawable.trinidad_and_tobago_flag);
            countryFlagImageMap.put("R.drawable.tunisia_flag", R.drawable.tunisia_flag);
            countryFlagImageMap.put("R.drawable.turkey_flag", R.drawable.turkey_flag);
            countryFlagImageMap.put("R.drawable.turkmenistan_flag", R.drawable.turkmenistan_flag);
            countryFlagImageMap.put("R.drawable.uganda_flag", R.drawable.uganda_flag);
            countryFlagImageMap.put("R.drawable.ukraine_flag", R.drawable.ukraine_flag);
            countryFlagImageMap.put("R.drawable.uae_flag", R.drawable.uae_flag);
            countryFlagImageMap.put("R.drawable.usa_flag", R.drawable.usa_flag);
            countryFlagImageMap.put("R.drawable.uruguay_flag", R.drawable.uruguay_flag);
            countryFlagImageMap.put("R.drawable.uzbekistan_flag", R.drawable.uzbekistan_flag);
            countryFlagImageMap.put("R.drawable.vanuatu_flag", R.drawable.vanuatu_flag);
            countryFlagImageMap.put("R.drawable.venezuela_flag", R.drawable.venezuela_flag);
            countryFlagImageMap.put("R.drawable.vietnam_flag", R.drawable.vietnam_flag);
            countryFlagImageMap.put("R.drawable.wales_flag", R.drawable.wales_flag);
            countryFlagImageMap.put("R.drawable.yemen_flag", R.drawable.yemen_flag);
            countryFlagImageMap.put("R.drawable.zambia_flag", R.drawable.zambia_flag);
            countryFlagImageMap.put("R.drawable.zimbabwe_flag", R.drawable.zimbabwe_flag);
        }  // HashMap linking country database flag image paths to resource directory paths.

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

    public static int getLandmarkResourceId (String key){

        Integer resourceId = countryLandmarkImageMap.get(key);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + key);
            return -1; // Or handle the missing resource appropriately
        }
    }

    public static int getFlagResourceId (String key){

        Integer resourceId = countryFlagImageMap.get(key);
        if (resourceId != null) {
            return resourceId;
        } else {
            Log.w("ResourceUtil", "No resource found for key: " + key);
            return -1; // Or handle the missing resource appropriately
        }
    }
}

