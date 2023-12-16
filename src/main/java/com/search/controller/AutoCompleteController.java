package com.search.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.search.service.SearchKeywordService;
import com.search.util.Jaso;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AutoCompleteController {

	private final Jaso jaso;
	private final SearchKeywordService searchKeywordService;

	@RequestMapping("/autocomplete")
	public List<String> autoComplete(String keyword) throws Exception {
		String keyword_spell = jaso.hangulToJaso(keyword);

		List<String> list = searchKeywordService.searchKeywordList(keyword_spell);

		return list;
	}
}
