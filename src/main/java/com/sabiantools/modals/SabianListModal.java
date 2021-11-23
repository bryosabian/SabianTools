package com.sabiantools.modals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.gc.materialdesign.views.ButtonFlat;
import com.sabiantools.R;
import com.sabiantools.utilities.SabianUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class SabianListModal extends Dialog {
    private ViewGroup vgBody;
    private ButtonFlat btnOk, btnCancel;
    private View.OnClickListener onOkayClickListener, onCancelClickListener;
    private String okayButtonText, cancelButtonText;
    private ListView lstModalList;
    private @ColorRes
    int okayButtonColor = NO_RES_ID;
    private @ColorRes
    int cancelButtonColor = NO_RES_ID;
    public static final int NO_RES_ID = -1;
    private Context context;
    private TextView txtTitle, txtMessage;
    private String title, message;
    private @ColorRes
    int titleColor = NO_RES_ID;
    private @ColorRes
    int messageColor = NO_RES_ID;
    private boolean animate = true;
    private ViewGroup vgBodyContainer;

    private ArrayList<ListItem> listItems;
    private ArrayList<ListItem> allItems;

    private ListItemAdapter adapter;
    private OnListItemSelectedListener onListItemSelectedListener;
    private ListItem selectedItem;

    private boolean enableImageIcon = false;

    private boolean enableSearch = true;

    private EditText edtSearch;
    private ViewGroup vgSearchContainer;

    private boolean showLoaderFirst;
    private String loaderText = "Loading..";
    private Loader loader;

    private Error error;

    private int minHeight = NO_DIMENSION_PIX;
    public static final int NO_DIMENSION_PIX = -1;

    public SabianListModal setMinHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public Loader getLoader() {
        return loader;
    }

    public SabianListModal setLoaderText(String loaderText) {
        this.loaderText = loaderText;
        return this;
    }

    public SabianListModal setEnableImageIcon(boolean enableImageIcon) {
        this.enableImageIcon = enableImageIcon;
        return this;
    }

    public SabianListModal setOnListItemSelectedListener(OnListItemSelectedListener onListItemSelectedListener) {
        this.onListItemSelectedListener = onListItemSelectedListener;
        return this;
    }

    public ListItem getSelectedItem() {
        return selectedItem;
    }

    public SabianListModal setSelectedItem(ListItem selectedItem) {
        this.selectedItem = selectedItem;
        return this;
    }

    public SabianListModal(@NonNull Context context) {
        super(context, R.style.SabianMaterialDialog);
        this.context = context;
        listItems = new ArrayList<>();
    }

    public SabianListModal(@NonNull Context context, String... items) {
        this(context);
        for (String item : items) {
            listItems.add(new ListItem(item.hashCode(), item).setValue(item));
        }
    }

    public SabianListModal(@NonNull Context context, int... items) {
        this(context);
        for (int item : items) {
            listItems.add(new ListItem(item, item + "").setValue(item));
        }
    }

    public SabianListModal(@NonNull Context context, Object... items) {
        this(context);
        for (Object item : items) {
            listItems.add(new ListItem(item.hashCode(), item.toString()).setValue(item));
        }
    }

    public SabianListModal addListItem(ListItem item) {
        if (listItems == null)
            listItems = new ArrayList<>();
        if (!listItems.contains(item)) {
            listItems.add(item);
        }
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sabian_modal_list_smooth);
        init_elements();
    }

    protected void init_elements() {
        vgBody = (ViewGroup) findViewById(R.id.rll_SabianModalContainer);
        btnOk = (ButtonFlat) findViewById(R.id.btn_SabianModalOk);
        btnCancel = (ButtonFlat) findViewById(R.id.btn_SabianModalCancel);
        txtTitle = (TextView) findViewById(R.id.sct_SabianModalTitle);
        txtMessage = (TextView) findViewById(R.id.sct_SabianModalMessage);
        vgBodyContainer = (ViewGroup) findViewById(R.id.rll_SabianModalContainer);
        lstModalList = (ListView) findViewById(R.id.lst_SabianModalList);

        edtSearch = (EditText) findViewById(R.id.edt_SabianModalSearch);
        vgSearchContainer = (ViewGroup) findViewById(R.id.rll_SabianModalSearchContainer);

        if (!enableSearch)
            vgSearchContainer.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchItems(edtSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        allItems = new ArrayList<>();
        allItems.addAll(listItems);

        adapter = new ListItemAdapter(getContext(), -1, listItems);
        lstModalList.setAdapter(adapter);

        lstModalList.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedItem = listItems.get(i);
            if (onListItemSelectedListener != null)
                onListItemSelectedListener.onSelect(selectedItem, SabianListModal.this);
            dismiss();
        });

        if (!SabianUtilities.IsStringEmpty(okayButtonText)) {
            btnOk.setText(okayButtonText);
        }

        if (!SabianUtilities.IsStringEmpty(cancelButtonText)) {
            btnCancel.setText(cancelButtonText);
        } else {
            btnCancel.setVisibility(View.GONE);
        }

        if (onOkayClickListener != null) {
            btnOk.setOnClickListener(onOkayClickListener);
        } else {
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCancelClickListener != null)
                    onCancelClickListener.onClick(view);
                dismiss();
            }
        });

        if (okayButtonColor != NO_RES_ID)
            btnOk.setBackgroundColor(context.getResources().getColor(okayButtonColor));

        if (cancelButtonColor != NO_RES_ID)
            btnCancel.setBackgroundColor(context.getResources().getColor(cancelButtonColor));

        if (!SabianUtilities.IsStringEmpty(title))
            txtTitle.setText(title);

        if (titleColor != NO_RES_ID)
            txtTitle.setTextColor(context.getResources().getColor(titleColor));

        if (messageColor != NO_RES_ID)
            txtMessage.setTextColor(context.getResources().getColor(messageColor));


        if (minHeight != NO_DIMENSION_PIX) {
            vgBody.setMinimumHeight(minHeight);
            vgBody.requestLayout();
        }

        error = new Error();
        loader = new Loader();
        if (showLoaderFirst)
            loader.show(loaderText);
    }

    private void searchItems(String search) {
        listItems.clear();
        if (SabianUtilities.IsStringEmpty(search)) {
            listItems.addAll(allItems);
        } else {
            for (ListItem item : allItems) {
                Pattern pattern = Pattern.compile(".*" + search + ".*", Pattern.CASE_INSENSITIVE);
                boolean matches = false;
                String searchFor = (item.title != null) ? item.title : "";
                searchFor += (item.subTitle != null) ? item.subTitle : "";
                matches = pattern.matcher(searchFor).matches();
                if (matches)
                    listItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public SabianListModal setListItems(List<ListItem> items) {
        this.listItems = new ArrayList<>();
        listItems.addAll(items);
        this.allItems = new ArrayList<>();
        allItems.addAll(items);
        return this;
    }


    public SabianListModal setListItems(List<ListItem> items, boolean refreshAdapter) {
        setListItems(items);
        if (refreshAdapter) {
            adapter = new ListItemAdapter(getContext(), -1, listItems);
            lstModalList.setAdapter(adapter);

            lstModalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedItem = listItems.get(i);
                    if (onListItemSelectedListener != null)
                        onListItemSelectedListener.onSelect(selectedItem, SabianListModal.this);
                    dismiss();
                }
            });
        }
        return this;
    }

    public Error getError() {
        return error;
    }

    @Override
    public void show() {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        super.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setAttributes(lp);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (animate) {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.modal_popup_show);
            vgBodyContainer.startAnimation(anim);
        }
    }

    public SabianListModal setOnOkayClickListener(View.OnClickListener onOkayCickListener) {
        this.onOkayClickListener = onOkayCickListener;
        return this;
    }

    public SabianListModal setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
        return this;
    }

    public SabianListModal setOkayButtonText(String okayButtonText) {
        this.okayButtonText = okayButtonText;
        return this;
    }

    public SabianListModal setCancelButtonText(String cancelButtonText) {
        this.cancelButtonText = cancelButtonText;
        return this;
    }

    public SabianListModal setOkayButtonColor(@ColorRes int okayButtonColor) {
        this.okayButtonColor = okayButtonColor;
        return this;
    }

    public SabianListModal setCancelButtonColor(@ColorRes int cancelButtonColor) {
        this.cancelButtonColor = cancelButtonColor;
        return this;
    }

    public SabianListModal setTitle(String title) {
        this.title = title;
        return this;
    }

    public SabianListModal setTitleColor(@ColorRes int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public SabianListModal setMessage(String message) {
        this.message = message;
        return this;
    }

    public SabianListModal setMessageColor(@ColorRes int messageColor) {
        this.messageColor = messageColor;
        return this;
    }

    public SabianListModal setAnimate(boolean animate) {
        this.animate = animate;
        return this;
    }

    public SabianListModal setEnableSearch(boolean enableSearch) {
        this.enableSearch = enableSearch;
        return this;
    }

    public SabianListModal setShowLoaderFirst(boolean showLoaderFirst) {
        this.showLoaderFirst = showLoaderFirst;
        return this;
    }

    public static class ListItem {
        private long ID = -2;
        private String title;
        private String subTitle;
        private String imageUrl;
        private boolean isImageVector = false;
        private @DrawableRes
        int imageRes = NO_IMAGE;
        private Object value;

        public Object getValue() {
            return value;
        }

        public ListItem setValue(Object value) {
            this.value = value;
            return this;
        }

        public static final int NO_IMAGE = -1;

        public ListItem(String title) {
            this.title = title;
        }

        public ListItem(long ID, String title) {
            this(title);
            this.ID = ID;
        }

        public ListItem(String title, String subTitle) {
            this(title);
            this.subTitle = subTitle;
        }

        public ListItem(String title, String subTitle, int imageRes) {
            this(title, subTitle);
            this.imageRes = imageRes;
        }

        public long getID() {
            if (ID == -2)
                this.ID = title.hashCode();
            return ID;
        }

        public ListItem setID(long ID) {
            this.ID = ID;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public ListItem setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public ListItem setSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public int getImageRes() {
            return imageRes;
        }

        public boolean hasImage() {
            return !SabianUtilities.IsStringEmpty(getImageUrl()) || getImageRes() != NO_IMAGE;
        }

        public ListItem setImageRes(int imageRes) {
            this.imageRes = imageRes;
            return this;
        }

        public static int getNoImage() {
            return NO_IMAGE;
        }

        public boolean isImageVector() {
            return isImageVector;
        }

        public ListItem setImageVector(boolean imageVector) {
            isImageVector = imageVector;
            return this;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public ListItem setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ListItem item = (ListItem) o;
            return getID() == item.getID();
        }

        @Override
        public int hashCode() {
            return (int) getID();
        }
    }

    public static class ListItemAdapter extends ArrayAdapter<ListItem> {
        private int transformRadius = 10;
        private int transformMargin = 5;
        private int imageEnabledPadding;

        private Context context;
        private LayoutInflater inflater;
        private List<ListItem> items;

        public ListItemAdapter(@NonNull Context context, int resource, @NonNull List<ListItem> objects) {
            super(context, resource, objects);
            this.items = objects;
            this.inflater = LayoutInflater.from(context);
            this.context = context;
            imageEnabledPadding = context.getResources().getDimensionPixelSize(R.dimen.sabian_modal_list_image_padding);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view;
            Holder holder = new Holder();
            if (convertView != null) {
                view = convertView;
                holder = (Holder) view.getTag();
            } else {
                view = inflater.inflate(R.layout.sabian_modal_list_item, parent, false);
                holder.vgMainContainer = (ViewGroup) view.findViewById(R.id.ll_ViewHoldermodalContainer);
                holder.txtTitle = (TextView) view.findViewById(R.id.sct_ViewHoldermodalTitle);
                holder.txtSubTitle = (TextView) view.findViewById(R.id.sct_ViewHoldermodalSubTitle);
                holder.imgIcon = (ImageView) view.findViewById(R.id.img_ViewHoldermodalImage);
                holder.imgContainer = (ViewGroup) holder.imgIcon.getParent();
                view.setTag(holder);
            }
            ListItem item = items.get(position);
            holder.txtTitle.setText(item.getTitle());
            if (!SabianUtilities.IsStringEmpty(item.getSubTitle())) {
                holder.txtSubTitle.setText(item.getSubTitle());
            }
            return view;
        }

        private class Holder {
            private ViewGroup vgMainContainer;
            public ImageView imgIcon;
            public TextView txtTitle;
            public TextView txtSubTitle;
            public FontAwesomeText ftMore;
            public ViewGroup imgContainer;
        }
    }

    public interface OnListItemSelectedListener {
        void onSelect(ListItem item, SabianListModal modal);
    }

    public class Loader {
        private ViewGroup vgContainer;
        private TextView txtTitle;

        public Loader() {
            vgContainer = findViewById(R.id.vg_ModalLoader);
            txtTitle = findViewById(R.id.sct_ModalLoaderText);
        }

        public void show(String loaderText) {
            show();
            txtTitle.setText(loaderText);
        }

        public void show() {
            vgContainer.setVisibility(View.VISIBLE);
        }

        public void hide() {
            vgContainer.setVisibility(View.GONE);
        }

        public void setLoaderText(String text) {
            txtTitle.setText(text);
        }
    }

    public class Error {
        private ViewGroup mainBody;
        private ViewGroup errorBody;
        private ViewGroup loaderBody;
        private TextView txtErrorMessage;
        private ButtonFlat btnRetry;
        private ImageView icon;

        public Error() {
            mainBody = findViewById(R.id.ll_SabianModalBodyContainer);
            errorBody = findViewById(R.id.ll_ModalFailedContainer);
            loaderBody = findViewById(R.id.vg_ModalLoader);
            txtErrorMessage = findViewById(R.id.sct_ModalFailedText);
            btnRetry = findViewById(R.id.btn_ModalErrorButtonRetry);
            icon = findViewById(R.id.img_ModalFailed);

            VectorDrawableCompat vc = VectorDrawableCompat.create(getContext().getResources(), R.drawable.vc_cloud_off_black_24dp, null);
            icon.setColorFilter(ContextCompat.getColor(getContext(), R.color.sabian_standard_background));
            icon.setImageDrawable(vc);
        }

        public void showError(String text, @Nullable String retryButtonText, @Nullable View.OnClickListener onRetryClickListener) {
            loaderBody.setVisibility(View.GONE);
            mainBody.setVisibility(View.GONE);
            errorBody.setVisibility(View.VISIBLE);
            txtErrorMessage.setText(text);
            if (retryButtonText == null) {
                btnRetry.setVisibility(View.GONE);
            } else {
                btnRetry.setText(retryButtonText);
            }
            if (onRetryClickListener != null)
                btnRetry.setOnClickListener(onRetryClickListener);
        }

        public void hideError() {
            errorBody.setVisibility(View.GONE);
            mainBody.setVisibility(View.VISIBLE);
        }
    }
}
