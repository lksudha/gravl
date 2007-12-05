import net.sf.textile4j.Textile

// Matt Secoske's Waycool Textile codec
// http://blog.secosoft.net/2007/02/24/using-textile-with-grails/

class WikiCodec {
	static encode = { str ->
		return new Textile().process(str)
	}
   
}

