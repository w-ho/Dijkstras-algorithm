package webBasedRouting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
public class ViewController {

    @RequestMapping(value = "/")
    public String index( String name,  Model model){
        Parser pasrer = new Parser();
        populate(pasrer, model);
        return "mapGraph";
    }

    @PostMapping(value = "/")
    public String route(@RequestParam(value="start", required = false, defaultValue = "A") String start,
                        @RequestParam(value="end", required = false, defaultValue = "C")String end
                                    ,Model model){
        Parser paser = new Parser();
        populate(paser, model);
        routeCalculator(model, paser , start, end);
        return "mapGraph";
    }

    private void populate(Parser pasrer, Model model){
        model.addAttribute("A", pasrer.nodeHandler.nodeList.get(0).getName());
        model.addAttribute("B", pasrer.nodeHandler.nodeList.get(1).getName());
        model.addAttribute("C", pasrer.nodeHandler.nodeList.get(2).getName());
        model.addAttribute("D", pasrer.nodeHandler.nodeList.get(3).getName());
        model.addAttribute("E", pasrer.nodeHandler.nodeList.get(4).getName());
        model.addAttribute("F", pasrer.nodeHandler.nodeList.get(5).getName());
        model.addAttribute("G", pasrer.nodeHandler.nodeList.get(6).getName());
        model.addAttribute("H", pasrer.nodeHandler.nodeList.get(7).getName());
        model.addAttribute("I", pasrer.nodeHandler.nodeList.get(8).getName());
        model.addAttribute("J", pasrer.nodeHandler.nodeList.get(9).getName());
        model.addAttribute("AB", pasrer.nodeHandler.nodeList.get(0).linkage.get("B"));
        model.addAttribute("AC", pasrer.nodeHandler.nodeList.get(0).linkage.get("C"));
        model.addAttribute("BE", pasrer.nodeHandler.nodeList.get(1).linkage.get("E"));
        model.addAttribute("BF", pasrer.nodeHandler.nodeList.get(1).linkage.get("F"));
        model.addAttribute("CD", pasrer.nodeHandler.nodeList.get(2).linkage.get("D"));
        model.addAttribute("CF", pasrer.nodeHandler.nodeList.get(2).linkage.get("F"));
        model.addAttribute("DE", pasrer.nodeHandler.nodeList.get(3).linkage.get("E"));
        model.addAttribute("EF", pasrer.nodeHandler.nodeList.get(4).linkage.get("F"));
        model.addAttribute("EG", pasrer.nodeHandler.nodeList.get(4).linkage.get("G"));
        model.addAttribute("FH", pasrer.nodeHandler.nodeList.get(5).linkage.get("H"));
        model.addAttribute("FJ", pasrer.nodeHandler.nodeList.get(5).linkage.get("J"));
        model.addAttribute("GH", pasrer.nodeHandler.nodeList.get(6).linkage.get("H"));
        model.addAttribute("GI", pasrer.nodeHandler.nodeList.get(6).linkage.get("I"));
        model.addAttribute("HI", pasrer.nodeHandler.nodeList.get(7).linkage.get("I"));
        model.addAttribute("IJ", pasrer.nodeHandler.nodeList.get(8).linkage.get("J"));
    }

    private void routeCalculator(Model model, Parser paser, String start, String end ){
        Set routeTaken = paser.nodeHandler.dijkstraRoute(start, end);
        int distance = paser.nodeHandler.getMinDistance();
        int stepMachine = routeTaken.size();
        model.addAttribute("begin", start);
        model.addAttribute("finish", end);
        model.addAttribute("route",  routeTaken);
        model.addAttribute("distance", distance);
        model.addAttribute("steps", stepMachine);
    }

}