package github;

import java.util.Collections;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;


public class GistC {

	private Gist gist_ = new Gist();
	private GistFile file_ = new GistFile();
	private String description_;
	private boolean public_;
	private String content_;
	private String name_;
	
	//Constructor por defecto de la clase GistC
	public GistC(){}
	
	//Constructor parametrizado de la clase GistC
	public GistC(String description, boolean isPublic, String content, String name){
		
		description_=description;
		public_=isPublic;
		content_=content;
		name_=name;
		
	}
	//Funcion que devuelve el gist
	public Gist build(){
		
		gist_.setDescription(description_);
		gist_.setPublic(public_);
		file_.setContent(content_);
		file_.setFilename(name_);
		gist_.setFiles(Collections.singletonMap(name_, file_));
		
		return gist_;
	}
	

}
