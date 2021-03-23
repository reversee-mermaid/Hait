package com.trainspotting.hait.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FileUtil {
	
	@Autowired
	private HttpSession session;
	
	public String getExtension(String fileName) {
		return FilenameUtils.getExtension(fileName);
	}
	
	public String generateFileName(MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String extension = getExtension(file.getOriginalFilename());
		return String.format("profile_%s.%s", uuid, extension);
	}
	
	public String getSavePath(int pk) {
		String realPath = session
							.getServletContext()
							.getRealPath("/resources/img/rstrnt/");
		return realPath + pk;
	}
	
	private FilenameFilter getProfileImgFilter() {
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains("profile_");
			}
		};
	}
	
	public String save(MultipartFile file, int rstrnt_pk) throws Exception {

		String savepath = getSavePath(rstrnt_pk);
		String filename = generateFileName(file);
		
		File dir = new File(savepath);
		
		if(!dir.exists()) dir.mkdirs();
		
		File[] list = dir.listFiles(getProfileImgFilter());
		if(list.length != 0) {
			for (File f : list) {
				f.delete();
			}
		}
		
		file.transferTo(new File(savepath, filename));
		
		return filename;
	}
}
