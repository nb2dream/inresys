package net.chenlin.dp.common.constant;

/**
 * 文件类型 常量类
 */
public class FileTypeConstant {

    public static enum ImageEnum {
        //gif,jpg,jpeg,bmp,png
        TYPE_JPG("jpg"),
        TYPE_JPEG("jpeg"),
        TYPE_GIF("gif"),
        TYPE_PNG("png"),
        TYPE_BMP("bmp"),;

        private String suffix;

        ImageEnum(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        /**
         * 判断类型
         * @param type
         * @return
         */
        public static boolean contains(String type){
            for(ImageEnum typeEnum : ImageEnum.values()){
                if(typeEnum.getSuffix().equals(type)){
                    return true;
                }
            }
            return false;
        }
    }

    public static enum VideoEnum {

        // mp4,webm,ogg
        TYPE_MP4("mp4"),
        TYPE_FLV("flv"),
        TYPE_WEBM("webm"),
        TYPE_RMVB("rmvb"),
        TYPE_OGG("ogg"),;

        private String suffix;

        VideoEnum(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        /**
         * 判断类型
         * @param type
         * @return
         */
        public static boolean contains(String type){
            for(VideoEnum typeEnum : VideoEnum.values()){
                if(typeEnum.getSuffix().equals(type)){
                    return true;
                }
            }
            return false;
        }
    }

    public static enum AudioEnum {

        // mp3,ogg,wma
        TYPE_MP3("mp4"),
        TYPE_WMA("wma"),
        TYPE_OGG("ogg"),;

        private String suffix;

        AudioEnum(String suffix) {
            this.suffix = suffix;
        }

        public String getSuffix() {
            return suffix;
        }

        /**
         * 判断类型
         * @param type
         * @return
         */
        public static boolean contains(String type){
            for(AudioEnum typeEnum : AudioEnum.values()){
                if(typeEnum.getSuffix().equals(type)){
                    return true;
                }
            }
            return false;
        }
    }

//    public static void main(String[] args) {
//        String suffix = "mp3";
//        System.out.println(AudioEnum.contains(suffix));
//    }

}
