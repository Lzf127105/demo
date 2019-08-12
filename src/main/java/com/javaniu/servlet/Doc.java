package com.javaniu.servlet;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Doc {
	private CommonsMultipartFile file;

	public CommonsMultipartFile getFile() {
		return file;
	}

	public void setFile(CommonsMultipartFile file) {
		this.file = file;
	}
}
