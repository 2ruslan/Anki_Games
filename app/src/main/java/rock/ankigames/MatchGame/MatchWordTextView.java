package rock.ankigames.MatchGame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import rock.ankigames.Anki.NoteInfo;
import rock.ankigames.R;

public class MatchWordTextView extends TextView {

    NoteInfo _note;
    boolean _isSelect;

    public MatchWordTextView(Context context) {
        super(context);
    }

    public MatchWordTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MatchWordTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setAnkiNote(NoteInfo n, boolean isShowBack){
        _note = n;
         this.setText(isShowBack ? _note.getAnswer() : _note.getQuestion() );
    }

    public void setIsSelect(boolean sel){
        _isSelect = sel;
        this.setBackground(getResources()
                .getDrawable( _isSelect ? R.drawable.match_back_select : R.drawable.match_back_normal));

    }
    public boolean getIsSelect(){
        return _isSelect;
    }


    public NoteInfo getNoteInfo(){
        return _note;
    }
}
