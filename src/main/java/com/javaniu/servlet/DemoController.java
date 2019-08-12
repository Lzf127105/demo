package com.javaniu.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

@Controller
@RequestMapping({ "/demo" })
public class DemoController {

	private static final String path = "/data/www/demo.javaniu.com/doc2html/";

	@RequestMapping(value = { "doc2html" }, method = { RequestMethod.GET })
	public ModelAndView doc2html(
			@RequestParam(value = "file", required = true) final String file,
			HttpServletResponse response) throws Exception,
			FactoryConfigurationError {
		ModelAndView modelAndView = new ModelAndView("doc2html/doc2html");
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		InputStream input = new FileInputStream(path + file);
		HWPFDocument wordDocument = new HWPFDocument(input);
		WordToHtmlConverter wordToHtmlConverter;
		wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory
				.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			public String savePicture(byte[] content, PictureType pictureType,
					String suggestedName, float widthInches, float heightInches) {
				return "http://demo.javaniu.com/doc2html/" + file + "_"
						+ suggestedName;
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		List pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				Picture pic = (Picture) pics.get(i);
				try {
					pic.writeImageContent(new FileOutputStream(path + file
							+ "_" + pic.suggestFullFileName()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();

		String content = new String(outStream.toByteArray());
		modelAndView.addObject("content", content);
		return modelAndView;
	}

	@RequestMapping(value = { "upload" }, method = { RequestMethod.GET })
	public ModelAndView create() throws IOException {
		Doc doc = new Doc();
		ModelAndView modelAndView = new ModelAndView("doc2html/upload");
		modelAndView.addObject("doc", doc);
		return modelAndView;
	}

	@RequestMapping(value = { "upload" }, method = { RequestMethod.POST })
	public void create(@ModelAttribute("doc") Doc doc,
			HttpServletResponse response) throws IOException {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		CommonsMultipartFile file = doc.getFile();
		String dst = path + "" + file.getOriginalFilename();
		file.transferTo(new File(dst));
		response.sendRedirect("/demo/doc2html.action?file="
				+ URLEncoder.encode(file.getOriginalFilename(), "utf-8"));
	}
}
