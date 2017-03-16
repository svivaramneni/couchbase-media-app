package images.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TemporaryFailureException;

import model.ImageVO;



@Service
public class ImageServiceImpl {


	public static ResponseEntity<String> uploadImage(final Bucket bucket, final String imageName, final MultipartFile uploadedFile) {
		try {

			String encodedBase64 = new String(Base64.encodeBase64(uploadedFile.getBytes()));

			JsonObject data = JsonObject.create()
					.put("type", "image")
					.put("name", uploadedFile.getOriginalFilename())
					.put("fileContent", encodedBase64);
			JsonDocument doc = JsonDocument.create(imageName, data); //tenant::entityType_entity_counter
			bucket.insert(doc, PersistTo.ONE);
			JsonObject responseData = JsonObject.create()
					.put("success", true)
					.put("data", data);
			return new ResponseEntity<String>(responseData.toString(), HttpStatus.OK);
		} catch (Exception e) {
			JsonObject responseData = JsonObject.empty()
					.put("success", false)
					.put("failure", "There was an error creating account")
					.put("exception", e.getMessage());
			return new ResponseEntity<String>(responseData.toString(), HttpStatus.OK);
		}
	}

	public static ResponseEntity<String> uploadImage2(final Bucket bucket, final String imageName, final MultipartFile uploadedFile) {
		try {

			String encodedBase64 = new String(Base64.encodeBase64(uploadedFile.getBytes()));

			JsonObject data = JsonObject.create()
					.put("type", "image")
					.put("name", imageName)
					.put("fileContent", encodedBase64);
			JsonDocument doc = JsonDocument.create("t1::quiz_q123_1", data); //tenant::entityType_entity_counter


			bucket.insert(doc, PersistTo.ONE);
			JsonObject responseData = JsonObject.create()
					.put("success", true)
					.put("data", data);
			return new ResponseEntity<String>(responseData.toString(), HttpStatus.OK);
		} catch (Exception e) {
			JsonObject responseData = JsonObject.empty()
					.put("success", false)
					.put("failure", "There was an error creating account")
					.put("exception", e.getMessage());
			return new ResponseEntity<String>(responseData.toString(), HttpStatus.OK);
		}
	}


	public static ResponseEntity<JsonDocument> getImage(final Bucket bucket, final String imageID) {
		JsonDocument doc = bucket.get("t1::quiz_q123_1", JsonDocument.class);
		return new ResponseEntity<JsonDocument>(doc, HttpStatus.OK);
	}

	public static ImageVO getImage2(final Bucket bucket, final String imageID) {
		JsonDocument doc = bucket.get(imageID, 20, TimeUnit.SECONDS);
		JsonObject content = doc.content();
		ImageVO imageVO = new ImageVO();
		imageVO.setImageName(content.getString("name"));
		imageVO.setEncodedImage(content.getString("fileContent"));
		return imageVO;
	}

	public static ImageVO getImage3(final Bucket bucket, final String imageID) {
		JsonDocument doc = null;
		ImageVO imageVO = null;
		try {
			doc = bucket.get("t1::quiz_q123_1", 2L, TimeUnit.SECONDS);
		} catch (RuntimeException e) {
			bucket.getFromReplica("t1::quiz_q123_1", 2L, TimeUnit.SECONDS);
		}
		if (doc!=null) {
			JsonObject content = doc.content();
			imageVO = new ImageVO();
			imageVO.setImageName(content.getString("name"));
			imageVO.setEncodedImage(content.getString("fileContent"));
		}
		return imageVO;
	}
}
