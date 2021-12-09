package com.example.testinterfaccia.logiccontroller;

public class ProfiloOfferteMostreLogicController {

    public String makeMapHtml(String luogo){

        String htmlMap="";
        StringBuilder sb = new StringBuilder(htmlMap);

        sb.append("<div style=\"margin-left:-8px; margin-top:-8px;\"><iframe width=\"422px\" height=\"100%\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?q=")
                .append(luogo.replaceAll(" ", "%20"))  // Dato che gli spazi nell'url vengono interpretati con %20
                .append("&t=&z=13&ie=UTF8&iwloc=&output=embed\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\"></iframe></div>");

        htmlMap = sb.toString();

        return htmlMap;
    }

}
