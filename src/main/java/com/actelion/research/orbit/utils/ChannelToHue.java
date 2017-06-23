/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2017 Actelion Pharmaceuticals Ltd., Gewerbestrasse 16, CH-4123 Allschwil, Switzerland.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.actelion.research.orbit.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChannelToHue {

    public static final AtomicLong lastUpdate = new AtomicLong(0);

    private static final float HueAlexa350 = 204f / 360f;
    private static final float HueAlexa388 = 204f / 360f;
    private static final float HueAlexa405 = 204f / 360f;
    private static final float HueAlexa430 = 135f / 360f;
    private static final float HueAlexa488 = 135f / 360f;
    private static final float HueAlexa500 = 135f / 360f;
    private static final float HueAlexa514 = 135f / 360f;
    private static final float HueAlexa532 = 48f / 360f;
    private static final float HueAlexa546 = 36f / 360f;
    private static final float HueAlexa550 = 36f / 360f;
    private static final float HueAlexa555 = 36f / 360f;
    private static final float HueAlexa568 = 0f / 360f;
    private static final float HueAlexa594 = 0f / 360f;
    private static final float HueAlexa610 = 0f / 360f;
    private static final float HueAlexa633 = 0f / 360f;
    private static final float HueAlexa635 = 0f / 360f;
    private static final float HueAlexa647 = 0f / 360f;
    private static final float HueAlexa650 = 0f / 360f;
    private static final float HueAlexa660 = 0f / 360f;
    private static final float HueAlexa680 = 0f / 360f;
    private static final float HueAlexa700 = 0f / 360f;
    private static final float HueAlexa750 = 0f / 360f;
    private static final float HueAlexa790 = 0f / 360f;


    private static final float HueCy3 = 40f / 360f;
    private static final float HueCy5 = 0f / 360f;  // 60
    private static final float HueCy7 = 62f / 360f; 
    private static final float HueEGFP = 141f / 360f;
    private static final float HueDAPI = 240f / 360f; // 202
    private static final float HueFITC = 108f / 360f;
    private static final float HueTRITC = 22f / 360f;
    private static final float HueCh2T1 =  0f / 360f;
    private static final float HueCh2T2 =  177f / 360f;


    private static ChannelHue[] mappings = new ChannelHue[] {
        new ChannelHue("alexa594", HueAlexa594),
        new ChannelHue("alexa fluor 594", HueAlexa594),
        new ChannelHue("alexa388", HueAlexa388),
        new ChannelHue("alexa fluor 388", HueAlexa388),
        new ChannelHue("alexa568", HueAlexa568),
        new ChannelHue("alexa fluor 568", HueAlexa568),
        new ChannelHue("cy2", HueFITC),
        new ChannelHue("cy3", HueCy3),
        new ChannelHue("cy3.5", HueCy3),
        new ChannelHue("cy3_5", HueCy3),
        new ChannelHue("cy35", HueCy3),
        new ChannelHue("cy5", HueCy5),
        new ChannelHue("cy5.5", HueCy5),
        new ChannelHue("cy5_5", HueCy5),
        new ChannelHue("cy55", HueCy5),
        new ChannelHue("cy5 2 (650)", HueCy5),
        new ChannelHue("cy7", HueCy7),
        new ChannelHue("egfp", HueEGFP),
        new ChannelHue("gfp", HueEGFP),
        new ChannelHue("dapi", HueDAPI),
        new ChannelHue("dapi 2 (387)", HueDAPI),
        new ChannelHue("fitc", HueFITC),
        new ChannelHue("fitc 2 (485)", HueFITC),
        new ChannelHue("tritc", HueTRITC),
        new ChannelHue("tritc 2 (560)", HueTRITC),
        new ChannelHue("amca", 204f/360f),
        new ChannelHue("coumarin", 204f/360f),
        new ChannelHue("channel0", HueDAPI),
        new ChannelHue("channel1", HueFITC),
        new ChannelHue("channel2", HueTRITC),
        new ChannelHue("channel3", HueCy5),
        new ChannelHue("channel4", HueEGFP),
        new ChannelHue("channel5", HueCy3),
        new ChannelHue("hoechst", HueDAPI),
        new ChannelHue("percp", 0f / 360f),
        new ChannelHue("Ch2-T1", HueCh2T1),
        new ChannelHue("Ch2-T2", HueCh2T2),
    };

    public final static Map<String, Float> hueMap = new ConcurrentHashMap<>(mappings.length);
    public final static Map<String, Float> userHueMap = new ConcurrentHashMap<>();

    static {
        for (ChannelHue channelHue: mappings) {
            hueMap.put(channelHue.getChannel(), channelHue.getHue());
        }
    }

    public static float getHue(String channel) {
        if (channel==null) return HueDAPI;
        String channelName = channel.trim().toLowerCase();
        if (userHueMap.containsKey(channelName)) return userHueMap.get(channelName);
        if (hueMap.containsKey(channelName)) return hueMap.get(channelName);
        if (channelName.contains("dapi")) return HueDAPI;
        if (channelName.contains("fitc")) return HueFITC;
        if (channelName.contains("tritc")) return HueTRITC;
        if (channelName.contains("cy5")) return HueCy5;
        if (channelName.contains("cy3")) return HueCy3;
        if (channelName.contains("gfp")) return HueEGFP;

        if (channelName.contains("alexa") || channelName.contains("af")) {
            if (channelName.contains("350")) return HueAlexa350;
            if (channelName.contains("388")) return HueAlexa388;
            if (channelName.contains("405")) return HueAlexa405;
            if (channelName.contains("430")) return HueAlexa430;
            if (channelName.contains("488")) return HueAlexa488;
            if (channelName.contains("500")) return HueAlexa500;
            if (channelName.contains("514")) return HueAlexa514;
            if (channelName.contains("532")) return HueAlexa532;
            if (channelName.contains("546")) return HueAlexa546;
            if (channelName.contains("550")) return HueAlexa550;
            if (channelName.contains("555")) return HueAlexa555;
            if (channelName.contains("568")) return HueAlexa568;
            if (channelName.contains("594")) return HueAlexa594;
            if (channelName.contains("610")) return HueAlexa610;
            if (channelName.contains("633")) return HueAlexa633;
            if (channelName.contains("635")) return HueAlexa635;
            if (channelName.contains("647")) return HueAlexa647;
            if (channelName.contains("650")) return HueAlexa650;
            if (channelName.contains("660")) return HueAlexa660;
            if (channelName.contains("680")) return HueAlexa680;
            if (channelName.contains("700")) return HueAlexa700;
            if (channelName.contains("750")) return HueAlexa750;
            if (channelName.contains("790")) return HueAlexa790;
        }


        if (channelName.contains("violet")) return 264f / 360f;
        if (channelName.contains("red")) return 0f / 360f;
        if (channelName.contains("green")) return 102f / 360f;
        if (channelName.contains("blue")) return 231f / 360f;

        for (String name: hueMap.keySet()) {
            if (channelName.contains(name)) return hueMap.get(name);
        }

        System.out.println("Channel-color unknown. Please define a color for channel: "+channel);

        return HueDAPI;
    }

    public static class ChannelHue {
        String channel;
        float hue;

        public ChannelHue(String channel, float hue) {
            this.channel = channel;
            this.hue = hue;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public float getHue() {
            return hue;
        }

        public void setHue(float hue) {
            this.hue = hue;
        }
    }

}
