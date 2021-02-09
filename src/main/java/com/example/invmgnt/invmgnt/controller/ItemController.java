package com.example.invmgnt.invmgnt.controller;

import com.example.invmgnt.invmgnt.domain.ItemMaster;
import com.example.invmgnt.invmgnt.service.ItemMasterService;
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
@RequestMapping("/item")
public class ItemController {
    private ItemMasterService service;
    @Autowired
    public void setInjectedBean(ItemMasterService service) {
        this.service = service;
    }


    @RequestMapping("/index1")
    public String getAll(Model model)
    {
        List<ItemMaster> list = service.getAll();
        model.addAttribute("objectList", list);
        return "view/auth/role/index";
    }

    //    @Secured({"ROLE_EDITOR", "ROLE_ADMIN"})
    @RequestMapping("/index")
    public String getAllPaginated(HttpServletRequest request, Model model) {

        PaginationHelper pHelper = new PaginationHelper(request);
        Page<ItemMaster> page = (Page<ItemMaster>) service.getAllPaginated(pHelper.pageNum, pHelper.pageSize, pHelper.sortField, pHelper.sortDir);
        List< ItemMaster > list = page.getContent();

//        model.addAttribute("currentPage", pHelper.pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

//        model.addAttribute("sortField", pHelper.sortField);
//        model.addAttribute("sortDir", pHelper.sortDir);
//        model.addAttribute("reverseSortDir", pHelper.sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("objectList", list);

        return "itemMaster_index";
    }


    @GetMapping(value = "/show/{id}")
    public String show(Model model, @PathVariable long id)
    {
        ItemMaster entity = null;
        try {
            entity = service.findById(id);
        } catch (Exception ex) {
//            model.addAttribute(SysMgsStr.msgKey3, SysMgsStr.msgDesc3);
        }
        model.addAttribute("entity", entity);
        return "itemMaster_show";
    }


/*    @GetMapping(value = "/showAll")
    public String showAll(Model model)
    {
        List<ItemMaster> entityList = null;
        try {
            entityList = service.getAll();
        } catch (Exception ex) {
//            model.addAttribute(SysMgsStr.msgKey3, SysMgsStr.msgDesc3);
        }
        model.addAttribute("entityList", entityList);
        return "itemMaster_index";
    }*/



    @RequestMapping(path = "/create")
    public String create(Model model)
    {
        model.addAttribute("entity", new ItemMaster());
        return "itemMaster_create";
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public String save(ItemMaster postObjInst, BindingResult result, Model model, RedirectAttributes redirAttrs)
    {
        if (result.hasErrors()) {
            return "itemMaster_create";
        }
        postObjInst = service.createOrUpdate(postObjInst);
        model.addAttribute("entity", postObjInst);
//        redirAttrs.addFlashAttribute(SysMgsStr.msgKey1, SysMgsStr.msgDesc1);

        return "redirect:/item/show/" + postObjInst.getId();
    }

    @RequestMapping(path = {"/edit", "/edit/{id}"})
    public String edit(Model model, @PathVariable("id") Optional<Long> id) throws Exception
    {
        if (id.isPresent()) {
            ItemMaster entity = service.getById(id.get());
            model.addAttribute("entity", entity);
        } else {
            model.addAttribute("entity", new ItemMaster());
        }
        return "itemMaster_edit";
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String update(ItemMaster postObjInst, RedirectAttributes redirAttrs)
    {
        postObjInst = service.createOrUpdate(postObjInst);
//        redirAttrs.addFlashAttribute(SysMgsStr.msgKey1, SysMgsStr.msgDesc1u);
        return "redirect:/item/show/" + postObjInst.getId();
    }

    @RequestMapping(path = "/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, RedirectAttributes redirAttrs) throws Exception
    {
        service.deleteById(id);
//        redirAttrs.addFlashAttribute(SysMgsStr.msgKey2, SysMgsStr.msgDesc2);
        return "redirect:/item/index";
    }
}
