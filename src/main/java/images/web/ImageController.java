package images.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.couchbase.client.java.Bucket;

import images.service.ImageServiceImpl;
import model.ImageVO;

/**
 * Created by I831921 on 6/1/2016.
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {

	private final Bucket bucket;

	@Autowired
	public ImageController(Bucket bucket) {
		this.bucket = bucket;
	}

	@RequestMapping(value="/v1/image-upload", method=RequestMethod.POST)
	public Object uploadImage(@RequestParam(value = "file", required = true) final MultipartFile file, @RequestParam(value = "fileId", required = true) final String fileId) {
		System.out.println("------------Image upload request received------------: " + file.getOriginalFilename());
		StopWatch sw = new StopWatch();
		sw.start();
		Object img = ImageServiceImpl.uploadImage(bucket, fileId, file);
		sw.stop();
		System.out.println(sw.shortSummary());
		return img;
	}

	@RequestMapping(value="/v1/image/{imageID}", method=RequestMethod.GET)
	public ImageVO getImage(@PathVariable(value = "imageID") final String imageID) {
		System.out.println("-------------------Image ID to retrieve :----" + imageID);

		StopWatch sw = new StopWatch();
		sw.start();
		ImageVO img = ImageServiceImpl.getImage2(bucket, imageID);
		sw.stop();
		System.out.println(sw.shortSummary());

		return img;
	}
}




