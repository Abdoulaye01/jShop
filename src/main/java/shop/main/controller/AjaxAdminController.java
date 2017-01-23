package shop.main.controller;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import shop.main.utils.URLUtils;

@Controller
public class AjaxAdminController implements ResourceLoaderAware
{
	private ResourceLoader resourceLoader;
	
	 @Autowired
	    ServletContext context;

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}	
	
	@RequestMapping(value = "a/translit")
	public @ResponseBody String getTranslitURL(@RequestBody String name) {
		System.out.println("request is "+name);
		String result = URLUtils.transliterate(name.substring(6));
		//logic
		return result;
	}
	
			
	 @RequestMapping(value="/uploadMultipleFiles", method=RequestMethod.POST)
	    public /*@ResponseBody*/ String handleFileUpload(@RequestParam String prefix, @RequestParam("files") MultipartFile files[]){
		 System.out.println("here______________________________________"+prefix);
	            try {
	            	String folder = context.getRealPath("/")+ "/resources/uploads/"+prefix;
	                StringBuffer result=new StringBuffer();
	                byte[] bytes=null;
	                result.append("Uploading of File(s) ");
	 
	                for (int i=0;i<files.length;i++) {
	                    if (!files[i].isEmpty()) {

	                    	 try {
	                    		 boolean created = false;

	                    		    try{
	                    		    	File theDir = new File(folder);
	                    		    	theDir.mkdir();
	                    		    	created = true;
	                    		    } 
	                    		    catch(SecurityException se){
	                    		        //handle it
	                    		    }        
	                    		    if(created) {    
	                    		        System.out.println("DIR created");  
	                    		    }
	                    		 int count = new File(folder).listFiles().length;
	                    		 System.out.println("count "+count);
	                    		 //create folder if not exists - products and categories, create id folder, count files in folder
	                    		 File destination = new File(folder+count+files[i].getOriginalFilename().substring(files[i].getOriginalFilename().lastIndexOf('.')));//files[i].getOriginalFilename()
	                    		 System.out.println("--> "+destination);
	                    		 files[i].transferTo(destination);

	                         } catch (Exception e) {
	                             throw new RuntimeException("Product Image saving failed", e);
	                         }
	 	                       
	                    }
	                    else
	                        result.append( files[i].getOriginalFilename() + " Failed. ");
	 
	            }
	                File[] listOfFiles = new File(folder).listFiles();

            	    for (int f = 0; f < listOfFiles.length; f++) {
            	      if (listOfFiles[f].isFile()) {
            	    	  result.append(listOfFiles[f].getName() + " Ok. ") ;
            	      } 
            	    }
	                System.out.println(result.toString());
	                return "redirect:/a/category/"+prefix.substring(prefix.indexOf("/")+1, prefix.length()-1)+"/update";
	 
	            } catch (Exception e) {
	                return "Error Occured while uploading files." + " => " + e.getMessage();
	            }
	 
	    } 
}
