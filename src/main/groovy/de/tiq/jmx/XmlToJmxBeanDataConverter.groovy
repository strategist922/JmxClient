package de.tiq.jmx

import javax.management.ObjectName

class XmlToJmxBeanDataConverter {
	
	String pathToFile;
	
	XmlToJmxBeanDataConverter(String pathToFile) {
		this.pathToFile = pathToFile
	}

	List<JmxMBeanData> convertTo(){
		def result = []
		def xmlContent = new File(pathToFile).getText('UTF-8')
		def jmxMBeanDataList = new XmlParser().parseText(xmlContent)
		jmxMBeanDataList.each { node ->
			def className = node.get("className").text()
			def associatedMethodName = node.get("associatedObjectName").text()
			//the get method of a node returns a nodelist, so we need to get the first value of it with [0]
			def applyableMethods = node.get("applyableMethods")[0].children().collect {it.text()}	
			result << new JmxMBeanData(className, 
							 		   new ObjectName(associatedMethodName), 
							 		   applyableMethods) 
		}
		return result
	}
}