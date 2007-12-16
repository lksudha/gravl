import org.xhtmlrenderer.pdf.ITextRenderer;


class PdfController {

    CacheService cacheService

    def index = {
        redirect(action: show)
    }

    private byte[] buildPdf(url) {

        // OutputStream os = new FileOutputStream(outputFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);
        renderer.layout();
        renderer.createPDF(baos);
        byte[] b = baos.toByteArray();
        return b

    }

    def show = {

        def url = params.url

        // grab pdf bytes from cache if possible
        byte[] b = cacheService.getFromCache("pdfCache", 60, "homePage")
        if (!b) {
            b = buildPdf(url)
            cacheService.putToCache("pdfCache", 60, "homePage", b)
        }

        response.setContentType("application/pdf")
        response.setHeader("Content-disposition", "attachment; filename=blog.pdf")
        response.setContentLength(b.length)
        response.getOutputStream().write(b)

    }
}

