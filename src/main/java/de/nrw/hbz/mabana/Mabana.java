package de.nrw.hbz.mabana;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.culturegraph.mf.morph.Metamorph;
import org.culturegraph.mf.stream.reader.Reader;
import org.culturegraph.mf.stream.source.FileOpener;

/**
 * Mabana counts the occurrence of keys in a metafacture stream
 * 
 * Find the original code at the tests folder under lodmill-rd
 * http://github.com/lobid/lodmill
 * 
 * @author Fabian Steeg
 * @author Jan Schnasse
 * 
 */
public class Mabana {

    @SuppressWarnings({ "serial", "javadoc" })
    public class NoDirectoryException extends RuntimeException {
    }

    private Metamorph metamorph;
    private Reader reader;

    /**
     * @param statsMorphFile
     *            a metafacture morph file
     * @param reader
     *            a reader to read from
     */
    public Mabana(final String statsMorphFile, final Reader reader) {
	metamorph = new Metamorph(Thread.currentThread()
		.getContextClassLoader().getResourceAsStream(statsMorphFile));
	this.reader = reader;
    }

    /**
     * Iterates through all files in the directory. Generates a Stats.class.
     * 
     * @param dataDir
     *            an existing directory
     * @return a Stats.class
     */
    public Stats getStats(String dataDir) {
	File directory = new File(dataDir);
	if (!directory.isDirectory())
	    throw new NoDirectoryException();

	final Stats stats = new Stats();
	reader.setReceiver(metamorph).setReceiver(stats);
	for (File f : directory.listFiles()) {
	    processFile(f);
	}
	reader.closeStream();
	return stats;
    }

    /**
     * @param stats
     *            statistics about field usage
     * @param out
     *            output stream to write into
     * @throws IOException
     *             if something goes wrong
     */
    public static void write(Stats stats, OutputStream out) throws IOException {
	final StringBuilder textileBuilder = new StringBuilder(
		"|*field*|*frequency*|\n");
	List<Entry<String, Integer>> entries = sortedByValuesDescending(stats);
	for (Entry<String, Integer> e : entries) {
	    textileBuilder.append(String.format("|%s|%s|\n", e.getKey(),
		    e.getValue()));
	}

	try {
	    out.write(textileBuilder.toString().getBytes("utf-8"));
	    out.flush();
	} finally {
	    out.close();
	}
    }

    private static List<Entry<String, Integer>> sortedByValuesDescending(
	    final Stats stats) {
	final List<Entry<String, Integer>> entries = new ArrayList<Entry<String, Integer>>(
		stats.map.entrySet());
	Collections.sort(entries, new Comparator<Entry<String, Integer>>() {
	    public int compare(final Entry<String, Integer> entry1,
		    final Entry<String, Integer> entry2) {
		// compare second to first for descending order:
		return entry2.getValue().compareTo(entry1.getValue());
	    }
	});
	return entries;
    }

    private void processFile(File file) {
	FileOpener fileOpener = null;
	fileOpener = new FileOpener();
	fileOpener.setReceiver(reader);
	fileOpener.process(file.getAbsolutePath());
    }
}
