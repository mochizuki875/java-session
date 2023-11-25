package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/session")
public class SessionController {
    
    @Autowired
    HttpSession session;

    // セッションの登録(Setter)
    @GetMapping("/set")
    @ResponseBody
    public String set(@RequestParam("data") String data) {
        // Sessionへの保存
        session.setAttribute("data", data);
        return "保存しました <br>data: " + data;
    }

    // セッションの取得(Getter)
    @GetMapping("/get")
    @ResponseBody
    public String get(){

        // Session情報の取得
        String data = (String)this.session.getAttribute("data");
        if (data == null) {
            data = "null";
        }
        return "[セッション情報]<br>data: " + data;
    }

    // セッションの削除
    @GetMapping("/delete")
    @ResponseBody
    public String delete(){
        // セッション情報の削除
        session.removeAttribute("data");
        return "削除しました";
    }
}
