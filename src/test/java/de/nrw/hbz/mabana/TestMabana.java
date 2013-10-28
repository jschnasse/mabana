package de.nrw.hbz.mabana;

import java.io.FileOutputStream;
import java.io.IOException;

import org.antlr.runtime.RecognitionException;

@SuppressWarnings("javadoc")
public class TestMabana {

    public void exampleUsage() throws IOException, RecognitionException {

	String inputDir = "/home/jan/Desktop/mabxml";
	String outputFile = "/home/jan/Desktop/mabxml.stats";

	Mabana mabana = new Mabana("default_morph-stats.xml",
		new MabXmlReader());
	Stats stats = mabana.getStats(inputDir);

	Mabana.write(stats, new FileOutputStream(outputFile));
	Mabana.write(stats, System.out);

    }
}
