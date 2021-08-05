package com.example.controller;

import com.example.starterexcel.annotation.ExportXlsx;
import com.example.starterexcel.annotation.ImportXlsx;
import com.example.starterexcel.model.ExportCloumn;
import com.example.starterexcel.util.Validators;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author gengweiweng
 * @time 2021/7/23
 * @desc
 */
@RestController
@RequestMapping("/test/excel")
public class TestController {

    /**
     * 根据配置resources模板导出
     * @return
     */
    @ExportXlsx(name = "导出模板", templateFile = "template.xlsx")
    @GetMapping("/template")
    public void template(){
    }

    /**
     * 导入模板数据
     * @param list 解析的数据集
     * @param bindingResult 数据校验结果
     */
    @PostMapping("/upload")
    public void upload(@ImportXlsx @Validated List<Demo> list, BindingResult bindingResult){

        System.out.println(list);
        System.out.println(Validators.resolveErrorString("导入结果：","导入结束！",bindingResult));
    }


    @ExportXlsx(name = "多sheet导出",sheet = {"test1","test2"})
    @PostMapping("/exportMore")
    public Map<String,List>  exportMore(@RequestBody ExportCloumn cloumn){
        Map<String,List> map = new HashMap<>();
        map.put("test1", list());
        map.put("test2", list());
        return map;
    }

    @ExportXlsx(name = "单sheet导出")
    @PostMapping("/export")
    public List<Demo>  export(@RequestBody ExportCloumn cloumnName){
        return list();
    }


    public List<Demo> list(){
        List<Demo> list = new ArrayList<>();
        list.add(new Demo("嗡嗡","123456", LocalDateTime.now(),"略","2021年07月"));
        list.add(new Demo("weng","1234567890",LocalDateTime.now(),"略","2021年07月"));
        list.add(new Demo("WENG","12345678910",LocalDateTime.now(),"略","2021年07月"));
        return list;
    }

}
