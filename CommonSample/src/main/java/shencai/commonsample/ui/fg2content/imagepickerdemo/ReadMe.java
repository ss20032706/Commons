/***
 * ImagePicker

 Android自定义相册，完全仿微信UI，实现了拍照、图片选择（单选/多选）、 裁剪 、旋转、等功能。

 由于个人时间有限，该项目停止维护

 如果你发现有bug，或者好的建议，可以提merge request，我测试通过后会立即合并并发布新版本，确保该库处于可用的状态。

 该项目参考了：

 https://github.com/pengjianbo/GalleryFinal
 https://github.com/easonline/AndroidImagePicker
 喜欢原作的可以去使用。同时欢迎大家下载体验本项目，如果使用过程中遇到什么问题，欢迎反馈。

 联系方式

 email： liaojeason@126.com
 QQ群： 489873144 Android 格调小窝（点击图标，可以直接加入，建议使用QQ群，邮箱使用较少，可能看的不及时）
 如果遇到问题欢迎在群里提问，个人能力也有限，希望一起学习一起进步。
 演示

 1.用法

 使用前，对于Android Studio的用户，可以选择添加:

 compile 'com.lzy.widget:imagepicker:0.6.1'  //指定版本
 2.功能和参数含义

 温馨提示:目前库中的预览界面有个原图的复选框,暂时只做了UI,还没有做压缩的逻辑

 配置参数	参数含义
 multiMode	图片选着模式，单选/多选
 selectLimit	多选限制数量，默认为9
 showCamera	选择照片时是否显示拍照按钮
 crop	是否允许裁剪（单选有效）
 style	有裁剪时，裁剪框是矩形还是圆形
 focusWidth	矩形裁剪框宽度（圆形自动取宽高最小值）
 focusHeight	矩形裁剪框高度（圆形自动取宽高最小值）
 outPutX	裁剪后需要保存的图片宽度
 outPutY	裁剪后需要保存的图片高度
 isSaveRectangle	裁剪后的图片是按矩形区域保存还是裁剪框的形状，例如圆形裁剪的时候，该参数给true，那么保存的图片是矩形区域，如果该参数给fale，保存的图片是圆形区域
 imageLoader	需要使用的图片加载器，自需要实现ImageLoader接口即可
 3.代码参考

 更多使用，请下载demo参看源代码

 首先你需要继承 com.lzy.imagepicker.loader.ImageLoader 这个接口,实现其中的方法,比如以下代码是使用 Picasso 三方加载库实现的
 public class PicassoImageLoader implements ImageLoader {

@Override
public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
Picasso.with(activity)//
.load(Uri.fromFile(new File(path)))//
.placeholder(R.mipmap.default_image)//
.error(R.mipmap.default_image)//
.resize(width, height)//
.centerInside()//
.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
.into(imageView);
}

@Override
public void clearMemoryCache() {
//这里是清除缓存的方法,根据需要自己实现
}
}
 然后配置图片选择器，一般在Application初始化配置一次就可以,这里就需要将上面的图片加载器设置进来,其余的配置根据需要设置
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_image_picker);

 ImagePicker imagePicker = ImagePicker.getInstance();
 imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
 imagePicker.setShowCamera(true);  //显示拍照按钮
 imagePicker.setCrop(true);        //允许裁剪（单选才有效）
 imagePicker.setSaveRectangle(true); //是否按矩形区域保存
 imagePicker.setSelectLimit(9);    //选中数量限制
 imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
 imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
 imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
 imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
 imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
 }
 以上配置完成后，在适当的方法中开启相册，例如点击按钮时
 public void onClick(View v) {
 Intent intent = new Intent(this, ImageGridActivity.class);
 startActivityForResult(intent, IMAGE_PICKER);
 }
 }
 如果你想直接调用相机
 Intent intent = new Intent(this, ImageGridActivity.class);
 intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
 startActivityForResult(intent, REQUEST_CODE_SELECT);
 重写onActivityResult方法,回调结果
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 super.onActivityResult(requestCode, resultCode, data);
 if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
 if (data != null && requestCode == IMAGE_PICKER) {
 ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
 MyAdapter adapter = new MyAdapter(images);
 gridView.setAdapter(adapter);
 } else {
 Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
 }
 }
 }
 */
