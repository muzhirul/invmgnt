package com.example.invmgnt.invmgnt.controller;

import com.example.invmgnt.invmgnt.domain.StockBalance;
import com.example.invmgnt.invmgnt.service.ItemMasterService;
import com.example.invmgnt.invmgnt.service.StockBalanceService;
import com.example.invmgnt.invmgnt.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private StockBalanceService service;
    private ItemMasterService imService;

    @Autowired
    public void setInjectedBean(StockBalanceService service, ItemMasterService imService) {
        this.service = service;
        this.imService = imService;
    }

    @RequestMapping("/index")
    public String getAllPaginated(HttpServletRequest request, Model model) {

        PaginationHelper pHelper = new PaginationHelper(request);
        Page<StockBalance> page = (Page<StockBalance>) service.getAllPaginated(pHelper.pageNum, pHelper.pageSize, pHelper.sortField, pHelper.sortDir);
        List< StockBalance > list = page.getContent();

//        model.addAttribute("currentPage", pHelper.pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", pHelper.sortField);
//        model.addAttribute("sortDir", pHelper.sortDir);
//        model.addAttribute("reverseSortDir", pHelper.sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("objectList", list);

        return "stockBalance_index";
    }




    @GetMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable long id)
    {
        StockBalance entity = null;
        try {
            entity = service.findById(id);
        } catch (Exception ex) {
        }
        model.addAttribute("entity", entity);
        return "stockBalance_show";
    }


    @RequestMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirAttrs) throws Exception
    {
        service.deleteById(id);
        return "redirect:/stock/index";
    }
}
