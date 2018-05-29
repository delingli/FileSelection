package com.stkj.freeshare.framework.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.stkj.freeshare.framework.R;

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**

 */
public class FileUtils {

    public static final int TYPE_NORMAL=1;
    public static final int TYPE_APK=2;
    public static final int TYPE_TXT=3;
    public static final int TYPE_DOC=4;
    public static final int TYPE_XLS=5;
    public static final int TYPE_VIDEO=6;
    public static final int TYPE_JPG=7;
    public static final int TYPE_PPT=8;
    /**
     * 根据文件路径获取当前路径下的所有file
     * @param path
     * @return
     */
    public static List<File> getFileListByDirPath(String path ) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        List<File> result = new ArrayList<>();
        if (files == null) {
            return new ArrayList<>();
        }

        for (int i = 0; i < files.length; i++) {
            result.add(files[i]);
        }
        Collections.sort(result, new FileComparator());
        return result;
    }

    public static int getFileType(String path){
        path = path.toLowerCase();
        int type = TYPE_NORMAL;
        if(path.endsWith(".apk")){
            type=TYPE_APK;
        }else if (path.endsWith(".txt")){
            type = TYPE_TXT;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            type = TYPE_DOC;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            type = TYPE_XLS;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            type = TYPE_PPT;
        }else if(path.endsWith(".mp3") || path.endsWith(".mp4")){
            type=TYPE_VIDEO;
        }else if(path.endsWith(".jpg")||path.endsWith(".jpeg")){
         type=TYPE_JPG;
        }
        return type;
    }




    public static String cutLastSegmentOfPath(String path) {
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 获取文件长度
     *
     * @param file 文件
     * @return 文件长度
     */
    public static long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }
    /**
     * 读取文件的修改时间
     *
     * @param f
     * @return
     */
    public static String getModifiedTime(File f) {
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        cal.setTimeInMillis(time);
        // System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
        // 输出：修改时间[2] 2009-08-17 10:32:38
        return formatter.format(cal.getTime());
    }
    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序,同时过滤掉不符合大小要求的文件
     *
     * @param path
     * @return List<File>
     */
    public static List<File> getFileList(String path) {
        List<File> list = FileUtils.getFileListByDirPath(path);
        //进行过滤文件大小
/*        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            File f = (File) iterator.next();
            if (f.isFile()) {
                //获取当前文件大小
                long size = FileUtils.getFileLength(f);
                if (isGreater) {
                    //当前想要留下大于指定大小的文件，所以过滤掉小于指定大小的文件
                    if (size < targetSize) {
                        iterator.remove();
                    }
                } else {
                    //当前想要留下小于指定大小的文件，所以过滤掉大于指定大小的文件
                    if (size > targetSize) {
                        iterator.remove();
                    }
                }
            }
        }*/
        return list;
    }

    /**
     * 获取apk包的信息：版本号，名称，图标等
     * @param absPath apk包的绝对路径
     * @param context
     */
    public static Drawable getApkDrawableInfo(String absPath, Context context) {
        if(absPath!=null&&absPath.endsWith(".apk")){
        PackageManager pm = context.getPackageManager();
        PackageInfo pkgInfo = pm.getPackageArchiveInfo(absPath,PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            /* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
            appInfo.sourceDir = absPath;
            appInfo.publicSourceDir = absPath;
            String appName = pm.getApplicationLabel(appInfo).toString();// 得到应用名
            String packageName = appInfo.packageName; // 得到包名
            String version = pkgInfo.versionName; // 得到版本信息
            /* icon1和icon2其实是一样的 */
            Drawable icon1 = pm.getApplicationIcon(appInfo);// 得到图标信息
            Drawable icon2 = appInfo.loadIcon(pm);
            String pkgInfoStr = String.format("PackageName:%s, Vesion: %s, AppName: %s", packageName, version, appName);
            return icon1;
        }   }
        return null;
    }
    /**通过文件名获取文件图标*/
 /*   public static int getFileIconByPath(String path){
        path = path.toLowerCase();
        int iconId = R.mipmap.unknow_file_icon;
        if(path.endsWith(".apk")){
            iconId = getA
        }else if (path.endsWith(".txt")){
            iconId = R.mipmap.type_txt;
        }else if(path.endsWith(".doc") || path.endsWith(".docx")){
            iconId = R.mipmap.type_doc;
        }else if(path.endsWith(".xls") || path.endsWith(".xlsx")){
            iconId = R.mipmap.type_xls;
        }else if(path.endsWith(".ppt") || path.endsWith(".pptx")){
            iconId = R.mipmap.type_ppt;
        }else if(path.endsWith(".xml")){
            iconId = R.mipmap.type_xml;
        }else if(path.endsWith(".htm") || path.endsWith(".html")){
            iconId = R.mipmap.type_html;
        }
        return iconId;
    }*/
}
