package com.coco.tango.surfing.chat.bootstrap.server.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Http 请求处理
 *
 * @author ckli01
 * @date 2019-06-24
 */
public abstract class AbstractHttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        httpDoMessage(ctx, request);

//        URI uri = new URI(request.getUri());
//        String uriStr = uri.getPath();
//
//        System.out.println(request.getMethod() + " request received");
//
//        if (request.getMethod() == HttpMethod.GET) {
//            serveFile(ctx, request); // user requested a file, serve it
//        } else if (request.getMethod() == HttpMethod.POST) {
//            uploadFile(ctx, request); // user requested to upload file, handle request
//        } else {
//             unknown request, send error message
//            System.out.println(request.getMethod() + " request received, sending 405");
//            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
//        }

    }


    protected abstract void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest request);


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
//
//    private void serveFile(ChannelHandlerContext ctx, FullHttpRequest request) {
//
//        // decode the query string
//        QueryStringDecoder decoderQuery = new QueryStringDecoder(request.getUri());
//        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
//
//        // get the requested file name
//        String fileName = "";
//        try {
//            fileName = uriAttributes.get(FILE_QUERY_PARAM).get(0);
//        } catch (Exception e) {
//            sendError(ctx, HttpResponseStatus.BAD_REQUEST, FILE_QUERY_PARAM + " query param not found");
//            return;
//        }
//
//        // start serving the requested file
//        sendFile(ctx, fileName, request);
//    }
//
//    /**
//     * This method reads the requested file from disk and sends it as response.
//     * It also sets proper content-type of the response header
//     *
//     * @param fileName name of the requested file
//     */
//    private void sendFile(ChannelHandlerContext ctx, String fileName, FullHttpRequest request) {
//        File file = new File(BASE_PATH + fileName);
//        if (file.isDirectory() || file.isHidden() || !file.exists()) {
//            sendError(ctx, NOT_FOUND);
//            return;
//        }
//
//        if (!file.isFile()) {
//            sendError(ctx, FORBIDDEN);
//            return;
//        }
//
//        RandomAccessFile raf;
//
//        try {
//            raf = new RandomAccessFile(file, "r");
//        } catch (FileNotFoundException fnfe) {
//            sendError(ctx, NOT_FOUND);
//            return;
//        }
//
//        long fileLength = 0;
//        try {
//            fileLength = raf.length();
//        } catch (IOException ex) {
//            Logger.getLogger(DefaultHttpHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
//        setContentLength(response, fileLength);
//        setContentTypeHeader(response, file);
//
//        //setDateAndCacheHeaders(response, file);
//        if (isKeepAlive(request)) {
//            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//        }
//
//        // Write the initial line and the header.
//        ctx.write(response);
//
//        // Write the content.
//        ChannelFuture sendFileFuture;
//        DefaultFileRegion defaultRegion = new DefaultFileRegion(raf.getChannel(), 0, fileLength);
//        sendFileFuture = ctx.write(defaultRegion);
//
//        // Write the end marker
//        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
//
//        // Decide whether to close the connection or not.
//        if (!isKeepAlive(request)) {
//            // Close the connection when the whole content is written out.
//            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
//        }
//    }

//    /**
//     * This will set the content types of files. If you want to support any
//     * files add the content type and corresponding file extension here.
//     *
//     * @param response
//     * @param file
//     */
//    private static void setContentTypeHeader(HttpResponse response, File file) {
//        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
//        mimeTypesMap.addMimeTypes("image png tif jpg jpeg bmp");
//        mimeTypesMap.addMimeTypes("text/plain txt");
//        mimeTypesMap.addMimeTypes("application/pdf pdf");
//
//        String mimeType = mimeTypesMap.getContentType(file);
//
//        response.headers().set(CONTENT_TYPE, mimeType);
//    }

    private void sendOptionsRequestResponse(ChannelHandlerContext ctx) {
        HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


}
