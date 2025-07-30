package utilities;

import base.base_class;
import org.openqa.selenium.By;

import java.util.Properties;

public class ObjectMap extends base_class {
	
	public static Properties OR;
	
	public static By getLocator(String objPropDesc) throws Exception{
		
		String locatorType;
		String locatorValue;
		boolean hasLocatorType = objPropDesc.startsWith("|");
		
		if (hasLocatorType){
			
			locatorType = objPropDesc.substring(1, objPropDesc.indexOf("|",1));
			locatorValue = objPropDesc.substring(objPropDesc.indexOf("|",1)+1);
		}else{
			locatorType="xpath";
			locatorValue=objPropDesc;
		}
		
		
		switch (locatorType.toLowerCase()){
		
		case "id":				return By.id(locatorValue);
		case "name":			return By.name(locatorValue);
		case "classname":		return By.className(locatorValue);
		case "tagname":			return By.tagName(locatorValue);
		case "linktext":		return By.linkText(locatorValue);
		case "partiallinktext":	return By.partialLinkText(locatorValue);
		case "cssselector":		return By.cssSelector(locatorValue);
		case "xpath":			return By.xpath(locatorValue);
		default:				throw new Exception("Unknown locator Type '" + locatorType + "' for Object '" + objPropDesc + "'");
		}
		
		
	}
}

