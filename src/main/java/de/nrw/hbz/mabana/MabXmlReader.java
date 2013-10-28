package de.nrw.hbz.mabana;

import org.culturegraph.mf.stream.reader.XmlReaderBase;
import org.lobid.lodmill.MabXmlHandler;

/**
 * Find the original code at the tests folder under lodmill-rd
 * http://github.com/lobid/lodmill
 * 
 * @author Pascal Christoph
 * 
 */
public class MabXmlReader extends XmlReaderBase {

    @SuppressWarnings("javadoc")
    public MabXmlReader() {
	super(new MabXmlHandler());
    }
}
