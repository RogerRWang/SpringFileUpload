package com.roger.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;

import app.User;

import com.roger.processer.RequestProcesser;

@Controller
public class FileUploadController {
	
	@Autowired
	private RequestProcesser processer;
	
	//no idea what this GET method handler does
	@RequestMapping(value="/index",method = RequestMethod.GET)
	public String index()
	{
		return "index";
	}
	
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public String result(@RequestParam("file") MultipartFile file, ModelMap model)
	{
		model = processer.doUpload(file,model);
        return "result";
	}
	
	@RequestMapping(value="/subOptions",method=RequestMethod.POST)
	public String OptionResult(@RequestParam MultiValueMap<String,String> allRequestParams,
							   ModelMap model) throws FileNotFoundException
	{
		List<User> userlist = new ArrayList<User>();
		userlist = processer.doWrite(allRequestParams.getFirst("filename"),allRequestParams.get("inputoptions"),
										allRequestParams.get("outputoptions"),allRequestParams.get("sortingoptions"),
										allRequestParams.getFirst("sortcol"));
		model.addAttribute("table",userlist);
		model.addAttribute("filename",allRequestParams.getFirst("filename"));
		return "result";
	}
}
