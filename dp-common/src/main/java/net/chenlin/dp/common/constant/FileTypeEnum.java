package net.chenlin.dp.common.constant;


/**
 * Created by Jacey on 2017/10/27.
 */
public enum FileTypeEnum {

    ImageEnum(new String[]{"jpg", "png", "gif", "jpeg", "bmp"}),    //gif,jpg,jpeg,bmp,png
    VideoEnum(new String[]{"mp4", "webm", "ogg", "avi", "mkv", "mov", "rmvb", "rm", "wmv"}),  // mp4,webm,ogg
    AudioEnum(new String[]{"mp3", "ogg", "wma"}),;                  // mp3,ogg,wma

    private String[] suffix;

    FileTypeEnum(String[] suffix) {
        this.suffix = suffix;
    }

    public String[] getSuffix() {
        return suffix;
    }

    public Boolean check(String type) {
        for (int i = 0; i < suffix.length; i++) {
            if (suffix[i].equals(type)) {
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        System.out.println(ImageEnum.check("mp4"));
//    }

}
