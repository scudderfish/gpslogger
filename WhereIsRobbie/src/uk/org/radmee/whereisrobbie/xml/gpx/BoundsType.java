//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.07.01 at 03:55:57 PM BST 
//

package uk.org.radmee.whereisrobbie.xml.gpx;

import java.math.BigDecimal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element(name = "boundsType")
public class BoundsType
{

	@Attribute(required = false)
	protected BigDecimal	minlat;
	@Attribute(required = false)
	protected BigDecimal	minlon;
	@Attribute(required = false)
	protected BigDecimal	maxlat;
	@Attribute(required = false)
	protected BigDecimal	maxlon;

	public BigDecimal getMinlat()
	{
		return minlat;
	}

	public void setMinlat(BigDecimal value)
	{
		this.minlat = value;
	}

	public BigDecimal getMinlon()
	{
		return minlon;
	}

	public void setMinlon(BigDecimal value)
	{
		this.minlon = value;
	}

	public BigDecimal getMaxlat()
	{
		return maxlat;
	}

	public void setMaxlat(BigDecimal value)
	{
		this.maxlat = value;
	}

	public BigDecimal getMaxlon()
	{
		return maxlon;
	}

	public void setMaxlon(BigDecimal value)
	{
		this.maxlon = value;
	}

}
