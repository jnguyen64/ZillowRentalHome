package controller;

public class OutputBuilder {
	String salePrice = "Unavailable";
	String address = "";
	String zSalePrice = "";
	String zRentEstimate = "Unavailable";
	String fullText = "";
	
public OutputBuilder()
{
	
}

public OutputBuilder(String fullText1)
{
	fullText = fullText1;
}

public String cleanUpFullText()
{
	 fullText = fullText.substring(fullText.lastIndexOf("and has been priced for sale at"));
	 
		if(fullText.contains("/mo.")){
		 fullText = fullText.substring(0,fullText.lastIndexOf("/mo."));
		}
		else if(fullText.contains("The"))
		{
		 fullText = fullText.substring(0,fullText.lastIndexOf("The"));
		}
		else if (fullText.contains("Another"))
		{
			 fullText = fullText.substring(0,fullText.lastIndexOf("Another"));
		}
		if(!fullText.contains(" . "))
		{
		 salePrice = fullText.substring(fullText.indexOf('$') ,fullText.indexOf('.'));
		}
		return fullText;
}

public void createOutputs()
{
	address = fullText.substring(fullText.indexOf("for") + 3, fullText.indexOf("is"));
	if(fullText.contains("Rent Zes"))
	{
	zSalePrice = fullText.substring(fullText.indexOf("is ") + 3, fullText.indexOf(" and"));
	zRentEstimate = fullText.substring(fullText.lastIndexOf("is $")+ 3, fullText.length());
	}
	else
	{
		zSalePrice = fullText.substring(fullText.indexOf("is ") + 3, fullText.lastIndexOf("."));
	}
	
}

public void createAddress()
{
	address = fullText.substring(fullText.indexOf("for") + 3, fullText.indexOf("is"));

}

/**
 * @param salePrice the salePrice to set
 */
public void setSalePrice(String salePrice) {
	this.salePrice = salePrice;
}

/**
 * @param address the address to set
 */
public void setAddress(String address) {
	this.address = address;
}

/**
 * @param zSalePrice the zSalePrice to set
 */
public void setzSalePrice(String zSalePrice) {
	this.zSalePrice = zSalePrice;
}

/**
 * @param zRentEstimate the zRentEstimate to set
 */
public void setzRentEstimate(String zRentEstimate) {
	this.zRentEstimate = zRentEstimate;
}

/**
 * @param fullText the fullText to set
 */
public void setFullText(String fullText) {
	this.fullText = fullText;
}

/**
 * @return the salePrice
 */
public String getSalePrice() {
	return salePrice;
}

/**
 * @return the address
 */
public String getAddress() {
	return address;
}

/**
 * @return the zSalePrice
 */
public String getzSalePrice() {
	return zSalePrice;
}

/**
 * @return the zRentEstimate
 */
public String getzRentEstimate() {
	return zRentEstimate;
}

/**
 * @return the fullText
 */
public String getFullText() {
	return fullText;
}
}
