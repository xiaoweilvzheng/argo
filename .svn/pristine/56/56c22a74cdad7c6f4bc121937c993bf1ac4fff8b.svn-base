package com.jx.argo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;


public class ArgoTool {
	protected static final String namespace;
	  public static final String CONFIG_FOLDER;
	  static{
		  CONFIG_FOLDER = getRootPath()+"/opt/argo/";
		  namespace = initnamespace();
	  }
	  protected static String initnamespace(){
		  String res = "";
		  ClassLoader cl = Thread.currentThread().getContextClassLoader();
	      InputStream inputStream = cl.getResourceAsStream("META-INF/namespace.properties");
		  PropertyResourceBundle pp = null;
	      try {
			pp = new PropertyResourceBundle(inputStream);
			res = (pp.containsKey("namespace")) ? pp.getString("namespace") : "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return res;
	  }
	  protected static String getRootPath()
	  {
	    File file = new File(System.getProperty("user.dir"));
	    String path = file.getAbsolutePath().replace('\\', '/');
	    path = path.substring(0, path.indexOf(47));
	    return path;
	  }
	  public static String getConfigFolder(){
		  return CONFIG_FOLDER;
	  }
	  public static String getNamespace(){
		  return namespace;
	  }
}
