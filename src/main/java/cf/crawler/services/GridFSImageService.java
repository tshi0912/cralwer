package cf.crawler.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;


public class GridFSImageService implements ImageService {

	@Autowired
	GridFsOperations gridFsOperations;
	
	public String put(File image) throws FileNotFoundException {
		GridFSFile file = gridFsOperations.store(new FileInputStream(image), 
				image.getName());
		Object id = file.getId();
		return id.toString();
	}

	public void get(String resId, OutputStream os) throws IOException {
		GridFSDBFile dbFile =  gridFsOperations.findOne(
				new Query(GridFsCriteria.where("id").is(resId)));
		if(dbFile!=null){
			dbFile.writeTo(os);
		}
	}
}
