package com.example.demo.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.TestForm;

import jakarta.servlet.http.HttpSession;

@Controller
public class HelloController {

	// セッションの初期化
	// HttpSession型のsessionをグローバル変数格納先的な感じで使ってるイメージ
	@Autowired
	HttpSession session;
	
	
	@GetMapping("/")
	public String getLogin(TestForm form,Model model) {
		
		model.addAttribute("form", form);

		return "login";
	}

	// POST用のパラメータを受け取る
    // フォームに入力された値を受け取りセッションに登録する
	@PostMapping("/formPost")
	public String postLogin(TestForm form, Model model) {
		
		// Formから受け取った値をModelに登録
		model.addAttribute("form", form);

		// ★セッションへデータを生成
		String sessionData = form.getName() + "==" + form.getPassword();
		// セッションへデータを登録
		session.setAttribute("sessionData", sessionData);

		return getIndex(form,model);
	}

	// GET用のパラメータを受け取る
    // セッションに登録された値を表示する
	@GetMapping("/index")
	public String getIndex(TestForm form,Model model) {
		
		// Modelに値を設定
		model.addAttribute("now", new Date().toString());
		model.addAttribute("form", form);
		
		// ★セッションデータを取得
		String sessionData = (String) session.getAttribute("sessionData");

		if (sessionData != null) {
			// セッションからデータを取得して設定
			model.addAttribute("sessionData", sessionData);
		}
		
		// セッションクリア
		// session.invalidate();
		
		// 返却
		return "index";
	}

	@GetMapping("/session")
	public String getSession(Model model) {
		
		// Modelに値を設定
		model.addAttribute("now", new Date().toString());

		// ★セッションデータを取得
		String sessionData = (String) session.getAttribute("sessionData");
		
		if (sessionData != null) {
			// セッションからデータを取得して設定
			model.addAttribute("sessionData", sessionData);
		}

		
		// 返却
		return "session";
	}
}
