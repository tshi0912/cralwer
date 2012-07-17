package cf.crawler.domains;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import cf.crawler.tos.ImageUnitTo;

@SuppressWarnings("serial")
@Document
public class ImageUnit implements Serializable {
	
	@Id
	private BigInteger id;
	private String title;
	private String alt;
	private String from;
	@NotEmpty
	private String href;
	private int[] size;
	@NotNull
	private Date recruitedAt;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public int[] getSize() {
		return size;
	}
	public void setSize(int[] size) {
		this.size = size;
	}
	public Date getRecruitedAt() {
		return recruitedAt;
	}
	public void setRecruitedAt(Date recruitedAt) {
		this.recruitedAt = recruitedAt;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(id)
				.toHashCode();
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}else if(!(obj instanceof ImageUnit)){
			return false;
		}
		return new EqualsBuilder()
				.append(id, ((ImageUnit)obj).getId())
				.isEquals();
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append(id)
				.append(alt)
				.toString();
	}
	
	public static ImageUnit from(ImageUnitTo to){
		if(to==null) return null;
		ImageUnit iu = new ImageUnit();
		iu.setAlt(to.getAlt());
		iu.setHref(to.getHref());
		iu.setTitle(to.getTitle());
		iu.setSize(to.getSize());
		iu.setRecruitedAt(new Date());
		iu.setFrom(to.getFrom());
		return iu;
	}
}
