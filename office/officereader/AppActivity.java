package com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader;

import static com.documentreader.docs.pdf.word.document.reader.viewer.viewer.helper.StorageConfig.KEY_SCRRENSHOT;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.documentreader.docs.pdf.word.document.reader.viewer.R;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.AllConstant;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.Database.FavoriteDocumentsDatabse;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.GoogleAds.GoogleAds;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.GoogleAds.RandomAdListener;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.helper.ScreenShotFile;
import com.documentreader.docs.pdf.word.document.reader.viewer.viewer.helper.StorageConfig;
//import com.documentreader.docs.pdf.word.document.reader.viewer.R;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.common.IOfficeToPicture;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.constant.EventConstant;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.constant.MainConstant;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.constant.wp.WPViewConstant;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.macro.DialogListener;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.AImageButton;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.AImageCheckButton;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.AToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.CalloutToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.PDFToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.PGToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.SSToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.beans.WPToolsbar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.officereader.database.DBService;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.res.ResKit;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.ss.sheetbar.SheetBar;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.FileKit;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.IControl;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.IMainFrame;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.MainControl;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.beans.pagelist.IPageListViewListener;
import com.documentreader.docs.pdf.word.document.reader.viewer.office.system.dialog.ColorPickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AppActivity extends Activity implements IMainFrame {

    private RelativeLayout Rv_main;
    private RelativeLayout RV_toolbar;
    private ImageView img_back;
    private TextView txt_file_name;
    private ImageView img_find;
    private ImageView img_share;
    private ImageView img_favorite;
    private ImageView img_screenShot;
    private ImageView img_backWard;
    private ImageView img_forWard;
    private RelativeLayout RV_search;
    private EditText edt_search;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_app, null, false);

        Rv_main = view.findViewById(R.id.Rv_main);

        RV_toolbar = view.findViewById(R.id.RV_toolbar);

        img_back = view.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txt_file_name = view.findViewById(R.id.txt_file_name);
        txt_file_name.setSelected(true);

        RV_search = view.findViewById(R.id.RV_search);

        edt_search = view.findViewById(R.id.edt_search);

        img_backWard = view.findViewById(R.id.img_backWard);

        img_forWard = view.findViewById(R.id.img_forWard);

        img_find = view.findViewById(R.id.img_find);
        img_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onShowSearch();
            }
        });

        img_share = view.findViewById(R.id.img_share);
        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileShare();
            }
        });

        favoriteDocumentsDatabse = new FavoriteDocumentsDatabse(this);

        img_favorite = view.findViewById(R.id.img_favorite);
        img_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleAds.getInstance().showCounterInterstitialAd(AppActivity.this, new RandomAdListener() {
                    @Override
                    public void onClick() {
                        bookMarkClick();
                    }
                });
            }
        });

        img_screenShot = view.findViewById(R.id.img_screenShot);
        img_screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleAds.getInstance().showCounterInterstitialAd(AppActivity.this, new RandomAdListener() {
                    @Override
                    public void onClick() {
                        takeScreenshot();
                        Toast.makeText(AppActivity.this, "ScreenShot Captured", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        control = new MainControl(this);
        appFrame = new AppFrame(getApplicationContext());

        appFrame.post(new Runnable() {
            /**
             */
            public void run() {
                init();
                initMarked();
            }
        });
        control.setOffictToPicture(new IOfficeToPicture() {
            public Bitmap getBitmap(int componentWidth, int componentHeight) {
                if (componentWidth == 0 || componentHeight == 0) {
                    return null;
                }
                if (bitmap == null
                        || bitmap.getWidth() != componentWidth
                        || bitmap.getHeight() != componentHeight) {
                    // custom picture size
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                    //bitmap = Bitmap.createBitmap(800, 600,  Config.ARGB_8888);
                    // 
                    bitmap = Bitmap.createBitmap((int) (componentWidth), (int) (componentHeight), Config.ARGB_8888);
                }
                return bitmap;
                //return null;
            }

            public void callBack(Bitmap bitmap) {
                saveBitmapToFile(bitmap);
            }

            //
            private Bitmap bitmap;

            @Override
            public void setModeType(byte modeType) {
                // TODO Auto-generated method stub

            }

            @Override
            public byte getModeType() {
                // TODO Auto-generated method stub
                return VIEW_CHANGE_END;
            }

            @Override
            public boolean isZoom() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void dispose() {
                // TODO Auto-generated method stub

            }
        });
        /* app theme is here */
        setTheme(control.getSysKit().isVertical(this) ? R.style.title_background_vertical : R.style.title_background_horizontal);
        appFrame.addView(view);
        setContentView(appFrame);
    }

    private void onShowSearch() {
        RV_search.setVisibility(View.VISIBLE);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void takeScreenshot() {

        try {
            bitmap = Bitmap.createBitmap(app.getWidth(), app.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            app.draw(canvas);

            File imageFile = new File(StorageConfig.getScreenShotFolder(), KEY_SCRRENSHOT + String.valueOf(Calendar.getInstance().getTimeInMillis() + ".jpg"));

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            screenShotFileArrayList.add(new ScreenShotFile(bitmap, imageFile.getAbsolutePath()));
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    private void saveBitmapToFile(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (tempFilePath == null) {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                tempFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            }
            File file = new File(tempFilePath + File.separatorChar + "tempPic");
            if (!file.exists()) {
                file.mkdir();
            }
            tempFilePath = file.getAbsolutePath();
        }

        File file = new File(tempFilePath + File.separatorChar + "export_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();

        } catch (IOException e) {
        } finally {
            //bitmap.recycle();
        }
    }

    public void setButtonEnabled(boolean enabled) {
        if (fullscreen) {
            pageUp.setEnabled(enabled);
            pageDown.setEnabled(enabled);
            penButton.setEnabled(enabled);
            eraserButton.setEnabled(enabled);
            settingsButton.setEnabled(enabled);
        }
    }

    protected void onPause() {
        super.onPause();

        Object obj = control.getActionValue(EventConstant.PG_SLIDESHOW, null);
        if (obj != null && (Boolean) obj) {
            wm.removeView(pageUp);
            wm.removeView(pageDown);
            wm.removeView(penButton);
            wm.removeView(eraserButton);
            wm.removeView(settingsButton);
        }
    }

    protected void onResume() {
        super.onResume();
        Object obj = control.getActionValue(EventConstant.PG_SLIDESHOW, null);
        if (obj != null && (Boolean) obj) {
            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wm.addView(penButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height;
            wm.addView(eraserButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height * 2;
            wm.addView(settingsButton, wmParams);

            wmParams.gravity = Gravity.LEFT | Gravity.CENTER;
            wmParams.x = MainConstant.GAP;
            wmParams.y = 0;
            wm.addView(pageUp, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.CENTER;
            wm.addView(pageDown, wmParams);
        }
    }

    /**
     *
     */
    public void onBackPressed() {
        GoogleAds.getInstance().showCounterInterstitialAd(AppActivity.this, new RandomAdListener() {
            @Override
            public void onClick() {
                if (isSearchbarActive()) {
                    showSearchBar(false);
                    updateToolsbarStatus();
                } else {
                    Object obj = control.getActionValue(EventConstant.PG_SLIDESHOW, null);
                    if (obj != null && (Boolean) obj) {
                        fullScreen(false);
                        //
                        control.actionEvent(EventConstant.PG_SLIDESHOW_END, null);
                    } else {
                        if (control.getReader() != null) {
                            control.getReader().abortReader();
                        }
                        if (marked != dbService.queryItem(MainConstant.TABLE_STAR, filePath)) {
                            if (!marked) {
                                dbService.deleteItem(MainConstant.TABLE_STAR, filePath);
                            } else {
                                dbService.insertStarFiles(MainConstant.TABLE_STAR, filePath);
                            }

                            Intent intent = new Intent();
                            intent.putExtra(MainConstant.INTENT_FILED_MARK_STATUS, marked);
                            setResult(RESULT_OK, intent);
                        }
                        if (control != null && control.isAutoTest()) {
                            System.exit(0);
                        } else {
                            finish();
                        }
                    }
                }
            }
        });
    }

    /**
     *
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (isSearchbarActive()) {
            searchBar.onConfigurationChanged(newConfig);
        }
    }

    /**
     *
     */
    protected void onDestroy() {
        dispose();
        super.onDestroy();
    }

    /**
     * (non-Javadoc)
     *
     * @see IMainFrame#showProgressBar(boolean)
     */
    public void showProgressBar(boolean visible) {
        setProgressBarIndeterminateVisibility(visible);
    }

    /**
     *
     */
    private void init() {
        //
        toast = Toast.makeText(getApplicationContext(), "", 0);
        //
        Intent intent = getIntent();
        dbService = new DBService(getApplicationContext());

        filePath = intent.getStringExtra(MainConstant.INTENT_FILED_FILE_PATH);
        fileName = intent.getStringExtra("FileName");
        fileType = intent.getStringExtra("FileType");
        fileIcon = intent.getIntExtra("FileIcon", 0);
        fileSize = intent.getStringExtra("FileSize");

        // 文件关联打开文件
        if (filePath == null) {
            this.filePath = intent.getDataString();
            int index = getFilePath().indexOf(":");
            if (index > 0) {
                filePath = filePath.substring(index + 3);
            }
            filePath = Uri.decode(filePath);
        }

        // 显示打开文件名称
        int index = filePath.lastIndexOf(File.separator);
        if (index > 0) {
            txt_file_name.setText(filePath.substring(index + 1));
        } else {
            txt_file_name.setText(filePath);
        }


        boolean isSupport = FileKit.instance().isSupport(filePath);
        //写入本地数据库
        if (isSupport) {
            dbService.insertRecentFiles(MainConstant.TABLE_RECENT, filePath);
        }
        // create view
        createView();
        // open file
        control.openFile(filePath);
        // initialization marked
    }

    /**
     * true: show message when zooming
     * false: not show message when zooming
     *
     * @return
     */
    public boolean isShowZoomingMsg() {
        return true;
    }

    /**
     * true: pop up dialog when throw err
     * false: not pop up dialog when throw err
     *
     * @return
     */
    public boolean isPopUpErrorDlg() {
        return true;
    }

    /**
     *
     */
    private void createView() {
        // word
        String file = filePath.toLowerCase();
        Log.e("TAG", "createView: " + filePath);
        if (file.endsWith(MainConstant.FILE_TYPE_DOC) || file.endsWith(MainConstant.FILE_TYPE_DOCX)
                || file.endsWith(MainConstant.FILE_TYPE_DOT)
                || file.endsWith(MainConstant.FILE_TYPE_DOTX)
                || file.endsWith(MainConstant.FILE_TYPE_DOTM)) {
            applicationType = MainConstant.APPLICATION_TYPE_WP;
            toolsbar = new WPToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.word_file));
        }
        // text
        else if (file.endsWith(MainConstant.FILE_TYPE_TXT)) {
            applicationType = MainConstant.APPLICATION_TYPE_TXT;
            toolsbar = new WPToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.text_file));
        }
        // excel
        else if (file.endsWith(MainConstant.FILE_TYPE_XLS)
                || file.endsWith(MainConstant.FILE_TYPE_XLSX)
                || file.endsWith(MainConstant.FILE_TYPE_XLT)
                || file.endsWith(MainConstant.FILE_TYPE_XLTX)
                || file.endsWith(MainConstant.FILE_TYPE_XLTM)
                || file.endsWith(MainConstant.FILE_TYPE_XLSM)) {
            applicationType = MainConstant.APPLICATION_TYPE_SS;
            toolsbar = new SSToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.excel_file));
        }
        // PowerPoint
        else if (file.endsWith(MainConstant.FILE_TYPE_PPT)
                || file.endsWith(MainConstant.FILE_TYPE_PPTX)
                || file.endsWith(MainConstant.FILE_TYPE_POT)
                || file.endsWith(MainConstant.FILE_TYPE_PPTM)
                || file.endsWith(MainConstant.FILE_TYPE_POTX)
                || file.endsWith(MainConstant.FILE_TYPE_POTM)) {
            applicationType = MainConstant.APPLICATION_TYPE_PPT;
            toolsbar = new PGToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.ppt_file));
        }
        // PDF document
        else if (file.endsWith(MainConstant.FILE_TYPE_PDF)) {
            applicationType = MainConstant.APPLICATION_TYPE_PDF;
            toolsbar = new PDFToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.pdf_file));
        } else {
            applicationType = MainConstant.APPLICATION_TYPE_WP;
            toolsbar = new WPToolsbar(getApplicationContext(), control);
            Rv_main.setBackgroundColor(getResources().getColor(R.color.word_file));
        }
        // 添加tool bar
        appFrame.addView(toolsbar);
    }

    /**
     * @return
     */
    private boolean isSearchbarActive() {
        if (appFrame == null || isDispose) {
            return false;
        }
        int count = appFrame.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = appFrame.getChildAt(i);
            if (v instanceof FindToolBar) {
                return v.getVisibility() == View.VISIBLE;
            }
        }
        return false;
    }

    /**
     * show toolbar or search bar
     *
     * @param show
     */
    public void showSearchBar(boolean show) {
        //show search bar
        if (show) {
            if (searchBar == null) {
                searchBar = new FindToolBar(this, control);
                appFrame.addView(searchBar, 0);
            }
            searchBar.setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.GONE);
        }
        // hide search bar
        else {
            if (searchBar != null) {
                searchBar.setVisibility(View.GONE);
            }
            toolsbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * show toolbar or search bar
     *
     * @param show
     */
    public void showCalloutToolsBar(boolean show) {
        //show callout bar
        if (show) {
            if (calloutBar == null) {
                calloutBar = new CalloutToolsbar(getApplicationContext(), control);
                appFrame.addView(calloutBar, 0);
            }
            calloutBar.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.CHECK);
            calloutBar.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK);
            calloutBar.setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.GONE);
        }
        // hide callout bar
        else {
            if (calloutBar != null) {
                calloutBar.setVisibility(View.GONE);
            }
            toolsbar.setVisibility(View.VISIBLE);
        }
    }

    public void setPenUnChecked() {
        if (fullscreen) {
            penButton.setState(AImageCheckButton.UNCHECK);
            penButton.postInvalidate();
        } else {
            calloutBar.setCheckState(EventConstant.APP_PEN_ID, AImageCheckButton.UNCHECK);
            calloutBar.postInvalidate();
        }
    }

    public void setEraserUnChecked() {
        if (fullscreen) {
            eraserButton.setState(AImageCheckButton.UNCHECK);
            eraserButton.postInvalidate();
        } else {
            calloutBar.setCheckState(EventConstant.APP_ERASER_ID, AImageCheckButton.UNCHECK);
            calloutBar.postInvalidate();
        }
    }

    /**
     * set the find back button and find forward button state
     *
     * @param state
     */
    public void setFindBackForwardState(boolean state) {
        if (isSearchbarActive()) {
            searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, state);
            searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, state);
        }
    }

    /**
     * 发送邮件
     */
    public void fileShare() {
        Uri uriForFile = FileProvider.getUriForFile(this, "com.alldocumentreader.pdfreader.document.viewer.fileProvider", new File(filePath));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.addFlags(1);
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        intent.setType("*/*");
        startActivity(Intent.createChooser(intent, "Share File using"));
    }

    /**
     * @return
     */
    public void initMarked() {
        if (favoriteDocumentsDatabse.checkIfRecordExist(AllConstant.generateId(filePath.toLowerCase()))) {
            img_favorite.setImageResource(R.drawable.ic_filled_heart);
            img_favorite.setColorFilter(Color.WHITE);
        } else {
            img_favorite.setImageResource(R.drawable.ic_favorite);
        }
    }

    public void bookMarkClick() {
        if (favoriteDocumentsDatabse.checkIfRecordExist(AllConstant.generateId(filePath))) {
            favoriteDocumentsDatabse.removeFavoriteDocument(AllConstant.generateId(filePath));
            img_favorite.setImageResource(R.drawable.ic_favorite);
            Toast.makeText(AppActivity.this, "Favorite Document Removed!", Toast.LENGTH_SHORT).show();
        } else {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm aa dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            System.out.println(dateFormat.format(cal.getTime()));
            Log.e("dateFormat_TAG", "onItemClick: " + dateFormat.format(cal.getTime()));

            String name = fileName;
            String dateTime = String.valueOf(dateFormat.format(cal.getTime()));
            String size = fileSize;
            String image = String.valueOf(fileIcon);
            String path = filePath;
            String type = fileType;

            img_favorite.setImageResource(R.drawable.ic_filled_heart);
            img_favorite.setColorFilter(Color.WHITE);
            favoriteDocumentsDatabse.addFavoriteDocuments(name, dateTime, size, image, path, type);
            Toast.makeText(AppActivity.this, "Favorite Document!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return
     */
    private void markFile() {
        marked = !marked;
    }

    public void resetTitle(String title) {
        if (title != null) {
            this.setTitle(title);
        }
    }

    public FindToolBar getSearchBar() {
        return searchBar;
    }

    /**
     *
     */
    public Dialog onCreateDialog(int id) {
        return control.getDialog(this, id);
    }

    /**
     * 更新工具条的状态
     */
    public void updateToolsbarStatus() {
        if (appFrame == null || isDispose) {
            return;
        }
        int count = appFrame.getChildCount();
        for (int i = 0; i < count; i++) {
            View v = appFrame.getChildAt(i);
            if (v instanceof AToolsbar) {
                ((AToolsbar) v).updateStatus();
            }
        }
    }

    /**
     *
     */
    public IControl getControl() {
        return this.control;
    }

    /**
     *
     */
    public int getApplicationType() {
        return this.applicationType;
    }

    /**
     * @return Returns the filePath.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     *
     */
    public Activity getActivity() {
        return this;
    }

    /**
     * do action，this is method don't call <code>control.actionEvent</code> method, Easily lead to infinite loop
     *
     * @param actionID action ID
     * @param obj      acValue
     * @return True if the listener has consumed the event, false otherwise.
     */
    public boolean doActionEvent(int actionID, Object obj) {
        try {
            switch (actionID) {
                case EventConstant.SYS_RESET_TITLE_ID:
                    txt_file_name.setText((String) obj);
                    break;

                case EventConstant.SYS_ONBACK_ID:
                    onBackPressed();
                    break;

                case EventConstant.SYS_UPDATE_TOOLSBAR_BUTTON_STATUS: //update toolsbar state
                    updateToolsbarStatus();
                    break;

                case EventConstant.SYS_HELP_ID: //show help net
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources()
                            .getString(R.string.sys_url_wxiwei)));
                    startActivity(intent);
                    break;

                case EventConstant.APP_FIND_ID: //show search bar
                    showSearchBar(true);
                    break;

                case EventConstant.APP_SHARE_ID: //share file
//                    fileShare();
                    break;

                case EventConstant.FILE_MARK_STAR_ID: //mark
//                    markFile();
                    break;

                case EventConstant.APP_FINDING:
                    String content = ((String) obj).trim();
                    if (content.length() > 0 && control.getFind().find(content)) {
                        setFindBackForwardState(true);
                    } else {
                        setFindBackForwardState(false);
                        toast.setText(getLocalString("DIALOG_FIND_NOT_FOUND"));
                        toast.show();
                    }
                    break;

                case EventConstant.APP_FIND_BACKWARD:
                    if (!control.getFind().findBackward()) {
                        searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, false);
                        toast.setText(getLocalString("DIALOG_FIND_TO_BEGIN"));
                        toast.show();
                    } else {
                        searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, true);
                    }
                    break;

                case EventConstant.APP_FIND_FORWARD:
                    if (!control.getFind().findForward()) {
                        searchBar.setEnabled(EventConstant.APP_FIND_FORWARD, false);
                        toast.setText(getLocalString("DIALOG_FIND_TO_END"));
                        toast.show();
                    } else {
                        searchBar.setEnabled(EventConstant.APP_FIND_BACKWARD, true);
                    }
                    break;

                case EventConstant.SS_CHANGE_SHEET:
                    bottomBar.setFocusSheetButton((Integer) obj);
                    break;

                case EventConstant.APP_DRAW_ID:
                    showCalloutToolsBar(true);
                    control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTDRAW);
                    appFrame.post(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null);

                        }
                    });

                    break;

                case EventConstant.APP_BACK_ID:
                    showCalloutToolsBar(false);
                    control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    break;

                case EventConstant.APP_PEN_ID:
                    if ((Boolean) obj) {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTDRAW);
                        setEraserUnChecked();
                        appFrame.post(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                control.actionEvent(EventConstant.APP_INIT_CALLOUTVIEW_ID, null);

                            }
                        });
                    } else {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    }
                    break;

                case EventConstant.APP_ERASER_ID:
                    if ((Boolean) obj) {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_CALLOUTERASE);
                        setPenUnChecked();
                    } else {
                        control.getSysKit().getCalloutManager().setDrawingMode(MainConstant.DRAWMODE_NORMAL);
                    }
                    break;

                case EventConstant.APP_COLOR_ID:
                    ColorPickerDialog dlg = new ColorPickerDialog(this, control);
                    dlg.show();
                    dlg.setOnDismissListener(new OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            setButtonEnabled(true);
                        }
                    });
                    setButtonEnabled(false);
                    break;

                default:
                    return false;
            }
        } catch (Exception e) {
            control.getSysKit().getErrorKit().writerLog(e);
        }
        return true;
    }

    /**
     * pages count change
     */
    public void onPagesCountChange() {

    }

    /**
     * current display pags change
     */
    public void onCurrentPageChange() {

    }

    /**
     *
     */
    public void openFileFinish() {
        // 加一条与应用视图分隔的灰色线
        gapView = new View(getApplicationContext());
        gapView.setBackgroundColor(Color.WHITE);
        appFrame.addView(gapView, new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        //
        app = control.getView();
        app.setBackgroundColor(Color.WHITE);
        appFrame.addView(app,
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));



      /*  if (applicationType == MainConstant.APPLICATION_TYPE_SS)
        {
            bottomBar = new SheetBar(getApplicationContext(), control, getResources().getDisplayMetrics().widthPixels);
            appFrame.addView(bottomBar, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }*/
    }

    /**
     *
     */
    public int getBottomBarHeight() {
        if (bottomBar != null) {
            return bottomBar.getSheetbarHeight();
        }
        return 0;
    }

    /**
     *
     */
    public int getTopBarHeight() {
        return 0;
    }

    /**
     * event method, office engine dispatch
     *
     * @param v               event source
     * @param e1              MotionEvent instance
     * @param e2              MotionEvent instance
     * @param xValue          eventNethodType is ON_SCROLL, this is value distanceX
     *                        eventNethodType is ON_FLING, this is value velocityY
     *                        eventNethodType is other type, this is value -1
     * @param yValue          eventNethodType is ON_SCROLL, this is value distanceY
     *                        eventNethodType is ON_FLING, this is value velocityY
     *                        eventNethodType is other type, this is value -1
     * @param eventNethodType event method
     * @see IMainFrame#ON_CLICK
     * @see IMainFrame#ON_DOUBLE_TAP
     * @see IMainFrame#ON_DOUBLE_TAP_EVENT
     * @see IMainFrame#ON_DOWN
     * @see IMainFrame#ON_FLING
     * @see IMainFrame#ON_LONG_PRESS
     * @see IMainFrame#ON_SCROLL
     * @see IMainFrame#ON_SHOW_PRESS
     * @see IMainFrame#ON_SINGLE_TAP_CONFIRMED
     * @see IMainFrame#ON_SINGLE_TAP_UP
     * @see IMainFrame#ON_TOUCH
     */
    public boolean onEventMethod(View v, MotionEvent e1, MotionEvent e2, float xValue,
                                 float yValue, byte eventMethodType) {
        return false;
    }


    public void changePage() {
    }

    /**
     *
     */
    public String getAppName() {
        return getString(R.string.sys_name);
    }

    /**
     * 是否绘制页码
     */
    public boolean isDrawPageNumber() {
        return true;
    }

    /**
     * 是否支持zoom in / zoom out
     */
    public boolean isTouchZoom() {
        return true;
    }

    /**
     * Word application 默认视图(Normal or Page)
     *
     * @return WPViewConstant.PAGE_ROOT or WPViewConstant.NORMAL_ROOT
     */
    public byte getWordDefaultView() {
        return WPViewConstant.PAGE_ROOT;
        //return WPViewConstant.NORMAL_ROOT;
    }

    /**
     * normal view, changed after zoom bend, you need to re-layout
     *
     * @return true   re-layout
     * false  don't re-layout
     */
    public boolean isZoomAfterLayoutForWord() {
        return true;
    }

    /**
     * init float button, for slideshow pageup/pagedown
     */
    private void initFloatButton() {
        //icon width and height
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_left, opts);

        //load page up button
        Resources res = getResources();
        pageUp = new AImageButton(this, control, res.getString(R.string.pg_slideshow_pageup), -1,
                -1, EventConstant.APP_PAGE_UP_ID);
        pageUp.setNormalBgResID(R.drawable.file_slideshow_left);
        pageUp.setPushBgResID(R.drawable.file_slideshow_left_push);
        pageUp.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        //load page down button
        pageDown = new AImageButton(this, control, res.getString(R.string.pg_slideshow_pagedown),
                -1, -1, EventConstant.APP_PAGE_DOWN_ID);
        pageDown.setNormalBgResID(R.drawable.file_slideshow_right);
        pageDown.setPushBgResID(R.drawable.file_slideshow_right_push);
        pageDown.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        BitmapFactory.decodeResource(getResources(), R.drawable.file_slideshow_pen_normal, opts);
        // load pen button
        penButton = new AImageCheckButton(this, control,
                res.getString(R.string.app_toolsbar_pen_check), res.getString(R.string.app_toolsbar_pen),
                R.drawable.file_slideshow_pen_check, R.drawable.file_slideshow_pen_normal,
                R.drawable.file_slideshow_pen_normal, EventConstant.APP_PEN_ID);
        penButton.setNormalBgResID(R.drawable.file_slideshow_pen_normal);
        penButton.setPushBgResID(R.drawable.file_slideshow_pen_push);
        penButton.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        // load eraser button
        eraserButton = new AImageCheckButton(this, control,
                res.getString(R.string.app_toolsbar_eraser_check), res.getString(R.string.app_toolsbar_eraser),
                R.drawable.file_slideshow_eraser_check, R.drawable.file_slideshow_eraser_normal,
                R.drawable.file_slideshow_eraser_normal, EventConstant.APP_ERASER_ID);
        eraserButton.setNormalBgResID(R.drawable.file_slideshow_eraser_normal);
        eraserButton.setPushBgResID(R.drawable.file_slideshow_eraser_push);
        eraserButton.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        // load settings button
        settingsButton = new AImageButton(this, control, res.getString(R.string.app_toolsbar_color),
                -1, -1, EventConstant.APP_COLOR_ID);
        settingsButton.setNormalBgResID(R.drawable.file_slideshow_settings_normal);
        settingsButton.setPushBgResID(R.drawable.file_slideshow_settings_push);
        settingsButton.setLayoutParams(new LayoutParams(opts.outWidth, opts.outHeight));

        wm = (WindowManager) getApplicationContext().getSystemService("window");
        wmParams = new WindowManager.LayoutParams();

        wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.width = opts.outWidth;
        wmParams.height = opts.outHeight;
    }

    /**
     * full screen, not show top tool bar
     */
    public void fullScreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        if (fullscreen) {
            if (wm == null || wmParams == null) {
                initFloatButton();
            }

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wm.addView(penButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height;
            wm.addView(eraserButton, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.TOP;
            wmParams.x = MainConstant.GAP;
            wmParams.y = wmParams.height * 2;
            wm.addView(settingsButton, wmParams);

            wmParams.gravity = Gravity.LEFT | Gravity.CENTER;
            wmParams.x = MainConstant.GAP;
            wmParams.y = 0;
            wm.addView(pageUp, wmParams);

            wmParams.gravity = Gravity.RIGHT | Gravity.CENTER;
            wm.addView(pageDown, wmParams);

            //hide title and tool bar
            ((View) getWindow().findViewById(android.R.id.title).getParent())
                    .setVisibility(View.GONE);
            //hide status bar
            toolsbar.setVisibility(View.GONE);
            //
            gapView.setVisibility(View.GONE);

            penButton.setState(AImageCheckButton.UNCHECK);
            eraserButton.setState(AImageCheckButton.UNCHECK);

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(params);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            //landscape
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        } else {
            wm.removeView(pageUp);
            wm.removeView(pageDown);
            wm.removeView(penButton);
            wm.removeView(eraserButton);
            wm.removeView(settingsButton);
            //show title and tool bar
            ((View) getWindow().findViewById(android.R.id.title).getParent())
                    .setVisibility(View.VISIBLE);
            toolsbar.setVisibility(View.VISIBLE);
            gapView.setVisibility(View.VISIBLE);

            //show status bar
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(params);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }

    }

    /**
     *
     */

    public void changeZoom() {
    }

    /**
     *
     */
    public void error(int errorCode) {
    }

    /**
     * when need destroy the office engine instance callback this method
     */
    public void destroyEngine() {
        super.onBackPressed();
    }

    /**
     * get Internationalization resource
     *
     * @param resName Internationalization resource name
     */
    public String getLocalString(String resName) {
        return ResKit.instance().getLocalString(resName);
    }

    @Override
    public boolean isShowPasswordDlg() {
        return true;
    }

    @Override
    public boolean isShowProgressBar() {
        return true;
    }

    @Override
    public boolean isShowFindDlg() {
        return true;
    }

    @Override
    public boolean isShowTXTEncodeDlg() {
        return true;
    }

    /**
     * get txt default encode when not showing txt encode dialog
     *
     * @return null if showing txt encode dialog
     */
    public String getTXTDefaultEncode() {
        return "GBK";
    }

    /**
     *
     */
    public DialogListener getDialogListener() {
        return null;
    }


    @Override
    public void completeLayout() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isChangePage() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @param saveLog
     */
    public void setWriteLog(boolean saveLog) {
        this.writeLog = saveLog;
    }

    /**
     * @return
     */
    public boolean isWriteLog() {
        return writeLog;
    }

    /**
     * @param isThumbnail
     */
    public void setThumbnail(boolean isThumbnail) {
        this.isThumbnail = isThumbnail;
    }

    /**
     * get view backgrouond
     *
     * @return
     */
    public Object getViewBackground() {
        return bg;
    }

    /**
     * set flag whether fitzoom can be larger than 100% but smaller than the max zoom
     *
     * @param ignoreOriginalSize
     */
    public void setIgnoreOriginalSize(boolean ignoreOriginalSize) {

    }

    /**
     * @return true fitzoom may be larger than 100% but smaller than the max zoom
     * false fitzoom can not larger than 100%
     */
    public boolean isIgnoreOriginalSize() {
        return false;
    }

    public byte getPageListViewMovingPosition() {
        return IPageListViewListener.Moving_Horizontal;
    }

    /**
     * @return
     */
    public boolean isThumbnail() {
        return isThumbnail;
    }

    /**
     * @param viewList
     */
    public void updateViewImages(List<Integer> viewList) {

    }

    /**
     * @return
     */
    public File getTemporaryDirectory() {
        // Get path for the file on external storage.  If external
        // storage is not currently mounted this will fail.
        File file = getExternalFilesDir(null);
        if (file != null) {
            return file;
        } else {
            return getFilesDir();
        }
    }

    /**
     * 释放内存
     */
    public void dispose() {
        isDispose = true;
        if (control != null) {
            control.dispose();
            control = null;
        }
        toolsbar = null;
        searchBar = null;
        bottomBar = null;
        if (dbService != null) {
            dbService.dispose();
            dbService = null;
        }
        if (appFrame != null) {
            int count = appFrame.getChildCount();
            for (int i = 0; i < count; i++) {
                View v = appFrame.getChildAt(i);
                if (v instanceof AToolsbar) {
                    ((AToolsbar) v).dispose();
                }
            }
            appFrame = null;
        }

        if (wm != null) {
            wm = null;
            wmParams = null;
            pageUp.dispose();
            pageDown.dispose();
            penButton.dispose();
            eraserButton.dispose();
            settingsButton.dispose();
            pageUp = null;
            pageDown = null;
            penButton = null;
            eraserButton = null;
            settingsButton = null;
        }
    }

    //
    private boolean isDispose;
    // 当前标星状态
    private boolean marked;
    //
    private int applicationType = -1;
    //
    private String filePath;

    private String fileName;

    private String fileType;

    private int fileIcon;

    private String fileSize;

    // application activity control
    private MainControl control;
    //
    private AppFrame appFrame;
    //tool bar
    private AToolsbar toolsbar;
    //search bar
    private FindToolBar searchBar;
    //
    private DBService dbService;
    //
    private SheetBar bottomBar;
    //
    private Toast toast;
    //
    private View gapView;

    private View app;

    private Bitmap bitmap;

    private ArrayList<ScreenShotFile> screenShotFileArrayList = new ArrayList<>();

    private FavoriteDocumentsDatabse favoriteDocumentsDatabse;

    //float button: PageUp/PageDown
    private WindowManager wm = null;
    private WindowManager.LayoutParams wmParams = null;
    private AImageButton pageUp;
    private AImageButton pageDown;
    private AImageCheckButton penButton;
    private AImageCheckButton eraserButton;
    private AImageButton settingsButton;

    //whether write log to temporary file
    private boolean writeLog = true;
    //open file to get thumbnail, or not
    private boolean isThumbnail;
    //view background
    private Object bg = Color.WHITE;
    //
    private CalloutToolsbar calloutBar;
    //
    private boolean fullscreen;
    //
    private String tempFilePath;
}
