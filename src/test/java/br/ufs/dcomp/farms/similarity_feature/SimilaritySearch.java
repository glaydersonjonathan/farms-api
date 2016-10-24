package br.ufs.dcomp.farms.similarity_feature;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ufs.dcomp.farms.core.Bibtex_Entry_Control;
import br.ufs.dcomp.farms.core.Bibtex_Extraction_Control;
import br.ufs.dcomp.farms.model.entity.Bibtex_String;

public class SimilaritySearch {

	static String main_folder;
	static File[] files;

	@BeforeClass
	public static void init() throws Exception {
		main_folder = System.getProperty("user.dir");
		FileWriter[] fileReferences = new FileWriter[100];
		
		for(int i=0; i < 100; i++) {
			FileWriter file = new FileWriter(main_folder + "\\article"+i+".bib\\");
			fileReferences[i] = file;
			
			PrintWriter fileWriter = new PrintWriter(file);
			fileWriter.printf("@article{article," 
					+ "author  = {Peter Adams}," 
					+ "title   = {The title of the work},"
					+ "journal = {The name of the journal}," 
					+ "year    = 1993," 
					+ "number  = 2," 
					+ "pages   = {201-213},"
					+ "month   = 7," 
					+ "note    = {An optional note}," 
					+ "volume  = 4}");
			
			file.close();
		}
	}

	@AfterClass
	public static void finish() throws Exception {
		files = Bibtex_Entry_Control.Bibtex_files_reader(main_folder);
		for (File file : files) {
			file.delete();
		}
	}

	@Test
	public void testBibtex_Map_Control() throws TokenMgrException, FileNotFoundException, ParseException {
		TreeMap<String, Bibtex_String> Entries = new TreeMap<String, Bibtex_String>();
		Bibtex_String saida = new Bibtex_String();
		files = Bibtex_Entry_Control.Bibtex_files_reader(main_folder);
		List<Bibtex_String> listArticles = new ArrayList<>();
		int i = 0;
		while (true) {
			try {
				saida = Bibtex_Extraction_Control.bibtex_Extraction(files[i]);
			} catch (java.lang.ArrayIndexOutOfBoundsException exception) {
				break;
			} catch (java.lang.NullPointerException error) {
				continue;
			}
			Entries = Bibtex_Entry_Control.Bibtex_Map_Control(saida, Entries);
			listArticles.add(saida);
			i++;
		}
		i=0;
		Iterator<Bibtex_String> iterator = listArticles.iterator();
		while(iterator.hasNext()){
			Bibtex_String article = iterator.next();
			
			System.out.println("Author: "+article.getAuthor() + i++);
			System.out.println("Author: "+article.getJournal() + i++);
		}
		assertNotNull(Entries);
	}
	
	private Integer pickNumber() {
		Random r = new Random();
		
		return r.nextInt(5);
	}

}
