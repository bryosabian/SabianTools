package com.sabiantools.controls;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.sabiantools.R;
import com.sabiantools.controls.texts.TypeFaceFactory;
import com.sabiantools.utilities.SabianUtilities;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class SabianButtonText extends FrameLayout {


    protected LayoutInflater inflater;
    protected FontAwesomeText icon, iconRight;
    protected SabianCondensedEditText editText;
    protected Context _context;
    protected ImageView imgIcon, imgRightIcon;
    protected boolean isImageIconVector = false;
    protected ViewGroup vgMainContainer;
    protected @ColorRes
    int textColor = NO_RES;
    protected @ColorRes
    int vectorColor = NO_RES;
    public static final int NO_RES = -1;
    private int inputType;
    protected ViewGroup vgLeftContainer, vgRightContainer;

    protected String backgroundType = BACKGROUND_STYLE_NORMAL;
    public static final String BACKGROUND_STYLE_ALT = "alt";
    public static final String BACKGROUND_STYLE_NORMAL = "normal";

    private TextWatcher onTextListener;
    private OnTextChangeListener onTextChangeListener;

    public enum Alignment {
        RIGHT(0, "right"), LEFT(1, "left");

        private int align;
        private String value;

        Alignment(int align, String value) {
            this.align = align;
            this.value = value;
        }

        public int getAlign() {
            return align;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }

    public interface OnTextChangeListener {
        void onTextChange(String value);
    }


    public SabianButtonText setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
        onTextListener = null;
        activateTextListener();
        return this;
    }

    public SabianButtonText(@NonNull Context context) {
        super(context);
        init_elements(context);
    }

    public SabianButtonText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init_elements(context);
        init_attributes(attrs);
    }

    public SabianButtonText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init_elements(context);
        init_attributes(attrs);
    }

    protected void init_elements(Context context) {
        _context = context;
        inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.control_button_text, this, true);
        vgMainContainer = root.findViewById(R.id.ll_ControlButtonTextContainer);
        icon = root.findViewById(R.id.fat_ControlButtonText);
        imgIcon = root.findViewById(R.id.img_ControlButtonText);
        editText = root.findViewById(R.id.txt_ControlButtonText);

        imgRightIcon = root.findViewById(R.id.img_ControlButtonTextRight);
        iconRight = root.findViewById(R.id.fat_ControlButtonTextRight);

        vgLeftContainer = root.findViewById(R.id.rll_ControlButtonContainerLeft);
        vgRightContainer = root.findViewById(R.id.rll_ControlButtonContainerRight);
    }

    private void activateTextListener() {
        editText.clearTextChangedListeners();
        if (onTextChangeListener != null) {
            onTextListener = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    onTextChangeListener.onTextChange(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            };
            editText.addTextChangedListener(onTextListener);
        }
    }

    public void setRobotoType(String type) {
        if (SabianUtilities.IsStringEmpty(type))
            return;
        editText.setTypeface(TypeFaceFactory.getTypeFace(_context, "fonts/" + type + ".ttf"));
    }

    public void setText(String text) {
        this.editText.setText(text);
    }

    public void setHint(String hint) {
        this.editText.setHint(hint);
    }

    public void setIcon(String favicon) {
        icon.setVisibility(VISIBLE);
        imgIcon.setVisibility(GONE);
        this.icon.setIcon(favicon);
    }

    public void setImageIcon(@DrawableRes int image) {
        icon.setVisibility(GONE);
        imgIcon.setVisibility(VISIBLE);

        iconRight.setVisibility(GONE);
        imgRightIcon.setVisibility(VISIBLE);

        imgIcon.setImageResource(image);
        imgRightIcon.setImageResource(image);
    }

    public void setImageVectorIcon(@DrawableRes int image) {
        icon.setVisibility(GONE);
        imgIcon.setVisibility(VISIBLE);

        iconRight.setVisibility(GONE);
        imgRightIcon.setVisibility(VISIBLE);

        if (image == NO_RES) {
            imgIcon.setImageDrawable(null);
            imgRightIcon.setImageDrawable(null);
            return;
        }

        VectorDrawableCompat vc = VectorDrawableCompat.create(_context.getResources(), image, null);
        imgIcon.setImageDrawable(vc);
        imgRightIcon.setImageDrawable(vc);

        isImageIconVector = true;
    }

    public void setImageVectorColor(int color) {
        if (color == NO_RES)
            return;
        vectorColor = color;
        imgIcon.setColorFilter(color);
        imgRightIcon.setColorFilter(color);
    }

    public void setTextColor(int colorRes) {
        textColor = colorRes;
        this.editText.setTextColor(colorRes);
        this.icon.setTextColor(colorRes);
        if (vectorColor == NO_RES)
            vectorColor = textColor;
    }

    public void setTextColor(String color) {
        int _col = Color.parseColor(color);
        this.setTextColor(_col);
    }

    public void setHintColor(int color) {
        if (color != NO_RES)
            editText.setHintTextColor(color);
    }

    @Deprecated
    public void setPassword(boolean yes) {
        if (yes) {
            this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            return;
        }
    }

    public void setTextSize(float size) {
        if (size != NO_RES) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
            icon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public String getText() {
        String txt = editText.getText().toString();
        return txt;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
        if (inputType != NO_RES) {
            editText.setInputType(inputType);
        }
    }

    public void setCustomBackgroundDrawable(Drawable drawable) {
        if (drawable != null) {
            vgMainContainer.setBackgroundDrawable(drawable);
            vgMainContainer.requestLayout();
        }
    }

    protected void setIconAlignment(String alignment) {
        //MarginLayoutParams p = (MarginLayoutParams) editText.getLayoutParams();
        int padd = _context.getResources().getDimensionPixelSize(R.dimen.control_button_search_no_margin);
        if (alignment.equals("right")) {
            vgRightContainer.setVisibility(VISIBLE);
            vgLeftContainer.setVisibility(GONE);
            editText.setPadding(padd, editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
            //p.leftMargin = _context.getResources().getDimensionPixelSize(R.dimen.control_button_search_no_margin);
        } else {
            vgRightContainer.setVisibility(GONE);
            vgLeftContainer.setVisibility(VISIBLE);
            editText.setPadding(editText.getPaddingLeft(), editText.getPaddingTop(), padd, editText.getPaddingBottom());
            //p.rightMargin = _context.getResources().getDimensionPixelSize(R.dimen.control_button_search_no_margin);
        }
        editText.requestLayout();
    }

    public void setIconAlignment(Alignment alignment) {
        setIconAlignment(alignment.toString());
    }

    private void setIconAlignment(int value) {
        Alignment[] alignments = Alignment.values();
        for (Alignment align : alignments) {
            if (align.getAlign() == value) {
                setIconAlignment(align);
                return;
            }
        }
        SabianUtilities.WriteLog("No icon alignment found for " + value);
    }

    public SabianButtonText setBackgroundType(String style) {
        this.backgroundType = style;
        if (style.equals(BACKGROUND_STYLE_ALT)) {
            setCustomBackgroundDrawable(_context.getResources().getDrawable(R.drawable.control_button_text_alt));
        } else {
            setCustomBackgroundDrawable(_context.getResources().getDrawable(R.drawable.control_button_text_bg));
        }
        return this;
    }

    public SabianButtonText setPadding(int padding) {
        if (padding == -1) {
            return this;
        }
        vgMainContainer.setPadding(
                padding,
                padding,
                padding,
                padding
        );
        return this;
    }

    public SabianButtonText setPaddingFromResource(@DimenRes int padding) {
        int padd = _context.getResources().getDimensionPixelSize(padding);
        return setPadding(padd);
    }

    public SabianButtonText setIconSize(int size) {
        if (size == -1) {
            return this;
        }
        ViewGroup.LayoutParams imgRightParams = imgRightIcon.getLayoutParams();
        imgRightParams.width = size;
        imgRightParams.height = size;

        ViewGroup.LayoutParams imgLeftParams = imgIcon.getLayoutParams();
        imgLeftParams.width = size;
        imgRightParams.height = size;

        ViewGroup.LayoutParams ftRightParams = iconRight.getLayoutParams();
        ftRightParams.width = size;
        ftRightParams.height = size;

        ViewGroup.LayoutParams ftLeftParams = icon.getLayoutParams();
        ftLeftParams.width = size;
        ftLeftParams.height = size;

        return this;
    }


    public void setGravity(int gravity) {
        editText.setGravity(gravity);
    }

    @Override
    public void setEnabled(boolean is) {
        super.setEnabled(is);
        editText.setEnabled(is);
        vgLeftContainer.setEnabled(is);
        vgRightContainer.setEnabled(is);
    }

    @Override
    public void setOnClickListener(final OnClickListener listener) {
        super.setOnClickListener(listener);
        boolean isClickable = listener != null;

        editText.setFocusable(!isClickable);
        editText.setFocusableInTouchMode(!isClickable);

        editText.setClickable(isClickable);
        vgRightContainer.setClickable(isClickable);
        vgLeftContainer.setClickable(isClickable);


        vgMainContainer.setOnClickListener(listener);
        editText.setOnClickListener(listener);
        vgRightContainer.setOnClickListener(listener);
        vgLeftContainer.setOnClickListener(listener);
    }

    private void init_attributes(AttributeSet set) {
        Resources res = _context.getResources();
        TypedArray a = _context.obtainStyledAttributes(set, R.styleable.SabianButtonText);
        int aCount = a.getIndexCount();
        for (int i = 0; i < aCount; ++i) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SabianButtonText_sbt_icon) {
                setIcon(a.getString(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_text) {
                this.setText(a.getString(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_textColor) {
                this.setTextColor(a.getColor(attr, res.getColor(R.color.sabian_actionbar_text_color)));
            } else if (attr == R.styleable.SabianButtonText_sbt_hint) {
                this.setHint(a.getString(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_password) {
                this.setPassword(a.getBoolean(attr, false));
            } else if (attr == R.styleable.SabianButtonText_sbt_vector_icon) {
                setImageVectorIcon(a.getResourceId(attr, NO_RES));
            } else if (attr == R.styleable.SabianButtonText_sbt_vector_icon_color) {
                setImageVectorColor(a.getColor(attr, NO_RES));
            } else if (attr == R.styleable.SabianButtonText_sbt_textsize) {
                setTextSize(a.getDimensionPixelSize(attr, NO_RES));
            } else if (attr == R.styleable.SabianButtonText_sbt_hintColor) {
                setHintColor(a.getColor(attr, NO_RES));
            } else if (attr == R.styleable.SabianButtonText_android_inputType) {
                setInputType(a.getInt(attr, InputType.TYPE_CLASS_TEXT));
            } else if (attr == R.styleable.SabianButtonText_sbt_background_drawable) {
                setCustomBackgroundDrawable(a.getDrawable(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_roboto_type) {
                setRobotoType(a.getString(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_icon_alignment) {
                setIconAlignment(a.getInt(attr, Alignment.LEFT.getAlign()));
            } else if (attr == R.styleable.SabianButtonText_sbt_style) {
                setBackgroundType(a.getString(attr));
            } else if (attr == R.styleable.SabianButtonText_sbt_padding) {
                setPadding(a.getDimensionPixelSize(attr, -1));
            } else if (attr == R.styleable.SabianButtonText_sbt_icon_size) {
                setIconSize(a.getDimensionPixelSize(attr, -1));
            } else if (attr == R.styleable.SabianButtonText_android_gravity) {
                setGravity(a.getInt(attr, Gravity.CENTER));
            }
        }
    }
}
