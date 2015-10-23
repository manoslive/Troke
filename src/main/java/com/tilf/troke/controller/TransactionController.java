package com.tilf.troke.controller;

import com.tilf.troke.auth.AuthUserContext;
import com.tilf.troke.dao.TradeForm;
import com.tilf.troke.entity.TransactionsEntity;
import com.tilf.troke.entity.UsersEntity;
import com.tilf.troke.repository.CustomObjectRepository;
import com.tilf.troke.repository.CustomTradeRepository;
import com.tilf.troke.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.WebContext;

import javax.validation.Valid;

/**
 * Created by Emmanuel on 2015-10-21.
 */
@Controller
public class TransactionController {

    @Autowired
    private CustomObjectRepository customObjectRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private CustomTradeRepository customTradeRepository;

    @RequestMapping(value = "/startTrade", method = RequestMethod.GET)
    public String getUserFromItem(@RequestParam("itemID") int itemID, Model model) {
        model.addAttribute("trade", new TradeForm());
        model.addAttribute("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
        model.addAttribute("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
        model.addAttribute("currentItemID", itemID);
        model.addAttribute("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
        UsersEntity currentUser = authUserContext.getUser();
        model.addAttribute("userActif", currentUser);
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("currentItemID", itemID);
            context.setVariable("startTradeOpponent", customUserRepository.getUserFromItem(itemID));
            context.setVariable("currentItem", customObjectRepository.getObjectNameByItemID(itemID));
            context.setVariable("inventory", customObjectRepository.getObjectsByUserID(itemID, customUserRepository.getUserFromItem(itemID).getIduser()));
            context.setVariable("userActif", currentUser);
        }
        return "fragments/home/startTrade";
    }

    @RequestMapping(value = "/addTrade", method = RequestMethod.GET)
    public String addNewTrade(@ModelAttribute("trade") @Valid TradeForm trade,  BindingResult result, Model model) {
        if(!result.hasErrors()){
            customTradeRepository.addNewTrade(trade.getIduser1(), trade.getIduser2(), trade.getObjects(), trade.getChatMsg());
        }
        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
        }
        return "fragments/home/myTrades";
    }

    // Boutton envoyer l'offre
    /*@RequestMapping(value = "/sendTrade", method = RequestMethod.GET)
    public String getItemByIdObject(@RequestParam("idObject") int idobject, Model model) {
        model.addAttribute("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
        model.addAttribute("adrItem", "/item?idObject=");
        model.addAttribute("adrStartTrade", "/startTrade?itemID=");
        model.addAttribute("leftMenu", fillLeftCatMenu());

        // TODO THYMELEAF HACK
        if (false) {
            WebContext context = new org.thymeleaf.context.WebContext(null, null, null);
            context.setVariable("singleobject", customObjectRepository.getObjectEntityByIdObject(idobject));
            context.setVariable("adrItem", "/item?objectName=");
            context.setVariable("leftMenu", fillLeftCatMenu());
            context.setVariable("adrStartTrade", "/startTrade?itemID=");
        }

        return "fragments/home/search";
    }*/
}
