package model;

import java.io.Serializable;
import java.util.UUID;

public class ImageVO implements Serializable{

	private String id; //tenantID_entityType_UUID or tenantID_countervalue // This needs to be hashed so that users won't see it.
	private String imageName;
	private String encodedImage;

	public ImageVO(String id, String imageName, String encodedImage) {
		this.id = id;
		this.imageName = imageName;
		this.encodedImage = encodedImage;
	}

	public ImageVO() {

	}

	public String getId() {
		return id;
	}

	public String getImageName() {
		return imageName;
	}

	public String getEncodedImage() {
		return encodedImage;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setEncodedImage(String encodedImage) {
		this.encodedImage = encodedImage;
	}
}
