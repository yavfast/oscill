package oscilldroid.inel;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.view.View;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.keywords.Common;
import anywheresoftware.b4a.keywords.constants.Colors;
import anywheresoftware.b4a.keywords.constants.Gravity;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.SeekBarWrapper;
import anywheresoftware.b4a.objects.Timer;
import anywheresoftware.b4a.objects.drawable.CanvasWrapper;
import anywheresoftware.b4a.objects.streams.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class graphpan extends B4AClass.ImplB4AClass implements BA.SubDelegator {
    private static HashMap<String, Method> htSubs;
    public Common __c = null;
    public int _back_color = 0;
    public int[] _buff_data = null;
    public converter _converter = null;
    public int _curevt = 0;
    public LabelWrapper[] _cursor = null;
    public LabelWrapper[] _cursor_info = null;
    public LabelWrapper[] _cursor_line = null;
    public int _cursor_move = 0;
    public int[] _cursor_pos = null;
    public int _cursor_push = 0;
    public boolean[] _cursorused = null;
    public int _data_size = 0;
    public CanvasWrapper _dcanv = null;
    public CanvasWrapper.PathWrapper _dpath = null;
    public float _draw_del = 0;
    public boolean _draw_peak_flg = false;
    public String _event_center = "";
    public String _event_cur = "";
    public files _files = null;
    public LabelWrapper _frm = null;
    public main _main = null;
    public Object _mainp = null;
    public int _net = 0;
    public int _netcolor = 0;
    public int _netxstep = 0;
    public int _netystep = 0;
    public int _pen_color = 0;
    public SeekBarWrapper _sb_buff_pos = null;
    public Timer _timer_curclose = null;
    public PanelWrapper _underpan = null;
    public PanelWrapper _workpan = null;
    public float _x_dawn = Common.Density;
    public int _xpoints = 0;
    public float _xstep = Common.Density;
    public float _y_dawn = Common.Density;
    public int _ypoints = 0;
    public float _ystep = Common.Density;

    private void innerInitialize(BA ba) throws Exception {
        if (this.ba == null) {
            this.ba = new BA(ba, this, htSubs, "oscilldroid.inel.graphpan");
            if (htSubs == null) {
                this.ba.loadHtSubs(getClass());
                htSubs = this.ba.htSubs;
            }
        }
        _class_globals();
    }

    public String _class_globals() throws Exception {
        this._net = 0;
        this._pen_color = 0;
        this._back_color = 0;
        this._workpan = new PanelWrapper();
        this._underpan = new PanelWrapper();
        this._cursor = new LabelWrapper[3];
        int length = this._cursor.length;
        for (int i = 0; i < length; i++) {
            this._cursor[i] = new LabelWrapper();
        }
        this._cursor_pos = new int[3];
        this._cursor_line = new LabelWrapper[3];
        int length2 = this._cursor_line.length;
        for (int i2 = 0; i2 < length2; i2++) {
            this._cursor_line[i2] = new LabelWrapper();
        }
        this._cursor_info = new LabelWrapper[3];
        int length3 = this._cursor_info.length;
        for (int i3 = 0; i3 < length3; i3++) {
            this._cursor_info[i3] = new LabelWrapper();
        }
        this._xpoints = 0;
        this._ypoints = 0;
        this._buff_data = new int[2000];
        this._data_size = 0;
        this._cursorused = new boolean[3];
        this._draw_del = 0;
        this._frm = new LabelWrapper();
        this._netxstep = 0;
        this._netystep = 0;
        this._netcolor = 0;
        this._curevt = 0;
        this._dcanv = new CanvasWrapper();
        this._dpath = new CanvasWrapper.PathWrapper();
        this._xstep = Common.Density;
        this._ystep = Common.Density;
        this._cursor_push = 0;
        this._cursor_move = 0;
        this._event_cur = "";
        this._event_center = "";
        this._mainp = new Object();
        this._timer_curclose = new Timer();
        this._x_dawn = Common.Density;
        this._y_dawn = Common.Density;
        this._sb_buff_pos = new SeekBarWrapper();
        this._draw_peak_flg = false;
        return "";
    }

    public boolean _cursor0_touch(float f, float f2) throws Exception {
        if (!this._cursorused[0] || f <= ((float) this._cursor[0].getLeft()) || f >= ((float) (this._cursor[0].getLeft() + this._cursor[0].getWidth()))) {
            return false;
        } else if (f2 <= ((float) this._cursor[0].getTop()) || f2 >= ((float) (this._cursor[0].getTop() + this._cursor[0].getHeight()))) {
            this._cursor_push = 1;
            return true;
        } else {
            this._cursor_move = 1;
            _cursors_visible(true);
            return true;
        }
    }

    public boolean _cursor1_touch(float f, float f2) throws Exception {
        if (!this._cursorused[1] || f <= ((float) this._cursor[1].getLeft()) || f >= ((float) (this._cursor[1].getLeft() + this._cursor[1].getWidth()))) {
            return false;
        } else if (f2 <= ((float) this._cursor[1].getTop()) || f2 >= ((float) (this._cursor[1].getTop() + this._cursor[1].getHeight()))) {
            this._cursor_push = 2;
            return true;
        } else {
            this._cursor_move = 2;
            _cursors_visible(true);
            return true;
        }
    }

    public boolean _cursor2_touch(float f, float f2) throws Exception {
        if (!this._cursorused[2] || f2 <= ((float) this._cursor[2].getTop()) || f2 >= ((float) (this._cursor[2].getTop() + this._cursor[2].getHeight()))) {
            return false;
        } else if (f <= ((float) this._cursor[2].getLeft()) || f >= ((float) (this._cursor[2].getLeft() + this._cursor[2].getWidth()))) {
            this._cursor_push = 3;
            return true;
        } else {
            this._cursor_move = 3;
            _cursors_visible(true);
            return true;
        }
    }

    public String _cursors_visible(boolean z) throws Exception {
        if (z) {
            Timer timer = this._timer_curclose;
            timer.setEnabled(false);
            for (int i = 0; ((double) i) <= 2.0d; i = (int) (((double) i) + 1.0d)) {
                if (this._cursorused[i]) {
                    LabelWrapper labelWrapper = this._cursor[i];
                    labelWrapper.setVisible(true);
                    LabelWrapper labelWrapper2 = this._cursor_line[i];
                    labelWrapper2.setVisible(true);
                    LabelWrapper labelWrapper3 = this._cursor_info[i];
                    labelWrapper3.setVisible(true);
                }
            }
            return "";
        }
        this._timer_curclose.setInterval(2000);
        Timer timer2 = this._timer_curclose;
        timer2.setEnabled(true);
        return "";
    }

    public String _draw() throws Exception {
        this._draw_peak_flg = false;
        if (this._data_size > this._xpoints) {
            this._sb_buff_pos.setMax(this._data_size - this._xpoints);
            SeekBarWrapper seekBarWrapper = this._sb_buff_pos;
            seekBarWrapper.setEnabled(true);
            SeekBarWrapper seekBarWrapper2 = this._sb_buff_pos;
            seekBarWrapper2.setVisible(true);
        } else {
            this._sb_buff_pos.setMax(0);
            SeekBarWrapper seekBarWrapper3 = this._sb_buff_pos;
            seekBarWrapper3.setEnabled(false);
            SeekBarWrapper seekBarWrapper4 = this._sb_buff_pos;
            seekBarWrapper4.setVisible(false);
        }
        if (this._sb_buff_pos.getValue() > this._sb_buff_pos.getMax()) {
            this._sb_buff_pos.setValue(0);
        }
        CanvasWrapper canvasWrapper = this._dcanv;
        canvasWrapper.DrawColor(Colors.ARGB(0, 0, 0, 0));
        int sb_buff_pos = this._sb_buff_pos.getValue();
        int[] buff_data = this._buff_data;
        this._dpath.Initialize(Common.Density, _setypos(((float) buff_data[sb_buff_pos]) / this._draw_del));
        int d = this._xpoints - 1;
        float x, y;
        for (int i = 0; i <= d; i++) {
            x = _setxpos(i);
            y = _setypos(((float) buff_data[sb_buff_pos + i]) / this._draw_del);
            this._dpath.LineTo(x, y);
//            this._dpath.getObject().addCircle(x, y, 1f, Path.Direction.CW);

        }
        int i2 = this._pen_color;
        this._dcanv.DrawPath(this._dpath.getObject(), i2, false, (float) Common.DipToCurrent(1));
        this._workpan.Invalidate();
        return "";
    }

    public String _drawnet() throws Exception {
        if (this._net == 1) {
            CanvasWrapper canvasWrapper = new CanvasWrapper();
            canvasWrapper.Initialize(this._underpan.getObject());
            canvasWrapper.DrawColor(this._back_color);
            if (this._netxstep == 0 || this._netystep == 0) {
                this._underpan.Invalidate();
                return "";
            }
            int width = (this._underpan.getWidth() * this._netxstep) / this._xpoints;
            int height = (this._underpan.getHeight() * this._netystep) / this._ypoints;
            int width2 = this._underpan.getWidth() / 2;
            int height2 = this._underpan.getHeight() / 2;
            int height3 = this._underpan.getHeight();

            int d = width2 / width;
            int i = 0;
            int i2 = width2;
            int i3 = width2;
            while (i <= d) {
                canvasWrapper.DrawLine((float) i3, (float) 0, (float) i3, (float) height3, this._netcolor, (float) Common.DipToCurrent(1));
                canvasWrapper.DrawLine((float) i2, (float) 0, (float) i2, (float) height3, this._netcolor, (float) Common.DipToCurrent(1));
                i++;
                i2 -= width;
                i3 += width;
            }

            int width3 = this._underpan.getWidth();
            int d2 = height2 / height;
            int i6 = 0;
            int i7 = height2;
            int i8 = height2;
            while (i6 <= d2) {
                canvasWrapper.DrawLine((float) 0, (float) i8, (float) width3, (float) i8, this._netcolor, (float) Common.DipToCurrent(1));
                canvasWrapper.DrawLine((float) 0, (float) i7, (float) width3, (float) i7, this._netcolor, (float) Common.DipToCurrent(1));
                i6++;
                i7 -= height;
                i8 += height;
            }

            canvasWrapper.DrawLine(Common.Density, (float) height2, (float) this._underpan.getWidth(), (float) height2, this._netcolor, (float) Common.DipToCurrent(3));
            canvasWrapper.DrawLine((float) width2, Common.Density, (float) width2, (float) this._underpan.getHeight(), this._netcolor, (float) Common.DipToCurrent(3));
            this._underpan.Invalidate();
        }
        return "";
    }

    public String _drawpeak() throws Exception {
        this._draw_peak_flg = true;
        if (this._data_size > this._xpoints) {
            this._sb_buff_pos.setMax(this._data_size - this._xpoints);
            SeekBarWrapper seekBarWrapper = this._sb_buff_pos;
            seekBarWrapper.setEnabled(true);
            SeekBarWrapper seekBarWrapper2 = this._sb_buff_pos;
            seekBarWrapper2.setVisible(true);
        } else {
            this._sb_buff_pos.setMax(0);
            SeekBarWrapper seekBarWrapper3 = this._sb_buff_pos;
            seekBarWrapper3.setEnabled(false);
            SeekBarWrapper seekBarWrapper4 = this._sb_buff_pos;
            seekBarWrapper4.setVisible(false);
        }
        if (this._sb_buff_pos.getValue() > this._sb_buff_pos.getMax()) {
            this._sb_buff_pos.setValue(0);
        }
        CanvasWrapper canvasWrapper = this._dcanv;
        canvasWrapper.DrawColor(Colors.ARGB(0, 0, 0, 0));
        int[] buff_data = this._buff_data;
        int sb_buff_pos = this._sb_buff_pos.getValue();
        this._dpath.Initialize(Common.Density, _setypos(buff_data[sb_buff_pos]));
        int d = this._xpoints - 1;
        int i = 0;
        float x;
        CanvasWrapper.PathWrapper dpath = this._dpath;
        for (int i2 = 0; i2 <= d; i2++) {
            x = _setxpos(i2);
            dpath.MoveTo(x, _setypos(buff_data[sb_buff_pos + i]));
            dpath.LineTo(x, _setypos(buff_data[sb_buff_pos + i + 1]));
            i += 2;
        }
        int i3 = this._pen_color;
        this._dcanv.DrawPath(this._dpath.getObject(), i3, false, (float) Common.DipToCurrent(1));
        this._workpan.Invalidate();
        return "";
    }

    public int _getcur(int i) throws Exception {
        switch (i) {
            case 0:
                return (int) ((((double) (this._workpan.getHeight() - this._cursor[0].getTop())) - (((double) this._cursor[0].getHeight()) / 2.0d)) / ((double) this._ystep));
            case 1:
                return (int) ((((double) (this._workpan.getHeight() - this._cursor[1].getTop())) - (((double) this._cursor[1].getHeight()) / 2.0d)) / ((double) this._ystep));
            case 2:
                return (int) (((((double) this._cursor[2].getLeft()) + (((double) this._cursor[2].getWidth()) / 2.0d)) / ((double) this._xstep)) + ((double) this._sb_buff_pos.getValue()));
            default:
                return 0;
        }
    }

    public String _initialize(BA ba, PanelWrapper panelWrapper, int i, int i2, int i3, int i4) throws Exception {
        innerInitialize(ba);
        CanvasWrapper.BitmapWrapper bitmapWrapper = new CanvasWrapper.BitmapWrapper();
        this._net = 0;
        this._pen_color = -1;
        this._back_color = Colors.ARGB(0, 0, 0, 0);
        this._draw_del = 1;
        for (int i5 = 0; ((double) i5) <= 2.0d; i5 = (int) (((double) i5) + 1.0d)) {
            this._cursor_push = 0;
            this._cursor_move = 0;
            this._cursor[i5].Initialize(this.ba, "");
            this._cursor_line[i5].Initialize(this.ba, "");
            this._cursor_info[i5].Initialize(this.ba, "");
            LabelWrapper labelWrapper = this._cursor[i5];
            labelWrapper.setVisible(false);
            boolean[] zArr = this._cursorused;
            zArr[i5] = false;
        }
        this._underpan.Initialize(this.ba, "UnderPan");
        this._workpan.Initialize(this.ba, "WorkPan");
        this._frm.Initialize(this.ba, "");
        panelWrapper.AddView((View) this._underpan.getObject(), i, i2, i3, i4);
        PanelWrapper panelWrapper2 = this._underpan;
        panelWrapper2.setColor(Colors.Black);
        this._underpan.AddView((View) this._workpan.getObject(), 0, 0, this._underpan.getWidth(), this._underpan.getHeight());
        PanelWrapper panelWrapper3 = this._workpan;
        panelWrapper3.setColor(Colors.ARGB(0, 0, 0, 0));
        LabelWrapper labelWrapper2 = this._frm;
        labelWrapper2.setGravity(3);
        this._frm.setText("0");
        int DipToCurrent = Common.DipToCurrent(20);
        this._underpan.AddView((View) this._frm.getObject(), 0, 0, DipToCurrent, Common.DipToCurrent(20));
        this._sb_buff_pos.Initialize(this.ba, "sb_buff_pos");
        this._sb_buff_pos.setMax(0);
        this._sb_buff_pos.setValue(0);
        int DipToCurrent2 = Common.DipToCurrent(40);
        int width = this._workpan.getWidth();
        int DipToCurrent3 = width - Common.DipToCurrent(80);
        this._workpan.AddView((View) this._sb_buff_pos.getObject(), DipToCurrent2, 0, DipToCurrent3, Common.DipToCurrent(60));
        SeekBarWrapper seekBarWrapper = this._sb_buff_pos;
        seekBarWrapper.setEnabled(false);
        SeekBarWrapper seekBarWrapper2 = this._sb_buff_pos;
        seekBarWrapper2.setVisible(false);
        bitmapWrapper.Initialize(File.getDirAssets(), "left_cur.png");
        this._cursor[0].SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        bitmapWrapper.Initialize(File.getDirAssets(), "right_cur.png");
        this._cursor[1].SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        bitmapWrapper.Initialize(File.getDirAssets(), "dawn_cur.png");
        this._cursor[2].SetBackgroundImage((Bitmap) bitmapWrapper.getObject());
        int DipToCurrent4 = Common.DipToCurrent(40);
        this._workpan.AddView((View) this._cursor[0].getObject(), 0, 0, DipToCurrent4, Common.DipToCurrent(40));
        int width2 = this._workpan.getWidth();
        int DipToCurrent5 = Common.DipToCurrent(40);
        this._workpan.AddView((View) this._cursor[1].getObject(), width2 - Common.DipToCurrent(40), 0, DipToCurrent5, Common.DipToCurrent(40));
        int height = this._workpan.getHeight();
        int DipToCurrent6 = height - Common.DipToCurrent(40);
        int DipToCurrent7 = Common.DipToCurrent(40);
        this._workpan.AddView((View) this._cursor[2].getObject(), 0, DipToCurrent6, DipToCurrent7, Common.DipToCurrent(40));
        LabelWrapper labelWrapper3 = this._cursor_line[0];
        labelWrapper3.setColor(Colors.RGB(255, 0, 0));
        LabelWrapper labelWrapper4 = this._cursor_line[1];
        labelWrapper4.setColor(Colors.RGB(0, 0, 255));
        LabelWrapper labelWrapper5 = this._cursor_line[2];
        labelWrapper5.setColor(Colors.RGB(255, 0, 0));
        int width3 = this._workpan.getWidth() - this._cursor[0].getWidth();
        this._workpan.AddView((View) this._cursor_line[0].getObject(), this._cursor[0].getWidth(), (int) (((double) this._cursor[0].getTop()) + (((double) this._cursor[0].getHeight()) / 2.0d)), width3, Common.DipToCurrent(1));
        int width4 = this._workpan.getWidth() - this._cursor[1].getWidth();
        this._workpan.AddView((View) this._cursor_line[1].getObject(), 0, (int) (((double) this._cursor[1].getTop()) + (((double) this._cursor[1].getHeight()) / 2.0d)), width4, Common.DipToCurrent(1));
        this._workpan.AddView((View) this._cursor_line[2].getObject(), (int) (((double) this._cursor[2].getLeft()) + (((double) this._cursor[2].getWidth()) / 2.0d)), 0, Common.DipToCurrent(1), this._workpan.getHeight() - this._cursor[2].getHeight());
        LabelWrapper labelWrapper6 = this._cursor_info[0];
        labelWrapper6.setColor(Colors.ARGB(0, 255, 0, 0));
        LabelWrapper labelWrapper7 = this._cursor_info[1];
        labelWrapper7.setColor(Colors.ARGB(0, 0, 0, 255));
        LabelWrapper labelWrapper8 = this._cursor_info[2];
        labelWrapper8.setColor(Colors.ARGB(0, 255, 0, 0));
        int left = this._cursor_line[0].getLeft();
        int DipToCurrent8 = left + Common.DipToCurrent(20);
        int top = this._cursor_line[0].getTop();
        int DipToCurrent9 = top - Common.DipToCurrent(20);
        int DipToCurrent10 = Common.DipToCurrent(80);
        this._workpan.AddView((View) this._cursor_info[0].getObject(), DipToCurrent8, DipToCurrent9, DipToCurrent10, Common.DipToCurrent(20));
        int width5 = this._cursor_line[1].getWidth();
        int DipToCurrent11 = width5 - Common.DipToCurrent(90);
        int top2 = this._cursor_line[1].getTop();
        int DipToCurrent12 = top2 - Common.DipToCurrent(20);
        int DipToCurrent13 = Common.DipToCurrent(80);
        this._workpan.AddView((View) this._cursor_info[1].getObject(), DipToCurrent11, DipToCurrent12, DipToCurrent13, Common.DipToCurrent(20));
        int left2 = this._cursor_line[2].getLeft();
        int DipToCurrent14 = left2 + Common.DipToCurrent(10);
        int height2 = this._cursor_line[2].getHeight();
        int DipToCurrent15 = height2 - Common.DipToCurrent(40);
        int DipToCurrent16 = Common.DipToCurrent(80);
        this._workpan.AddView((View) this._cursor_info[2].getObject(), DipToCurrent14, DipToCurrent15, DipToCurrent16, Common.DipToCurrent(20));
        this._timer_curclose.Initialize(this.ba, "T_CurClose", 2000);
        Timer timer = this._timer_curclose;
        timer.setEnabled(true);
        this._dcanv.Initialize((View) this._workpan.getObject());
        return "";
    }

    public String _regevents(Object obj, String str, String str2) throws Exception {
        this._mainp = obj;
        this._event_cur = str;
        this._event_center = str2;
        return "";
    }

    public String _resize(int i, int i2, String str, String str2) throws Exception {
        this._workpan.setHeight(this._underpan.getHeight());
        this._dcanv.Initialize((View) this._workpan.getObject());
        this._xpoints = i;
        this._ypoints = i2;
        this._xstep = (float) (((double) this._workpan.getWidth()) / ((double) this._xpoints));
        this._ystep = (float) (((double) this._workpan.getHeight()) / ((double) this._ypoints));
        this._netxstep = (int) Double.parseDouble(str);
        this._netystep = (int) Double.parseDouble(str2);
        _drawnet();
        _setcur(0, this._cursor_pos[0]);
        _setcur(1, this._cursor_pos[1]);
        _setcur(2, this._cursor_pos[2]);
        return "";
    }

    public String _sb_buff_pos_valuechanged(int i, boolean z) throws Exception {
        _setcur(2, this._cursor_pos[2]);
        _cursors_visible(true);
        _cursors_visible(false);
        if (this._draw_peak_flg) {
            _drawpeak();
            return "";
        }
        _draw();
        return "";
    }

    public String _setcur(int i, int i2) throws Exception {
        switch (i) {
            case 0:
                this._cursor_pos[0] = i2;
                this._cursor[0].setTop((int) (((double) _setypos(i2)) - (((double) this._cursor[0].getHeight()) / 2.0d)));
                this._cursor_line[0].setTop((int) (((double) this._cursor[0].getTop()) + (((double) this._cursor[0].getHeight()) / 2.0d)));
                int top = this._cursor_line[0].getTop() - this._cursor_info[0].getHeight();
                if (top < 0) {
                    this._cursor_info[0].setTop(top + this._cursor_info[0].getHeight());
                    return "";
                }
                this._cursor_info[0].setTop(top);
                return "";
            case 1:
                this._cursor_pos[1] = i2;
                this._cursor[1].setTop((int) (((double) _setypos(i2)) - (((double) this._cursor[1].getHeight()) / 2.0d)));
                this._cursor_line[1].setTop((int) (((double) this._cursor[1].getTop()) + (((double) this._cursor[1].getHeight()) / 2.0d)));
                int top2 = this._cursor_line[1].getTop() - this._cursor_info[1].getHeight();
                if (top2 < 0) {
                    this._cursor_info[1].setTop(top2 + this._cursor_info[1].getHeight());
                    return "";
                }
                this._cursor_info[1].setTop(top2);
                return "";
            case 2:
                this._cursor_pos[2] = i2;
                this._cursor[2].setLeft((int) (((double) _setxpos(i2 - this._sb_buff_pos.getValue())) - (((double) this._cursor[2].getWidth()) / 2.0d)));
                this._cursor_line[2].setLeft((int) (((double) this._cursor[2].getLeft()) + (((double) this._cursor[2].getWidth()) / 2.0d)));
                int left = this._cursor_line[2].getLeft();
                if (this._cursor_info[2].getWidth() + left > this._workpan.getWidth()) {
                    this._cursor_info[2].setLeft(left - this._cursor_info[2].getWidth());
                    return "";
                }
                this._cursor_info[2].setLeft(left);
                return "";
            default:
                return "";
        }
    }

    public String _setcurtext(int i, String str) {
        this._cursor_info[i].setText(str);
        return "";
    }

    public float _setxpos(int i) {
        return ((float) i) * this._xstep;
    }

    public float _setxpos(float i) {
        return i * this._xstep;
    }

    public float _setypos(int i) {
        return ((float) this._workpan.getHeight()) - (((float) i) * this._ystep);
    }

    public float _setypos(float i) {
        return ((float) this._workpan.getHeight()) - (i * this._ystep);
    }

    public String _t_curclose_tick() throws Exception {
        Timer timer = this._timer_curclose;
        timer.setEnabled(false);
        for (int i = 0; ((double) i) <= 2.0d; i = (int) (((double) i) + 1.0d)) {
            LabelWrapper labelWrapper = this._cursor[i];
            labelWrapper.setVisible(false);
            LabelWrapper labelWrapper2 = this._cursor_line[i];
            labelWrapper2.setVisible(false);
            LabelWrapper labelWrapper3 = this._cursor_info[i];
            labelWrapper3.setVisible(false);
        }
        return "";
    }

    public String _workpan_touch(int i, float f, float f2) throws Exception {
        switch (i) {
            case 0:
                this._x_dawn = f;
                this._y_dawn = f2;
                if (!_cursor0_touch(f, f2) && !_cursor1_touch(f, f2) && !_cursor2_touch(f, f2)) {
                    Common.CallSubNew2(this.ba, this._mainp, this._event_center, 0);
                    break;
                } else {
                    return "";
                }
            case 1:
                if (this._cursor_move > 0) {
                    _cursors_visible(false);
                    this._curevt = this._cursor_move - 1;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                    this._cursor_move = 0;
                    return "";
                }
                if (this._cursor_push > 0) {
                    if (this._cursor_push < 3) {
                        if (f2 - this._y_dawn > ((float) Common.DipToCurrent(50))) {
                            this._curevt = 3;
                            Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                            this._cursor_push = 0;
                            return "";
                        }
                        if (this._y_dawn - f2 > ((float) Common.DipToCurrent(50))) {
                            this._curevt = 4;
                            Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                            this._cursor_push = 0;
                            return "";
                        }
                    } else {
                        if (f - this._x_dawn > ((float) Common.DipToCurrent(50))) {
                            this._curevt = 7;
                            Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                            this._cursor_push = 0;
                            return "";
                        }
                        if (this._x_dawn - f > ((float) Common.DipToCurrent(50))) {
                            this._curevt = 8;
                            Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                            this._cursor_push = 0;
                            return "";
                        }
                    }
                    _cursors_visible(true);
                    _cursors_visible(false);
                }
                if (this._cursor_push == 1) {
                    if (f2 > ((float) (this._cursor[0].getTop() + this._cursor[0].getHeight()))) {
                        _setcur(0, this._cursor_pos[0] - 1);
                        if (((double) this._cursor[0].getTop()) > ((double) this._workpan.getHeight()) - (((double) this._cursor[0].getHeight()) / 2.0d)) {
                            this._cursor[0].setTop((int) (((double) this._workpan.getHeight()) - (((double) this._cursor[0].getHeight()) / 2.0d)));
                        }
                    } else {
                        _setcur(0, this._cursor_pos[0] + 1);
                        if (((double) this._cursor[0].getTop()) < 0.0d - (((double) this._cursor[0].getHeight()) / 2.0d)) {
                            this._cursor[0].setTop((int) (0.0d - (((double) this._cursor[0].getHeight()) / 2.0d)));
                        }
                    }
                    this._cursor_push = 0;
                    this._curevt = 0;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                    return "";
                } else if (this._cursor_push == 2) {
                    if (f2 > ((float) (this._cursor[1].getTop() + this._cursor[1].getHeight()))) {
                        _setcur(1, this._cursor_pos[1] - 1);
                        if (((double) this._cursor[1].getTop()) > ((double) this._workpan.getHeight()) - (((double) this._cursor[1].getHeight()) / 2.0d)) {
                            this._cursor[1].setTop((int) (((double) this._workpan.getHeight()) - (((double) this._cursor[1].getHeight()) / 2.0d)));
                        }
                    } else {
                        _setcur(1, this._cursor_pos[1] + 1);
                        if (((double) this._cursor[1].getTop()) < 0.0d - (((double) this._cursor[1].getHeight()) / 2.0d)) {
                            this._cursor[1].setTop((int) (0.0d - (((double) this._cursor[1].getHeight()) / 2.0d)));
                        }
                    }
                    this._cursor_push = 0;
                    this._curevt = 1;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                    return "";
                } else if (this._cursor_push == 3) {
                    if (f > ((float) (this._cursor[2].getLeft() + this._cursor[2].getWidth()))) {
                        _setcur(2, this._cursor_pos[2] + 1);
                        if (((double) this._cursor[2].getLeft()) > ((double) this._workpan.getWidth()) - (((double) this._cursor[2].getWidth()) / 2.0d)) {
                            this._cursor[2].setLeft((int) (((double) this._workpan.getWidth()) - (((double) this._cursor[2].getWidth()) / 2.0d)));
                        }
                    } else {
                        _setcur(2, this._cursor_pos[2] - 1);
                        if (((double) this._cursor[2].getLeft()) < 0.0d - (((double) this._cursor[2].getWidth()) / 2.0d)) {
                            this._cursor[2].setLeft((int) (0.0d - (((double) this._cursor[2].getWidth()) / 2.0d)));
                        }
                    }
                    this._cursor_push = 0;
                    this._curevt = 2;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 1);
                    return "";
                }
                break;
            case 2:
                if (this._cursor_move == 1) {
                    this._cursor[0].setTop((int) (((double) f2) - (((double) this._cursor[0].getHeight()) / 2.0d)));
                    if (((double) this._cursor[0].getTop()) > ((double) this._workpan.getHeight()) - (((double) this._cursor[0].getHeight()) / 2.0d)) {
                        this._cursor[0].setTop((int) (((double) this._workpan.getHeight()) - (((double) this._cursor[0].getHeight()) / 2.0d)));
                    }
                    if (((double) this._cursor[0].getTop()) < 0.0d - (((double) this._cursor[0].getHeight()) / 2.0d)) {
                        this._cursor[0].setTop((int) (0.0d - (((double) this._cursor[0].getHeight()) / 2.0d)));
                    }
                    _setcur(0, _getcur(0));
                    this._curevt = 0;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 2);
                }
                if (this._cursor_move == 2) {
                    this._cursor[1].setTop((int) (((double) f2) - (((double) this._cursor[1].getHeight()) / 2.0d)));
                    if (((double) this._cursor[1].getTop()) > ((double) this._workpan.getHeight()) - (((double) this._cursor[1].getHeight()) / 2.0d)) {
                        this._cursor[1].setTop((int) (((double) this._workpan.getHeight()) - (((double) this._cursor[1].getHeight()) / 2.0d)));
                    }
                    if (((double) this._cursor[1].getTop()) < 0.0d - (((double) this._cursor[1].getHeight()) / 2.0d)) {
                        this._cursor[1].setTop((int) (0.0d - (((double) this._cursor[1].getHeight()) / 2.0d)));
                    }
                    _setcur(1, _getcur(1));
                    this._curevt = 1;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 2);
                }
                if (this._cursor_move == 3) {
                    this._cursor[2].setLeft((int) (((double) f) - (((double) this._cursor[2].getWidth()) / 2.0d)));
                    if (((double) this._cursor[2].getLeft()) < 0.0d - (((double) this._cursor[2].getWidth()) / 2.0d)) {
                        this._cursor[2].setLeft((int) (0.0d - (((double) this._cursor[2].getWidth()) / 2.0d)));
                    }
                    if (((double) this._cursor[2].getLeft()) > ((double) this._workpan.getWidth()) - (((double) this._cursor[2].getWidth()) / 2.0d)) {
                        this._cursor[2].setLeft((int) (((double) this._workpan.getWidth()) - (((double) this._cursor[2].getWidth()) / 2.0d)));
                    }
                    _setcur(2, _getcur(2));
                    this._curevt = 2;
                    Common.CallSubNew2(this.ba, this._mainp, this._event_cur, 2);
                    break;
                }
                break;
        }
        return "";
    }

    public Object callSub(String str, Object obj, Object[] objArr) throws Exception {
        this.ba.sharedProcessBA.sender = obj;
        return BA.SubDelegator.SubNotFound;
    }
}
