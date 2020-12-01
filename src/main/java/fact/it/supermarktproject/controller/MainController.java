package fact.it.supermarktproject.controller;
//Vermeld hier je naam en studentennummer
//  Wouter De Smet
//  r0785520

// Deze imports geven fouten dus daarom commented.
//import com.sun.org.apache.xpath.internal.operations.Mod;
//import com.sun.org.apache.xpath.internal.operations.Bool;
//import com.sun.org.apache.xpath.internal.operations.Mod;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import fact.it.supermarktproject.model.*;
//import jdk.vm.ci.meta.Local;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
//import sun.jvm.hotspot.types.CIntegerField;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.HTML;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class MainController {
    private ArrayList<Personeelslid> personeelsleden;
    private ArrayList<Klant> klanten;
    private ArrayList<Supermarkt> supermarkten;

    /* EXAMEN JUNI */
    @RequestMapping("/0_overzichtPersoneelMetWedde")
    public String MetWedde(Model model, HttpServletRequest request) {
        model.addAttribute("personeelsleden", personeelsleden);
        return "0_overzichtPersoneelMetWedde";
    }

    @RequestMapping("/4_welkomPersoneelslid")
    public String Personeelgegevens(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        LocalDate inDienstSinds = LocalDate.parse(request.getParameter("inDienstSinds"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        /* EXAMEN */ String weddeStr = request.getParameter("wedde");
        /* EXAMEN */ double weddeDbl = Double.parseDouble(weddeStr);
        Personeelslid personeelslid = new Personeelslid(voornaam, familienaam);
        personeelslid.setInDienstSinds(inDienstSinds);
        /* EXAMEN */ personeelslid.setWedde(weddeDbl);
        model.addAttribute("personeelslid", personeelslid);
        personeelsleden.add(personeelslid);
        return "4_welkomPersoneelslid";
    }

    /* Codeer hieronder al je verschillende mappings */
    @RequestMapping("/1_nieuweKlant")
    public String nieuweKlant(Model model, HttpServletRequest request) {
        model.addAttribute("supermarkten", supermarkten);
        return "1_nieuweKlant";
    }

    @RequestMapping("/2_welkomKlant")
    public String klantgegevens(Model model, HttpServletRequest request) {
        String voornaam = request.getParameter("voornaam");
        String familienaam = request.getParameter("familienaam");
        int geboortejaar = Integer.parseInt(request.getParameter("geboortejaar"));
        Klant klant = new Klant(voornaam, familienaam);
        klant.setGeboortejaar(geboortejaar);
        model.addAttribute("klant", klant);
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarktIndex"));
        supermarkten.get(supermarktIndex).registreerKlant(klant);
        klanten.add(klant);
        return "2_welkomKlant";
    }

    @RequestMapping("/3_nieuwPersoneelslid")
    public String nieuwPersoneelslid() {
        return "3_nieuwPersoneelslid";
    }



    @RequestMapping("5_overzichtPersoneel")
    public String overzichtPersoneel(Model model, HttpServletRequest request) {
        model.addAttribute("personeelsleden", personeelsleden);
        return "5_overzichtPersoneel";
    }

    @RequestMapping("6_overzichtKlanten")
    public String overzichtKlanten(Model model, HttpServletRequest request) {
        model.addAttribute("klanten", klanten);
        return "6_overzichtKlanten";
    }

    @RequestMapping("7_nieuweSupermarkt")
    public String nieuweSupermarkt() {
        return "7_nieuweSupermarkt";
    }

    @RequestMapping("/8_overzichtSupermarkten_toevoegen")
    public String overzichtSupermarkten_toevoegen(Model model, HttpServletRequest request) {
        String supermarktStr = request.getParameter("supermarkt");
        Supermarkt s = new Supermarkt(supermarktStr);
        model.addAttribute("supermarkten", supermarkten);
        supermarkten.add(s);
        return "8_overzichtSupermarkten";
    }

    @RequestMapping("8_overzichtSupermarkten_tonen")
    public String overzichtSupermarkten_tonen(Model model) {
        model.addAttribute("supermarkten", supermarkten);
        return "8_overzichtSupermarkten";
    }

    @RequestMapping("9_nieuweAfdeling")
    public String nieuweAfdeling(Model model, HttpServletRequest request) {
        model.addAttribute("personeelsleden", personeelsleden);
        model.addAttribute("supermarkten", supermarkten);
        return "9_nieuweAfdeling";
    }

    @RequestMapping("10_overzichtAfdeling_toevoegen")
    public String overzichtAfdelingen_toevoegen(Model model, HttpServletRequest request) {
        String naam = request.getParameter("naam");
        String foto = request.getParameter("foto");
        boolean gekoeld = Boolean.parseBoolean(request.getParameter("gekoeld"));
        int verantwoordelijkeIndex = Integer.parseInt(request.getParameter("verantwoordelijke"));
        if (verantwoordelijkeIndex < 0) {
            model.addAttribute("foutmelding", "Je hebt nog geen verantwoordelijke gekozen");
            return "99_fout";
        }
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarkt"));
        if (supermarktIndex < 0) {
            model.addAttribute("foutmelding", "Je hebt nog geen supermarkt gekozen");
            return "99_fout";
        }
        Afdeling afdeling = new Afdeling();
        afdeling.setNaam(naam);
        afdeling.setFoto(foto);
        afdeling.setGekoeld(gekoeld);
        afdeling.setVerantwoordelijke(personeelsleden.get(verantwoordelijkeIndex));
        model.addAttribute("supermarkt", supermarkten.get(supermarktIndex));
        supermarkten.get(supermarktIndex).voegAfdelingToe(afdeling);
        model.addAttribute("afdelingen", supermarkten.get(supermarktIndex).getAfdelingen());
//        if (afdeling.isGekoeld()) {
//            model.addAttribute("koeling", "Gekoelde afdeling");
//        } else {
//            model.addAttribute("koeling", "Geen koeling");
//        }
        return "10_overzichtAfdeling";
    }

    @RequestMapping("10_overzichtAfdeling_tonen")
        public String overzichtAfdeling_tonen(Model model, HttpServletRequest request) {
        int supermarktIndex = Integer.parseInt(request.getParameter("supermarkt"));
        model.addAttribute("supermarkt", supermarkten.get(supermarktIndex));
        model.addAttribute("afdelingen", supermarkten.get(supermarktIndex).getAfdelingen());
//        if (afdeling.isGekoeld()) {
//            model.addAttribute("koeling", "Gekoelde afdeling");
//        } else {
//            model.addAttribute("koeling", "Geen koeling");
//        }
        return "10_overzichtAfdeling";
    }

    @RequestMapping("11_zoekOpNaam")
    public String zoekOpNaam(Model model, HttpServletRequest request) {
        String zoekopdracht = request.getParameter("zoek");
        for (Supermarkt supermarkt : supermarkten) {
            Afdeling resultaat = supermarkt.zoekAfdelingOpNaam(zoekopdracht.toLowerCase());
            if (resultaat != null) {
                model.addAttribute("zoekopdracht", zoekopdracht);
                model.addAttribute("afdeling", resultaat);
                return "11_zoekOpNaam";
            }
        }
        model.addAttribute("foutmelding", "Geen afdeling met naam " + zoekopdracht + " gevonden!" );
        return "99_fout";
    }

    @RequestMapping("99_fout")
    public String foutmelding() {
        return "99_fout";
    }

    @PostConstruct
    private ArrayList<Personeelslid> vulPersoneelsledenLijst() {
        personeelsleden = new ArrayList<>();
        Personeelslid jitse = new Personeelslid("Jitse", "Verhaegen");
        Personeelslid bert = new Personeelslid("Bert", "De Meulenaere");
        Personeelslid sanne = new Personeelslid("Sanne", "Beckers");
        personeelsleden.add(jitse);
        personeelsleden.add(bert);
        personeelsleden.add(sanne);
        jitse.setWedde(2000);
        bert.setWedde(15000);
        sanne.setWedde(50);
        return personeelsleden;
    }

    @PostConstruct
    private ArrayList<Klant> vulKlantenLijst() {
        klanten = new ArrayList<>();
        Klant daan = new Klant("Daan", "Mertens");
        daan.setGeboortejaar(2001);
        Klant wim = new Klant("Wim", "Wijns");
        wim.setGeboortejaar(1956);
        Klant gert = new Klant("Gert", "Pauwels");
        gert.setGeboortejaar(1978);
        //mezelf toevoegen als klant
        Klant wouter = new Klant("Wouter", "De Smet");
        wouter.setGeboortejaar(2001);
        klanten.add(daan);
        klanten.add(wim);
        klanten.add(gert);
        klanten.add(wouter);
        klanten.get(0).voegToeAanBoodschappenlijst("melk");
        klanten.get(0).voegToeAanBoodschappenlijst("kaas");
        klanten.get(1).voegToeAanBoodschappenlijst("eieren");
        klanten.get(1).voegToeAanBoodschappenlijst("water");
        klanten.get(1).voegToeAanBoodschappenlijst("bloemkool");
        klanten.get(1).voegToeAanBoodschappenlijst("sla");
        klanten.get(2).voegToeAanBoodschappenlijst("tomaten");
        //eigen boodschappenlijstje
        klanten.get(3).voegToeAanBoodschappenlijst("lasagne");
        klanten.get(3).voegToeAanBoodschappenlijst("chips");
        klanten.get(3).voegToeAanBoodschappenlijst("sandwiches");
        klanten.get(3).voegToeAanBoodschappenlijst("paprika");
        klanten.get(3).voegToeAanBoodschappenlijst("appelsienen");
        return klanten;
    }

    @PostConstruct
    private ArrayList<Supermarkt> vulSupermarktenLijst() {
        supermarkten = new ArrayList<>();
        Supermarkt supermarkt1 = new Supermarkt("Colruyt Geel");
        Supermarkt supermarkt2 = new Supermarkt("Okay Meerhout");
        Supermarkt supermarkt3 = new Supermarkt("Colruyt Herentals");
        Afdeling afdeling1 = new Afdeling("Brood");
        Afdeling afdeling2 = new Afdeling("Groenten");
        afdeling2.setGekoeld(true);
        Afdeling afdeling3 = new Afdeling("Fruit");
        afdeling3.setGekoeld(true);
        Afdeling afdeling4 = new Afdeling("Vlees");
        afdeling4.setGekoeld(true);
        Afdeling afdeling5 = new Afdeling("Dranken");
        Afdeling afdeling6 = new Afdeling("Diepvries");
        afdeling1.setFoto("/img/brood.jpg");
        afdeling2.setFoto("/img/groenten.jpg");
        afdeling3.setFoto("/img/fruit.jpg");
        afdeling1.setVerantwoordelijke(personeelsleden.get(0));
        afdeling2.setVerantwoordelijke(personeelsleden.get(1));
        afdeling3.setVerantwoordelijke(personeelsleden.get(2));
        afdeling4.setVerantwoordelijke(personeelsleden.get(0));
        afdeling5.setVerantwoordelijke(personeelsleden.get(1));
        afdeling6.setVerantwoordelijke(personeelsleden.get(2));

        supermarkt1.voegAfdelingToe(afdeling1);
        supermarkt1.voegAfdelingToe(afdeling2);
        supermarkt2.voegAfdelingToe(afdeling3);
        supermarkt2.voegAfdelingToe(afdeling4);
        supermarkt3.voegAfdelingToe(afdeling5);
        supermarkt3.voegAfdelingToe(afdeling6);
        supermarkten.add(supermarkt1);
        supermarkten.add(supermarkt2);
        supermarkten.add(supermarkt3);
        return supermarkten;
    }
}
