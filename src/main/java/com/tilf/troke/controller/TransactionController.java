package com.tilf.troke.controller;

import com.tilf.troke.repository.CustomObjectRepository;
import com.tilf.troke.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.WebContext;

/**
 * Created by Emmanuel on 2015-10-21.
 */
@Controller
public class TransactionController {

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @RequestMapping(value = "/startTrade", method = RequestMethod.GET)
    public String getUserFromItem(@RequestParam("itemID") int itemID, Model model) {
        model.addAttribute("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
        model.addAttribute("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
        model.addAttribute("currentItemID", itemID);
        model.addAttribute("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("currentItemID", itemID);
            context.setVariable("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
            context.setVariable("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
            context.setVariable("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
        }
        return "fragments/home/startTrade";
    }
}
