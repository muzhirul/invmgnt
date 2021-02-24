package com.example.invmgnt.invmgnt.controller;

import com.example.invmgnt.invmgnt.domain.PurchaseReceived;
import com.example.invmgnt.invmgnt.domain.SalesMaster;
import com.example.invmgnt.invmgnt.service.ItemMasterService;
import com.example.invmgnt.invmgnt.service.PurchaseReceivedService;
import com.example.invmgnt.invmgnt.service.SalesMasterService;
import com.example.invmgnt.invmgnt.util.PaginationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sales")
public class SalesMasterController {

    private SalesMasterService service;
    private ItemMasterService imService;

    @Autowired
    public void setInjectedBean(SalesMasterService service, ItemMasterService imService) {
        this.service = service;
        this.imService = imService;
    }

    @RequestMapping("/index")
    public String getAllPaginated(HttpServletRequest request, Model model) {

        PaginationHelper pHelper = new PaginationHelper(request);
        Page<SalesMaster> page = (Page<SalesMaster>) service.getAllPaginated(pHelper.pageNum, pHelper.pageSize, pHelper.sortField, pHelper.sortDir);
        List< SalesMaster > list = page.getContent();

//        model.addAttribute("currentPage", pHelper.pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", pHelper.sortField);
//        model.addAttribute("sortDir", pHelper.sortDir);
//        model.addAttribute("reverseSortDir", pHelper.sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("objectList", list);

        return "salesMaster_index";
    }

    @RequestMapping(path = "/create")
    public String create(Model model)
    {
        model.addAttribute("allItems", imService.getMapAllItems());
        model.addAttribute("entity", new SalesMaster());
        return "salesMaster_create";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(SalesMaster postObjInst, BindingResult result, Model model, RedirectAttributes redirAttrs)
    {
        if (result.hasErrors()) {
            return "salesMaster_create";
        }
        postObjInst = service.createOrUpdate(postObjInst);
        model.addAttribute("entity", postObjInst);

        return "redirect:/sales/index";
    }

    @GetMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable long id)
    {
        SalesMaster entity = null;
        try {
            entity = service.findById(id);
        } catch (Exception ex) {
//            model.addAttribute(SysMgsStr.msgKey3, SysMgsStr.msgDesc3);
        }
        model.addAttribute("entity", entity);
        return "salesMaster_show";
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws Exception
    {
        model.addAttribute("allItems", imService.getMapAllItems());
        if (id.isPresent()) {
            SalesMaster entity = service.getById(id.get());
            model.addAttribute("entity", entity);
        } else {
            model.addAttribute("entity", new PurchaseReceived());
        }
        return "salesMaster_edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(SalesMaster postObjInst, RedirectAttributes redirAttrs)
    {
        postObjInst = service.createOrUpdate(postObjInst);
//        redirAttrs.addFlashAttribute(SysMgsStr.msgKey1, SysMgsStr.msgDesc1u);
        return "redirect:/sales/show/" + postObjInst.getId();
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirAttrs) throws Exception
    {
        service.deleteById(id);
//        redirAttrs.addFlashAttribute(SysMgsStr.msgKey2, SysMgsStr.msgDesc2);
        return "redirect:/sales/index";
    }
}
