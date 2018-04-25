package com.sh.shvideolibrary.compression;

import android.app.Activity;
import android.os.Build;

import java.io.File;

/**
 * Created by zhush on 2017/1/14.
 * E-mail zhush@jerei.com
 * PS
 */
public class CompressorUtils {

	/**
	 * 视频压缩
	 * @param currentInputVideoPath 输入视频源地址
	 * @param currentOutputVideoPath 输出视频地址
	 * @param activity
	 * @param compressListener
	 */
	public static void Compressor(final String currentInputVideoPath, final String currentOutputVideoPath, final Activity activity, final CompressListener compressListener) {
		final Compressor mCompressor = new Compressor(activity);
		mCompressor.loadBinary(new InitListener() {
			@Override
			public void onLoadSuccess() {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
					compressListener.onExecFail("暂时不支持android7.0以上版本");
					return;
				}

//        String  command ="-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast " +
//                "-crf 24 -acodec aac -ar 44100 -ac 2 -b:a 96k -s 640x480 -aspect 16:9 " + currentOutputVideoPath;;
				String command = "-y -i " + currentInputVideoPath + " -strict -2 -vcodec libx264 -preset ultrafast -acodec aac -ar 44100 -ac 1 " +
						"-b:a 72k -s 480x480 -aspect 1:1 -r 24 " + currentOutputVideoPath;
				File mFile = new File(currentOutputVideoPath);
				if (mFile.exists()) {
					mFile.delete();
				}
				mCompressor.execCommand(command, compressListener);
			}

			@Override
			public void onLoadFail(String reason) {
			}
		});
	}
}
