package com.weather.advisory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CountyCoordinates {
    private static final Map<String, double[]> COORDINATES = new HashMap<>();

    static {
        // All 47 Kenyan Counties with their approximate coordinates [latitude, longitude]
        COORDINATES.put("Baringo", new double[]{0.4693, 36.0891});
        COORDINATES.put("Bomet", new double[]{-0.7806, 35.3086});
        COORDINATES.put("Bungoma", new double[]{0.5635, 34.5606});
        COORDINATES.put("Busia", new double[]{0.4346, 34.1115});
        COORDINATES.put("Elgeyo-Marakwet", new double[]{0.7500, 35.4500});
        COORDINATES.put("Embu", new double[]{-0.5312, 37.4579});
        COORDINATES.put("Garissa", new double[]{-0.4569, 39.6582});
        COORDINATES.put("Homa Bay", new double[]{-0.5273, 34.4571});
        COORDINATES.put("Isiolo", new double[]{0.3555, 37.5826});
        COORDINATES.put("Kajiado", new double[]{-2.0982, 36.7820});
        COORDINATES.put("Kakamega", new double[]{0.2827, 34.7519});
        COORDINATES.put("Kericho", new double[]{-0.3676, 35.2837});
        COORDINATES.put("Kiambu", new double[]{-1.1714, 36.8356});
        COORDINATES.put("Kilifi", new double[]{-3.6309, 39.8498});
        COORDINATES.put("Kirinyaga", new double[]{-0.6589, 37.3826});
        COORDINATES.put("Kisii", new double[]{-0.6820, 34.7674});
        COORDINATES.put("Kisumu", new double[]{-0.0917, 34.7680});
        COORDINATES.put("Kitui", new double[]{-1.3667, 38.0104});
        COORDINATES.put("Kwale", new double[]{-4.1747, 39.4605});
        COORDINATES.put("Laikipia", new double[]{0.3676, 36.7820});
        COORDINATES.put("Lamu", new double[]{-2.2717, 40.9020});
        COORDINATES.put("Machakos", new double[]{-1.5177, 37.2634});
        COORDINATES.put("Makueni", new double[]{-2.2480, 37.8221});
        COORDINATES.put("Mandera", new double[]{3.9366, 41.8550});
        COORDINATES.put("Marsabit", new double[]{2.3284, 37.9899});
        COORDINATES.put("Meru", new double[]{0.3556, 37.6489});
        COORDINATES.put("Migori", new double[]{-1.0634, 34.4731});
        COORDINATES.put("Mombasa", new double[]{-4.0435, 39.6682});
        COORDINATES.put("Murang'a", new double[]{-0.7808, 37.0450});
        COORDINATES.put("Nairobi", new double[]{-1.2864, 36.8172});
        COORDINATES.put("Nakuru", new double[]{-0.3031, 36.0800});
        COORDINATES.put("Nandi", new double[]{0.1830, 35.1267});
        COORDINATES.put("Narok", new double[]{-1.0803, 35.8711});
        COORDINATES.put("Nyamira", new double[]{-0.5667, 34.9333});
        COORDINATES.put("Nyandarua", new double[]{-0.1807, 36.5219});
        COORDINATES.put("Nyeri", new double[]{-0.4194, 36.9472});
        COORDINATES.put("Samburu", new double[]{1.2153, 37.0714});
        COORDINATES.put("Siaya", new double[]{-0.0633, 34.2882});
        COORDINATES.put("Taita-Taveta", new double[]{-3.3167, 38.4810});
        COORDINATES.put("Tana River", new double[]{-1.5219, 39.6591});
        COORDINATES.put("Tharaka-Nithi", new double[]{-0.2966, 37.7308});
        COORDINATES.put("Trans-Nzoia", new double[]{1.0522, 34.9502});
        COORDINATES.put("Turkana", new double[]{3.3122, 35.5656});
        COORDINATES.put("Uasin Gishu", new double[]{0.5500, 35.3000});
        COORDINATES.put("Vihiga", new double[]{0.0670, 34.7192});
        COORDINATES.put("Wajir", new double[]{1.7471, 40.0573});
        COORDINATES.put("West Pokot", new double[]{1.6211, 35.3903});
    }

    public double[] getCoordinates(String county) {
        return COORDINATES.get(county);
    }

    public boolean hasCounty(String county) {
        return COORDINATES.containsKey(county);
    }
}

