// strip all non word chars...
class NiceTitleCodec {
	static encode = { str ->
		return str.toString().replaceAll("\\W", "_")
	}

}